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

package com.liferay.portalweb.portal.controlpanel.webcontent.wcstructure.editwcsubstructuresdefaultvalues;

import com.liferay.portalweb.portal.BaseTestCase;
import com.liferay.portalweb.portal.util.RuntimeVariables;

/**
 * @author Brian Wing Shun Chan
 */
public class ViewEditWCSubstucture1DefaultValueTest extends BaseTestCase {
	public void testViewEditWCSubstucture1DefaultValue()
		throws Exception {
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

		for (int second = 0;; second++) {
			if (second >= 90) {
				fail("timeout");
			}

			try {
				if (selenium.isVisible(
							"//span[@title='Add']/ul/li/strong/a/span")) {
					break;
				}
			}
			catch (Exception e) {
			}

			Thread.sleep(1000);
		}

		assertEquals(RuntimeVariables.replace("Add"),
			selenium.getText("//span[@title='Add']/ul/li/strong/a/span"));
		selenium.clickAt("//span[@title='Add']/ul/li/strong/a/span",
			RuntimeVariables.replace("Add"));

		for (int second = 0;; second++) {
			if (second >= 90) {
				fail("timeout");
			}

			try {
				if (RuntimeVariables.replace("WC SubStructure1 Name")
										.equals(selenium.getText(
								"//div[@class='lfr-component lfr-menu-list']/ul/li[contains(.,'WC SubStructure1 Name')]/a"))) {
					break;
				}
			}
			catch (Exception e) {
			}

			Thread.sleep(1000);
		}

		assertEquals(RuntimeVariables.replace("WC SubStructure1 Name"),
			selenium.getText(
				"//div[@class='lfr-component lfr-menu-list']/ul/li[contains(.,'WC SubStructure1 Name')]/a"));
		selenium.clickAt("//div[@class='lfr-component lfr-menu-list']/ul/li[contains(.,'WC SubStructure1 Name')]/a",
			RuntimeVariables.replace("WC SubStructure1 Name"));
		selenium.waitForPageToLoad("30000");
		assertEquals(RuntimeVariables.replace("Give the Web Content a Name"),
			selenium.getText("//h1[@class='header-title']"));
		assertEquals(RuntimeVariables.replace("WC SubStructure1 Name"),
			selenium.getText("//span[@id='_15_structureNameLabel']"));
		assertEquals(RuntimeVariables.replace("Title (Required)"),
			selenium.getText("//label[@for='_15_null_en_US']"));
		assertEquals("Give the Web Content a Name",
			selenium.getValue("//input[@id='_15_title_en_US']"));
		assertEquals(RuntimeVariables.replace("Head"),
			selenium.getText("//label[@for='Head']"));
		assertEquals("Article's Title Here",
			selenium.getValue("//input[@id='Head']"));
		assertEquals(RuntimeVariables.replace("Subtitle"),
			selenium.getText("//label[@for='Subtitle']"));
		assertEquals("Article's Subtitle Here",
			selenium.getValue("//input[@id='Subtitle']"));
		assertEquals(RuntimeVariables.replace("Content"),
			selenium.getText("//label[@for='Content']"));
		assertEquals("Enter Article Content",
			selenium.getValue("//textarea[@id='Content']"));
		assertEquals(RuntimeVariables.replace("ImageBox"),
			selenium.getText("//label[@for='ImageBox']"));
		assertEquals(RuntimeVariables.replace("Image"),
			selenium.getText("//label[@for='Image']"));
		assertEquals(RuntimeVariables.replace("Summary"),
			selenium.getText("//label[@for='Summary']"));
		assertEquals(RuntimeVariables.replace("Photographer"),
			selenium.getText("//label[@for='Photographer']"));
		assertEquals(RuntimeVariables.replace("Documents and Media"),
			selenium.getText("//label[@for='Documents and Media']"));
		assertEquals(RuntimeVariables.replace("Extra"),
			selenium.getText("//label[@for='Extra']"));
	}
}