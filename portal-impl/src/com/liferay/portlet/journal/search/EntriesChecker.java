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

package com.liferay.portlet.journal.search;

import com.liferay.portal.kernel.dao.search.RowChecker;
import com.liferay.portal.kernel.portlet.LiferayPortletRequest;
import com.liferay.portal.kernel.portlet.LiferayPortletResponse;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.portal.security.permission.ActionKeys;
import com.liferay.portal.security.permission.PermissionChecker;
import com.liferay.portal.theme.ThemeDisplay;
import com.liferay.portlet.journal.model.JournalArticle;
import com.liferay.portlet.journal.service.JournalArticleServiceUtil;
import com.liferay.portlet.journal.service.permission.JournalArticlePermission;

import javax.servlet.http.HttpServletRequest;

/**
 * @author Sergio González
 * @author Tibor Lipusz
 */
public class EntriesChecker extends RowChecker {

	public EntriesChecker(
		LiferayPortletRequest liferayPortletRequest,
		LiferayPortletResponse liferayPortletResponse) {

		super(liferayPortletResponse);

		ThemeDisplay themeDisplay =
			(ThemeDisplay)liferayPortletRequest.getAttribute(
				WebKeys.THEME_DISPLAY);

		_permissionChecker = themeDisplay.getPermissionChecker();
	}

	@Override
	public String getRowCheckBox(
		HttpServletRequest request, boolean checked, boolean disabled,
		String primaryKey) {

		ThemeDisplay themeDisplay = (ThemeDisplay)request.getAttribute(
			WebKeys.THEME_DISPLAY);

		JournalArticle article = null;

		String rowCheckBox = StringPool.BLANK;

		try {
			article = JournalArticleServiceUtil.getArticle(
				themeDisplay.getScopeGroupId(), primaryKey);

			if (JournalArticlePermission.contains(
					_permissionChecker, article, ActionKeys.DELETE) ||
				JournalArticlePermission.contains(
					_permissionChecker, article, ActionKeys.EXPIRE)) {

				rowCheckBox = getRowCheckBox(
					checked, disabled, getRowIds(), primaryKey,
					StringUtil.quote(getRowIds()),
					StringUtil.quote(getAllRowIds()), StringPool.BLANK);
			}
		}
		catch (Exception e) {
		}

		return rowCheckBox;
	}

	private PermissionChecker _permissionChecker;

}