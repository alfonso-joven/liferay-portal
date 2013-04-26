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

package com.liferay.portal.upgrade.v5_2_0;

import com.liferay.portal.kernel.dao.jdbc.DataAccess;
import com.liferay.portal.kernel.upgrade.UpgradeProcess;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.model.PortletConstants;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

/**
 * @author Brian Wing Shun Chan
 */
public class UpgradePortletId extends UpgradeProcess {

	@Override
	protected void doUpgrade() throws Exception {

		// This is only tested to work on instanceable portlets

		try {
			runSQL("create index IX_5F076332 on Resource_ (primKey)");
		}
		catch (Exception e) {
		}

		try {
			runSQL(
				"create index IX_8E6DA3A1 on PortletPreferences (portletId)");
		}
		catch (Exception e) {
		}

		String[][] portletIdsArray = getPortletIdsArray();

		for (int i = 0; i < portletIdsArray.length; i++) {
			String[] portletIds = portletIdsArray[i];

			String oldRootPortletId = portletIds[0];
			String newRootPortletId = portletIds[1];

			updatePortlet(oldRootPortletId, newRootPortletId);
			updateLayouts(oldRootPortletId, newRootPortletId);
			updateResource(oldRootPortletId, newRootPortletId);
			updateResourceCode(oldRootPortletId, newRootPortletId);
		}
	}

	protected String[][] getPortletIdsArray() {
		return new String[][] {
			new String[] {
				"109", "1_WAR_webformportlet"
			},
			new String[] {
				"google_adsense_portlet_WAR_googleadsenseportlet",
				"1_WAR_googleadsenseportlet"
			},
			new String[] {
				"google_gadget_portlet_WAR_googlegadgetportlet",
				"1_WAR_googlegadgetportlet"
			},
			new String[] {
				"google_maps_portlet_WAR_googlemapsportlet",
				"1_WAR_googlemapsportlet"
			}
		};
	}

	protected void updateInstanceablePortletPreferences(
			String oldRootPortletId, String newRootPortletId)
		throws Exception {

		Connection con = null;
		PreparedStatement ps = null;
		ResultSet rs = null;

		try {
			con = DataAccess.getUpgradeOptimizedConnection();

			ps = con.prepareStatement(
				"select portletPreferencesId, portletId from " +
					"PortletPreferences where portletId like '" +
						oldRootPortletId + "%'");

			rs = ps.executeQuery();

			while (rs.next()) {
				long portletPreferencesId = rs.getLong("portletPreferencesId");
				String portletId = rs.getString("portletId");

				String newPortletId = StringUtil.replace(
					portletId, oldRootPortletId, newRootPortletId);

				updatePortletPreference(portletPreferencesId, newPortletId);
			}
		}
		finally {
			DataAccess.cleanUp(con, ps, rs);
		}
	}

	protected void updateLayout(long plid, String typeSettings)
		throws Exception {

		Connection con = null;
		PreparedStatement ps = null;

		try {
			con = DataAccess.getUpgradeOptimizedConnection();

			ps = con.prepareStatement(
				"update Layout set typeSettings = ? where plid = " + plid);

			ps.setString(1, typeSettings);

			ps.executeUpdate();
		}
		finally {
			DataAccess.cleanUp(con, ps);
		}
	}

	protected void updateLayout(
			long plid, String oldPortletId, String newPortletId)
		throws Exception {

		Connection con = null;
		PreparedStatement ps = null;
		ResultSet rs = null;

		try {
			con = DataAccess.getUpgradeOptimizedConnection();

			ps = con.prepareStatement(
				"select typeSettings from Layout where plid = " + plid);

			rs = ps.executeQuery();

			while (rs.next()) {
				String typeSettings = rs.getString("typeSettings");

				String newTypeSettings = StringUtil.replace(
					typeSettings, oldPortletId, newPortletId);

				updateLayout(plid, newTypeSettings);
			}
		}
		finally {
			DataAccess.cleanUp(con, ps, rs);
		}
	}

	protected void updateLayouts(
			String oldRootPortletId, String newRootPortletId)
		throws Exception {

		Connection con = null;
		PreparedStatement ps = null;
		ResultSet rs = null;

		try {
			con = DataAccess.getUpgradeOptimizedConnection();

			ps = con.prepareStatement(
				"select plid, typeSettings from Layout where typeSettings " +
					"like '%" + oldRootPortletId + "%'");

			rs = ps.executeQuery();

			while (rs.next()) {
				long plid = rs.getLong("plid");
				String typeSettings = rs.getString("typeSettings");

				String newTypeSettings = StringUtil.replace(
					typeSettings, oldRootPortletId, newRootPortletId);

				updateLayout(plid, newTypeSettings);
			}
		}
		finally {
			DataAccess.cleanUp(con, ps, rs);
		}
	}

