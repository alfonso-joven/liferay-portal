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

package com.liferay.portlet.messageboards.security.permission;

import com.liferay.portal.kernel.dao.orm.QueryUtil;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.workflow.WorkflowConstants;
import com.liferay.portal.security.permission.BasePermissionPropagator;
import com.liferay.portlet.messageboards.model.MBCategory;
import com.liferay.portlet.messageboards.model.MBMessage;
import com.liferay.portlet.messageboards.model.MBThread;
import com.liferay.portlet.messageboards.service.MBCategoryLocalServiceUtil;
import com.liferay.portlet.messageboards.service.MBMessageLocalServiceUtil;

import java.util.ArrayList;
import java.util.List;

import javax.portlet.ActionRequest;

/**
 * @author Kenneth Chang
 */
public class MBPermissionPropagatorImpl extends BasePermissionPropagator {

	public void propagateCategoryRolePermissions(
			ActionRequest actionRequest, String className, long primaryKey,
			long categoryId, long[] roleIds)
		throws Exception {

		for (long roleId : roleIds) {
			propagateRolePermissions(
				actionRequest, roleId, className, primaryKey,
				MBCategory.class.getName(), categoryId);
		}
	}

	public void propagateCategoryRolePermissions(
			ActionRequest actionRequest, String className, String primKey,
			long[] roleIds)
		throws Exception {

		long categoryId = GetterUtil.getLong(primKey);

		MBCategory category = MBCategoryLocalServiceUtil.getCategory(
			categoryId);

		List<Object> categoriesAndThreads =
			MBCategoryLocalServiceUtil.getCategoriesAndThreads(
				category.getGroupId(), categoryId);

		for (Object categoryOrThread : categoriesAndThreads) {
			if (categoryOrThread instanceof MBThread) {
				MBThread thread = (MBThread)categoryOrThread;

				List<MBMessage> messages =
					MBMessageLocalServiceUtil.getThreadMessages(
						thread.getThreadId(), WorkflowConstants.STATUS_ANY);

				for (MBMessage message : messages) {
					propagateMessageRolePermissions(
						actionRequest, className, categoryId,
						message.getMessageId(), roleIds);
				}
			}
			else {
				category = (MBCategory)categoryOrThread;

				List<Long> categoryIds = new ArrayList<Long>();

				categoryIds.add(category.getCategoryId());

				categoryIds = MBCategoryLocalServiceUtil.getSubcategoryIds(
					categoryIds, category.getGroupId(),
					category.getCategoryId());

				for (long addCategoryId : categoryIds) {
					propagateCategoryRolePermissions(
						actionRequest, className, categoryId, addCategoryId,
						roleIds);

					List<MBMessage> messages =
						MBMessageLocalServiceUtil.getCategoryMessages(
							category.getGroupId(), addCategoryId,
							WorkflowConstants.STATUS_ANY, QueryUtil.ALL_POS,
							QueryUtil.ALL_POS);

					for (MBMessage message : messages) {
						propagateMessageRolePermissions(
							actionRequest, className, categoryId,
							message.getMessageId(), roleIds);
					}
				}
			}
		}
	}

	public void propagateMBRolePermissions(
			ActionRequest actionRequest, String className, String primKey,
			long[] roleIds)
		throws Exception {

		long groupId = GetterUtil.getLong(primKey);

		List<MBCategory> categories = MBCategoryLocalServiceUtil.getCategories(
			groupId);

		for (MBCategory category : categories) {
			propagateCategoryRolePermissions(
				actionRequest, className, groupId, category.getCategoryId(),
				roleIds);
		}

		List<MBMessage> messages = MBMessageLocalServiceUtil.getGroupMessages(
			groupId, WorkflowConstants.STATUS_ANY, QueryUtil.ALL_POS,
			QueryUtil.ALL_POS);

		for (MBMessage message : messages) {
			propagateMessageRolePermissions(
				actionRequest, className, groupId, message.getMessageId(),
				roleIds);
		}
	}

	public void propagateMessageRolePermissions(
			ActionRequest actionRequest, String className, long primaryKey,
			long messageId, long[] roleIds)
		throws Exception {

		for (long roleId : roleIds) {
			propagateRolePermissions(
				actionRequest, roleId, className, primaryKey,
				MBMessage.class.getName(), messageId);
		}
	}

	public void propagateRolePermissions(
			ActionRequest actionRequest, String className, String primKey,
			long[] roleIds)
		throws Exception {

		if (className.equals(MBCategory.class.getName())) {
			propagateCategoryRolePermissions(
				actionRequest, className, primKey, roleIds);
		}
		else if (className.equals(MBMessage.class.getName())) {
			long messageId = GetterUtil.getLong(primKey);

			MBMessage message = MBMessageLocalServiceUtil.getMessage(messageId);

			if (message.isRoot()) {
				propagateThreadRolePermissions(
					actionRequest, className, messageId, message.getThreadId(),
					roleIds);
			}
		}
		else if (className.equals("com.liferay.portlet.messageboards")) {
			propagateMBRolePermissions(
				actionRequest, className, primKey, roleIds);
		}
	}

	public void propagateThreadRolePermissions(
			ActionRequest actionRequest, String className, long messageId,
			long threadId, long[] roleIds)
		throws Exception {

		List<MBMessage> messages = MBMessageLocalServiceUtil.getThreadMessages(
			threadId, WorkflowConstants.STATUS_ANY);

		for (MBMessage message : messages) {
			propagateMessageRolePermissions(
				actionRequest, className, messageId, message.getMessageId(),
				roleIds);
		}
	}

}