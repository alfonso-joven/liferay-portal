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

package com.liferay.portlet.journal.model;

import com.liferay.portal.kernel.bean.AutoEscape;
import com.liferay.portal.model.BaseModel;
import com.liferay.portal.model.CacheModel;
import com.liferay.portal.service.ServiceContext;

import com.liferay.portlet.expando.model.ExpandoBridge;

import java.io.Serializable;

/**
 * The base model interface for the JournalContentSearch service. Represents a row in the &quot;JournalContentSearch&quot; database table, with each column mapped to a property of this class.
 *
 * <p>
 * This interface and its corresponding implementation {@link com.liferay.portlet.journal.model.impl.JournalContentSearchModelImpl} exist only as a container for the default property accessors generated by ServiceBuilder. Helper methods and all application logic should be put in {@link com.liferay.portlet.journal.model.impl.JournalContentSearchImpl}.
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @see JournalContentSearch
 * @see com.liferay.portlet.journal.model.impl.JournalContentSearchImpl
 * @see com.liferay.portlet.journal.model.impl.JournalContentSearchModelImpl
 * @generated
 */
public interface JournalContentSearchModel extends BaseModel<JournalContentSearch> {
	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this interface directly. All methods that expect a journal content search model instance should use the {@link JournalContentSearch} interface instead.
	 */

	/**
	 * Returns the primary key of this journal content search.
	 *
	 * @return the primary key of this journal content search
	 */
	public long getPrimaryKey();

	/**
	 * Sets the primary key of this journal content search.
	 *
	 * @param primaryKey the primary key of this journal content search
	 */
	public void setPrimaryKey(long primaryKey);

	/**
	 * Returns the content search ID of this journal content search.
	 *
	 * @return the content search ID of this journal content search
	 */
	public long getContentSearchId();

	/**
	 * Sets the content search ID of this journal content search.
	 *
	 * @param contentSearchId the content search ID of this journal content search
	 */
	public void setContentSearchId(long contentSearchId);

	/**
	 * Returns the group ID of this journal content search.
	 *
	 * @return the group ID of this journal content search
	 */
	public long getGroupId();

	/**
	 * Sets the group ID of this journal content search.
	 *
	 * @param groupId the group ID of this journal content search
	 */
	public void setGroupId(long groupId);

	/**
	 * Returns the company ID of this journal content search.
	 *
	 * @return the company ID of this journal content search
	 */
	public long getCompanyId();

	/**
	 * Sets the company ID of this journal content search.
	 *
	 * @param companyId the company ID of this journal content search
	 */
	public void setCompanyId(long companyId);

	/**
	 * Returns the private layout of this journal content search.
	 *
	 * @return the private layout of this journal content search
	 */
	public boolean getPrivateLayout();

	/**
	 * Returns <code>true</code> if this journal content search is private layout.
	 *
	 * @return <code>true</code> if this journal content search is private layout; <code>false</code> otherwise
	 */
	public boolean isPrivateLayout();

	/**
	 * Sets whether this journal content search is private layout.
	 *
	 * @param privateLayout the private layout of this journal content search
	 */
	public void setPrivateLayout(boolean privateLayout);

	/**
	 * Returns the layout ID of this journal content search.
	 *
	 * @return the layout ID of this journal content search
	 */
	public long getLayoutId();

	/**
	 * Sets the layout ID of this journal content search.
	 *
	 * @param layoutId the layout ID of this journal content search
	 */
	public void setLayoutId(long layoutId);

	/**
	 * Returns the portlet ID of this journal content search.
	 *
	 * @return the portlet ID of this journal content search
	 */
	@AutoEscape
	public String getPortletId();

	/**
	 * Sets the portlet ID of this journal content search.
	 *
	 * @param portletId the portlet ID of this journal content search
	 */
	public void setPortletId(String portletId);

	/**
	 * Returns the article ID of this journal content search.
	 *
	 * @return the article ID of this journal content search
	 */
	@AutoEscape
	public String getArticleId();

	/**
	 * Sets the article ID of this journal content search.
	 *
	 * @param articleId the article ID of this journal content search
	 */
	public void setArticleId(String articleId);

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

	public int compareTo(JournalContentSearch journalContentSearch);

	public int hashCode();

	public CacheModel<JournalContentSearch> toCacheModel();

	public JournalContentSearch toEscapedModel();

	public JournalContentSearch toUnescapedModel();

	public String toString();

	public String toXmlString();
}