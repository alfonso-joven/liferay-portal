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

package com.liferay.portal.model.impl;

import com.liferay.portal.kernel.bean.AutoEscapeBeanHandler;
import com.liferay.portal.kernel.json.JSON;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.ProxyUtil;
import com.liferay.portal.kernel.util.StringBundler;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.model.CacheModel;
import com.liferay.portal.model.Permission;
import com.liferay.portal.model.PermissionModel;
import com.liferay.portal.model.PermissionSoap;
import com.liferay.portal.service.ServiceContext;

import com.liferay.portlet.expando.model.ExpandoBridge;
import com.liferay.portlet.expando.util.ExpandoBridgeFactoryUtil;

import java.io.Serializable;

import java.sql.Types;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * The base model implementation for the Permission service. Represents a row in the &quot;Permission_&quot; database table, with each column mapped to a property of this class.
 *
 * <p>
 * This implementation and its corresponding interface {@link com.liferay.portal.model.PermissionModel} exist only as a container for the default property accessors generated by ServiceBuilder. Helper methods and all application logic should be put in {@link PermissionImpl}.
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @see PermissionImpl
 * @see com.liferay.portal.model.Permission
 * @see com.liferay.portal.model.PermissionModel
 * @generated
 */
@JSON(strict = true)
public class PermissionModelImpl extends BaseModelImpl<Permission>
	implements PermissionModel {
	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this class directly. All methods that expect a permission model instance should use the {@link com.liferay.portal.model.Permission} interface instead.
	 */
	public static final String TABLE_NAME = "Permission_";
	public static final Object[][] TABLE_COLUMNS = {
			{ "permissionId", Types.BIGINT },
			{ "companyId", Types.BIGINT },
			{ "actionId", Types.VARCHAR },
			{ "resourceId", Types.BIGINT }
		};
	public static final String TABLE_SQL_CREATE = "create table Permission_ (permissionId LONG not null primary key,companyId LONG,actionId VARCHAR(75) null,resourceId LONG)";
	public static final String TABLE_SQL_DROP = "drop table Permission_";
	public static final String DATA_SOURCE = "liferayDataSource";
	public static final String SESSION_FACTORY = "liferaySessionFactory";
	public static final String TX_MANAGER = "liferayTransactionManager";
	public static final boolean ENTITY_CACHE_ENABLED = GetterUtil.getBoolean(com.liferay.portal.util.PropsUtil.get(
				"value.object.entity.cache.enabled.com.liferay.portal.model.Permission"),
			true);
	public static final boolean FINDER_CACHE_ENABLED = GetterUtil.getBoolean(com.liferay.portal.util.PropsUtil.get(
				"value.object.finder.cache.enabled.com.liferay.portal.model.Permission"),
			true);
	public static final boolean COLUMN_BITMASK_ENABLED = GetterUtil.getBoolean(com.liferay.portal.util.PropsUtil.get(
				"value.object.column.bitmask.enabled.com.liferay.portal.model.Permission"),
			true);
	public static long ACTIONID_COLUMN_BITMASK = 1L;
	public static long RESOURCEID_COLUMN_BITMASK = 2L;

	/**
	 * Converts the soap model instance into a normal model instance.
	 *
	 * @param soapModel the soap model instance to convert
	 * @return the normal model instance
	 */
	public static Permission toModel(PermissionSoap soapModel) {
		if (soapModel == null) {
			return null;
		}

		Permission model = new PermissionImpl();

		model.setPermissionId(soapModel.getPermissionId());
		model.setCompanyId(soapModel.getCompanyId());
		model.setActionId(soapModel.getActionId());
		model.setResourceId(soapModel.getResourceId());

		return model;
	}

	/**
	 * Converts the soap model instances into normal model instances.
	 *
	 * @param soapModels the soap model instances to convert
	 * @return the normal model instances
	 */
	public static List<Permission> toModels(PermissionSoap[] soapModels) {
		if (soapModels == null) {
			return null;
		}

		List<Permission> models = new ArrayList<Permission>(soapModels.length);

		for (PermissionSoap soapModel : soapModels) {
			models.add(toModel(soapModel));
		}

		return models;
	}

	public static final String MAPPING_TABLE_GROUPS_PERMISSIONS_NAME = "Groups_Permissions";
	public static final Object[][] MAPPING_TABLE_GROUPS_PERMISSIONS_COLUMNS = {
			{ "groupId", Types.BIGINT },
			{ "permissionId", Types.BIGINT }
		};
	public static final String MAPPING_TABLE_GROUPS_PERMISSIONS_SQL_CREATE = "create table Groups_Permissions (groupId LONG not null,permissionId LONG not null,primary key (groupId, permissionId))";
	public static final boolean FINDER_CACHE_ENABLED_GROUPS_PERMISSIONS = GetterUtil.getBoolean(com.liferay.portal.util.PropsUtil.get(
				"value.object.finder.cache.enabled.Groups_Permissions"), true);
	public static final String MAPPING_TABLE_ROLES_PERMISSIONS_NAME = "Roles_Permissions";
	public static final Object[][] MAPPING_TABLE_ROLES_PERMISSIONS_COLUMNS = {
			{ "roleId", Types.BIGINT },
			{ "permissionId", Types.BIGINT }
		};
	public static final String MAPPING_TABLE_ROLES_PERMISSIONS_SQL_CREATE = "create table Roles_Permissions (roleId LONG not null,permissionId LONG not null,primary key (roleId, permissionId))";
	public static final boolean FINDER_CACHE_ENABLED_ROLES_PERMISSIONS = GetterUtil.getBoolean(com.liferay.portal.util.PropsUtil.get(
				"value.object.finder.cache.enabled.Roles_Permissions"), true);
	public static final String MAPPING_TABLE_USERS_PERMISSIONS_NAME = "Users_Permissions";
	public static final Object[][] MAPPING_TABLE_USERS_PERMISSIONS_COLUMNS = {
			{ "userId", Types.BIGINT },
			{ "permissionId", Types.BIGINT }
		};
	public static final String MAPPING_TABLE_USERS_PERMISSIONS_SQL_CREATE = "create table Users_Permissions (userId LONG not null,permissionId LONG not null,primary key (userId, permissionId))";
	public static final boolean FINDER_CACHE_ENABLED_USERS_PERMISSIONS = GetterUtil.getBoolean(com.liferay.portal.util.PropsUtil.get(
				"value.object.finder.cache.enabled.Users_Permissions"), true);
	public static final long LOCK_EXPIRATION_TIME = GetterUtil.getLong(com.liferay.portal.util.PropsUtil.get(
				"lock.expiration.time.com.liferay.portal.model.Permission"));

	public PermissionModelImpl() {
	}

	public long getPrimaryKey() {
		return _permissionId;
	}

	public void setPrimaryKey(long primaryKey) {
		setPermissionId(primaryKey);
	}

	public Serializable getPrimaryKeyObj() {
		return new Long(_permissionId);
	}

	public void setPrimaryKeyObj(Serializable primaryKeyObj) {
		setPrimaryKey(((Long)primaryKeyObj).longValue());
	}

	public Class<?> getModelClass() {
		return Permission.class;
	}

	public String getModelClassName() {
		return Permission.class.getName();
	}

	@Override
	public Map<String, Object> getModelAttributes() {
		Map<String, Object> attributes = new HashMap<String, Object>();

		attributes.put("permissionId", getPermissionId());
		attributes.put("companyId", getCompanyId());
		attributes.put("actionId", getActionId());
		attributes.put("resourceId", getResourceId());

		return attributes;
	}

	@Override
	public void setModelAttributes(Map<String, Object> attributes) {
		Long permissionId = (Long)attributes.get("permissionId");

		if (permissionId != null) {
			setPermissionId(permissionId);
		}

		Long companyId = (Long)attributes.get("companyId");

		if (companyId != null) {
			setCompanyId(companyId);
		}

		String actionId = (String)attributes.get("actionId");

		if (actionId != null) {
			setActionId(actionId);
		}

		Long resourceId = (Long)attributes.get("resourceId");

		if (resourceId != null) {
			setResourceId(resourceId);
		}
	}

	@JSON
	public long getPermissionId() {
		return _permissionId;
	}

	public void setPermissionId(long permissionId) {
		_permissionId = permissionId;
	}

	@JSON
	public long getCompanyId() {
		return _companyId;
	}

	public void setCompanyId(long companyId) {
		_companyId = companyId;
	}

	@JSON
	public String getActionId() {
		if (_actionId == null) {
			return StringPool.BLANK;
		}
		else {
			return _actionId;
		}
	}

	public void setActionId(String actionId) {
		_columnBitmask |= ACTIONID_COLUMN_BITMASK;

		if (_originalActionId == null) {
			_originalActionId = _actionId;
		}

		_actionId = actionId;
	}

	public String getOriginalActionId() {
		return GetterUtil.getString(_originalActionId);
	}

	@JSON
	public long getResourceId() {
		return _resourceId;
	}

	public void setResourceId(long resourceId) {
		_columnBitmask |= RESOURCEID_COLUMN_BITMASK;

		if (!_setOriginalResourceId) {
			_setOriginalResourceId = true;

			_originalResourceId = _resourceId;
		}

		_resourceId = resourceId;
	}

	public long getOriginalResourceId() {
		return _originalResourceId;
	}

	public long getColumnBitmask() {
		return _columnBitmask;
	}

	@Override
	public ExpandoBridge getExpandoBridge() {
		return ExpandoBridgeFactoryUtil.getExpandoBridge(getCompanyId(),
			Permission.class.getName(), getPrimaryKey());
	}

	@Override
	public void setExpandoBridgeAttributes(ServiceContext serviceContext) {
		ExpandoBridge expandoBridge = getExpandoBridge();

		expandoBridge.setAttributes(serviceContext);
	}

	@Override
	public Permission toEscapedModel() {
		if (_escapedModel == null) {
			_escapedModel = (Permission)ProxyUtil.newProxyInstance(_classLoader,
					_escapedModelInterfaces, new AutoEscapeBeanHandler(this));
		}

		return _escapedModel;
	}

	public Permission toUnescapedModel() {
		return (Permission)this;
	}

	@Override
	public Object clone() {
		PermissionImpl permissionImpl = new PermissionImpl();

		permissionImpl.setPermissionId(getPermissionId());
		permissionImpl.setCompanyId(getCompanyId());
		permissionImpl.setActionId(getActionId());
		permissionImpl.setResourceId(getResourceId());

		permissionImpl.resetOriginalValues();

		return permissionImpl;
	}

	public int compareTo(Permission permission) {
		long primaryKey = permission.getPrimaryKey();

		if (getPrimaryKey() < primaryKey) {
			return -1;
		}
		else if (getPrimaryKey() > primaryKey) {
			return 1;
		}
		else {
			return 0;
		}
	}

	@Override
	public boolean equals(Object obj) {
		if (obj == null) {
			return false;
		}

		Permission permission = null;

		try {
			permission = (Permission)obj;
		}
		catch (ClassCastException cce) {
			return false;
		}

		long primaryKey = permission.getPrimaryKey();

		if (getPrimaryKey() == primaryKey) {
			return true;
		}
		else {
			return false;
		}
	}

	@Override
	public int hashCode() {
		return (int)getPrimaryKey();
	}

	@Override
	public void resetOriginalValues() {
		PermissionModelImpl permissionModelImpl = this;

		permissionModelImpl._originalActionId = permissionModelImpl._actionId;

		permissionModelImpl._originalResourceId = permissionModelImpl._resourceId;

		permissionModelImpl._setOriginalResourceId = false;

		permissionModelImpl._columnBitmask = 0;
	}

	@Override
	public CacheModel<Permission> toCacheModel() {
		PermissionCacheModel permissionCacheModel = new PermissionCacheModel();

		permissionCacheModel.permissionId = getPermissionId();

		permissionCacheModel.companyId = getCompanyId();

		permissionCacheModel.actionId = getActionId();

		String actionId = permissionCacheModel.actionId;

		if ((actionId != null) && (actionId.length() == 0)) {
			permissionCacheModel.actionId = null;
		}

		permissionCacheModel.resourceId = getResourceId();

		return permissionCacheModel;
	}

	@Override
	public String toString() {
		StringBundler sb = new StringBundler(9);

		sb.append("{permissionId=");
		sb.append(getPermissionId());
		sb.append(", companyId=");
		sb.append(getCompanyId());
		sb.append(", actionId=");
		sb.append(getActionId());
		sb.append(", resourceId=");
		sb.append(getResourceId());
		sb.append("}");

		return sb.toString();
	}

	public String toXmlString() {
		StringBundler sb = new StringBundler(16);

		sb.append("<model><model-name>");
		sb.append("com.liferay.portal.model.Permission");
		sb.append("</model-name>");

		sb.append(
			"<column><column-name>permissionId</column-name><column-value><![CDATA[");
		sb.append(getPermissionId());
		sb.append("]]></column-value></column>");
		sb.append(
			"<column><column-name>companyId</column-name><column-value><![CDATA[");
		sb.append(getCompanyId());
		sb.append("]]></column-value></column>");
		sb.append(
			"<column><column-name>actionId</column-name><column-value><![CDATA[");
		sb.append(getActionId());
		sb.append("]]></column-value></column>");
		sb.append(
			"<column><column-name>resourceId</column-name><column-value><![CDATA[");
		sb.append(getResourceId());
		sb.append("]]></column-value></column>");

		sb.append("</model>");

		return sb.toString();
	}

	private static ClassLoader _classLoader = Permission.class.getClassLoader();
	private static Class<?>[] _escapedModelInterfaces = new Class[] {
			Permission.class
		};
	private long _permissionId;
	private long _companyId;
	private String _actionId;
	private String _originalActionId;
	private long _resourceId;
	private long _originalResourceId;
	private boolean _setOriginalResourceId;
	private long _columnBitmask;
	private Permission _escapedModel;
}