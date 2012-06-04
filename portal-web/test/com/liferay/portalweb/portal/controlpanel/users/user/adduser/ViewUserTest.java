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

package com.liferay.portalweb.portal.controlpanel.users.user.adduser;

import com.liferay.portalweb.portal.BaseTestCase;
import com.liferay.portalweb.portal.util.RuntimeVariables;

/**
 * @author Brian Wing Shun Chan
 */
public class ViewUserTest extends BaseTestCase {
	public void testViewUser() throws Exception {
		int label = 1;

		while (label >= 1) {
			switch (label) {
			case 1:
				selenium.open("/web/guest/home/");
				loadRequiredJavaScriptModules();
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
				loadRequiredJavaScriptModules();
				selenium.clickAt("link=Users and Organizations",
					RuntimeVariables.replace("Users and Organizations"));
				selenium.waitForPageToLoad("30000");
				loadRequiredJavaScriptModules();
				selenium.clickAt("link=Search All Users",
					RuntimeVariables.replace("Search All Users"));
				selenium.waitForPageToLoad("30000");
				loadRequiredJavaScriptModules();

				boolean basicVisible = selenium.isVisible("link=\u00ab Basic");

				if (!basicVisible) {
					label = 2;

					continue;
				}

				selenium.clickAt("link=\u00ab Basic",
					RuntimeVariables.replace("\u00ab Basic"));

				for (int second = 0;; second++) {
					if (second >= 90) {
						fail("timeout");
					}

					try {
						if (selenium.isVisible("//input[@name='_125_keywords']")) {
							break;
						}
					}
					catch (Exception e) {
					}

					Thread.sleep(1000);
				}

			case 2:
				selenium.type("//input[@name='_125_keywords']",
					RuntimeVariables.replace("usersn"));
				selenium.clickAt("//input[@value='Search']",
					RuntimeVariables.replace("Search"));
				selenium.waitForPageToLoad("30000");
				loadRequiredJavaScriptModules();
				assertEquals(RuntimeVariables.replace("userfn"),
					selenium.getText("//td[2]/a"));
				assertEquals(RuntimeVariables.replace("userln"),
					selenium.getText("//td[3]/a"));
				assertEquals(RuntimeVariables.replace("usersn"),
					selenium.getText("//td[4]/a"));
				selenium.clickAt("//td[2]/a", RuntimeVariables.replace("userfn"));
				selenium.waitForPageToLoad("30000");
				loadRequiredJavaScriptModules();
				assertEquals(RuntimeVariables.replace("userfn userln"),
					selenium.getText("//div[2]/h1/span"));
				assertEquals("usersn",
					selenium.getValue("//input[@id='_125_screenName']"));
				assertEquals("userea@liferay.com",
					selenium.getValue("//input[@id='_125_emailAddress']"));
				assertEquals("userfn",
					selenium.getValue("//input[@id='_125_firstName']"));
				assertEquals("userln",
					selenium.getValue("//input[@id='_125_lastName']"));
				assertEquals("1888",
					selenium.getValue("//select[@id='_125_birthdayYear']"));

			case 100:
				label = -1;
			}
		}
	}
}