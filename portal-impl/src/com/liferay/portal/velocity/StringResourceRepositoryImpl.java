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

package com.liferay.portal.velocity;

import com.liferay.portal.kernel.util.AutoResetThreadLocal;
import com.liferay.portal.kernel.util.StringPool;

import java.util.HashMap;
import java.util.Map;

import org.apache.velocity.runtime.resource.util.StringResource;
import org.apache.velocity.runtime.resource.util.StringResourceRepository;

/**
 * @author Raymond Augé
 */
public class StringResourceRepositoryImpl implements StringResourceRepository {

	@Override
	public String getEncoding() {
		return _encoding;
	}

	@Override
	public StringResource getStringResource(String key) {
		Map<String, SerializableStringResource> _templateMap =
			_templateMapThreadLocal.get();

		SerializableStringResource serializableStringResource =
			_templateMap.get(key);

		if (serializableStringResource == null) {
			return null;
		}

		return serializableStringResource.toStringResource();
	}

	@Override
	public void putStringResource(String key, String body) {
		Map<String, SerializableStringResource> _templateMap =
			_templateMapThreadLocal.get();

		_templateMap.put(
			key, new SerializableStringResource(body, getEncoding()));
	}

	@Override
	public void putStringResource(String key, String body, String encoding) {
		Map<String, SerializableStringResource> _templateMap =
			_templateMapThreadLocal.get();

		_templateMap.put(key, new SerializableStringResource(body, encoding));
	}

	@Override
	public void removeStringResource(String key) {
		Map<String, SerializableStringResource> _templateMap =
			_templateMapThreadLocal.get();

		_templateMap.remove(key);
	}

	@Override
	public void setEncoding(String encoding) {
		_encoding = encoding;
	}

	private static ThreadLocal<Map<String, SerializableStringResource>>
		_templateMapThreadLocal =
			new AutoResetThreadLocal<Map<String, SerializableStringResource>>(
				StringResourceRepositoryImpl.class.getName() +
					"._templateMapThreadLocal",
				new HashMap<String, SerializableStringResource>());

	private String _encoding = StringPool.UTF8;

}