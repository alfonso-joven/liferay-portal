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

package com.liferay.portalweb.portal.controlpanel.messageboards;

import com.liferay.portalweb.portal.BaseTestCase;
import com.liferay.portalweb.portal.util.RuntimeVariables;

/**
 * @author Brian Wing Shun Chan
 */
public class AddNullEntryTest extends BaseTestCase {
	public void testAddNullEntry() throws Exception {
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
		selenium.clickAt("link=Message Boards",
			RuntimeVariables.replace("Message Boards"));
		selenium.waitForPageToLoad("30000");
		assertEquals(RuntimeVariables.replace("T\u00e9st Cat\u00e9gory"),
			selenium.getText("//td[2]/a/strong"));
		selenium.clickAt("//td[2]/a/strong",
			RuntimeVariables.replace("T\u00e9st Cat\u00e9gory"));
		selenium.waitForPageToLoad("30000");
		selenium.clickAt("//input[@value='Post New Thread']",
			RuntimeVariables.replace("Post New Thread"));
		selenium.waitForPageToLoad("30000");
		selenium.type("//input[@id='_162_subject']",
			RuntimeVariables.replace("Null Test Entry"));
		selenium.waitForElementPresent(
			"//textarea[@id='_162_editor' and @style='display: none;']");
		assertEquals(RuntimeVariables.replace("Source"),
			selenium.getText("//a[@id='cke_33']"));
		selenium.clickAt("//a[@id='cke_33']", RuntimeVariables.replace("Source"));
		selenium.waitForVisible("//td[@id='cke_contents__162_editor']/textarea");
		selenium.type("//td[@id='cke_contents__162_editor']/textarea",
			RuntimeVariables.replace(""));
		assertEquals(RuntimeVariables.replace("Source"),
			selenium.getText("//a[@id='cke_33']"));
		selenium.clickAt("//a[@id='cke_33']", RuntimeVariables.replace("Source"));
		selenium.waitForElementPresent(
			"//textarea[@id='_162_editor' and @style='display: none;']");
		selenium.waitForVisible("//td[@id='cke_contents__162_editor']/iframe");
		selenium.selectFrame("//td[@id='cke_contents__162_editor']/iframe");
		selenium.waitForText("//body", "");
		selenium.selectFrame("relative=top");
		selenium.clickAt("//input[@value='Publish']",
			RuntimeVariables.replace("Publish"));
		selenium.waitForPageToLoad("30000");
		assertEquals(RuntimeVariables.replace(
				"Your request completed successfully."),
			selenium.getText("//div[@class='portlet-msg-success']"));
		assertEquals(RuntimeVariables.replace("Null Test Entry"),
			selenium.getText("//div[contains(@class,'taglib-header')]/h1/span"));
		assertEquals(RuntimeVariables.replace("Null Test Entry"),
			selenium.getText("//div/a/strong"));
		assertEquals(RuntimeVariables.replace("Null Test Entry"),
			selenium.getText("//div[@class='thread-body']"));
	}
}