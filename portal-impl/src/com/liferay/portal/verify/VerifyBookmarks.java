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

package com.liferay.portal.verify;

import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.model.Role;
import com.liferay.portal.model.RoleConstants;
import com.liferay.portal.security.permission.ResourceActionsUtil;
import com.liferay.portal.service.ResourceBlockLocalServiceUtil;
import com.liferay.portal.service.RoleLocalServiceUtil;
import com.liferay.portal.util.PropsValues;
import com.liferay.portlet.bookmarks.model.BookmarksEntry;
import com.liferay.portlet.bookmarks.model.BookmarksFolder;
import com.liferay.portlet.bookmarks.service.BookmarksEntryLocalServiceUtil;
import com.liferay.portlet.bookmarks.service.BookmarksFolderLocalServiceUtil;

import java.util.List;

/**
 * @author Raymond Augé
 * @author Joshua Steven Rodriguez
 */
public class VerifyBookmarks extends VerifyProcess {

	@Override
	protected void doVerify() throws Exception {
		List<BookmarksEntry> entries =
			BookmarksEntryLocalServiceUtil.getNoAssetEntries();

		if (_log.isDebugEnabled()) {
			_log.debug(
				"Processing " + entries.size() + " entries with no asset");
		}

		for (BookmarksEntry entry : entries) {
			try {
				BookmarksEntryLocalServiceUtil.updateAsset(
					entry.getUserId(), entry, null, null, null);
			}
			catch (Exception e) {
				if (_log.isWarnEnabled()) {
					_log.warn(
						"Unable to update asset for entry " +
							entry.getEntryId() + ": " + e.getMessage());
				}
			}
		}

		if (PropsValues.PERMISSIONS_USER_CHECK_ALGORITHM > 5) {
			List<String> actionIds =
				ResourceActionsUtil.getModelResourceActions(
					BookmarksEntry.class.getName());

			entries =
				BookmarksEntryLocalServiceUtil.getNoResourceBlockEntries();

			for (BookmarksEntry entry : entries) {
				Role ownerRole = RoleLocalServiceUtil.getRole(
					entry.getCompanyId(), RoleConstants.OWNER);

				ResourceBlockLocalServiceUtil.setIndividualScopePermissions(
					entry.getCompanyId(), entry.getGroupId(),
					BookmarksEntry.class.getName(), entry,
					ownerRole.getRoleId(), actionIds);
			}

			actionIds = ResourceActionsUtil.getModelResourceActions(
				BookmarksFolder.class.getName());

			List<BookmarksFolder> folders =
				BookmarksFolderLocalServiceUtil.getNoResourceBlockFolders();

			for (BookmarksFolder folder : folders) {
				Role ownerRole = RoleLocalServiceUtil.getRole(
					folder.getCompanyId(), RoleConstants.OWNER);

				ResourceBlockLocalServiceUtil.setIndividualScopePermissions(
					folder.getCompanyId(), folder.getGroupId(),
					BookmarksFolder.class.getName(), folder,
					ownerRole.getRoleId(), actionIds);
			}
		}

		if (_log.isDebugEnabled()) {
			_log.debug("Assets verified for entries");
		}
	}

	private static Log _log = LogFactoryUtil.getLog(VerifyBookmarks.class);

}