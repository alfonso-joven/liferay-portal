/**
 * Copyright (c) 2000-2013 Liferay, Inc. All rights reserved.
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

package com.liferay.portal.apache.bridges.struts;

import com.liferay.portal.kernel.util.ContextPathUtil;

import java.io.InputStream;

import java.net.MalformedURLException;
import java.net.URL;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Enumeration;
import java.util.EventListener;
import java.util.Map;
import java.util.Set;

import javax.servlet.Filter;
import javax.servlet.FilterRegistration.Dynamic;
import javax.servlet.FilterRegistration;
import javax.servlet.RequestDispatcher;
import javax.servlet.Servlet;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletRegistration;
import javax.servlet.SessionCookieConfig;
import javax.servlet.SessionTrackingMode;
import javax.servlet.descriptor.JspConfigDescriptor;

/**
 * @author Michael Young
 */
public class LiferayServletContext implements ServletContext {

	public LiferayServletContext(ServletContext servletContext) {
		_servletContext = servletContext;
	}

	public Dynamic addFilter(String filterName, Class<? extends Filter> clazz) {
		return _servletContext.addFilter(filterName, clazz);
	}

	public Dynamic addFilter(String filterName, Filter filter) {
		return _servletContext.addFilter(filterName, filter);
	}

	public Dynamic addFilter(String filterName, String className) {
		return _servletContext.addFilter(filterName, className);
	}

	public void addListener(Class<? extends EventListener> clazz) {
		_servletContext.addListener(clazz);
	}

	public void addListener(String className) {
		_servletContext.addListener(className);
	}

	public <T extends EventListener> void addListener(T eventListener) {
		_servletContext.addListener(eventListener);
	}

	public ServletRegistration.Dynamic addServlet(
		String servletName, Class<? extends Servlet> clazz) {

		return _servletContext.addServlet(servletName, clazz);
	}

	public ServletRegistration.Dynamic addServlet(
		String servletName, Servlet servlet) {

		return _servletContext.addServlet(servletName, servlet);
	}

	public ServletRegistration.Dynamic addServlet(
		String servletName, String className) {

		return _servletContext.addServlet(servletName, className);
	}

	public <T extends Filter> T createFilter(Class<T> clazz)
		throws ServletException {

		return _servletContext.createFilter(clazz);
	}

	public <T extends EventListener> T createListener(Class<T> clazz)
		throws ServletException {

		return _servletContext.createListener(clazz);
	}

	public <T extends Servlet> T createServlet(Class<T> clazz)
		throws ServletException {

		return _servletContext.createServlet(clazz);
	}

	public void declareRoles(String... roleNames) {
		_servletContext.declareRoles(roleNames);
	}

	public Object getAttribute(String name) {
		return _servletContext.getAttribute(name);
	}

	public Enumeration<String> getAttributeNames() {
		return _servletContext.getAttributeNames();
	}

	public ClassLoader getClassLoader() {
		return _servletContext.getClassLoader();
	}

	public ServletContext getContext(String uriPath) {
		ServletContext servletContext = _servletContext.getContext(uriPath);

		if (servletContext == _servletContext) {
			return this;
		}
		else {
			return servletContext;
		}
	}

	public String getContextPath() {
		return ContextPathUtil.getContextPath(_servletContext);
	}

	public Set<SessionTrackingMode> getDefaultSessionTrackingModes() {
		return _servletContext.getDefaultSessionTrackingModes();
	}

	public int getEffectiveMajorVersion() {
		return _servletContext.getEffectiveMajorVersion();
	}

	public int getEffectiveMinorVersion() {
		return _servletContext.getEffectiveMinorVersion();
	}

	public Set<SessionTrackingMode> getEffectiveSessionTrackingModes() {
		return _servletContext.getEffectiveSessionTrackingModes();
	}

	public FilterRegistration getFilterRegistration(String filterName) {
		return _servletContext.getFilterRegistration(filterName);
	}

	public Map<String, ? extends FilterRegistration> getFilterRegistrations() {
		return _servletContext.getFilterRegistrations();
	}

	public String getInitParameter(String name) {
		return _servletContext.getInitParameter(name);
	}

	public Enumeration<String> getInitParameterNames() {
		return _servletContext.getInitParameterNames();
	}

	public JspConfigDescriptor getJspConfigDescriptor() {
		return _servletContext.getJspConfigDescriptor();
	}

	public int getMajorVersion() {
		return _servletContext.getMajorVersion();
	}

	public String getMimeType(String file) {
		return _servletContext.getMimeType(file);
	}

	public int getMinorVersion() {
		return _servletContext.getMinorVersion();
	}

	public RequestDispatcher getNamedDispatcher(String name) {
		RequestDispatcher requestDispatcher =
			_servletContext.getNamedDispatcher(name);

		if (requestDispatcher != null) {
			requestDispatcher = new LiferayRequestDispatcher(
				requestDispatcher, name);
		}

		return requestDispatcher;
	}

	public String getRealPath(String path) {
		return _servletContext.getRealPath(path);
	}

	public RequestDispatcher getRequestDispatcher(String path) {
		RequestDispatcher requestDispatcher =
			_servletContext.getRequestDispatcher(path);

		if (requestDispatcher != null) {
			requestDispatcher = new LiferayRequestDispatcher(
				requestDispatcher, path);
		}

		return requestDispatcher;
	}

	public URL getResource(String path) throws MalformedURLException {
		return _servletContext.getResource(path);
	}

	public InputStream getResourceAsStream(String path) {
		return _servletContext.getResourceAsStream(path);
	}

	public Set<String> getResourcePaths(String path) {
		return _servletContext.getResourcePaths(path);
	}

	public String getServerInfo() {
		return _servletContext.getServerInfo();
	}

	public Servlet getServlet(String name) {
		return null;
	}

	public String getServletContextName() {
		return _servletContext.getServletContextName();
	}

	public Enumeration<String> getServletNames() {
		return Collections.enumeration(new ArrayList<String>());
	}

	public ServletRegistration getServletRegistration(String servletName) {
		return _servletContext.getServletRegistration(servletName);
	}

	public Map<String, ? extends ServletRegistration>
		getServletRegistrations() {

		return _servletContext.getServletRegistrations();
	}

	public Enumeration<Servlet> getServlets() {
		return Collections.enumeration(new ArrayList<Servlet>());
	}

	public SessionCookieConfig getSessionCookieConfig() {
		return _servletContext.getSessionCookieConfig();
	}

	public void log(Exception exception, String message) {
		_servletContext.log(message, exception);
	}

	public void log(String message) {
		_servletContext.log(message);
	}

	public void log(String message, Throwable t) {
		_servletContext.log(message, t);
	}

	public void removeAttribute(String name) {
		_servletContext.removeAttribute(name);
	}

	public void setAttribute(String name, Object value) {
		_servletContext.setAttribute(name, value);
	}

	public boolean setInitParameter(String name, String value) {
		return _servletContext.setInitParameter(name, value);
	}

	public void setSessionTrackingModes(
		Set<SessionTrackingMode> sessionTrackingModes) {

		_servletContext.setSessionTrackingModes(sessionTrackingModes);
	}

	@Override
	public String toString() {
		return _servletContext.toString();
	}

	private ServletContext _servletContext;

}