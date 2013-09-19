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

package com.liferay.portalweb.socialofficehome.microblogs.mbentry.sousreplymicroblogscontentprofile;

import com.liferay.portalweb.portal.BaseTestCase;
import com.liferay.portalweb.portal.util.RuntimeVariables;

/**
 * @author Brian Wing Shun Chan
 */
public class SOUs_ViewReplyMicroblogsContentTest extends BaseTestCase {
	public void testSOUs_ViewReplyMicroblogsContent() throws Exception {
		selenium.selectWindow("null");
		selenium.selectFrame("relative=top");
		selenium.open("/user/socialoffice01/so/dashboard");
		selenium.waitForVisible("xPath=(//div[@class='activity-user-name'])[1]");
		assertEquals(RuntimeVariables.replace("Social01"),
			selenium.getText("xPath=(//div[@class='activity-user-name'])[1]"));
		assertEquals(RuntimeVariables.replace("Commented on a microblog entry."),
			selenium.getText("xPath=(//div[@class='activity-action'])[1]"));
		assertEquals(RuntimeVariables.replace("Microblogs Post"),
			selenium.getText("xPath=(//div[@class='activity-body'])[1]"));
		assertEquals(RuntimeVariables.replace("1 Comment"),
			selenium.getText("//span[@class='view-comments action']/a"));
		selenium.clickAt("//span[@class='view-comments action']/a",
			RuntimeVariables.replace("1 Comment"));
		selenium.waitForVisible("//div[@class='comment-body']");
		assertTrue(selenium.isPartialText("//div[@class='comment-body']",
				"Social01 Office01 User01"));
		assertTrue(selenium.isPartialText("//div[@class='comment-body']",
				"Microblogs Post Comment"));
	}
}