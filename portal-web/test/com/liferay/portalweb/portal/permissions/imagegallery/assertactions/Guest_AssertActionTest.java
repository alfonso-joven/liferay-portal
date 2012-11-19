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

package com.liferay.portalweb.portal.permissions.imagegallery.assertactions;

import com.liferay.portalweb.portal.BaseTestCase;
import com.liferay.portalweb.portal.util.RuntimeVariables;

/**
 * @author Brian Wing Shun Chan
 */
public class Guest_AssertActionTest extends BaseTestCase {
	public void testGuest_AssertAction() throws Exception {
		selenium.selectWindow("null");
		selenium.selectFrame("relative=top");
		selenium.open("/web/guest/home/");
		selenium.waitForElementPresent(
			"link=Media Gallery Permissions Test Page");
		selenium.clickAt("link=Media Gallery Permissions Test Page",
			RuntimeVariables.replace("Media Gallery Permissions Test Page"));
		selenium.waitForPageToLoad("30000");
		assertTrue(selenium.isElementNotPresent("link=Look and Feel"));
		assertTrue(selenium.isElementNotPresent("link=Configuration"));
		assertTrue(selenium.isElementNotPresent("link=Export / Import"));
		assertTrue(selenium.isElementNotPresent("//img[@title='Remove']"));
		assertTrue(selenium.isElementNotPresent(
				"//div[@class='lfr-component lfr-menu-list lfr-menu-expanded align-right null']/ul/li/a[contains(.,'Add Subfolder')]"));
		assertTrue(selenium.isElementNotPresent(
				"//div[@class='lfr-component lfr-menu-list lfr-menu-expanded align-right null']/ul/li/a[contains(.,'Permissions')]"));
		assertTrue(selenium.isElementNotPresent(
				"//div[@class='lfr-component lfr-menu-list lfr-menu-expanded align-right null']/ul/li/a[contains(.,'Add Folder')]"));
		assertTrue(selenium.isElementNotPresent(
				"//div[@class='lfr-component lfr-menu-list lfr-menu-expanded align-right null']/ul/li/a[contains(.,'Add Repository')]"));
		assertTrue(selenium.isElementNotPresent(
				"//div[@class='lfr-component lfr-menu-list lfr-menu-expanded align-right null']/ul/li/a[contains(.,'Add Shortcut')]"));
		assertEquals(RuntimeVariables.replace(
				"Media Gallery Permissions Test Folder"),
			selenium.getText(
				"//a[@title='Media Gallery Permissions Test Folder - ']"));
		selenium.clickAt("//a[@title='Media Gallery Permissions Test Folder - ']",
			RuntimeVariables.replace("Media Gallery Permissions Test Folder"));
		selenium.waitForPageToLoad("30000");
		assertTrue(selenium.isElementNotPresent(
				"//div[@class='lfr-component lfr-menu-list lfr-menu-expanded align-right null']/ul/li/a[contains(.,'Add Subfolder')]"));
		assertTrue(selenium.isElementNotPresent(
				"//div[@class='lfr-component lfr-menu-list lfr-menu-expanded align-right null']/ul/li/a[contains(.,'Multiple Media')]"));
		assertTrue(selenium.isElementNotPresent(
				"//div[@class='lfr-component lfr-menu-list lfr-menu-expanded align-right null']/ul/li/a[contains(.,'Add Media')]"));
		selenium.open("/web/guest/home/");
		selenium.clickAt("link=Media Gallery Permissions Test Page",
			RuntimeVariables.replace("Media Gallery Permissions Test Page"));
		selenium.waitForPageToLoad("30000");
		selenium.clickAt("link=Recent", RuntimeVariables.replace("Recent"));
		selenium.waitForPageToLoad("30000");
		assertTrue(selenium.isTextPresent("Permissions Image 2 Test"));
	}
}