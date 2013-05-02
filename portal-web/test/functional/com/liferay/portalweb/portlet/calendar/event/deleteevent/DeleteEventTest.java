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

package com.liferay.portalweb.portlet.calendar.event.deleteevent;

import com.liferay.portalweb.portal.BaseTestCase;
import com.liferay.portalweb.portal.util.RuntimeVariables;

/**
 * @author Brian Wing Shun Chan
 */
public class DeleteEventTest extends BaseTestCase {
	public void testDeleteEvent() throws Exception {
		selenium.selectWindow("null");
		selenium.selectFrame("relative=top");
		selenium.open("/web/guest/home/");
		selenium.clickAt("link=Calendar Test Page",
			RuntimeVariables.replace("Calendar Test Page"));
		selenium.waitForPageToLoad("30000");
		assertTrue(selenium.isElementPresent(
				"//td[@id='_8_ocerSearchContainer_col-time_row-1']"));
		assertEquals(RuntimeVariables.replace("Calendar Event Title"),
			selenium.getText(
				"//td[@id='_8_ocerSearchContainer_col-title_row-1']"));
		assertEquals(RuntimeVariables.replace("Anniversary"),
			selenium.getText(
				"//td[@id='_8_ocerSearchContainer_col-type_row-1']"));
		Thread.sleep(1000);
		selenium.waitForVisible(
			"//td[contains(.,'Actions')]/span/ul/li/strong/a");
		selenium.clickAt("//td[contains(.,'Actions')]/span/ul/li/strong/a",
			RuntimeVariables.replace("Actions"));
		selenium.waitForVisible(
			"//div[@class='lfr-component lfr-menu-list']/ul/li[4]/a");
		assertEquals(RuntimeVariables.replace("Delete"),
			selenium.getText(
				"//div[@class='lfr-component lfr-menu-list']/ul/li[4]/a"));
		selenium.click(RuntimeVariables.replace(
				"//div[@class='lfr-component lfr-menu-list']/ul/li[4]/a"));
		selenium.waitForPageToLoad("30000");
		assertTrue(selenium.getConfirmation()
						   .matches("^Are you sure you want to delete this[\\s\\S]$"));
		assertEquals(RuntimeVariables.replace(
				"Your request completed successfully."),
			selenium.getText("//div[@class='portlet-msg-success']"));
		assertTrue(selenium.isElementNotPresent(
				"//td[@id='_8_ocerSearchContainer_col-time_row-1']"));
		assertTrue(selenium.isElementNotPresent(
				"//td[@id='_8_ocerSearchContainer_col-title_row-1']"));
		assertTrue(selenium.isElementNotPresent(
				"//td[@id='_8_ocerSearchContainer_col-type_row-1']"));
	}
}