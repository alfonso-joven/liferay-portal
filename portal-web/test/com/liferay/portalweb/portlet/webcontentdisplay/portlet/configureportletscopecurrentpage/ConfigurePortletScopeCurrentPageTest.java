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

package com.liferay.portalweb.portlet.webcontentdisplay.portlet.configureportletscopecurrentpage;

import com.liferay.portalweb.portal.BaseTestCase;
import com.liferay.portalweb.portal.util.RuntimeVariables;

/**
 * @author Brian Wing Shun Chan
 */
public class ConfigurePortletScopeCurrentPageTest extends BaseTestCase {
	public void testConfigurePortletScopeCurrentPage()
		throws Exception {
		selenium.open("/web/guest/home/");
		selenium.clickAt("link=Web Content Display Test Page2",
			RuntimeVariables.replace("Web Content Display Test Page2"));
		selenium.waitForPageToLoad("30000");
		Thread.sleep(5000);
		assertEquals(RuntimeVariables.replace("Options"),
			selenium.getText("//span[@title='Options']/ul/li/strong/a"));
		selenium.clickAt("//span[@title='Options']/ul/li/strong/a",
			RuntimeVariables.replace("Options"));

		for (int second = 0;; second++) {
			if (second >= 90) {
				fail("timeout");
			}

			try {
				if (selenium.isVisible(
							"//div[@class='lfr-component lfr-menu-list']/ul/li[contains(.,'Configuration')]/a")) {
					break;
				}
			}
			catch (Exception e) {
			}

			Thread.sleep(1000);
		}

		assertEquals(RuntimeVariables.replace("Configuration"),
			selenium.getText(
				"//div[@class='lfr-component lfr-menu-list']/ul/li[contains(.,'Configuration')]/a"));
		selenium.clickAt("//div[@class='lfr-component lfr-menu-list']/ul/li[contains(.,'Configuration')]/a",
			RuntimeVariables.replace("Configuration"));

		for (int second = 0;; second++) {
			if (second >= 90) {
				fail("timeout");
			}

			try {
				if (selenium.isVisible("//iframe")) {
					break;
				}
			}
			catch (Exception e) {
			}

			Thread.sleep(1000);
		}

		selenium.selectFrame("//iframe");

		for (int second = 0;; second++) {
			if (second >= 90) {
				fail("timeout");
			}

			try {
				if (selenium.isElementPresent(
							"//script[contains(@src,'/liferay/navigation_interaction.js')]")) {
					break;
				}
			}
			catch (Exception e) {
			}

			Thread.sleep(1000);
		}

		for (int second = 0;; second++) {
			if (second >= 90) {
				fail("timeout");
			}

			try {
				if (selenium.isVisible("link=Scope")) {
					break;
				}
			}
			catch (Exception e) {
			}

			Thread.sleep(1000);
		}

		selenium.clickAt("link=Scope", RuntimeVariables.replace("Scope"));
		selenium.waitForPageToLoad("30000");
		selenium.select("//select[@id='_86_scopeType']",
			RuntimeVariables.replace("Select Page"));

		for (int second = 0;; second++) {
			if (second >= 90) {
				fail("timeout");
			}

			try {
				if (selenium.isVisible("//select[@id='_86_scopeLayoutUuid']")) {
					break;
				}
			}
			catch (Exception e) {
			}

			Thread.sleep(1000);
		}

		selenium.select("//select[@id='_86_scopeLayoutUuid']",
			RuntimeVariables.replace(
				"Current Page (Web Content Display Test Page2)"));
		selenium.clickAt("//input[@value='Save']",
			RuntimeVariables.replace("Save"));
		selenium.waitForPageToLoad("30000");
		assertEquals(RuntimeVariables.replace(
				"You have successfully updated the setup."),
			selenium.getText("//div[@class='portlet-msg-success']"));
		assertEquals("Select Page",
			selenium.getSelectedLabel("//select[@id='_86_scopeType']"));
		assertEquals("Current Page (Web Content Display Test Page2)",
			selenium.getSelectedLabel("//select[@id='_86_scopeLayoutUuid']"));
		selenium.selectFrame("relative=top");
	}
}