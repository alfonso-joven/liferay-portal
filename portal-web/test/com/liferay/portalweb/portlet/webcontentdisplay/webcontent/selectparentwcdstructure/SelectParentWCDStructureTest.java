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

package com.liferay.portalweb.portlet.webcontentdisplay.webcontent.selectparentwcdstructure;

import com.liferay.portalweb.portal.BaseTestCase;
import com.liferay.portalweb.portal.util.RuntimeVariables;

/**
 * @author Brian Wing Shun Chan
 */
public class SelectParentWCDStructureTest extends BaseTestCase {
	public void testSelectParentWCDStructure() throws Exception {
		selenium.open("/web/guest/home/");
		selenium.clickAt("link=Web Content Display Test Page",
			RuntimeVariables.replace("Web Content Display Test Page"));
		selenium.waitForPageToLoad("30000");
		selenium.clickAt("//img[@alt='Add Web Content']",
			RuntimeVariables.replace("Add Web Content"));
		selenium.waitForPageToLoad("30000");

		for (int second = 0;; second++) {
			if (second >= 90) {
				fail("timeout");
			}

			try {
				if (selenium.isVisible("//a[@id='_15_changeStructureButton']")) {
					break;
				}
			}
			catch (Exception e) {
			}

			Thread.sleep(1000);
		}

		selenium.clickAt("//a[@id='_15_changeStructureButton']",
			RuntimeVariables.replace("Change"));

		for (int second = 0;; second++) {
			if (second >= 90) {
				fail("timeout");
			}

			try {
				if (selenium.isVisible("//iframe[@name='_15_changeStruture']")) {
					break;
				}
			}
			catch (Exception e) {
			}

			Thread.sleep(1000);
		}

		selenium.selectFrame("//iframe[@name='_15_changeStruture']");
		Thread.sleep(5000);
		selenium.clickAt("//input[@value='Add Structure']",
			RuntimeVariables.replace("Add Structure"));
		selenium.waitForPageToLoad("30000");

		for (int second = 0;; second++) {
			if (second >= 90) {
				fail("timeout");
			}

			try {
				if (selenium.isVisible("//input[@value='Select']")) {
					break;
				}
			}
			catch (Exception e) {
			}

			Thread.sleep(1000);
		}

		selenium.clickAt("//input[@value='Select']",
			RuntimeVariables.replace("Select"));
		selenium.selectFrame("relative=top");
		Thread.sleep(5000);

		for (int second = 0;; second++) {
			if (second >= 90) {
				fail("timeout");
			}

			try {
				if (selenium.isVisible(
							"//iframe[@name='_15_parentStructureSelector']")) {
					break;
				}
			}
			catch (Exception e) {
			}

			Thread.sleep(1000);
		}

		selenium.selectFrame("//iframe[@name='_15_parentStructureSelector']");
		assertTrue(selenium.isVisible("//td[1]/a"));
		assertEquals(RuntimeVariables.replace("WC Structure Name 1"),
			selenium.getText("//td[2]/a"));
		assertEquals(RuntimeVariables.replace("WC Structure Description 1"),
			selenium.getText("//td[3]/a"));
		selenium.clickAt("//td[2]/a",
			RuntimeVariables.replace("WC Structure Name 1"));
		selenium.selectFrame("relative=top");

		for (int second = 0;; second++) {
			if (second >= 90) {
				fail("timeout");
			}

			try {
				if (selenium.isVisible("//iframe[@name='_15_changeStruture']")) {
					break;
				}
			}
			catch (Exception e) {
			}

			Thread.sleep(1000);
		}

		selenium.selectFrame("//iframe[@name='_15_changeStruture']");
		selenium.type("//input[@id='_15_name_en_US']",
			RuntimeVariables.replace("WC Structure Name 2"));
		selenium.type("//textarea[@id='_15_description_en_US']",
			RuntimeVariables.replace("WC Structure Description 2"));
		selenium.clickAt("//input[@value='Add Row']",
			RuntimeVariables.replace("Add Row"));
		selenium.waitForPageToLoad("30000");
		selenium.select("//select[@id='_15_structure_el0_type']",
			RuntimeVariables.replace("Text Area (HTML)"));
		selenium.type("//input[@id='_15_structure_el0_name']",
			RuntimeVariables.replace("text 1"));
		selenium.clickAt("//input[@value='Save']",
			RuntimeVariables.replace("Save"));
		selenium.waitForPageToLoad("30000");
		assertEquals(RuntimeVariables.replace(
				"Your request completed successfully."),
			selenium.getText("//div[@class='portlet-msg-success']"));
		assertTrue(selenium.isVisible("//tr[4]/td[1]/a"));
		assertEquals(RuntimeVariables.replace("WC Structure Name 2"),
			selenium.getText("//tr[4]/td[2]/a"));
		assertEquals(RuntimeVariables.replace("WC Structure Description 2"),
			selenium.getText("//tr[4]/td[3]/a"));
		selenium.selectFrame("relative=top");
	}
}