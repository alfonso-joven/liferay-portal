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

package com.liferay.portalweb.permissions.blogs.blogsentry.addblogsentry.orgrole;

import com.liferay.portalweb.portal.BaseTestCase;
import com.liferay.portalweb.portal.util.RuntimeVariables;

/**
 * @author Brian Wing Shun Chan
 */
public class DefineOrgRoleContentBlogsAddEntryTest extends BaseTestCase {
	public void testDefineOrgRoleContentBlogsAddEntry()
		throws Exception {
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
		selenium.clickAt("link=Roles", RuntimeVariables.replace("Roles"));
		selenium.waitForPageToLoad("30000");
		loadRequiredJavaScriptModules();
		selenium.type("//input[@id='_128_keywords']",
			RuntimeVariables.replace("Orgrole"));
		selenium.clickAt("//input[@value='Search']",
			RuntimeVariables.replace("Search"));
		selenium.waitForPageToLoad("30000");
		loadRequiredJavaScriptModules();
		assertEquals(RuntimeVariables.replace("Roles Orgrole Name"),
			selenium.getText(
				"//td[@id='_128_ocerSearchContainer_col-name_row-1']/a"));
		assertEquals(RuntimeVariables.replace("Organization"),
			selenium.getText(
				"//td[@id='_128_ocerSearchContainer_col-type_row-1']/a"));
		selenium.clickAt("//td[@id='_128_ocerSearchContainer_col-name_row-1']/a",
			RuntimeVariables.replace("Roles Orgrole Name"));
		selenium.waitForPageToLoad("30000");
		loadRequiredJavaScriptModules();
		selenium.clickAt("link=Define Permissions",
			RuntimeVariables.replace("Define Permissions"));
		selenium.waitForPageToLoad("30000");
		loadRequiredJavaScriptModules();
		assertEquals(RuntimeVariables.replace("Roles Orgrole Name"),
			selenium.getText("//h1[@class='header-title']"));
		selenium.select("//select[@id='_128_add-permissions']",
			RuntimeVariables.replace("label=Blogs"));
		selenium.waitForPageToLoad("30000");
		loadRequiredJavaScriptModules();
		assertFalse(selenium.isChecked(
				"//input[@value='com.liferay.portlet.blogsADD_ENTRY']"));
		selenium.check("//input[@value='com.liferay.portlet.blogsADD_ENTRY']");
		assertTrue(selenium.isChecked(
				"//input[@value='com.liferay.portlet.blogsADD_ENTRY']"));
		selenium.clickAt("//input[@value='Save']",
			RuntimeVariables.replace("Save"));
		selenium.waitForPageToLoad("30000");
		loadRequiredJavaScriptModules();
		assertEquals(RuntimeVariables.replace(
				"The role permissions were updated."),
			selenium.getText("//div[@class='portlet-msg-success']"));
		assertEquals(RuntimeVariables.replace("Summary"),
			selenium.getText("//section[@id='portlet_128']/div/div/div/h3"));
		assertEquals(RuntimeVariables.replace("Blogs"),
			selenium.getText(
				"//tr[contains(.,'Blogs')]/td[@headers='_128_ocerSearchContainer_col-resource-set']/a"));
		assertEquals(RuntimeVariables.replace("Blogs"),
			selenium.getText(
				"//tr[contains(.,'Blogs')]/td[@headers='_128_ocerSearchContainer_col-resource']"));
		assertEquals(RuntimeVariables.replace("Add Entry"),
			selenium.getText(
				"//tr[contains(.,'Add Entry')]/td[@headers='_128_ocerSearchContainer_col-action']"));
	}
}