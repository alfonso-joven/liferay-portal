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

package com.liferay.portalweb.socialofficehome.activities.activitiessites.viewdmfolderdocumentsiteactivities;

import com.liferay.portalweb.portal.BaseTestCase;
import com.liferay.portalweb.portal.util.RuntimeVariables;

/**
 * @author Brian Wing Shun Chan
 */
public class ViewDMFolderDocumentSiteActivitiesTest extends BaseTestCase {
	public void testViewDMFolderDocumentSiteActivities()
		throws Exception {
		selenium.selectWindow("null");
		selenium.selectFrame("relative=top");
		selenium.open("/user/joebloggs/so/dashboard/");
		assertEquals(RuntimeVariables.replace("Activities"),
			selenium.getText(
				"xPath=(//h1[@class='portlet-title']/span)[contains(.,'Activities')]"));
		assertEquals(RuntimeVariables.replace("Me"), selenium.getText("link=Me"));
		selenium.clickAt("link=Me", RuntimeVariables.replace("Me"));
		selenium.waitForPageToLoad("30000");
		selenium.waitForVisible("//div[@class='activity-user-name']");
		assertEquals(RuntimeVariables.replace("Joe in Open Site Name"),
			selenium.getText("//div[@class='activity-user-name']"));
		assertEquals(RuntimeVariables.replace("Uploaded 2 new documents."),
			selenium.getText("//div[@class='activity-action']"));
		assertEquals(RuntimeVariables.replace("DM Folder Document Title"),
			selenium.getText(
				"xPath=(//div[@class='activity-subentry']/span/a)[1]"));
		assertEquals(RuntimeVariables.replace("DM Document Title"),
			selenium.getText(
				"xPath=(//div[@class='activity-subentry']/span/a)[2]"));
		selenium.clickAt("xPath=(//div[@class='activity-subentry']/span/a)[2]",
			RuntimeVariables.replace("DM Document Title"));
		selenium.waitForPageToLoad("30000");
		assertEquals(RuntimeVariables.replace("Download"),
			selenium.getText(
				"//span[@class='aui-toolbar-content']/button[contains(.,'Download')]/span[2]"));
		assertEquals(RuntimeVariables.replace("Checkout"),
			selenium.getText(
				"//span[@class='aui-toolbar-content']/button[contains(.,'Checkout')]/span[2]"));
		assertEquals(RuntimeVariables.replace("Edit"),
			selenium.getText(
				"//span[@class='aui-toolbar-content']/button[contains(.,'Edit')]/span[2]"));
		assertEquals(RuntimeVariables.replace("Move"),
			selenium.getText(
				"//span[@class='aui-toolbar-content']/button[contains(.,'Move')]/span[2]"));
		assertEquals(RuntimeVariables.replace("Permissions"),
			selenium.getText(
				"//span[@class='aui-toolbar-content']/button[contains(.,'Permissions')]/span[2]"));
		assertEquals(RuntimeVariables.replace("Delete"),
			selenium.getText(
				"//span[@class='aui-toolbar-content']/button[contains(.,'Delete')]/span[2]"));
		assertEquals(RuntimeVariables.replace("DM Document Title"),
			selenium.getText("//span[@class='document-title']"));
		assertTrue(selenium.isVisible("//span[@class='document-thumbnail']/img"));
		assertTrue(selenium.isPartialText(
				"//span[@class='user-date']/span/span", "Uploaded by Joe Bloggs"));
		assertTrue(selenium.isVisible("//span[@class='lfr-asset-ratings']"));
		assertEquals(RuntimeVariables.replace("Version History"),
			selenium.getText(
				"//div[@class='lfr-document-library-versions']/div/div/div"));
		assertEquals(RuntimeVariables.replace("Version 1.0"),
			selenium.getText("//span[@class='version-number']"));
		assertEquals(RuntimeVariables.replace("By: Joe Bloggs"),
			selenium.getText("//span[@class='user-name']"));
		assertTrue(selenium.isVisible("//span[@class='modified-date']"));
		assertEquals(RuntimeVariables.replace("Size: 21.5k"),
			selenium.getText("//span[@class='size']"));
		assertEquals(RuntimeVariables.replace(
				"No comments yet. Be the first. Subscribe to Comments"),
			selenium.getText(
				"//fieldset[@class='aui-fieldset add-comment ']/div"));
		assertEquals(RuntimeVariables.replace("Be the first."),
			selenium.getText(
				"//fieldset[@class='aui-fieldset add-comment ']/div/a"));
		assertEquals(RuntimeVariables.replace("Subscribe to Comments"),
			selenium.getText("//span[@class='subscribe-link']/a/span"));
		assertEquals(RuntimeVariables.replace("Version 1.0"),
			selenium.getText("//h3[contains(@class,'version')]"));
		assertEquals(RuntimeVariables.replace("Last Updated by Joe Bloggs"),
			selenium.getText("//div[@class='lfr-asset-icon lfr-asset-author']"));
		assertTrue(selenium.isVisible(
				"//div[@class='lfr-asset-icon lfr-asset-date']"));
		assertEquals(RuntimeVariables.replace("Status: Approved"),
			selenium.getText("//span[@class='workflow-status']"));
		assertEquals(RuntimeVariables.replace("Download (21.5k)"),
			selenium.getText("//span[@class='download-document']/span/a/span"));
		assertEquals(RuntimeVariables.replace("Get URL or WebDAV URL."),
			selenium.getText("//span[@class='webdav-url']"));
		selenium.open("/user/joebloggs/so/dashboard/");
		assertEquals(RuntimeVariables.replace("Activities"),
			selenium.getText(
				"xPath=(//h1[@class='portlet-title']/span)[contains(.,'Activities')]"));
		assertEquals(RuntimeVariables.replace("Me"), selenium.getText("link=Me"));
		selenium.clickAt("link=Me", RuntimeVariables.replace("Me"));
		selenium.waitForPageToLoad("30000");
		selenium.waitForVisible("//div[@class='activity-user-name']");
		assertEquals(RuntimeVariables.replace("Joe in Open Site Name"),
			selenium.getText("//div[@class='activity-user-name']"));
		assertEquals(RuntimeVariables.replace("Uploaded 2 new documents."),
			selenium.getText("//div[@class='activity-action']"));
		assertEquals(RuntimeVariables.replace("DM Folder Document Title"),
			selenium.getText(
				"xPath=(//div[@class='activity-subentry']/span/a)[1]"));
		assertEquals(RuntimeVariables.replace("DM Document Title"),
			selenium.getText(
				"xPath=(//div[@class='activity-subentry']/span/a)[2]"));
		selenium.clickAt("xPath=(//div[@class='activity-subentry']/span/a)[1]",
			RuntimeVariables.replace("DM Folder Document Title"));
		selenium.waitForPageToLoad("30000");
		assertEquals(RuntimeVariables.replace("Download"),
			selenium.getText(
				"//span[@class='aui-toolbar-content']/button[contains(.,'Download')]/span[2]"));
		assertEquals(RuntimeVariables.replace("Checkout"),
			selenium.getText(
				"//span[@class='aui-toolbar-content']/button[contains(.,'Checkout')]/span[2]"));
		assertEquals(RuntimeVariables.replace("Edit"),
			selenium.getText(
				"//span[@class='aui-toolbar-content']/button[contains(.,'Edit')]/span[2]"));
		assertEquals(RuntimeVariables.replace("Move"),
			selenium.getText(
				"//span[@class='aui-toolbar-content']/button[contains(.,'Move')]/span[2]"));
		assertEquals(RuntimeVariables.replace("Permissions"),
			selenium.getText(
				"//span[@class='aui-toolbar-content']/button[contains(.,'Permissions')]/span[2]"));
		assertEquals(RuntimeVariables.replace("Delete"),
			selenium.getText(
				"//span[@class='aui-toolbar-content']/button[contains(.,'Delete')]/span[2]"));
		assertEquals(RuntimeVariables.replace("DM Folder Document Title"),
			selenium.getText("//span[@class='document-title']"));
		assertTrue(selenium.isVisible("//span[@class='document-thumbnail']/img"));
		assertTrue(selenium.isPartialText(
				"//span[@class='user-date']/span/span", "Uploaded by Joe Bloggs"));
		assertTrue(selenium.isVisible("//span[@class='lfr-asset-ratings']"));
		assertEquals(RuntimeVariables.replace("Version History"),
			selenium.getText(
				"//div[@class='lfr-document-library-versions']/div/div/div"));
		assertEquals(RuntimeVariables.replace("Version 1.0"),
			selenium.getText("//span[@class='version-number']"));
		assertEquals(RuntimeVariables.replace("By: Joe Bloggs"),
			selenium.getText("//span[@class='user-name']"));
		assertTrue(selenium.isVisible("//span[@class='modified-date']"));
		assertEquals(RuntimeVariables.replace("Size: 21.5k"),
			selenium.getText("//span[@class='size']"));
		assertEquals(RuntimeVariables.replace(
				"No comments yet. Be the first. Subscribe to Comments"),
			selenium.getText(
				"//fieldset[@class='aui-fieldset add-comment ']/div"));
		assertEquals(RuntimeVariables.replace("Be the first."),
			selenium.getText(
				"//fieldset[@class='aui-fieldset add-comment ']/div/a"));
		assertEquals(RuntimeVariables.replace("Subscribe to Comments"),
			selenium.getText("//span[@class='subscribe-link']/a/span"));
		assertEquals(RuntimeVariables.replace("Version 1.0"),
			selenium.getText("//h3[contains(@class,'version')]"));
		assertEquals(RuntimeVariables.replace("Last Updated by Joe Bloggs"),
			selenium.getText("//div[@class='lfr-asset-icon lfr-asset-author']"));
		assertTrue(selenium.isVisible(
				"//div[@class='lfr-asset-icon lfr-asset-date']"));
		assertEquals(RuntimeVariables.replace("Status: Approved"),
			selenium.getText("//span[@class='workflow-status']"));
		assertEquals(RuntimeVariables.replace("Download (21.5k)"),
			selenium.getText("//span[@class='download-document']/span/a/span"));
		assertEquals(RuntimeVariables.replace("Get URL or WebDAV URL."),
			selenium.getText("//span[@class='webdav-url']"));
	}
}