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

package com.liferay.portal.velocity;

import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.security.pacl.DoPrivileged;
import com.liferay.portal.kernel.security.pacl.NotPrivileged;
import com.liferay.portal.kernel.util.PropsKeys;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.velocity.VelocityContext;
import com.liferay.portal.kernel.velocity.VelocityEngine;
import com.liferay.portal.kernel.velocity.VelocityVariablesUtil;
import com.liferay.portal.template.TemplateControlContext;
import com.liferay.portal.util.ClassLoaderUtil;
import com.liferay.portal.util.PropsUtil;
import com.liferay.portal.util.PropsValues;

import java.io.Writer;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.apache.commons.collections.ExtendedProperties;
import org.apache.velocity.runtime.RuntimeConstants;
import org.apache.velocity.runtime.resource.ResourceManager;
import org.apache.velocity.runtime.resource.loader.StringResourceLoader;
import org.apache.velocity.runtime.resource.util.StringResourceRepository;

/**
 * @author Raymond Augé
 */
@DoPrivileged
public class VelocityEngineImpl implements VelocityEngine {

	public VelocityEngineImpl() {
	}

	public void clearClassLoader(ClassLoader classLoader) {
		_classLoaderVelocityContexts.remove(classLoader);
	}

	public void flushTemplate(String velocityTemplateId) {
		StringResourceRepository stringResourceRepository =
			StringResourceLoader.getRepository();

		if (stringResourceRepository != null) {
			stringResourceRepository.removeStringResource(velocityTemplateId);
		}

		LiferayResourceCacheUtil.remove(
			_getResourceCacheKey(velocityTemplateId));
	}

	@NotPrivileged
	public VelocityContext getEmptyContext() {
		return new VelocityContextImpl();
	}

	@NotPrivileged
	public VelocityContext getRestrictedToolsContext() {
		return _restrictedToolsContext;
	}

	@NotPrivileged
	public VelocityContext getStandardToolsContext() {
		return _standardToolsContext;
	}

	public TemplateControlContext getTemplateControlContext() {
		return _pacl.getTemplateControlContext();
	}

	@NotPrivileged
	public VelocityContext getWrappedClassLoaderToolsContext() {

		// This context will have all of its utilities initialized within the
		// class loader of the current thread

		ClassLoader contextClassLoader =
			ClassLoaderUtil.getContextClassLoader();

		VelocityContextImpl velocityContextImpl =
			_classLoaderVelocityContexts.get(contextClassLoader);

		if (velocityContextImpl == null) {
			velocityContextImpl = new VelocityContextImpl();

			VelocityVariablesUtil.insertHelperUtilities(
				velocityContextImpl, null);

			_classLoaderVelocityContexts.put(
				contextClassLoader, velocityContextImpl);
		}

		return velocityContextImpl;
	}

	@NotPrivileged
	public VelocityContext getWrappedRestrictedToolsContext() {
		return new VelocityContextImpl(
			_restrictedToolsContext.getWrappedVelocityContext());
	}

	@NotPrivileged
	public VelocityContext getWrappedStandardToolsContext() {
		return new VelocityContextImpl(
			_standardToolsContext.getWrappedVelocityContext());
	}

