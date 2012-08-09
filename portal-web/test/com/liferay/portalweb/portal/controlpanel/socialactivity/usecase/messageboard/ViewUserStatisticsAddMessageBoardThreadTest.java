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

package com.liferay.portalweb.portal.controlpanel.socialactivity.usecase.messageboard;

import com.liferay.portalweb.portal.BaseTestCase;
import com.liferay.portalweb.portal.util.RuntimeVariables;

/**
 * @author Brian Wing Shun Chan
 */
public class ViewUserStatisticsAddMessageBoardThreadTest extends BaseTestCase {
	public void testViewUserStatisticsAddMessageBoardThread()
		throws Exception {
		selenium.open("/web/site-name/");

		for (int second = 0;; second++) {
			if (second >= 90) {
				fail("timeout");
			}

			try {
				if (selenium.isVisible("link=User Statistics Test Page")) {
					break;
				}
			}
			catch (Exception e) {
			}

			Thread.sleep(1000);
		}

		selenium.clickAt("link=User Statistics Test Page",
			RuntimeVariables.replace("User Statistics Test Page"));
		selenium.waitForPageToLoad("30000");
		assertEquals(RuntimeVariables.replace("Joe Bloggs"),
			selenium.getText("//span[@class='user-name']"));
		assertEquals(RuntimeVariables.replace("Rank: 1"),
			selenium.getText("//div[@class='user-rank']"));
		assertEquals(RuntimeVariables.replace("Contribution Score: 0"),
			selenium.getText("//div[@class='contribution-score']"));
		assertEquals(RuntimeVariables.replace("Participation Score: 5"),
			selenium.getText("//div[@class='participation-score']"));
		assertEquals(RuntimeVariables.replace("User's Message Board Posts: 1"),
			selenium.getText(
				"//div[@class='social-counter-user.message-posts']"));
	}
}