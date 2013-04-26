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

package com.liferay.portalweb.portlet.softwarecatalog.portlet.addportletduplicate;

import com.liferay.portalweb.portal.BaseTestCase;
import com.liferay.portalweb.portal.util.RuntimeVariables;

/**
 * @author Brian Wing Shun Chan
 */
public class AddPortletSCDuplicateTest extends BaseTestCase {
	public void testAddPortletSCDuplicate() throws Exception {
		selenium.selectWindow("null");
		selenium.selectFrame("relative=top");
		selenium.open("/web/guest/home/");
		selenium.clickAt("link=Software Catalog Test Page",
			RuntimeVariables.replace("Software Catalog Test Page"));
		selenium.waitForPageToLoad("30000");
		selenium.clickAt("//div[@id='dockbar']",
			RuntimeVariables.replace("Dockbar"));
		selenium.waitForElementPresent(
			"//script[contains(@src,'/aui/aui-editable/aui-editable-min.js')]");
		assertEquals(RuntimeVariables.replace("Add"),
			selenium.getText("//li[@id='_145_addContent']/a/span"));
		selenium.mouseOver("//li[@id='_145_addContent']/a/span");
		selenium.waitForVisible("//a[@id='_145_addApplication']");
		assertTrue(selenium.isPartialText("//a[@id='_145_addApplication']",
				"More"));
		selenium.clickAt("//a[@id='_145_addApplication']",
			RuntimeVariables.replace("More"));
		selenium.waitForElementPresent(
			"//script[contains(@src,'/aui/aui-live-search/aui-live-search-min.js')]");
		selenium.waitForVisible("//input[@id='layout_configuration_content']");
		selenium.type("//input[@id='layout_configuration_content']",
			RuntimeVariables.replace("s"));
		selenium.keyDown("//input[@id='layout_configuration_content']",
			RuntimeVariables.replace("\\13"));
		selenium.keyUp("//input[@id='layout_configuration_content']",
			RuntimeVariables.replace("\\13"));
		selenium.waitForVisible("//div[@title='Software Catalog']");
		assertFalse(selenium.isVisible("//div[@title='Software Catalog']/p/a"));
	}
}