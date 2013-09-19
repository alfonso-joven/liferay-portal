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

package com.liferay.portalweb.socialofficeprofile.profile.viewactivitiesdashboardactivitiesprofile;

import com.liferay.portalweb.portal.BaseTestCase;
import com.liferay.portalweb.portal.util.RuntimeVariables;

/**
 * @author Brian Wing Shun Chan
 */
public class ViewActivitiesDashboardActivitiesProfileTest extends BaseTestCase {
	public void testViewActivitiesDashboardActivitiesProfile()
		throws Exception {
		selenium.selectWindow("null");
		selenium.selectFrame("relative=top");
		selenium.open("/web/joebloggs/so/profile");
		selenium.waitForVisible("//li[contains(@class, 'selected')]/a/span");
		assertEquals(RuntimeVariables.replace("Profile"),
			selenium.getText("//li[contains(@class, 'selected')]/a/span"));
		assertEquals(RuntimeVariables.replace("Joe Bloggs"),
			selenium.getText("xPath=(//div[@class='lfr-contact-name']/a)[2]"));
		assertEquals(RuntimeVariables.replace("test@liferay.com"),
			selenium.getText("//div[@class='lfr-contact-extra']"));
		selenium.waitForVisible("xPath=(//div[@class='activity-user-name'])[1]");
		assertEquals(RuntimeVariables.replace("Joe"),
			selenium.getText("xPath=(//div[@class='activity-user-name'])[1]"));
		assertTrue(selenium.isPartialText(
				"xPath=(//div[@class='activity-action'])[1]", "Microblogs Post2"));
		assertTrue(selenium.isPartialText(
				"xPath=(//div[@class='activity-action']/a)[1]", "Microblogs"));
		assertEquals(RuntimeVariables.replace("Joe"),
			selenium.getText("xPath=(//div[@class='activity-user-name'])[2]"));
		assertTrue(selenium.isPartialText(
				"xPath=(//div[@class='activity-action'])[2]", "Microblogs Post1"));
		assertFalse(selenium.isTextPresent("Microblogs Post3"));
		assertFalse(selenium.isTextPresent("Microblogs Post4"));
		assertFalse(selenium.isTextPresent("DM Folder Document2 Title"));
		assertFalse(selenium.isTextPresent("Task3 Description"));
	}
}