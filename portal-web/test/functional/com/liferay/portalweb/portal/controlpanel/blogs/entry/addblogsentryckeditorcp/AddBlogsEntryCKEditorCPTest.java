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

package com.liferay.portalweb.portal.controlpanel.blogs.entry.addblogsentryckeditorcp;

import com.liferay.portalweb.portal.BaseTestCase;
import com.liferay.portalweb.portal.util.RuntimeVariables;

/**
 * @author Brian Wing Shun Chan
 */
public class AddBlogsEntryCKEditorCPTest extends BaseTestCase {
	public void testAddBlogsEntryCKEditorCP() throws Exception {
		selenium.selectWindow("null");
		selenium.selectFrame("relative=top");
		selenium.open("/web/guest/home/");
		selenium.waitForElementPresent("link=Control Panel");
		selenium.clickAt("link=Control Panel",
			RuntimeVariables.replace("Control Panel"));
		selenium.waitForPageToLoad("30000");
		selenium.clickAt("link=Blogs", RuntimeVariables.replace("Blogs"));
		selenium.waitForPageToLoad("30000");
		selenium.clickAt("link=Add", RuntimeVariables.replace("Add"));
		selenium.waitForPageToLoad("30000");
		selenium.type("//input[@id='_161_title']",
			RuntimeVariables.replace("Blogs Entry Title"));
		Thread.sleep(5000);
		selenium.waitForVisible("//td[@id='cke_contents__161_editor']/iframe");
		selenium.selectFrame("//td[@id='cke_contents__161_editor']/iframe");
		selenium.typeKeys("//body[contains(@class,'html-editor')]",
			RuntimeVariables.replace("CK Editor Content"));
		selenium.selectFrame("relative=top");
		selenium.clickAt("//input[@value='Publish']",
			RuntimeVariables.replace("Publish"));
		selenium.waitForPageToLoad("30000");
		assertEquals(RuntimeVariables.replace(
				"Your request completed successfully."),
			selenium.getText("//div[@class='portlet-msg-success']"));
		assertEquals(RuntimeVariables.replace("Blogs Entry Title"),
			selenium.getText("//td[2]/a"));
		selenium.clickAt("//td[2]/a",
			RuntimeVariables.replace("Blogs Entry Title"));
		selenium.waitForPageToLoad("30000");
		assertEquals(RuntimeVariables.replace("Blogs Entry Title"),
			selenium.getText("//h1[@class='header-title']/span"));
		assertEquals(RuntimeVariables.replace("CK Editor Content"),
			selenium.getText("//div[@class='entry-body']/p"));
	}
}