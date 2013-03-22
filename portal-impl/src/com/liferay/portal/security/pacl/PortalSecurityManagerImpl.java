/**
 * Copyright (c) 2000-2012 Liferay, Inc. All rights reserved.
 *
 * This library is free software; you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation; either version 2.1 of the License, or (at your option)
 * any later version.
 *
 * This library is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
 */

package com.liferay.portal.security.pacl;

import com.liferay.portal.bean.BeanLocatorImpl;
import com.liferay.portal.bean.VelocityBeanHandler;
import com.liferay.portal.dao.jdbc.DataSourceFactoryImpl;
import com.liferay.portal.deploy.hot.HotDeployImpl;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.portlet.PortletClassLoaderUtil;
import com.liferay.portal.kernel.security.pacl.PACLConstants;
import com.liferay.portal.kernel.security.pacl.permission.PortalFilePermission;
import com.liferay.portal.kernel.security.pacl.permission.PortalHookPermission;
import com.liferay.portal.kernel.security.pacl.permission.PortalMessageBusPermission;
import com.liferay.portal.kernel.security.pacl.permission.PortalRuntimePermission;
import com.liferay.portal.kernel.security.pacl.permission.PortalServicePermission;
import com.liferay.portal.kernel.security.pacl.permission.PortalSocketPermission;
import com.liferay.portal.kernel.servlet.taglib.FileAvailabilityUtil;
import com.liferay.portal.kernel.util.AggregateClassLoader;
import com.liferay.portal.kernel.util.AutoResetThreadLocal;
import com.liferay.portal.kernel.util.CentralizedThreadLocal;
import com.liferay.portal.kernel.util.JavaDetector;
import com.liferay.portal.kernel.util.PreloadClassLoader;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.security.lang.DoPrivilegedFactory;
import com.liferay.portal.security.lang.DoPrivilegedUtil;
import com.liferay.portal.security.lang.PortalSecurityManager;
import com.liferay.portal.security.pacl.dao.jdbc.PACLDataSource;
import com.liferay.portal.security.pacl.jndi.PACLInitialContextFactoryBuilder;
import com.liferay.portal.security.pacl.servlet.PACLRequestDispatcherWrapper;
import com.liferay.portal.servlet.DirectRequestDispatcherFactoryImpl;
import com.liferay.portal.spring.context.PortletApplicationContext;
import com.liferay.portal.spring.util.FilterClassLoader;
import com.liferay.portal.util.ClassLoaderUtil;
import com.liferay.portal.util.PropsValues;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Member;
import java.lang.reflect.Method;
import java.lang.reflect.ReflectPermission;

import java.net.SocketPermission;

import java.security.AccessController;
import java.security.Permission;
import java.security.Policy;
import java.security.PrivilegedAction;
import java.security.PrivilegedExceptionAction;

import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import javax.naming.spi.InitialContextFactoryBuilder;
import javax.naming.spi.NamingManager;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;

import javax.sql.DataSource;

import sun.reflect.Reflection;

import sun.security.util.SecurityConstants;

/**
 * This is the portal's implementation of a security manager. The goal is to
 * protect portal resources from plugins and prevent security issues by forcing
 * plugin developers to openly declare their requirements. Where a
 * SecurityManager exists, we set that as the parent and delegate to it as a
 * fallback. This class will not delegate checks to super when there is no
 * parent so as to avoid forcing the need for a default policy.
 *
 * @author Brian Wing Shun Chan
 * @author Raymond Augé
 * @author Zsolt Berentey
 */