	protected void updatePortlet(
			String oldRootPortletId, String newRootPortletId)
		throws Exception {

		runSQL(
			"update Portlet set portletId = '" + newRootPortletId +
				"' where portletId = '" + oldRootPortletId + "'");

		runSQL(
			"update ResourceAction set name = '" + newRootPortletId +
				"' where name = '" + oldRootPortletId + "'");

		updateResourcePermission(oldRootPortletId, newRootPortletId);

		updateInstanceablePortletPreferences(
			oldRootPortletId, newRootPortletId);
	}

	protected void updatePortletPreference(
			long portletPreferencesId, String portletId)
		throws Exception {

		Connection con = null;
		PreparedStatement ps = null;

		try {
			con = DataAccess.getUpgradeOptimizedConnection();

			ps = con.prepareStatement(
				"update PortletPreferences set portletId = ? where " +
					"portletPreferencesId = " + portletPreferencesId);

			ps.setString(1, portletId);

			ps.executeUpdate();
		}
		finally {
			DataAccess.cleanUp(con, ps);
		}
	}

	protected void updateResource(
			String oldRootPortletId, String newRootPortletId)
		throws Exception {

		Connection con = null;
		PreparedStatement ps = null;
		ResultSet rs = null;

		try {
			con = DataAccess.getUpgradeOptimizedConnection();

			ps = con.prepareStatement(
				"select primKey from Resource_ where primKey like ?");

			ps.setString(
				1,
				"%" + PortletConstants.LAYOUT_SEPARATOR + oldRootPortletId +
					"%");

			rs = ps.executeQuery();

			while (rs.next()) {
				String oldPrimKey = rs.getString("primKey");

				int pos = oldPrimKey.indexOf(PortletConstants.LAYOUT_SEPARATOR);

				long plid = GetterUtil.getLong(oldPrimKey.substring(0, pos));

				String portletId = oldPrimKey.substring(
					pos + PortletConstants.LAYOUT_SEPARATOR.length());

				String newPrimKey =
					plid + PortletConstants.LAYOUT_SEPARATOR + newRootPortletId;

				String oldPortletId = oldRootPortletId;
				String newPortletId = newRootPortletId;

				pos = portletId.indexOf(PortletConstants.INSTANCE_SEPARATOR);

				if (pos != -1) {
					portletId = portletId.substring(0, pos);

					String instanceId = oldPrimKey.substring(
						pos + PortletConstants.INSTANCE_SEPARATOR.length());

					newPrimKey +=
						PortletConstants.INSTANCE_SEPARATOR + instanceId;

					oldPortletId +=
						PortletConstants.INSTANCE_SEPARATOR + instanceId;
					newPortletId +=
						PortletConstants.INSTANCE_SEPARATOR + instanceId;
				}

				if (!portletId.equals(oldRootPortletId)) {
					continue;
				}

				runSQL(
					"update Resource_ set primKey = '" + newPrimKey +
						"' where primKey = '" + oldPrimKey + "'");

				updateLayout(plid, oldPortletId, newPortletId);

				runSQL(
					"update PortletPreferences set portletId = '" +
						newPortletId + "' where portletId = '" + oldPortletId +
							"'");
			}
		}
		finally {
			DataAccess.cleanUp(con, ps, rs);
		}
	}

	protected void updateResourceCode(
			String oldRootPortletId, String newRootPortletId)
		throws Exception {

		runSQL(
			"update ResourceCode set name = '" + newRootPortletId +
				"' where name = '" + oldRootPortletId + "'");
	}

	protected void updateResourcePermission(
			long resourcePermissionId, String name, String primKey)
		throws Exception {

		Connection con = null;
		PreparedStatement ps = null;

		try {
			con = DataAccess.getUpgradeOptimizedConnection();

			ps = con.prepareStatement(
				"update ResourcePermission set name = ?, primKey = ? where " +
					"resourcePermissionId = " + resourcePermissionId);

			ps.setString(1, name);
			ps.setString(2, primKey);

			ps.executeUpdate();
		}
		finally {
			DataAccess.cleanUp(con, ps);
		}
	}

	protected void updateResourcePermission(
			String oldRootPortletId, String newRootPortletId)
		throws Exception {

		Connection con = null;
		PreparedStatement ps = null;
		ResultSet rs = null;

		try {
			con = DataAccess.getUpgradeOptimizedConnection();

			ps = con.prepareStatement(
				"select resourcePermissionId, name, primKey from " +
					"ResourcePermission where name = '" + oldRootPortletId +
						"'");

			rs = ps.executeQuery();

			while (rs.next()) {
				long resourcePermissionId = rs.getLong("resourcePermissionId");
				String name = rs.getString("name");
				String primKey = rs.getString("primKey");

				String newName = StringUtil.replace(
					name, oldRootPortletId, newRootPortletId);
				String newPrimKey = StringUtil.replace(
					primKey, oldRootPortletId, newRootPortletId);

				updateResourcePermission(
					resourcePermissionId, newName, newPrimKey);
			}
		}
		finally {
			DataAccess.cleanUp(con, ps, rs);
		}
	}

}