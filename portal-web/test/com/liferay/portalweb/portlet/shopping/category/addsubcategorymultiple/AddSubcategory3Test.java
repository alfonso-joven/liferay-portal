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

package com.liferay.portalweb.portlet.shopping.category.addsubcategorymultiple;

import com.liferay.portalweb.portal.BaseTestCase;
import com.liferay.portalweb.portal.util.RuntimeVariables;

/**
 * @author Brian Wing Shun Chan
 */
public class AddSubcategory3Test extends BaseTestCase {
	public void testAddSubcategory3() throws Exception {
		selenium.open("/web/guest/home/");

		for (int second = 0;; second++) {
			if (second >= 90) {
				fail("timeout");
			}

			try {
				if (selenium.isVisible("link=Shopping Test Page")) {
					break;
				}
			}
			catch (Exception e) {
			}

			Thread.sleep(1000);
		}

		selenium.clickAt("link=Shopping Test Page",
			RuntimeVariables.replace("Shopping Test Page"));
		selenium.waitForPageToLoad("30000");
		selenium.clickAt("link=Categories",
			RuntimeVariables.replace("Categories"));
		selenium.waitForPageToLoad("30000");
		assertEquals(RuntimeVariables.replace(
				"Shopping Category Name\nShopping Category Description"),
			selenium.getText("//td[1]/a"));
		selenium.clickAt("//td[1]/a",
			RuntimeVariables.replace(
				"Shopping Category Name\nShopping Category Description"));
		selenium.waitForPageToLoad("30000");
		selenium.clickAt("//input[@value='Add Subcategory']",
			RuntimeVariables.replace("Add Subcategory"));
		selenium.waitForPageToLoad("30000");
		selenium.type("//input[@id='_34_name']",
			RuntimeVariables.replace("Shopping Subcategory3 Name"));
		selenium.type("//textarea[@id='_34_description']",
			RuntimeVariables.replace("Shopping Subcategory3 Description"));
		selenium.clickAt("//input[@value='Save']",
			RuntimeVariables.replace("Save"));
		selenium.waitForPageToLoad("30000");
		assertEquals(RuntimeVariables.replace(
				"Your request completed successfully."),
			selenium.getText("//div[@class='portlet-msg-success']"));
		assertEquals(RuntimeVariables.replace(
				"Shopping Subcategory1 Name\nShopping Subcategory1 Description"),
			selenium.getText("//td[1]/a"));
		assertEquals(RuntimeVariables.replace(
				"Shopping Subcategory2 Name\nShopping Subcategory2 Description"),
			selenium.getText("//tr[4]/td[1]/a"));
		assertEquals(RuntimeVariables.replace(
				"Shopping Subcategory3 Name\nShopping Subcategory3 Description"),
			selenium.getText("//tr[5]/td[1]/a"));
	}
}