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

package com.liferay.portalweb.socialofficehome.microblogs.mbentry.addmicroblogscontentspecialcharacters;

import com.liferay.portalweb.portal.BaseTestCase;
import com.liferay.portalweb.portal.util.RuntimeVariables;

/**
 * @author Brian Wing Shun Chan
 */
public class SOUs_ViewMBContentSpecialCharactersProfileTest extends BaseTestCase {
	public void testSOUs_ViewMBContentSpecialCharactersProfile()
		throws Exception {
		selenium.selectWindow("null");
		selenium.selectFrame("relative=top");
		selenium.open("/web/joebloggs/so/profile");
		assertEquals(RuntimeVariables.replace(
				"Today is Joe Blogg's fake \"birthday\". <script>alert(\"this is an xss test\");</script>."),
			selenium.getText("//div[@class='content']"));
		selenium.waitForVisible("//div[@class='activity-action']");
		assertEquals(RuntimeVariables.replace(
				"Today is Joe Blogg's fake \"birthday\". <script>alert(\"this is an xss test\");</script>."),
			selenium.getText("//div[@class='activity-action']"));
		selenium.clickAt("//nav/ul/li[contains(.,'Microblogs')]/a/span",
			RuntimeVariables.replace("Microblogs"));
		selenium.waitForPageToLoad("30000");
		assertEquals(RuntimeVariables.replace("Joe Bloggs"),
			selenium.getText("//div[@class='user-name']/span"));
		assertEquals(RuntimeVariables.replace(
				"Today is Joe Blogg's fake \"birthday\". <script>alert(\"this is an xss test\");</script>."),
			selenium.getText("//div[@class='content']"));
		assertEquals(RuntimeVariables.replace("fake"),
			selenium.getText("//div[@class='content']/span/a"));
		selenium.clickAt("//div[@class='content']/span/a",
			RuntimeVariables.replace("fake"));
		selenium.waitForPageToLoad("30000");
		assertEquals(RuntimeVariables.replace("fake"),
			selenium.getText(
				"//ul[contains(@class,'tabview-list')]/li[contains(.,'fake')]"));
		assertEquals(RuntimeVariables.replace("Joe Bloggs"),
			selenium.getText("//div[@class='user-name']/span"));
		assertEquals(RuntimeVariables.replace(
				"Today is Joe Blogg's fake \"birthday\". <script>alert(\"this is an xss test\");</script>."),
			selenium.getText("//div[@class='content']"));
		assertEquals(RuntimeVariables.replace("fake"),
			selenium.getText("//div[@class='content']/span/a"));
	}
}