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

package com.liferay.portal.bean;

import com.liferay.portal.kernel.bean.BeanLocator;
import com.liferay.portal.kernel.bean.BeanLocatorException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.security.pacl.DoPrivileged;
import com.liferay.portal.kernel.security.pacl.permission.PortalRuntimePermission;
import com.liferay.portal.kernel.util.ProxyUtil;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.springframework.context.ApplicationContext;

/**
 * @author Brian Wing Shun Chan
 * @author Miguel Pastor
 */
@DoPrivileged
public class BeanLocatorImpl implements BeanLocator {

	public static final String VELOCITY_SUFFIX = ".velocity";

	public BeanLocatorImpl(
		ClassLoader classLoader, ApplicationContext applicationContext) {

		_classLoader = classLoader;
		_applicationContext = applicationContext;
	}

	public ApplicationContext getApplicationContext() {
		return _applicationContext;
	}

	public ClassLoader getClassLoader() {
		PortalRuntimePermission.checkGetClassLoader(_paclServletContextName);

		return _classLoader;
	}

	public String[] getNames() {
		return _applicationContext.getBeanDefinitionNames();
	}

	public Class<?> getType(String name) {
		try {
			return _applicationContext.getType(name);
		}
		catch (Exception e) {
			throw new BeanLocatorException(e);
		}
	}

	public <T> Map<String, T> locate(Class<T> clazz) {
		try {
			return _applicationContext.getBeansOfType(clazz);
		}
		catch (Exception e) {
			throw new BeanLocatorException(e);
		}
	}

	public Object locate(String name) throws BeanLocatorException {
		try {
			return doLocate(name);
		}
		catch (SecurityException se) {
			throw se;
		}
		catch (Exception e) {
			throw new BeanLocatorException(e);
		}
	}

	public void setPACLServletContextName(String paclServletContextName) {
		_paclServletContextName = paclServletContextName;
	}

	protected Object doLocate(String name) throws Exception {
		if (_log.isDebugEnabled()) {
			_log.debug("Locating " + name);
		}

		if (name.equals("portletClassLoader")) {
			PortalRuntimePermission.checkGetClassLoader(
				_paclServletContextName);
		}

		if (name.endsWith(VELOCITY_SUFFIX)) {
			Object velocityBean = _velocityBeans.get(name);

			if (velocityBean == null) {
				String originalName = name.substring(
					0, name.length() - VELOCITY_SUFFIX.length());

				Object bean = _applicationContext.getBean(originalName);

				velocityBean = ProxyUtil.newProxyInstance(
					_classLoader, getInterfaces(bean),
					new VelocityBeanHandler(bean, _classLoader));

				_velocityBeans.put(name, velocityBean);
			}

			return velocityBean;
		}

		return _applicationContext.getBean(name);
	}

	/**
	 * @see {@link
	 *      com.liferay.portal.security.lang.DoPrivilegedFactory#_getInterfaces(
	 *      List, Class)}
	 */
	protected void getInterfaces(
		List<Class<?>> interfaceClasses, Class<?> clazz) {

		for (Class<?> interfaceClass : clazz.getInterfaces()) {
			try {
				interfaceClasses.add(
					_classLoader.loadClass(interfaceClass.getName()));
			}
			catch (ClassNotFoundException cnfe) {
			}
		}
	}

	/**
	 * @see {@link
	 *      com.liferay.portal.security.lang.DoPrivilegedFactory#_getInterfaces(
	 *      Object)}
	 */
	protected Class<?>[] getInterfaces(Object object) {
		List<Class<?>> interfaceClasses = new ArrayList<Class<?>>();

		Class<?> clazz = object.getClass();

		getInterfaces(interfaceClasses, clazz);

		Class<?> superClass = clazz.getSuperclass();

		while (superClass != null) {
			getInterfaces(interfaceClasses, superClass);

			superClass = superClass.getSuperclass();
		}

		return interfaceClasses.toArray(new Class<?>[interfaceClasses.size()]);
	}

	private static Log _log = LogFactoryUtil.getLog(BeanLocatorImpl.class);

	private ApplicationContext _applicationContext;
	private ClassLoader _classLoader;
	private String _paclServletContextName;
	private Map<String, Object> _velocityBeans =
		new ConcurrentHashMap<String, Object>();

}