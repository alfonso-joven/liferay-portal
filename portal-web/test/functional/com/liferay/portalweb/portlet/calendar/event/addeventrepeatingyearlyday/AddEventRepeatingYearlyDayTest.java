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

package com.liferay.portalweb.portlet.calendar.event.addeventrepeatingyearlyday;

import com.liferay.portalweb.portal.BaseTestCase;
import com.liferay.portalweb.portal.util.RuntimeVariables;

/**
 * @author Brian Wing Shun Chan
 */
public class AddEventRepeatingYearlyDayTest extends BaseTestCase {
	public void testAddEventRepeatingYearlyDay() throws Exception {
		selenium.selectWindow("null");
		selenium.selectFrame("relative=top");
		selenium.open("/web/guest/home/");
		selenium.clickAt("link=Calendar Test Page",
			RuntimeVariables.replace("Calendar Test Page"));
		selenium.waitForPageToLoad("30000");
		selenium.clickAt("//input[@value='Add Event']",
			RuntimeVariables.replace("Add Event"));
		selenium.waitForPageToLoad("30000");
		Thread.sleep(5000);
		selenium.type("//input[@id='_8_title']",
			RuntimeVariables.replace("Calendar Event Title"));
		selenium.waitForElementPresent(
			"//textarea[@id='_8_editor' and @style='display: none;']");
		selenium.waitForVisible("//span[.='Source']");
		assertEquals(RuntimeVariables.replace("Source"),
			selenium.getText("//span[.='Source']"));
		selenium.clickAt("//span[.='Source']",
			RuntimeVariables.replace("Source"));
		selenium.waitForVisible("//a[@class='cke_button_source cke_on']");
		selenium.waitForVisible("//td[@id='cke_contents__8_editor']/textarea");
		selenium.type("//td[@id='cke_contents__8_editor']/textarea",
			RuntimeVariables.replace("Calendar Event Description"));
		assertEquals(RuntimeVariables.replace("Source"),
			selenium.getText("//span[.='Source']"));
		selenium.clickAt("//span[.='Source']",
			RuntimeVariables.replace("Source"));
		selenium.waitForElementPresent(
			"//textarea[@id='_8_editor' and @style='display: none;']");
		selenium.waitForVisible("//td[@id='cke_contents__8_editor']/iframe");
		selenium.selectFrame("//td[@id='cke_contents__8_editor']/iframe");
		selenium.waitForText("//body", "Calendar Event Description");
		selenium.selectFrame("relative=top");
		selenium.waitForVisible("//select[@id='_8_startDateMonth']");
		selenium.clickAt("//select[@id='_8_startDateMonth']",
			RuntimeVariables.replace("Start Date Month"));
		selenium.select("//select[@id='_8_startDateMonth']",
			RuntimeVariables.replace("January"));
		selenium.clickAt("//select[@id='_8_startDateDay']",
			RuntimeVariables.replace("Start Date Day"));
		selenium.select("//select[@id='_8_startDateDay']",
			RuntimeVariables.replace("1"));
		selenium.clickAt("//select[@id='_8_startDateYear']",
			RuntimeVariables.replace("Start Date Year"));
		selenium.select("//select[@id='_8_startDateYear']",
			RuntimeVariables.replace("2014"));
		selenium.clickAt("//input[@id='_8_recurrenceTypeYearly']",
			RuntimeVariables.replace("Yearly"));
		selenium.waitForElementPresent(
			"//input[@name='_8_yearlyType' and @value='1']");
		selenium.clickAt("//input[@name='_8_yearlyType' and @value='1']",
			RuntimeVariables.replace("Day"));
		selenium.clickAt("//select[@id='_8_yearlyPos']",
			RuntimeVariables.replace("Position"));
		selenium.select("//select[@id='_8_yearlyPos']",
			RuntimeVariables.replace("Second"));
		selenium.clickAt("//select[@id='_8_yearlyDay1']",
			RuntimeVariables.replace("Day"));
		selenium.select("//select[@id='_8_yearlyDay1']",
			RuntimeVariables.replace("Monday"));
		selenium.clickAt("//select[@id='_8_yearlyMonth1']",
			RuntimeVariables.replace("Month"));
		selenium.select("//select[@id='_8_yearlyMonth1']",
			RuntimeVariables.replace("February"));
		selenium.type("//input[@id='_8_yearlyInterval1']",
			RuntimeVariables.replace("1"));
		selenium.clickAt("//input[@name='_8_endDateType' and @value='2']",
			RuntimeVariables.replace("End by"));
		selenium.waitForVisible("//select[@id='_8_endDateMonth']");
		selenium.clickAt("//select[@id='_8_endDateMonth']",
			RuntimeVariables.replace("End Date Month"));
		selenium.select("//select[@id='_8_endDateMonth']",
			RuntimeVariables.replace("January"));
		selenium.clickAt("//select[@id='_8_endDateDay']",
			RuntimeVariables.replace("End Date Day"));
		selenium.select("//select[@id='_8_endDateDay']",
			RuntimeVariables.replace("1"));
		selenium.clickAt("//select[@id='_8_endDateYear']",
			RuntimeVariables.replace("End Date Year"));
		selenium.select("//select[@id='_8_endDateYear']",
			RuntimeVariables.replace("2016"));
		selenium.clickAt("//input[@value='Save']", RuntimeVariables.replace(""));
		selenium.waitForPageToLoad("30000");
		assertEquals(RuntimeVariables.replace(
				"Your request completed successfully."),
			selenium.getText("//div[@class='portlet-msg-success']"));
		selenium.open("/web/guest/home/");
		selenium.clickAt("link=Calendar Test Page",
			RuntimeVariables.replace("Calendar Test Page"));
		selenium.waitForPageToLoad("30000");
		selenium.clickAt("link=Events", RuntimeVariables.replace("Events"));
		selenium.waitForPageToLoad("30000");
		selenium.clickAt("//td[@id='_8_ocerSearchContainer_col-title_row-1']/a",
			RuntimeVariables.replace("Calendar Event Title"));
		selenium.waitForPageToLoad("30000");
		assertEquals(RuntimeVariables.replace("Calendar Event Title"),
			selenium.getText("//h1[@class='header-title']"));
		assertEquals(RuntimeVariables.replace("1/1/14"),
			selenium.getText("//dl[@class='property-list']/dd[1]"));
		assertEquals(RuntimeVariables.replace("1/1/16"),
			selenium.getText("//dl[@class='property-list']/dd[2]"));
		selenium.open("/web/guest/home/");
		selenium.clickAt("link=Calendar Test Page",
			RuntimeVariables.replace("Calendar Test Page"));
		selenium.waitForPageToLoad("30000");
		selenium.clickAt("link=Events", RuntimeVariables.replace("Events"));
		selenium.waitForPageToLoad("30000");
		Thread.sleep(5000);
		selenium.waitForVisible(
			"//td[contains(.,'Actions')]/span/ul/li/strong/a");
		selenium.clickAt("//td[contains(.,'Actions')]/span/ul/li/strong/a",
			RuntimeVariables.replace("Actions"));
		selenium.waitForVisible(
			"//div[@class='lfr-component lfr-menu-list']/ul/li[1]/a");
		assertEquals(RuntimeVariables.replace("Edit"),
			selenium.getText(
				"//div[@class='lfr-component lfr-menu-list']/ul/li[1]/a"));
		selenium.click(RuntimeVariables.replace(
				"//div[@class='lfr-component lfr-menu-list']/ul/li[1]/a"));
		selenium.waitForPageToLoad("30000");
		assertEquals("January",
			selenium.getSelectedLabel("//select[@id='_8_startDateMonth']"));
		assertEquals("1",
			selenium.getSelectedLabel("//select[@id='_8_startDateDay']"));
		assertEquals("2014",
			selenium.getSelectedLabel("//select[@id='_8_startDateYear']"));
		assertEquals("Calendar Event Title",
			selenium.getValue("//input[@id='_8_title']"));
		assertTrue(selenium.isChecked("//input[@id='_8_recurrenceTypeYearly']"));
		assertTrue(selenium.isChecked(
				"//input[@name='_8_yearlyType' and @value='1']"));
		assertEquals("Second",
			selenium.getSelectedLabel("//select[@id='_8_yearlyPos']"));
		assertEquals("Monday",
			selenium.getSelectedLabel("//select[@id='_8_yearlyDay1']"));
		assertEquals("February",
			selenium.getSelectedLabel("//select[@id='_8_yearlyMonth1']"));
		assertEquals("1", selenium.getValue("//input[@id='_8_yearlyInterval1']"));
		assertTrue(selenium.isChecked(
				"//input[@name='_8_endDateType' and @value='2']"));
		assertEquals("January",
			selenium.getSelectedLabel("//select[@id='_8_endDateMonth']"));
		assertEquals("1",
			selenium.getSelectedLabel("//select[@id='_8_endDateDay']"));
		assertEquals("2016",
			selenium.getSelectedLabel("//select[@id='_8_endDateYear']"));
		selenium.open(
			"/web/guest/calendar-test-page?p_p_id=8&p_p_lifecycle=0&p_p_state=normal&p_p_mode=view&p_p_col_id=column-1&p_p_col_count=1&_8_tabs1=day&_8_month=1&_8_day=10&_8_year=2014");
		assertTrue(selenium.isElementPresent("link=Calendar Event Title"));
		selenium.open(
			"/web/guest/calendar-test-page?p_p_id=8&p_p_lifecycle=0&p_p_state=normal&p_p_mode=view&p_p_col_id=column-1&p_p_col_count=1&_8_tabs1=day&_8_month=1&_8_day=9&_8_year=2015");
		assertTrue(selenium.isElementPresent("link=Calendar Event Title"));
		selenium.open(
			"/web/guest/calendar-test-page?p_p_id=8&p_p_lifecycle=0&p_p_state=normal&p_p_mode=view&p_p_col_id=column-1&p_p_col_count=1&_8_tabs1=day&_8_month=1&_8_day=9&_8_year=2016");
		assertTrue(selenium.isElementNotPresent("link=Calendar Event Title"));
	}
}