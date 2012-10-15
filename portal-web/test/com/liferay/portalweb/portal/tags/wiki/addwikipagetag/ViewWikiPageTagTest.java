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

package com.liferay.portalweb.portal.tags.wiki.addwikipagetag;

import com.liferay.portalweb.portal.BaseTestCase;
import com.liferay.portalweb.portal.util.RuntimeVariables;

/**
 * @author Brian Wing Shun Chan
 */
public class ViewWikiPageTagTest extends BaseTestCase {
	public void testViewWikiPageTag() throws Exception {
		selenium.selectWindow("null");
		selenium.selectFrame("relative=top");
		selenium.open("/web/guest/home/");
		selenium.clickAt("link=Wiki Test Page",
			RuntimeVariables.replace("Wiki Test Page"));
		selenium.waitForPageToLoad("30000");
		selenium.clickAt("//span/a[contains(.,'All Pages')]",
			RuntimeVariables.replace("All Pages"));
		selenium.waitForPageToLoad("30000");
		selenium.clickAt("//tr[contains(.,'Wiki Page Title')]/td[1]/a",
			RuntimeVariables.replace("Wiki Page Title"));
		selenium.waitForPageToLoad("30000");
		selenium.clickAt("//a[contains(.,'wiki tag1')]",
			RuntimeVariables.replace("wiki tag1"));
		selenium.waitForPageToLoad("30000");
		assertEquals(RuntimeVariables.replace("FrontPage"),
			selenium.getText("//span[contains(.,'FrontPage')]"));
		assertEquals(RuntimeVariables.replace("Recent Changes"),
			selenium.getText("//span[contains(.,'Recent Changes')]"));
		assertEquals(RuntimeVariables.replace("All Pages"),
			selenium.getText("//span[contains(.,'All Pages')]"));
		assertEquals(RuntimeVariables.replace("Orphan Pages"),
			selenium.getText("//span[contains(.,'Orphan Pages')]"));
		assertEquals(RuntimeVariables.replace("Draft Pages"),
			selenium.getText("//span[contains(.,'Draft Pages')]"));
		assertTrue(selenium.isVisible("//input[@id='_36_keywords']"));
		assertTrue(selenium.isVisible("//input[@value='Search']"));
		assertEquals(RuntimeVariables.replace("Pages with tag wiki tag1 ."),
			selenium.getText("//h1[contains(.,'Pages with tag')]"));
		assertEquals(RuntimeVariables.replace("wiki tag1"),
			selenium.getText("//span[@class='asset-entry']"));
		assertEquals(RuntimeVariables.replace("Wiki Page Title"),
			selenium.getText("//tr[contains(.,'Wiki Page Title')]/td[1]/a"));
		assertEquals(RuntimeVariables.replace("Approved"),
			selenium.getText("//tr[contains(.,'Approved')]/td[2]/a"));
		assertEquals(RuntimeVariables.replace("1.1"),
			selenium.getText("//tr[contains(.,'1.1')]/td[3]/a"));
		assertEquals(RuntimeVariables.replace("Joe Bloggs"),
			selenium.getText("//tr[contains(.,'Joe Bloggs')]/td[4]/a"));
		assertEquals(RuntimeVariables.replace("Date"),
			selenium.getText("//a[contains(text(),'Date')]"));
		assertEquals(RuntimeVariables.replace("Actions"),
			selenium.getText("//span[@title='Actions']/ul/li/strong/a"));
		assertEquals(RuntimeVariables.replace("Showing 1 result."),
			selenium.getText("//div[@class='search-results']"));
	}
}