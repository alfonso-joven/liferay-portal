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

package com.liferay.portalweb.portal.controlpanel.webcontent.wctemplate.advancedsearchwctemplate;

import com.liferay.portalweb.portal.BaseTestCase;
import com.liferay.portalweb.portal.util.RuntimeVariables;

/**
 * @author Brian Wing Shun Chan
 */
public class AdvancedSearchWCTemplateNameTest extends BaseTestCase {
	public void testAdvancedSearchWCTemplateName() throws Exception {
		int label = 1;

		while (label >= 1) {
			switch (label) {
			case 1:
				selenium.open("/web/guest/home/");
				selenium.clickAt("//div[@id='dockbar']",
					RuntimeVariables.replace("Dockbar"));

				for (int second = 0;; second++) {
					if (second >= 90) {
						fail("timeout");
					}

					try {
						if (selenium.isElementPresent(
									"//script[contains(@src,'/aui/aui-editable/aui-editable-min.js')]")) {
							break;
						}
					}
					catch (Exception e) {
					}

					Thread.sleep(1000);
				}

				assertEquals(RuntimeVariables.replace("Go to"),
					selenium.getText("//li[@id='_145_mySites']/a/span"));
				selenium.mouseOver("//li[@id='_145_mySites']/a/span");

				for (int second = 0;; second++) {
					if (second >= 90) {
						fail("timeout");
					}

					try {
						if (selenium.isVisible("link=Control Panel")) {
							break;
						}
					}
					catch (Exception e) {
					}

					Thread.sleep(1000);
				}

				selenium.clickAt("link=Control Panel",
					RuntimeVariables.replace("Control Panel"));
				selenium.waitForPageToLoad("30000");
				selenium.clickAt("link=Web Content",
					RuntimeVariables.replace("Web Content"));
				selenium.waitForPageToLoad("30000");
				selenium.clickAt("link=Templates",
					RuntimeVariables.replace("Templates"));
				selenium.waitForPageToLoad("30000");

				boolean advancedVisible = selenium.isVisible(
						"//a[.='Advanced \u00bb']");

				if (!advancedVisible) {
					label = 2;

					continue;
				}

				selenium.clickAt("//a[.='Advanced \u00bb']",
					RuntimeVariables.replace("Advanced \u00bb"));

			case 2:

				for (int second = 0;; second++) {
					if (second >= 90) {
						fail("timeout");
					}

					try {
						if (selenium.isVisible("//input[@id='_15_name']")) {
							break;
						}
					}
					catch (Exception e) {
					}

					Thread.sleep(1000);
				}

				selenium.type("//input[@id='_15_name']",
					RuntimeVariables.replace("Name"));
				selenium.clickAt("xPath=(//input[@value='Search'])[2]",
					RuntimeVariables.replace("Search"));
				selenium.waitForPageToLoad("30000");
				selenium.type("//input[@id='_15_name']",
					RuntimeVariables.replace(""));
				assertEquals(RuntimeVariables.replace("WC Template Name"),
					selenium.getText("//td[3]/a"));
				assertEquals(RuntimeVariables.replace("WC Template Description"),
					selenium.getText("//td[4]/a"));
				selenium.type("//input[@id='_15_name']",
					RuntimeVariables.replace("Name1"));
				selenium.clickAt("xPath=(//input[@value='Search'])[2]",
					RuntimeVariables.replace("Search"));
				selenium.waitForPageToLoad("30000");
				selenium.type("//input[@id='_15_name']",
					RuntimeVariables.replace(""));
				selenium.clickAt("//a[.='\u00ab Basic']",
					RuntimeVariables.replace("\u00ab Basic"));
				assertFalse(selenium.isTextPresent("WC Template Name"));
				assertFalse(selenium.isTextPresent("WC Template Description"));
				assertEquals(RuntimeVariables.replace(
						"No templates were found."),
					selenium.getText("//div[@class='portlet-msg-info']"));

			case 100:
				label = -1;
			}
		}
	}
}