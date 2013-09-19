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

package com.liferay.portalweb.socialofficeprofile.profile.viewactivitiessitesactivitiesprofile;

import com.liferay.portalweb.portal.BaseTestCase;
import com.liferay.portalweb.portal.util.RuntimeVariables;

/**
 * @author Brian Wing Shun Chan
 */
public class SOUs_ViewActivitiesSitesActivitesProfileTest extends BaseTestCase {
	public void testSOUs_ViewActivitiesSitesActivitesProfile()
		throws Exception {
		selenium.selectWindow("null");
		selenium.selectFrame("relative=top");
		selenium.open("/web/socialoffice01/so/profile");
		selenium.waitForVisible("//li[contains(@class, 'selected')]/a/span");
		assertEquals(RuntimeVariables.replace("Profile"),
			selenium.getText("//li[contains(@class, 'selected')]/a/span"));
		assertEquals(RuntimeVariables.replace("Social01 Office01 User01"),
			selenium.getText("//div[@class='lfr-contact-name']/a"));
		assertEquals(RuntimeVariables.replace("socialoffice01@liferay.com"),
			selenium.getText("//div[@class='lfr-contact-extra']"));
		selenium.waitForVisible("xpath=(//div[@class='activity-user-name'])[1]");
		assertEquals(RuntimeVariables.replace("Social01 in Open Site Name"),
			selenium.getText("xpath=(//div[@class='activity-user-name'])[1]"));
		assertEquals(RuntimeVariables.replace("Added a new bookmark."),
			selenium.getText("xpath=(//div[@class='activity-action'])[1]"));
		assertTrue(selenium.isPartialText(
				"xpath=(//div[@class='activity-body'])[1]",
				"Bookmarks Entry2 Name"));
		assertTrue(selenium.isPartialText(
				"xpath=(//div[@class='activity-body'])[1]",
				"Bookmarks Entry2 Description"));
		assertEquals(RuntimeVariables.replace("Social01 in Open Site Name"),
			selenium.getText("xpath=(//div[@class='activity-user-name'])[2]"));
		assertEquals(RuntimeVariables.replace(
				"Updated a wiki page in the Main wiki."),
			selenium.getText("xpath=(//div[@class='activity-action'])[2]"));
		assertTrue(selenium.isPartialText(
				"xpath=(//div[@class='activity-body'])[2]", "FrontPage"));
		assertTrue(selenium.isPartialText(
				"xpath=(//div[@class='activity-body'])[2]",
				"Wiki FrontPage Content Edit2"));
		assertEquals(RuntimeVariables.replace("Social01 in Open Site Name"),
			selenium.getText("xpath=(//div[@class='activity-user-name'])[3]"));
		assertEquals(RuntimeVariables.replace("Wrote a new blog entry."),
			selenium.getText("xpath=(//div[@class='activity-action'])[3]"));
		assertTrue(selenium.isPartialText(
				"xpath=(//div[@class='activity-body'])[3]", "Blogs Entry2 Title"));
		assertTrue(selenium.isPartialText(
				"xpath=(//div[@class='activity-body'])[3]",
				"Blogs Entry2 Content"));
		assertEquals(RuntimeVariables.replace("Social01 in Open Site Name"),
			selenium.getText("xpath=(//div[@class='activity-user-name'])[4]"));
		assertEquals(RuntimeVariables.replace(
				"Wrote a new forum post in MB Category Name."),
			selenium.getText("xpath=(//div[@class='activity-action'])[4]"));
		assertTrue(selenium.isPartialText(
				"xpath=(//div[@class='activity-body'])[4]",
				"MB Category Thread2 Message Subject"));
		assertTrue(selenium.isPartialText(
				"xpath=(//div[@class='activity-body'])[4]",
				"MB Category Thread2 Message Body"));
		assertEquals(RuntimeVariables.replace("Social01 in Open Site Name"),
			selenium.getText("xpath=(//div[@class='activity-user-name'])[5]"));
		assertEquals(RuntimeVariables.replace(
				"Uploaded a new document in the DM Folder Name folder."),
			selenium.getText("xpath=(//div[@class='activity-action'])[5]"));
		assertTrue(selenium.isPartialText(
				"//div[@class='document-container']",
				"DM Folder Document2 Title"));
		assertEquals(RuntimeVariables.replace("Social01 in Open Site Name"),
			selenium.getText("xpath=(//div[@class='activity-user-name'])[6]"));
		assertEquals(RuntimeVariables.replace("Added a new calendar event."),
			selenium.getText("xpath=(//div[@class='activity-action'])[6]"));
		assertTrue(selenium.isPartialText(
				"xpath=(//div[@class='activity-body'])[5]",
				"Calendar Event2 Title"));
		assertTrue(selenium.isPartialText(
				"xpath=(//div[@class='activity-body'])[5]",
				"Calendar Event2 Description"));
		assertFalse(selenium.isTextPresent("Bookmarks Entry1 Name"));
		assertFalse(selenium.isTextPresent("Blogs Entry1 Title"));
		assertFalse(selenium.isTextPresent(
				"MB Category Thread1 Message Subject"));
		assertFalse(selenium.isTextPresent("DM Folder Document1 Title"));
		assertFalse(selenium.isTextPresent("Calendar Event1 Title"));
	}
}