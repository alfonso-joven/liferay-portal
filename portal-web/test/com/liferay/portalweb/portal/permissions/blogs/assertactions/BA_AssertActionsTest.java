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

package com.liferay.portalweb.portal.permissions.blogs.assertactions;

import com.liferay.portalweb.portal.BaseTestCase;
import com.liferay.portalweb.portal.util.RuntimeVariables;

/**
 * @author Brian Wing Shun Chan
 */
public class BA_AssertActionsTest extends BaseTestCase {
	public void testBA_AssertActions() throws Exception {
		selenium.selectWindow("null");
		selenium.selectFrame("relative=top");
		selenium.open("/web/guest/home/");
		selenium.clickAt("link=Blogs Permissions Page",
			RuntimeVariables.replace("Blogs Permissions Page"));
		selenium.waitForPageToLoad("30000");
		assertTrue(selenium.isElementPresent(
				"//li[@class='portlet-configuration portlet-configuration-icon']/a"));
		assertTrue(selenium.isVisible("//input[@value='Add Blog Entry']"));
		assertTrue(selenium.isVisible("//span[contains(.,'Edit')]/a"));
		assertTrue(selenium.isVisible("//span[contains(.,'Permissions')]/a"));
		assertTrue(selenium.isVisible("//span[contains(.,'Delete')]/a"));
		assertEquals(RuntimeVariables.replace("RSS (Opens New Window)"),
			selenium.getText("//span[contains(.,'RSS')]/a"));
		selenium.clickAt("//div[@class='entry-title']/h2/a",
			RuntimeVariables.replace("Permissions Blogs Test Entry"));
		selenium.waitForPageToLoad("30000");
		assertEquals(RuntimeVariables.replace("Edit"),
			selenium.getText("//span[contains(.,'Edit')]/a"));
		assertEquals(RuntimeVariables.replace("Permissions"),
			selenium.getText("xPath=(//span[contains(.,'Permissions')]/a)[3]"));
		assertEquals(RuntimeVariables.replace("Delete"),
			selenium.getText("//span[contains(.,'Delete')]/a"));
	}
}