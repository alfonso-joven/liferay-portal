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

package com.liferay.portlet.journal.model.impl;

import com.liferay.portal.LocaleException;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.util.LocaleThreadLocal;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.kernel.util.LocalizationUtil;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.model.Image;
import com.liferay.portal.service.ImageLocalServiceUtil;
import com.liferay.portlet.journal.model.JournalArticleResource;
import com.liferay.portlet.journal.service.JournalArticleResourceLocalServiceUtil;
import com.liferay.portlet.journal.util.LocaleTransformerListener;

import java.util.Locale;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;

/**
 * @author Brian Wing Shun Chan
 * @author Wesley Gong
 */
public class JournalArticleImpl extends JournalArticleBaseImpl {

	public static String getContentByLocale(
		String content, boolean templateDriven, String languageId) {

		LocaleTransformerListener listener = new LocaleTransformerListener();

		listener.setTemplateDriven(templateDriven);
		listener.setLanguageId(languageId);

		return listener.onXml(content);
	}

	public JournalArticleImpl() {
	}

	@Override
	public JournalArticleResource getArticleResource()
		throws PortalException, SystemException {

		return JournalArticleResourceLocalServiceUtil.getArticleResource(
			getResourcePrimKey());
	}

	@Override
	public String getArticleResourceUuid()
		throws PortalException, SystemException {

		JournalArticleResource articleResource = getArticleResource();

		return articleResource.getUuid();
	}

	@Override
	public String[] getAvailableLocales() {
		Set<String> availableLocales = new TreeSet<String>();

		// Title

		Map<Locale, String> titleMap = getTitleMap();

		for (Map.Entry<Locale, String> entry : titleMap.entrySet()) {
			Locale locale = entry.getKey();
			String value = entry.getValue();

			if (Validator.isNotNull(value)) {
				availableLocales.add(locale.toString());
			}
		}

		// Description

		Map<Locale, String> descriptionMap = getDescriptionMap();

		for (Map.Entry<Locale, String> entry : descriptionMap.entrySet()) {
			Locale locale = entry.getKey();
			String value = entry.getValue();

			if (Validator.isNotNull(value)) {
				availableLocales.add(locale.toString());
			}
		}

		// Content

		String[] availableLocalesArray = LocalizationUtil.getAvailableLocales(
			getContent());

		for (String availableLocale : availableLocalesArray) {
			availableLocales.add(availableLocale);
		}

		return availableLocales.toArray(new String[availableLocales.size()]);
	}

	@Override
	public String getContentByLocale(String languageId) {
		return getContentByLocale(getContent(), isTemplateDriven(), languageId);
	}

	@Override
	public String getDefaultLocale() {
		String xml = getContent();

		if (xml == null) {
			return StringPool.BLANK;
		}

		String defaultLanguageId = LocalizationUtil.getDefaultLocale(xml);

		if (isTemplateDriven() && Validator.isNull(defaultLanguageId)) {
			defaultLanguageId = LocaleUtil.toLanguageId(
				LocaleUtil.getDefault());
		}

		return defaultLanguageId;
	}

	@Override
	public String getSmallImageType() throws PortalException, SystemException {
		if ((_smallImageType == null) && isSmallImage()) {
			Image smallImage = ImageLocalServiceUtil.getImage(
				getSmallImageId());

			_smallImageType = smallImage.getType();
		}

		return _smallImageType;
	}

	@Override
	public Map<Locale, String> getTitleMap() {
		Locale defaultLocale = LocaleThreadLocal.getDefaultLocale();

		try {
			Locale articleDefaultLocale = LocaleUtil.fromLanguageId(
				getDefaultLocale());

			LocaleThreadLocal.setDefaultLocale(articleDefaultLocale);

			return super.getTitleMap();
		}
		finally {
			LocaleThreadLocal.setDefaultLocale(defaultLocale);
		}
	}

	@Override
	public boolean isTemplateDriven() {
		if (Validator.isNull(getStructureId())) {
			return false;
		}
		else {
			return true;
		}
	}

	/**
	 * @throws LocaleException
	 */
	@Override
	public void prepareLocalizedFieldsForImport(Locale defaultImportLocale)
		throws LocaleException {
	}

	@Override
	public void setSmallImageType(String smallImageType) {
		_smallImageType = smallImageType;
	}

	private String _smallImageType;

}