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

package com.liferay.portalweb.stagingsite.blogs;

import com.liferay.portalweb.portal.BaseTestCase;
import com.liferay.portalweb.portal.util.RuntimeVariables;

/**
 * @author Brian Wing Shun Chan
 */
public class AddUserPortalContentReviewerTest extends BaseTestCase {
	public void testAddUserPortalContentReviewer() throws Exception {
		int label = 1;

		while (label >= 1) {
			switch (label) {
			case 1:
				selenium.selectWindow("null");
				selenium.selectFrame("relative=top");
				selenium.open("/web/guest/home/");
				selenium.clickAt("//div[@id='dockbar']",
					RuntimeVariables.replace("Dockbar"));
				selenium.waitForElementPresent(
					"//script[contains(@src,'/aui/aui-editable/aui-editable-min.js')]");
				assertEquals(RuntimeVariables.replace("Go to"),
					selenium.getText("//li[@id='_145_mySites']/a/span"));
				selenium.mouseOver("//li[@id='_145_mySites']/a/span");
				selenium.waitForVisible("link=Control Panel");
				selenium.clickAt("link=Control Panel",
					RuntimeVariables.replace("Control Panel"));
				selenium.waitForPageToLoad("30000");
				selenium.clickAt("link=Users and Organizations",
					RuntimeVariables.replace("Users and Organizations"));
				selenium.waitForPageToLoad("30000");
				assertEquals(RuntimeVariables.replace("Add"),
					selenium.getText("//span[@title='Add']/ul/li/strong/a"));
				selenium.clickAt("//span[@title='Add']/ul/li/strong/a",
					RuntimeVariables.replace("Add"));
				selenium.waitForVisible(
					"//div[@class='lfr-component lfr-menu-list']/ul/li/a[contains(.,'User')]");
				assertEquals(RuntimeVariables.replace("User"),
					selenium.getText(
						"//div[@class='lfr-component lfr-menu-list']/ul/li/a[contains(.,'User')]"));
				selenium.click(RuntimeVariables.replace(
						"//div[@class='lfr-component lfr-menu-list']/ul/li/a[contains(.,'User')]"));
				selenium.waitForPageToLoad("30000");
				selenium.select("//select[@id='_125_prefixId']",
					RuntimeVariables.replace("label=Mr."));
				selenium.type("//input[@id='_125_screenName']",
					RuntimeVariables.replace("portalcontentreviewer"));
				selenium.type("//input[@id='_125_emailAddress']",
					RuntimeVariables.replace(
						"portalcontentreviewer@selenium.com"));
				selenium.type("//input[@id='_125_firstName']",
					RuntimeVariables.replace("Portal"));
				selenium.type("//input[@id='_125_middleName']",
					RuntimeVariables.replace("Content"));
				selenium.type("//input[@id='_125_lastName']",
					RuntimeVariables.replace("Reviewer"));
				selenium.select("//select[@id='_125_suffixId']",
					RuntimeVariables.replace("label=Phd."));
				selenium.select("//select[@id='_125_birthdaymonth']",
					RuntimeVariables.replace("label=April"));
				selenium.select("//select[@id='_125_birthdayday']",
					RuntimeVariables.replace("label=10"));
				selenium.select("//select[@id='_125_birthdayyear']",
					RuntimeVariables.replace("label=1986"));
				selenium.select("//select[@id='_125_male']",
					RuntimeVariables.replace("label=Male"));
				selenium.clickAt("//input[@value='Save']",
					RuntimeVariables.replace("Save"));
				selenium.waitForPageToLoad("30000");
				selenium.waitForVisible("//div[@class='portlet-msg-success']");
				assertEquals(RuntimeVariables.replace(
						"Your request completed successfully."),
					selenium.getText("//div[@class='portlet-msg-success']"));
				assertEquals("portalcontentreviewer",
					selenium.getValue("//input[@id='_125_screenName']"));
				assertEquals("portalcontentreviewer@selenium.com",
					selenium.getValue("//input[@id='_125_emailAddress']"));
				assertEquals("Portal",
					selenium.getValue("//input[@id='_125_firstName']"));
				assertEquals("Content",
					selenium.getValue("//input[@id='_125_middleName']"));
				assertEquals("Reviewer",
					selenium.getValue("//input[@id='_125_lastName']"));
				assertTrue(selenium.isPartialText(
						"//a[@id='_125_passwordLink']", "Password"));
				selenium.clickAt("//a[@id='_125_passwordLink']",
					RuntimeVariables.replace("Password"));
				selenium.waitForVisible("//input[@id='_125_password1']");
				selenium.type("//input[@id='_125_password1']",
					RuntimeVariables.replace("password"));
				selenium.type("//input[@id='_125_password2']",
					RuntimeVariables.replace("password"));
				selenium.clickAt("//input[@value='Save']",
					RuntimeVariables.replace("Save"));
				selenium.waitForPageToLoad("30000");
				selenium.waitForVisible("//div[@class='portlet-msg-success']");
				assertEquals(RuntimeVariables.replace(
						"Your request completed successfully."),
					selenium.getText("//div[@class='portlet-msg-success']"));
				assertTrue(selenium.isPartialText("//a[@id='_125_rolesLink']",
						"Roles"));
				selenium.clickAt("//a[@id='_125_rolesLink']",
					RuntimeVariables.replace("Roles"));
				selenium.waitForVisible("//div[6]/span/a/span");
				assertEquals(RuntimeVariables.replace("Select"),
					selenium.getText("//div[6]/span/a/span"));
				selenium.clickAt("//div[6]/span/a/span",
					RuntimeVariables.replace("Select"));
				Thread.sleep(5000);
				selenium.selectWindow("title=Users and Organizations");
				selenium.waitForVisible(
					"//tr[contains(.,'Portal Content Reviewer')]/td[1]/a");
				assertEquals(RuntimeVariables.replace("Portal Content Reviewer"),
					selenium.getText(
						"//tr[contains(.,'Portal Content Reviewer')]/td[1]/a"));
				selenium.clickAt("//tr[contains(.,'Portal Content Reviewer')]/td[1]/a",
					RuntimeVariables.replace("Portal Content Reviewer"));
				Thread.sleep(5000);
				selenium.selectWindow("null");
				assertEquals(RuntimeVariables.replace("Portal Content Reviewer"),
					selenium.getText(
						"//td[contains(.,'Portal Content Reviewer')]"));
				selenium.clickAt("//input[@value='Save']",
					RuntimeVariables.replace("Save"));
				selenium.waitForPageToLoad("30000");
				assertEquals(RuntimeVariables.replace(
						"Your request completed successfully."),
					selenium.getText("//div[@class='portlet-msg-success']"));
				assertEquals(RuntimeVariables.replace("Portal Content Reviewer"),
					selenium.getText(
						"//td[contains(.,'Portal Content Reviewer')]"));
				selenium.open("/web/guest/home/");
				selenium.clickAt("link=Sign Out",
					RuntimeVariables.replace("Sign Out"));
				selenium.waitForPageToLoad("30000");
				assertTrue(selenium.isVisible("//input[@value='Sign In']"));
				selenium.open("/web/guest/home/");
				selenium.waitForVisible("//input[@id='_58_login']");
				selenium.type("//input[@id='_58_login']",
					RuntimeVariables.replace(
						"portalcontentreviewer@selenium.com"));
				selenium.type("//input[@id='_58_password']",
					RuntimeVariables.replace("password"));

				boolean rememberMeCheckboxChecked1 = selenium.isChecked(
						"//input[@id='_58_rememberMeCheckbox']");

				if (rememberMeCheckboxChecked1) {
					label = 2;

					continue;
				}

				selenium.clickAt("//input[@id='_58_rememberMeCheckbox']",
					RuntimeVariables.replace("Remember Me Checkbox"));

			case 2:
				selenium.clickAt("//input[@value='Sign In']",
					RuntimeVariables.replace("Sign In"));
				selenium.waitForPageToLoad("30000");

				boolean iAgreeVisible1 = selenium.isElementPresent(
						"//input[@value='I Agree']");

				if (!iAgreeVisible1) {
					label = 3;

					continue;
				}

				selenium.clickAt("//input[@value='I Agree']",
					RuntimeVariables.replace("I Agree"));
				selenium.waitForPageToLoad("30000");

			case 3:
				Thread.sleep(5000);
				selenium.type("password1", RuntimeVariables.replace("test"));
				selenium.type("password2", RuntimeVariables.replace("test"));
				selenium.clickAt("//input[@value='Save']",
					RuntimeVariables.replace("Save"));
				selenium.waitForPageToLoad("30000");

				boolean passwordReminderVisible1 = selenium.isElementPresent(
						"//input[@id='reminderQueryAnswer']");

				if (!passwordReminderVisible1) {
					label = 4;

					continue;
				}

				assertEquals(RuntimeVariables.replace(
						"Please choose a reminder query."),
					selenium.getText("//form/div[1]"));
				selenium.type("//input[@id='reminderQueryAnswer']",
					RuntimeVariables.replace("test"));
				selenium.clickAt("//input[@value='Save']",
					RuntimeVariables.replace("Save"));
				selenium.waitForPageToLoad("30000");

			case 4:
				selenium.open("/web/guest/home/");
				selenium.waitForVisible("link=Sign Out");
				selenium.clickAt("link=Sign Out",
					RuntimeVariables.replace("Sign Out"));
				selenium.waitForPageToLoad("30000");
				assertTrue(selenium.isVisible("//input[@value='Sign In']"));
				selenium.open("/web/guest/home/");
				selenium.waitForVisible("//input[@id='_58_login']");
				selenium.type("//input[@id='_58_login']",
					RuntimeVariables.replace("test@liferay.com"));
				selenium.type("//input[@id='_58_password']",
					RuntimeVariables.replace("test"));

				boolean rememberMeCheckboxChecked2 = selenium.isChecked(
						"//input[@id='_58_rememberMeCheckbox']");

				if (rememberMeCheckboxChecked2) {
					label = 5;

					continue;
				}

				selenium.clickAt("//input[@id='_58_rememberMeCheckbox']",
					RuntimeVariables.replace("Remember Me Checkbox"));

			case 5:
				selenium.clickAt("//input[@value='Sign In']",
					RuntimeVariables.replace("Sign In"));
				selenium.waitForPageToLoad("30000");

			case 100:
				label = -1;
			}
		}
	}
}