public class PortalSecurityManagerImpl extends SecurityManager
	implements PortalSecurityManager {

	public PortalSecurityManagerImpl() {
		SecurityManager securityManager = System.getSecurityManager();

		initClasses();

		try {
			Policy policy = null;

			if (securityManager != null) {
				policy = Policy.getPolicy();
			}

			_policy = new PortalPolicy(policy);

			Policy.setPolicy(_policy);

			_policy.refresh();
		}
		catch (Exception e) {
			if (_log.isInfoEnabled()) {
				_log.info(
					"Unable to override the original Java security policy " +
						"because sufficient privileges are not granted to " +
							"Liferay. PACL is not enabled.");
			}

			if (_log.isWarnEnabled()) {
				_log.warn(e, e);
			}

			return;
		}

		try {
			initInitialContextFactoryBuilder();
		}
		catch (Exception e) {
			if (_log.isInfoEnabled()) {
				_log.info(
					"Unable to override the initial context factory builder " +
						"because one already exists. JNDI security is not " +
							"enabled.");
			}

			if (_log.isWarnEnabled()) {
				_log.warn(e, e);
			}
		}

		try {
			initPACLImpls();
		}
		catch (Exception e) {
			if (_log.isInfoEnabled()) {
				_log.info(
					"Unable to initialize portal runtime permissions. Some " +
						"portal runtime security is not enabled.");
			}

			if (_log.isWarnEnabled()) {
				_log.warn(e, e);
			}
		}
	}

	@Override
	public void checkMemberAccess(Class<?> clazz, int accessibility) {
		if (clazz == null) {
			throw new NullPointerException("Class cannot be null");
		}

		ClassLoader clazzClassLoader = clazz.getClassLoader();

		if (accessibility == Member.PUBLIC) {
			_checkMemberAccessClassLoader.set(clazzClassLoader);

			return;
		}

		Class<?> stack[] = getClassContext();

		// Stack depth of 4 should be the caller of one of the methods in
		// java.lang.Class that invoked the checkMember access. The stack should
		// look like:

		// [3] someCaller
		// [2] java.lang.Class.someReflectionAPI
		// [1] java.lang.Class.checkMemberAccess
		// [0] SecurityManager.checkMemberAccess

		if ((stack.length < 4) ||
			(stack[3].getClassLoader() != clazzClassLoader)) {

			_checkMemberAccessClassLoader.set(null);

			checkPermission(SecurityConstants.CHECK_MEMBER_ACCESS_PERMISSION);
		}
		else {
			_checkMemberAccessClassLoader.set(clazzClassLoader);
		}
	}

	@Override
	public void checkPermission(Permission permission) {
		boolean clearCheckMemberAccessClassLoader = true;

		try {
			String name = permission.getName();

			if ((permission instanceof ReflectPermission) &&
				name.equals("suppressAccessChecks") &&
				(_checkMemberAccessClassLoader.get() != null)) {

				// The "suppressAccessChecks" permission is particularly
				// difficult to handle because the Java API does not have a
				// mechanism to get the class on which the accessibility is
				// being suppressed. This makes it difficult to differentiate
				// between code changing its own accessibility (allowed) from
				// accessibility changes on foreign code (not allowed). However,
				// there is a common programming pattern we can take advantage
				// of to short circuit the problem.

				// T t = clazz.getDeclared*(..);

				// t.setAccessible(true);

				// Call getDeclared* and immediately change the accessibility of
				// it. The getDeclared* results in a call to
				// SecurityManager#checkMemberAccess(Class, int). In the case
				// where the target class and the caller class are from the same
				// class loader, the checking is short circuited with a
				// successful result. If this short circuit happens in our
				// implementation, we will store the class loader of the target
				// class, and on the very next permission check, if the check is
				// for "suppressAccessChecks" and the classLoader of the caller
				// is the same as the stored class loader from the previous
				// check, we will also allow the check to succeed. In all cases,
				// the thread local is purged to avoid later erroneous
				// successes.

				Class<?> stack[] = getClassContext();

				// [2] someCaller
				// [1] java.lang.reflect.AccessibleObject
				// [0] SecurityManager.checkMemberAccess

				if (_checkMemberAccessClassLoader.get() ==
						stack[2].getClassLoader()) {

					// The clearCheckMemberAccessClassLoader variable is set to
					// false to support the calls to getDeclared*s that return
					// an array.

					// T[] t = clazz.getDeclared*s(..);

					// We will hang onto the class loader as long as subsequent
					// checks are for "suppressAccessChecks" and the class
					// loader still matches.

					clearCheckMemberAccessClassLoader = false;

					return;
				}
			}

			AccessController.checkPermission(permission);
		}
		finally {
			if (clearCheckMemberAccessClassLoader) {
				_checkMemberAccessClassLoader.set(null);
			}
		}
	}

	public Policy getPolicy() {
		return _policy;
	}

	protected void initClass(Class<?> clazz) {
		_log.debug(
			"Loading " + clazz.getName() + " and " +
				clazz.getDeclaredClasses().length + " inner classes");
	}

	protected void initClasses() {

		// Load dependent classes to prevent ClassCircularityError

		initClass(CentralizedThreadLocal.class);
		initClass(FileAvailabilityUtil.class);
		initClass(PortalHookPermission.class);
	}

	protected void initInitialContextFactoryBuilder() throws Exception {
		if (!NamingManager.hasInitialContextFactoryBuilder()) {
			PACLInitialContextFactoryBuilder paclInitialContextFactoryBuilder =
				new PACLInitialContextFactoryBuilder();

			if (_log.isInfoEnabled()) {
				_log.info("Overriding the initial context factory builder");
			}

			NamingManager.setInitialContextFactoryBuilder(
				paclInitialContextFactoryBuilder);
		}

		Class<?> clazz = NamingManager.class;

		String fieldName = "initctx_factory_builder";

		if (JavaDetector.isIBM()) {
			fieldName = "icfb";
		}

		Field field = clazz.getDeclaredField(fieldName);

		field.setAccessible(true);

		InitialContextFactoryBuilder initialContextFactoryBuilder =
			(InitialContextFactoryBuilder)field.get(null);

		if (initialContextFactoryBuilder
				instanceof PACLInitialContextFactoryBuilder) {

			return;
		}

		PACLInitialContextFactoryBuilder paclInitialContextFactoryBuilder =
			new PACLInitialContextFactoryBuilder();

		paclInitialContextFactoryBuilder.setInitialContextFactoryBuilder(
			initialContextFactoryBuilder);

		field.set(null, paclInitialContextFactoryBuilder);

		if (_log.isInfoEnabled()) {
			_log.info(
				"Overriding the initial context factory builder using " +
					"reflection");
		}
	}

	protected void initPACLImpl(Class<?> clazz, Object pacl) throws Exception {
		Field field = clazz.getDeclaredField("_pacl");

		synchronized (field) {
			field.setAccessible(true);

			field.set(null, pacl);
		}
	}

	protected void initPACLImpls() throws Exception {
		initPACLImpl(BeanLocatorImpl.class, new DoBeanLocatorImplPACL());
		initPACLImpl(
			DataSourceFactoryImpl.class, new DoDataSourceFactoryImplPACL());
		initPACLImpl(
			DirectRequestDispatcherFactoryImpl.class,
			new DoDirectRequestDispatcherFactoryImplPACL());
		initPACLImpl(DoPrivilegedUtil.class, new DoDoPrivilegedPACL());
		initPACLImpl(HotDeployImpl.class, new DoHotDeployImplPACL());
		initPACLImpl(
			PortalFilePermission.class, new DoPortalFilePermissionPACL());
		initPACLImpl(
			PortalHookPermission.class, new DoPortalHookPermissionPACL());
		initPACLImpl(
			PortalMessageBusPermission.class,
			new DoPortalMessageBusPermissionPACL());
		initPACLImpl(
			PortalRuntimePermission.class, new DoPortalRuntimePermissionPACL());
		initPACLImpl(
			PortalServicePermission.class, new DoPortalServicePermissionPACL());
		initPACLImpl(
			PortalSocketPermission.class, new DoPortalSocketPermissionPACL());
		initPACLImpl(
			PortletApplicationContext.class,
			new DoPortletApplicationContextPACL());
	}

	private static Log _log = LogFactoryUtil.getLog(
		PortalSecurityManagerImpl.class.getName());

	private static ThreadLocal<ClassLoader> _checkMemberAccessClassLoader =
		new AutoResetThreadLocal<ClassLoader>(
			PortalSecurityManagerImpl.class +
				"._checkMembersAccessClassLoader");

	private Policy _policy;

	private static class DoBeanLocatorImplPACL implements BeanLocatorImpl.PACL {

		public InvocationHandler getInvocationHandler(
			Object bean, ClassLoader classLoader) {

			InvocationHandler invocationHandler = new VelocityBeanHandler(
				bean, classLoader);

			return new PACLInvocationHandler(invocationHandler);
		}

	}

	private static class DoDataSourceFactoryImplPACL
		implements DataSourceFactoryImpl.PACL {

		public DataSource getDataSource(DataSource dataSource) {
			return new PACLDataSource(dataSource);
		}

	}

	private static class DoDirectRequestDispatcherFactoryImplPACL
		implements DirectRequestDispatcherFactoryImpl.PACL {

		public RequestDispatcher getRequestDispatcher(
			ServletContext servletContext,
			RequestDispatcher requestDispatcher) {

			if (PACLPolicyManager.isActive()) {
				requestDispatcher = new PACLRequestDispatcherWrapper(
					servletContext, requestDispatcher);
			}

			return requestDispatcher;
		}

	}

	private static class DoDoPrivilegedPACL implements DoPrivilegedUtil.PACL {

		public <T> T wrap(PrivilegedAction<T> privilegedAction) {
			if (!PACLPolicyManager.isActive()) {
				return privilegedAction.run();
			}

			return DoPrivilegedFactory.wrap(
				AccessController.doPrivileged(privilegedAction));
		}

		public <T> T wrap(
				PrivilegedExceptionAction<T> privilegedExceptionAction)
			throws Exception {

			if (!PACLPolicyManager.isActive()) {
				return privilegedExceptionAction.run();
			}

			return DoPrivilegedFactory.wrap(
				AccessController.doPrivileged(privilegedExceptionAction));
		}

		public <T> T wrap(T t) {
			return DoPrivilegedFactory.wrap(t);
		}

		public <T> T wrap(T t, boolean checkActive) {
			if (!PACLPolicyManager.isActive()) {
				return t;
			}

			return DoPrivilegedFactory.wrap(t);
		}

	}

	private static class DoHotDeployImplPACL implements HotDeployImpl.PACL {

		public void initPolicy(
			String servletContextName, ClassLoader classLoader,
			Properties properties) {

			PACLPolicy paclPolicy = PACLPolicyManager.buildPACLPolicy(
				servletContextName, classLoader, properties);

			PACLPolicyManager.register(classLoader, paclPolicy);
		}

		public void unregister(ClassLoader classLoader) {
			PACLPolicyManager.unregister(classLoader);
		}

	}

	private static class DoPortalFilePermissionPACL
		implements PortalFilePermission.PACL {

		public void checkCopy(String source, String destination) {
			SecurityManager securityManager = System.getSecurityManager();

			if (securityManager == null) {
				return;
			}

			if (Validator.isNotNull(source)) {
				securityManager.checkRead(source);
			}

			if (Validator.isNull(destination)) {
				return;
			}

			securityManager.checkWrite(destination);
		}

		public void checkDelete(String path) {
			SecurityManager securityManager = System.getSecurityManager();

			if (securityManager == null) {
				return;
			}

			if (Validator.isNull(path)) {
				return;
			}

			securityManager.checkDelete(path);
		}

		public void checkMove(String source, String destination) {
			SecurityManager securityManager = System.getSecurityManager();

			if (securityManager == null) {
				return;
			}

			if (Validator.isNotNull(source)) {
				securityManager.checkRead(source);
				securityManager.checkDelete(source);
			}

			if (Validator.isNull(destination)) {
				return;
			}

			securityManager.checkWrite(destination);
			securityManager.checkDelete(destination);
		}

		public void checkRead(String path) {
			SecurityManager securityManager = System.getSecurityManager();

			if (securityManager == null) {
				return;
			}

			if (Validator.isNull(path)) {
				return;
			}

			securityManager.checkRead(path);
		}

		public void checkWrite(String path) {
			SecurityManager securityManager = System.getSecurityManager();

			if (securityManager == null) {
				return;
			}

			if (Validator.isNull(path)) {
				return;
			}

			securityManager.checkWrite(path);
		}

	}

	private static class DoPortalHookPermissionPACL
		implements PortalHookPermission.PACL {

		public void checkPermission(
			String name, ClassLoader portletClassLoader, Object subject) {

			PACLPolicy paclPolicy = PACLPolicyManager.getPACLPolicy(
				portletClassLoader);

			if (paclPolicy == null) {
				return;
			}

			Permission permission = new PortalHookPermission(
				name, portletClassLoader, subject);

			if (!paclPolicy.implies(permission)) {
				throw new SecurityException(permission.toString());
			}
		}

	}

	private static class DoPortalMessageBusPermissionPACL
		implements PortalMessageBusPermission.PACL {

		public void checkListen(String destinationName) {
			SecurityManager securityManager = System.getSecurityManager();

			if (securityManager == null) {
				return;
			}

			Permission permission = new PortalMessageBusPermission(
				PACLConstants.PORTAL_MESSAGE_BUS_PERMISSION_LISTEN,
				destinationName);

			securityManager.checkPermission(permission);
		}

		public void checkSend(String destinationName) {
			SecurityManager securityManager = System.getSecurityManager();

			if (securityManager == null) {
				return;
			}

			Permission permission = new PortalMessageBusPermission(
				PACLConstants.PORTAL_MESSAGE_BUS_PERMISSION_SEND,
				destinationName);

			securityManager.checkPermission(permission);
		}

	}

	private static class DoPortalRuntimePermissionPACL
		implements PortalRuntimePermission.PACL {

		public void checkExpandoBridge(String className) {
			SecurityManager securityManager = System.getSecurityManager();

			if (securityManager == null) {
				return;
			}

			Permission permission = new PortalRuntimePermission(
				PACLConstants.PORTAL_RUNTIME_PERMISSION_EXPANDO_BRIDGE, null,
				className);

			securityManager.checkPermission(permission);
		}

		public void checkGetBeanProperty(
			String servletContextName, Class<?> clazz, String property) {

			SecurityManager securityManager = System.getSecurityManager();

			if (securityManager == null) {
				return;
			}

			Class<?> callerClass = Reflection.getCallerClass(5);

			if (clazz == callerClass) {

				// The bean is calling its own getBean method

				return;
			}

			clazz = PACLUtil.getClass(clazz);

			Permission permission = new PortalRuntimePermission(
				PACLConstants.PORTAL_RUNTIME_PERMISSION_GET_BEAN_PROPERTY,
				servletContextName, clazz, property);

			securityManager.checkPermission(permission);
		}

		public void checkGetClassLoader(String classLoaderReferenceId) {
			SecurityManager securityManager = System.getSecurityManager();

			if (securityManager == null) {
				return;
			}

			Permission permission = new PortalRuntimePermission(
				PACLConstants.PORTAL_RUNTIME_PERMISSION_GET_CLASSLOADER, null,
				classLoaderReferenceId);

			securityManager.checkPermission(permission);
		}

		public void checkPortletBagPool(String portletId) {
			SecurityManager securityManager = System.getSecurityManager();

			if (securityManager == null) {
				return;
			}

			Permission permission = new PortalRuntimePermission(
				PACLConstants.PORTAL_RUNTIME_PERMISSION_PORTLET_BAG_POOL, null,
				portletId);

			securityManager.checkPermission(permission);
		}

		public void checkSearchEngine(String searchEngineId) {
			SecurityManager securityManager = System.getSecurityManager();

			if (securityManager == null) {
				return;
			}

			Permission permission = new PortalRuntimePermission(
				PACLConstants.PORTAL_RUNTIME_PERMISSION_SEARCH_ENGINE, null,
				searchEngineId);

			securityManager.checkPermission(permission);
		}

		public void checkSetBeanProperty(
			String servletContextName, Class<?> clazz, String property) {

			SecurityManager securityManager = System.getSecurityManager();

			if (securityManager == null) {
				return;
			}

			clazz = PACLUtil.getClass(clazz);

			Permission permission = new PortalRuntimePermission(
				PACLConstants.PORTAL_RUNTIME_PERMISSION_SET_BEAN_PROPERTY,
				servletContextName, clazz, property);

			securityManager.checkPermission(permission);
		}

		public void checkThreadPoolExecutor(String name) {
			SecurityManager securityManager = System.getSecurityManager();

			if (securityManager == null) {
				return;
			}

			Permission permission = new PortalRuntimePermission(
				PACLConstants.PORTAL_RUNTIME_PERMISSION_THREAD_POOL_EXECUTOR,
				null, name);

			securityManager.checkPermission(permission);
		}

	}

	private static class DoPortalServicePermissionPACL
		implements PortalServicePermission.PACL {

		public void checkDynamicQuery(Class<?> implClass) {
			SecurityManager securityManager = System.getSecurityManager();

			if (securityManager == null) {
				return;
			}

			Permission permission = new PortalServicePermission(
				PACLConstants.PORTAL_SERVICE_PERMISSION_DYNAMIC_QUERY,
				implClass, null);

			securityManager.checkPermission(permission);
		}

		public void checkService(
			Object object, Method method, Object[] arguments) {

			SecurityManager securityManager = System.getSecurityManager();

			if (securityManager == null) {
				return;
			}

			PortalServicePermission portalServicePermission =
				new PortalServicePermission(
					PACLConstants.PORTAL_SERVICE_PERMISSION_SERVICE, object,
					method, arguments);

			securityManager.checkPermission(portalServicePermission);
		}

	}

	private static class DoPortalSocketPermissionPACL
		implements PortalSocketPermission.PACL {

		public void checkPermission(String host, String action) {
			SecurityManager securityManager = System.getSecurityManager();

			if (securityManager == null) {
				return;
			}

			Permission permission = new SocketPermission(host, action);

			securityManager.checkPermission(permission);
		}

	}

	private static class DoPortletApplicationContextPACL
		implements PortletApplicationContext.PACL {

		public ClassLoader getBeanClassLoader() {
			if (PACLPolicyManager.isActive()) {
				return DoPrivilegedFactory.wrap(
					new PreloadClassLoader(
						PortletClassLoaderUtil.getClassLoader(), _classes));
			}

			ClassLoader beanClassLoader =
				AggregateClassLoader.getAggregateClassLoader(
					new ClassLoader[] {
						PortletClassLoaderUtil.getClassLoader(),
						ClassLoaderUtil.getPortalClassLoader()
					});

			return new FilterClassLoader(beanClassLoader);
		}

		private static Map<String, Class<?>> _classes =
			new HashMap<String, Class<?>>();

		static {
			for (String className :
					PropsValues.
						PORTAL_SECURITY_MANAGER_PRELOAD_CLASSLOADER_CLASSES) {

				Class<?> clazz = null;

				try {
					clazz = Class.forName(className);
				}
				catch (ClassNotFoundException cnfe) {
					_log.error(cnfe, cnfe);
				}

				_classes.put(clazz.getName(), clazz);
			}
		}

	}

}