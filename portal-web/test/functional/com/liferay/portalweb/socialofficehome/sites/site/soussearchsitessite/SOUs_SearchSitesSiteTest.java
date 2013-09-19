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

package com.liferay.portalweb.socialofficehome.sites.site.soussearchsitessite;

import com.liferay.portalweb.portal.BaseTestCase;
import com.liferay.portalweb.portal.util.RuntimeVariables;

/**
 * @author Brian Wing Shun Chan
 */
public class SOUs_SearchSitesSiteTest extends BaseTestCase {
	public void testSOUs_SearchSitesSite() throws Exception {
		selenium.selectWindow("null");
		selenium.selectFrame("relative=top");
		selenium.open("/user/socialoffice01/so/dashboard/");
		selenium.waitForVisible("//li[contains(@class, 'selected')]/a/span");
		assertEquals(RuntimeVariables.replace("Dashboard"),
			selenium.getText("//li[contains(@class, 'selected')]/a/span"));
		selenium.clickAt("//input[contains(@class,'search-input')]",
			RuntimeVariables.replace("Go to"));
		selenium.waitForVisible("//input[contains(@class,'search-input')]");
		selenium.select("//div[@class='sites-tabs']/span/span/span/select",
			RuntimeVariables.replace("All Sites"));
		selenium.type("//input[contains(@class,'search-input')]",
			RuntimeVariables.replace("Open"));
		Thread.sleep(5000);
		assertEquals(RuntimeVariables.replace("Open Site Name"),
			selenium.getText(
				"//li[contains(@class, 'social-office-enabled')]/span[2]/a"));
		selenium.open("/user/socialoffice01/so/dashboard/");
		selenium.waitForVisible("//li[contains(@class, 'selected')]/a/span");
		assertEquals(RuntimeVariables.replace("Dashboard"),
			selenium.getText("//li[contains(@class, 'selected')]/a/span"));
		selenium.clickAt("//input[contains(@class,'search-input')]",
			RuntimeVariables.replace("Go to"));
		selenium.waitForVisible("//input[contains(@class,'search-input')]");
		selenium.select("//div[@class='sites-tabs']/span/span/span/select",
			RuntimeVariables.replace("My Sites"));
		selenium.type("//input[contains(@class,'search-input')]",
			RuntimeVariables.replace("Open"));
		Thread.sleep(5000);
		assertEquals(RuntimeVariables.replace("There are no results."),
			selenium.getText("//li[@class='empty']"));
		assertFalse(selenium.isTextPresent("Open Site Name"));
		selenium.clickAt("//input[contains(@class,'search-input')]",
			RuntimeVariables.replace("User Name"));
		selenium.waitForVisible(
			"//button[contains(.,'Sites Directory')]/span[2]");
		selenium.clickAt("//button[contains(.,'Sites Directory')]/span[2]",
			RuntimeVariables.replace("Sites Directory"));
		Thread.sleep(5000);
		selenium.waitForVisible("xPath=(//h1[@class='header-title']/span)[1]");
		assertEquals(RuntimeVariables.replace("Directory"),
			selenium.getText("xPath=(//h1[@class='header-title']/span)[1]"));
		selenium.select("//span[@class='sites-tabs']/span/span/span/select",
			RuntimeVariables.replace("All Sites"));
		assertTrue(selenium.isVisible(
				"//input[@id='_5_WAR_soportlet_dialogKeywords']"));
		selenium.type("//input[@id='_5_WAR_soportlet_dialogKeywords']",
			RuntimeVariables.replace("Open Site Name"));
		Thread.sleep(5000);
		assertEquals(RuntimeVariables.replace("Open Site Name"),
			selenium.getText("//span[@class='name']/a"));
		assertEquals(RuntimeVariables.replace("Open Site Description"),
			selenium.getText("//span[@class='description']"));
		assertTrue(selenium.isVisible("//span[@class='action join']/a"));
		selenium.clickAt("//span[@class='name']/a",
			RuntimeVariables.replace("Open Site Name"));
		selenium.waitForPageToLoad("30000");
		selenium.selectFrame("relative=top");
		assertEquals(RuntimeVariables.replace("Open Site Name"),
			selenium.getText("//div[@class='community-title']/a"));
		assertEquals(RuntimeVariables.replace("Join Site"),
			selenium.getText("//span[@class='action request']/a"));
		selenium.open("/user/socialoffice01/so/dashboard/");
		selenium.waitForVisible("//li[contains(@class, 'selected')]/a/span");
		assertEquals(RuntimeVariables.replace("Dashboard"),
			selenium.getText("//li[contains(@class, 'selected')]/a/span"));
		selenium.clickAt("//input[contains(@class,'search-input')]",
			RuntimeVariables.replace("Go to"));
		selenium.waitForVisible("//input[contains(@class,'search-input')]");
		selenium.select("//div[@class='sites-tabs']/span/span/span/select",
			RuntimeVariables.replace("All Sites"));
		selenium.type("//input[contains(@class,'search-input')]",
			RuntimeVariables.replace("Open"));
		Thread.sleep(5000);
		assertEquals(RuntimeVariables.replace("Open Site Name"),
			selenium.getText(
				"//li[contains(@class, 'social-office-enabled')]/span[2]/a"));
		selenium.clickAt("//li[contains(@class, 'social-office-enabled')]/span[2]/a",
			RuntimeVariables.replace("Open Site Name"));
		selenium.waitForPageToLoad("30000");
		assertEquals(RuntimeVariables.replace("Open Site Name"),
			selenium.getText("//div[@class='community-title']/a"));
		assertEquals(RuntimeVariables.replace("Join Site"),
			selenium.getText("//span[@class='action request']/a"));
	}
}