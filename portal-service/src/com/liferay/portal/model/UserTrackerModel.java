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

package com.liferay.portal.model;

import com.liferay.portal.kernel.bean.AutoEscape;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.service.ServiceContext;

import com.liferay.portlet.expando.model.ExpandoBridge;

import java.io.Serializable;

import java.util.Date;

/**
 * The base model interface for the UserTracker service. Represents a row in the &quot;UserTracker&quot; database table, with each column mapped to a property of this class.
 *
 * <p>
 * This interface and its corresponding implementation {@link com.liferay.portal.model.impl.UserTrackerModelImpl} exist only as a container for the default property accessors generated by ServiceBuilder. Helper methods and all application logic should be put in {@link com.liferay.portal.model.impl.UserTrackerImpl}.
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @see UserTracker
 * @see com.liferay.portal.model.impl.UserTrackerImpl
 * @see com.liferay.portal.model.impl.UserTrackerModelImpl
 * @generated
 */
public interface UserTrackerModel extends BaseModel<UserTracker> {
	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this interface directly. All methods that expect a user tracker model instance should use the {@link UserTracker} interface instead.
	 */

	/**
	 * Returns the primary key of this user tracker.
	 *
	 * @return the primary key of this user tracker
	 */
	public long getPrimaryKey();

	/**
	 * Sets the primary key of this user tracker.
	 *
	 * @param primaryKey the primary key of this user tracker
	 */
	public void setPrimaryKey(long primaryKey);

	/**
	 * Returns the user tracker ID of this user tracker.
	 *
	 * @return the user tracker ID of this user tracker
	 */
	public long getUserTrackerId();

	/**
	 * Sets the user tracker ID of this user tracker.
	 *
	 * @param userTrackerId the user tracker ID of this user tracker
	 */
	public void setUserTrackerId(long userTrackerId);

	/**
	 * Returns the company ID of this user tracker.
	 *
	 * @return the company ID of this user tracker
	 */
	public long getCompanyId();

	/**
	 * Sets the company ID of this user tracker.
	 *
	 * @param companyId the company ID of this user tracker
	 */
	public void setCompanyId(long companyId);

	/**
	 * Returns the user ID of this user tracker.
	 *
	 * @return the user ID of this user tracker
	 */
	public long getUserId();

	/**
	 * Sets the user ID of this user tracker.
	 *
	 * @param userId the user ID of this user tracker
	 */
	public void setUserId(long userId);

	/**
	 * Returns the user uuid of this user tracker.
	 *
	 * @return the user uuid of this user tracker
	 * @throws SystemException if a system exception occurred
	 */
	public String getUserUuid() throws SystemException;

	/**
	 * Sets the user uuid of this user tracker.
	 *
	 * @param userUuid the user uuid of this user tracker
	 */
	public void setUserUuid(String userUuid);

	/**
	 * Returns the modified date of this user tracker.
	 *
	 * @return the modified date of this user tracker
	 */
	public Date getModifiedDate();

	/**
	 * Sets the modified date of this user tracker.
	 *
	 * @param modifiedDate the modified date of this user tracker
	 */
	public void setModifiedDate(Date modifiedDate);

	/**
	 * Returns the session ID of this user tracker.
	 *
	 * @return the session ID of this user tracker
	 */
	@AutoEscape
	public String getSessionId();

	/**
	 * Sets the session ID of this user tracker.
	 *
	 * @param sessionId the session ID of this user tracker
	 */
	public void setSessionId(String sessionId);

	/**
	 * Returns the remote addr of this user tracker.
	 *
	 * @return the remote addr of this user tracker
	 */
	@AutoEscape
	public String getRemoteAddr();

	/**
	 * Sets the remote addr of this user tracker.
	 *
	 * @param remoteAddr the remote addr of this user tracker
	 */
	public void setRemoteAddr(String remoteAddr);

	/**
	 * Returns the remote host of this user tracker.
	 *
	 * @return the remote host of this user tracker
	 */
	@AutoEscape
	public String getRemoteHost();

	/**
	 * Sets the remote host of this user tracker.
	 *
	 * @param remoteHost the remote host of this user tracker
	 */
	public void setRemoteHost(String remoteHost);

	/**
	 * Returns the user agent of this user tracker.
	 *
	 * @return the user agent of this user tracker
	 */
	@AutoEscape
	public String getUserAgent();

	/**
	 * Sets the user agent of this user tracker.
	 *
	 * @param userAgent the user agent of this user tracker
	 */
	public void setUserAgent(String userAgent);

	public boolean isNew();

	public void setNew(boolean n);

	public boolean isCachedModel();

	public void setCachedModel(boolean cachedModel);

	public boolean isEscapedModel();

	public Serializable getPrimaryKeyObj();

	public void setPrimaryKeyObj(Serializable primaryKeyObj);

	public ExpandoBridge getExpandoBridge();

	public void setExpandoBridgeAttributes(ServiceContext serviceContext);

	public Object clone();

	public int compareTo(UserTracker userTracker);

	public int hashCode();

	public CacheModel<UserTracker> toCacheModel();

	public UserTracker toEscapedModel();

	public UserTracker toUnescapedModel();

	public String toString();

	public String toXmlString();
}