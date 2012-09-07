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

package com.liferay.portalweb.properties.webcontent.mailintegration.addwcwebcontentemail;

import com.liferay.portalweb.portal.BaseTestCase;
import com.liferay.portalweb.portal.util.RuntimeVariables;

/**
 * @author Brian Wing Shun Chan
 */
public class DefineRoleWebContentEditorCPTest extends BaseTestCase {
	public void testDefineRoleWebContentEditorCP() throws Exception {
		selenium.selectWindow("null");
		selenium.selectFrame("relative=top");
		selenium.open("/web/guest/home/");
		assertEquals(RuntimeVariables.replace("Go to"),
			selenium.getText("//li[@id='_145_mySites']/a/span"));
		selenium.mouseOver("//li[@id='_145_mySites']/a/span");
		selenium.waitForVisible("link=Control Panel");
		selenium.clickAt("link=Control Panel",
			RuntimeVariables.replace("Control Panel"));
		selenium.waitForPageToLoad("30000");
		selenium.clickAt("link=Roles", RuntimeVariables.replace("Roles"));
		selenium.waitForPageToLoad("30000");
		selenium.type("//input[@id='_128_keywords']",
			RuntimeVariables.replace("Web Content Editor"));
		selenium.clickAt("//input[@value='Search']",
			RuntimeVariables.replace("Search"));
		selenium.waitForPageToLoad("30000");
		assertEquals(RuntimeVariables.replace("Web Content Editor"),
			selenium.getText("//td/a"));
		assertEquals(RuntimeVariables.replace("Regular"),
			selenium.getText("//td[2]/a"));
		assertEquals(RuntimeVariables.replace(
				"Web Content Editors are users who have full permissions and usage over all available Web Content"),
			selenium.getText("//td[3]/a"));
		selenium.clickAt("//td/a",
			RuntimeVariables.replace("Web Content Editor"));
		selenium.waitForPageToLoad("30000");
		assertEquals(RuntimeVariables.replace("Web Content Editor"),
			selenium.getText("//h1[@class='header-title']"));
		selenium.clickAt("link=Define Permissions",
			RuntimeVariables.replace("Define Permissions"));
		selenium.waitForPageToLoad("30000");
		selenium.select("//select[@id='_128_add-permissions']",
			RuntimeVariables.replace("label=Web Content"));
		selenium.waitForPageToLoad("30000");
		assertEquals(RuntimeVariables.replace("Web Content"),
			selenium.getText("//h3"));
		assertFalse(selenium.isChecked(
				"//input[@name='_128_rowIds' and @value='com.liferay.portlet.journalSUBSCRIBE']"));
		selenium.check(
			"//input[@name='_128_rowIds' and @value='com.liferay.portlet.journalSUBSCRIBE']");
		assertTrue(selenium.isChecked(
				"//input[@name='_128_rowIds' and @value='com.liferay.portlet.journalSUBSCRIBE']"));
		selenium.clickAt("//input[@value='Save']",
			RuntimeVariables.replace("Save"));
		selenium.waitForPageToLoad("30000");
		assertEquals(RuntimeVariables.replace(
				"The role permissions were updated."),
			selenium.getText("//div[@class='portlet-msg-success']"));
		selenium.clickAt("link=Define Permissions",
			RuntimeVariables.replace("Define Permissions"));
		selenium.waitForPageToLoad("30000");
		selenium.select("//select[@id='_128_add-permissions']",
			RuntimeVariables.replace("index=105"));
		selenium.waitForPageToLoad("30000");
		assertEquals(RuntimeVariables.replace("Web Content"),
			selenium.getText("//h3"));
		assertFalse(selenium.isChecked(
				"//input[@value='15ACCESS_IN_CONTROL_PANEL']"));
		selenium.check("//input[@value='15ACCESS_IN_CONTROL_PANEL']");
		assertTrue(selenium.isChecked(
				"//input[@value='15ACCESS_IN_CONTROL_PANEL']"));
		assertFalse(selenium.isChecked("//input[@value='15VIEW']"));
		selenium.check("//input[@value='15VIEW']");
		assertTrue(selenium.isChecked("//input[@value='15VIEW']"));
		selenium.clickAt("//input[@value='Save']",
			RuntimeVariables.replace("Save"));
		selenium.waitForPageToLoad("30000");
		assertEquals(RuntimeVariables.replace(
				"The role permissions were updated."),
			selenium.getText("//div[@class='portlet-msg-success']"));
		assertEquals(RuntimeVariables.replace("Web Content"),
			selenium.getText("//td/a"));
		assertEquals(RuntimeVariables.replace("Access in Control Panel"),
			selenium.getText("//tr[3]/td[3]"));
		assertEquals(RuntimeVariables.replace("Portal"),
			selenium.getText("//tr[3]/td[4]"));
		assertEquals(RuntimeVariables.replace("Web Content"),
			selenium.getText("//tr[4]/td/a"));
		assertEquals(RuntimeVariables.replace("View"),
			selenium.getText("//tr[4]/td[3]"));
		assertEquals(RuntimeVariables.replace("Portal"),
			selenium.getText("//tr[4]/td[4]"));
		assertEquals(RuntimeVariables.replace("Web Content"),
			selenium.getText("//tr[5]/td/a"));
		assertEquals(RuntimeVariables.replace("Web Content Management"),
			selenium.getText("//tr[5]/td[2]"));
		assertEquals(RuntimeVariables.replace("Subscribe"),
			selenium.getText("//tr[5]/td[3]"));
		assertEquals(RuntimeVariables.replace("Portal"),
			selenium.getText("//tr[5]/td[4]"));
	}
}