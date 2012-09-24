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

package com.liferay.portalweb.portal.dbupgrade.sampledata6011.documentlibrary.shortcut;

import com.liferay.portalweb.portal.BaseTestCase;
import com.liferay.portalweb.portal.util.RuntimeVariables;

/**
 * @author Brian Wing Shun Chan
 */
public class AddShortcutTest extends BaseTestCase {
	public void testAddShortcut() throws Exception {
		selenium.selectWindow("null");
		selenium.selectFrame("relative=top");
		selenium.open("/web/guest/home/");
		selenium.clickAt("//div[@id='dockbar']",
			RuntimeVariables.replace("Dockbar"));
		selenium.waitForElementPresent(
			"//script[contains(@src,'/aui/aui-editable/aui-editable-min.js')]");
		assertEquals(RuntimeVariables.replace("Manage"),
			selenium.getText("//li[@id='_145_manageContent']/a/span"));
		selenium.mouseOver("//li[@id='_145_manageContent']/a/span");
		selenium.waitForVisible("link=Control Panel");
		selenium.clickAt("link=Control Panel",
			RuntimeVariables.replace("Control Panel"));
		selenium.waitForPageToLoad("30000");
		selenium.clickAt("link=Communities",
			RuntimeVariables.replace("Communities"));
		selenium.waitForPageToLoad("30000");
		selenium.type("//input[@id='_134_name']",
			RuntimeVariables.replace("Document Library Shortcut Community"));
		selenium.clickAt("//input[@value='Search']",
			RuntimeVariables.replace("Search"));
		selenium.waitForPageToLoad("30000");
		selenium.clickAt("//td[2]/a", RuntimeVariables.replace("Open"));
		selenium.waitForPageToLoad("30000");
		selenium.clickAt("link=Document Library Page",
			RuntimeVariables.replace(""));
		selenium.waitForPageToLoad("30000");
		selenium.clickAt("//tr[4]/td[1]/a[2]/strong",
			RuntimeVariables.replace("Test2 Folder2"));
		selenium.waitForPageToLoad("30000");
		assertEquals(RuntimeVariables.replace("Add Shortcut"),
			selenium.getText("//div[2]/ul/li[6]/a"));
		selenium.clickAt("//div[2]/ul/li[6]/a",
			RuntimeVariables.replace("Add Shortcut"));
		selenium.waitForPageToLoad("30000");
		selenium.clickAt("//div/span[2]/span/input",
			RuntimeVariables.replace("Community"));
		selenium.waitForPopUp("toGroup", RuntimeVariables.replace("30000"));
		selenium.selectWindow("toGroup");
		selenium.click("link=Document Library Shortcut Community");
		selenium.selectWindow("null");
		selenium.waitForText("//input[@id='_20_toGroupName']",
			"Document Library Shortcut Community");
		assertEquals(RuntimeVariables.replace(
				"Document Library Shortcut Community"),
			selenium.getText("//input[@id='_20_toGroupName']"));
		selenium.clickAt("//input[@id='_20_selectToFileEntryButton']",
			RuntimeVariables.replace("Document"));
		selenium.waitForPopUp("toGroup", RuntimeVariables.replace("30000"));
		selenium.selectWindow("toGroup");
		selenium.click(RuntimeVariables.replace("link=Test1 Folder1"));
		selenium.waitForPageToLoad("30000");
		selenium.click("//td[1]/a");
		selenium.selectWindow("null");
		selenium.waitForText("//input[@id='_20_toFileEntryTitle']",
			"Test1 Document1.txt");
		assertEquals(RuntimeVariables.replace("Test1 Document1.txt"),
			selenium.getText("//input[@id='_20_toFileEntryTitle']"));
		selenium.clickAt("//input[@value='Save']",
			RuntimeVariables.replace("Save"));
		selenium.waitForPageToLoad("30000");
		assertEquals(RuntimeVariables.replace(
				"Your request processed successfully."),
			selenium.getText("//div[@class='portlet-msg-success']"));
		assertEquals(RuntimeVariables.replace("Test1 Document1.txt"),
			selenium.getText("//a/span/span"));
		assertEquals(RuntimeVariables.replace("This is test1 document1."),
			selenium.getText("//a/div"));
	}
}