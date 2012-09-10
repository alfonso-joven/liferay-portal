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

package com.liferay.portalweb.demo.sitemanagement.staginglocallive;

import com.liferay.portalweb.portal.BaseTestCase;
import com.liferay.portalweb.portal.util.RuntimeVariables;

/**
 * @author Brian Wing Shun Chan
 */
public class User_MarkAsReadyForPublicationPageVariationSantaTest
	extends BaseTestCase {
	public void testUser_MarkAsReadyForPublicationPageVariationSanta()
		throws Exception {
		int label = 1;

		while (label >= 1) {
			switch (label) {
			case 1:
				selenium.selectWindow("null");
				selenium.selectFrame("relative=top");
				selenium.open("/web/guest/home/");
				selenium.clickAt("//div[@id='dockbar']",
					RuntimeVariables.replace("Dockbar"));
				selenium.waitForElementPresent(
					"//script[contains(@src,'/liferay/dockbar_underlay.js')]");
				assertEquals(RuntimeVariables.replace("Go to"),
					selenium.getText("//li[@id='_145_mySites']/a/span"));
				selenium.mouseOver("//li[@id='_145_mySites']/a/span");
				selenium.waitForVisible("link=Site Name");
				selenium.clickAt("link=Site Name",
					RuntimeVariables.replace("Site Name"));
				selenium.waitForPageToLoad("30000");
				assertTrue(selenium.isElementPresent(
						"//body[contains(@class,'live-view')]"));
				assertTrue(selenium.isElementNotPresent(
						"//body[contains(@class,'local-staging')]"));
				assertEquals(RuntimeVariables.replace("Staging"),
					selenium.getText(
						"//div[@class='staging-bar']/ul/li[2]/span/a"));
				selenium.clickAt("//div[@class='staging-bar']/ul/li[2]/span/a",
					RuntimeVariables.replace("Staging"));
				selenium.waitForPageToLoad("30000");
				assertTrue(selenium.isElementPresent(
						"//body[contains(@class,'local-staging')]"));
				assertTrue(selenium.isElementNotPresent(
						"//body[contains(@class,'live-view')]"));

				boolean seasonPresent = selenium.isElementPresent("link=Season");

				if (!seasonPresent) {
					label = 2;

					continue;
				}

				selenium.clickAt("link=Season",
					RuntimeVariables.replace("Season"));
				selenium.waitForPageToLoad("30000");

			case 2:
				assertEquals(RuntimeVariables.replace(
						"Season Site Pages Variation of Site Name"),
					selenium.getText(
						"//span[@class='layout-set-branch-description']"));
				assertEquals(RuntimeVariables.replace("Regular Description"),
					selenium.getText(
						"//div[@class='layout-branch-description']"));

				boolean santaPresent = selenium.isElementPresent("link=Santa");

				if (!santaPresent) {
					label = 3;

					continue;
				}

				selenium.clickAt("link=Santa", RuntimeVariables.replace("Santa"));
				selenium.waitForPageToLoad("30000");

			case 3:
				assertEquals(RuntimeVariables.replace("Santa Description"),
					selenium.getText(
						"//div[@class='layout-branch-description']"));
				assertEquals(RuntimeVariables.replace("Santa"),
					selenium.getText("//form/div/ul/li[2]/span/span"));
				assertEquals(RuntimeVariables.replace("Draft"),
					selenium.getText("//span[@class='workflow-status']/strong"));
				assertEquals(RuntimeVariables.replace(
						"Mark as Ready for Publication"),
					selenium.getText(
						"//button/span[.='Mark as Ready for Publication']"));
				selenium.clickAt("//button/span[.='Mark as Ready for Publication']",
					RuntimeVariables.replace("Mark as Ready for Publication"));
				selenium.waitForText("//span[@class='workflow-status']/strong",
					"Ready for Publication");
				assertEquals(RuntimeVariables.replace("Ready for Publication"),
					selenium.getText("//span[@class='workflow-status']/strong"));

			case 100:
				label = -1;
			}
		}
	}
}