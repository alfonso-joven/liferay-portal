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

package com.liferay.portlet.documentlibrary.util;

import com.liferay.portal.kernel.portlet.LiferayPortletRequest;
import com.liferay.portal.kernel.repository.model.FileVersion;
import com.liferay.portal.kernel.util.ContentTypes;
import com.liferay.portal.kernel.util.FileUtil;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.MimeTypesUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portlet.PortletRequestImpl;

import java.io.File;
import java.io.InputStream;

import javax.servlet.http.HttpServletRequest;

/**
 * @author Alexander Chow
 */
public class DLAppUtil {

	public static String getExtension(String title, String sourceFileName) {
		String extension = FileUtil.getExtension(sourceFileName);

		if (Validator.isNull(extension)) {
			extension = FileUtil.getExtension(title);
		}

		return extension;
	}

	public static String getMimeType(
		String sourceFileName, String mimeType, String title, File file,
		InputStream is) {

		if (Validator.isNull(mimeType) ||
			!mimeType.equals(ContentTypes.APPLICATION_OCTET_STREAM)) {

			return mimeType;
		}

		if (Validator.isNull(title)) {
			title = sourceFileName;
		}

		String extension = getExtension(title, sourceFileName);

		String titleWithExtension = DLUtil.getTitleWithExtension(
			title, extension);

		if (file != null) {
			mimeType = MimeTypesUtil.getContentType(file, titleWithExtension);
		}
		else {
			mimeType = MimeTypesUtil.getContentType(is, titleWithExtension);
		}

		return mimeType;
	}

	public static boolean isMajorVersion(
		FileVersion previousFileVersion, FileVersion currentFileVersion) {

		long currentVersion = GetterUtil.getLong(
			currentFileVersion.getVersion());
		long previousVersion = GetterUtil.getLong(
			previousFileVersion.getVersion());

		return (currentVersion - previousVersion) >= 1;
	}

	public static boolean portletHasFocus(
		LiferayPortletRequest liferayPortletRequest) {

		if (!(liferayPortletRequest instanceof PortletRequestImpl)) {
			return true;
		}

		PortletRequestImpl portletRequestImpl =
			(PortletRequestImpl)liferayPortletRequest;

		HttpServletRequest originalHttpServletRequest =
			portletRequestImpl.getOriginalHttpServletRequest();

		String portletId = originalHttpServletRequest.getParameter("p_p_id");

		if (portletId.equals(portletRequestImpl.getPortletName())) {
			return true;
		}

		return false;
	}

}