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

package com.liferay.portalweb.portal.util.liferayselenium;

import com.liferay.portalweb.portal.util.TestPropsValues;

import java.util.HashMap;
import java.util.Map;

import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.remote.DesiredCapabilities;

/**
 * @author Brian Wing Shun Chan
 */
public class ChromeWebDriverImpl extends BaseWebDriverImpl {

	public ChromeWebDriverImpl(String projectDir, String browserURL) {
		super(projectDir, browserURL, new ChromeDriver(_desiredCapabilities));
	}

	private static DesiredCapabilities _desiredCapabilities;

	static {
		_desiredCapabilities = DesiredCapabilities.chrome();

		Map<String, Object> preferences = new HashMap<String, Object>();

		preferences.put("download.prompt_for_download", false);
		preferences.put(
			"download.default_directory", TestPropsValues.OUTPUT_DIR);

		_desiredCapabilities.setCapability("chrome.prefs", preferences);
	}

}