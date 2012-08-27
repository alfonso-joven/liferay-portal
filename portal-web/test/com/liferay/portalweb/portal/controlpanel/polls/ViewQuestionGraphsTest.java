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

package com.liferay.portalweb.portal.controlpanel.polls;

import com.liferay.portalweb.portal.BaseTestCase;
import com.liferay.portalweb.portal.util.RuntimeVariables;

/**
 * @author Brian Wing Shun Chan
 */
public class ViewQuestionGraphsTest extends BaseTestCase {
	public void testViewQuestionGraphs() throws Exception {
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
		selenium.clickAt("link=Polls", RuntimeVariables.replace("Polls"));
		selenium.waitForPageToLoad("30000");
		selenium.clickAt("link=Test Poll Question",
			RuntimeVariables.replace("Test Poll Question"));
		selenium.waitForPageToLoad("30000");
		selenium.clickAt("link=Area", RuntimeVariables.replace("Area"));
		selenium.waitForPopUp("", RuntimeVariables.replace("30000"));
		selenium.selectWindow("");
		selenium.close();
		selenium.selectWindow("null");

		for (int second = 0;; second++) {
			if (second >= 90) {
				fail("timeout");
			}

			try {
				if (selenium.isVisible("link=Horizontal Bar")) {
					break;
				}
			}
			catch (Exception e) {
			}

			Thread.sleep(1000);
		}

		selenium.clickAt("link=Horizontal Bar",
			RuntimeVariables.replace("Horizontal Bar"));
		selenium.waitForPopUp("", RuntimeVariables.replace("30000"));
		selenium.selectWindow("");
		selenium.close();
		selenium.selectWindow("null");

		for (int second = 0;; second++) {
			if (second >= 90) {
				fail("timeout");
			}

			try {
				if (selenium.isVisible("link=Line")) {
					break;
				}
			}
			catch (Exception e) {
			}

			Thread.sleep(1000);
		}

		selenium.clickAt("link=Line", RuntimeVariables.replace("Line"));
		selenium.waitForPopUp("", RuntimeVariables.replace("30000"));
		selenium.selectWindow("");
		selenium.close();
		selenium.selectWindow("null");

		for (int second = 0;; second++) {
			if (second >= 90) {
				fail("timeout");
			}

			try {
				if (selenium.isVisible("link=Pie")) {
					break;
				}
			}
			catch (Exception e) {
			}

			Thread.sleep(1000);
		}

		selenium.clickAt("link=Pie", RuntimeVariables.replace("Pie"));
		selenium.waitForPopUp("", RuntimeVariables.replace("30000"));
		selenium.selectWindow("");
		selenium.close();
		selenium.selectWindow("null");

		for (int second = 0;; second++) {
			if (second >= 90) {
				fail("timeout");
			}

			try {
				if (selenium.isVisible("link=Vertical Bar")) {
					break;
				}
			}
			catch (Exception e) {
			}

			Thread.sleep(1000);
		}

		selenium.clickAt("link=Vertical Bar",
			RuntimeVariables.replace("Vertical Bar"));
		selenium.waitForPopUp("", RuntimeVariables.replace("30000"));
		selenium.selectWindow("");
		selenium.close();
		selenium.selectWindow("null");
	}
}