	public void init() throws Exception {
		if (_velocityEngine != null) {
			return;
		}

		_velocityEngine = new org.apache.velocity.app.VelocityEngine();

		LiferayResourceLoader.setVelocityResourceListeners(
			PropsValues.VELOCITY_ENGINE_RESOURCE_LISTENERS);

		ExtendedProperties extendedProperties = new FastExtendedProperties();

		extendedProperties.setProperty(
			org.apache.velocity.app.VelocityEngine.EVENTHANDLER_METHODEXCEPTION,
			LiferayMethodExceptionEventHandler.class.getName());

		extendedProperties.setProperty(
			RuntimeConstants.INTROSPECTOR_RESTRICT_CLASSES,
			StringUtil.merge(PropsValues.VELOCITY_ENGINE_RESTRICTED_CLASSES));

		extendedProperties.setProperty(
			RuntimeConstants.INTROSPECTOR_RESTRICT_PACKAGES,
			StringUtil.merge(PropsValues.VELOCITY_ENGINE_RESTRICTED_PACKAGES));

		extendedProperties.setProperty(_RESOURCE_LOADER, "string,servlet");

		extendedProperties.setProperty(
			"string." + _RESOURCE_LOADER + ".cache",
			String.valueOf(
				PropsValues.VELOCITY_ENGINE_RESOURCE_MANAGER_CACHE_ENABLED));

		extendedProperties.setProperty(
			"string." + _RESOURCE_LOADER + ".class",
			StringResourceLoader.class.getName());

		extendedProperties.setProperty(
			"string." + _RESOURCE_LOADER + ".repository.class",
			StringResourceRepositoryImpl.class.getName());

		extendedProperties.setProperty(
			"servlet." + _RESOURCE_LOADER + ".cache",
			String.valueOf(
				PropsValues.VELOCITY_ENGINE_RESOURCE_MANAGER_CACHE_ENABLED));

		extendedProperties.setProperty(
			"servlet." + _RESOURCE_LOADER + ".class",
			LiferayResourceLoader.class.getName());

		extendedProperties.setProperty(
			org.apache.velocity.app.VelocityEngine.RESOURCE_MANAGER_CLASS,
			PropsUtil.get(PropsKeys.VELOCITY_ENGINE_RESOURCE_MANAGER));

		extendedProperties.setProperty(
			org.apache.velocity.app.VelocityEngine.RESOURCE_MANAGER_CACHE_CLASS,
			PropsUtil.get(PropsKeys.VELOCITY_ENGINE_RESOURCE_MANAGER_CACHE));

		extendedProperties.setProperty(
			org.apache.velocity.app.VelocityEngine.VM_LIBRARY,
			PropsUtil.get(PropsKeys.VELOCITY_ENGINE_VELOCIMACRO_LIBRARY));

		extendedProperties.setProperty(
			org.apache.velocity.app.VelocityEngine.VM_LIBRARY_AUTORELOAD,
			String.valueOf(
				!PropsValues.VELOCITY_ENGINE_RESOURCE_MANAGER_CACHE_ENABLED));

		extendedProperties.setProperty(
			org.apache.velocity.app.VelocityEngine.
				VM_PERM_ALLOW_INLINE_REPLACE_GLOBAL,
			String.valueOf(
				!PropsValues.VELOCITY_ENGINE_RESOURCE_MANAGER_CACHE_ENABLED));

		extendedProperties.setProperty(
			org.apache.velocity.app.VelocityEngine.RUNTIME_LOG_LOGSYSTEM_CLASS,
			PropsUtil.get(PropsKeys.VELOCITY_ENGINE_LOGGER));

		extendedProperties.setProperty(
			org.apache.velocity.app.VelocityEngine.RUNTIME_LOG_LOGSYSTEM +
				".log4j.category",
			PropsUtil.get(PropsKeys.VELOCITY_ENGINE_LOGGER_CATEGORY));

		_velocityEngine.setExtendedProperties(extendedProperties);

		_velocityEngine.init();

		_restrictedToolsContext = new VelocityContextImpl();

		VelocityVariablesUtil.insertHelperUtilities(
			_restrictedToolsContext,
			PropsValues.JOURNAL_TEMPLATE_VELOCITY_RESTRICTED_VARIABLES);

		_standardToolsContext = new VelocityContextImpl();

		VelocityVariablesUtil.insertHelperUtilities(
			_standardToolsContext, null);
	}

	@NotPrivileged
	public boolean mergeTemplate(
			String velocityTemplateId, String velocityTemplateContent,
			VelocityContext velocityContext, Writer writer)
		throws Exception {

		if (Validator.isNotNull(velocityTemplateContent)) {
			LiferayResourceCacheUtil.remove(
				_getResourceCacheKey(velocityTemplateId));

			StringResourceRepository stringResourceRepository =
				StringResourceLoader.getRepository();

			stringResourceRepository.putStringResource(
				velocityTemplateId, velocityTemplateContent);

			if (_log.isDebugEnabled()) {
				_log.debug(
					"Added " + velocityTemplateId +
						" to the Velocity template repository");
			}
		}

		VelocityContextImpl velocityContextImpl =
			(VelocityContextImpl)velocityContext;

		return _velocityEngine.mergeTemplate(
			velocityTemplateId, StringPool.UTF8,
			velocityContextImpl.getWrappedVelocityContext(), writer);
	}

	@NotPrivileged
	public boolean mergeTemplate(
			String velocityTemplateId, VelocityContext velocityContext,
			Writer writer)
		throws Exception {

		return mergeTemplate(velocityTemplateId, null, velocityContext, writer);
	}

	@NotPrivileged
	public boolean resourceExists(String resource) {
		return _velocityEngine.resourceExists(resource);
	}

	private String _getResourceCacheKey(String velocityTemplateId) {
		return _RESOURCE_TEMPLATE_NAME_SPACE.concat(velocityTemplateId);
	}

	private static final String _RESOURCE_LOADER =
		org.apache.velocity.app.VelocityEngine.RESOURCE_LOADER;

	private static final String _RESOURCE_TEMPLATE_NAME_SPACE = String.valueOf(
		ResourceManager.RESOURCE_TEMPLATE);

	private static Log _log = LogFactoryUtil.getLog(VelocityEngineImpl.class);

	private static PACL _pacl = new NoPACL();

	private Map<ClassLoader, VelocityContextImpl> _classLoaderVelocityContexts =
		new ConcurrentHashMap<ClassLoader, VelocityContextImpl>();
	private VelocityContextImpl _restrictedToolsContext;
	private VelocityContextImpl _standardToolsContext;
	private org.apache.velocity.app.VelocityEngine _velocityEngine;

	private static class NoPACL implements PACL {

		public TemplateControlContext getTemplateControlContext() {
			ClassLoader contextClassLoader =
				ClassLoaderUtil.getContextClassLoader();

			return new TemplateControlContext(null, contextClassLoader);
		}

	}

	public static interface PACL {

		public TemplateControlContext getTemplateControlContext();

	}

}