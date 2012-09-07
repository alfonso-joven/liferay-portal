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

package com.liferay.portalweb.portlet.assetpublisher.wikipagecomment.ratewikipagecommentap;

import com.liferay.portalweb.portal.BaseTestCase;
import com.liferay.portalweb.portal.util.RuntimeVariables;

/**
 * @author Brian Wing Shun Chan
 */
public class RateWikiPageCommentAPTest extends BaseTestCase {
	public void testRateWikiPageCommentAP() throws Exception {
		selenium.selectWindow("null");
		selenium.selectFrame("relative=top");
		selenium.open("/web/guest/home/");
		selenium.waitForVisible("link=Asset Publisher Test Page");
		selenium.clickAt("link=Asset Publisher Test Page",
			RuntimeVariables.replace("Asset Publisher Test Page"));
		selenium.waitForPageToLoad("30000");
		selenium.waitForText("//div[@class='aui-rating-label-element']",
			"0 (0 Votes)");
		assertEquals(RuntimeVariables.replace("0 (0 Votes)"),
			selenium.getText("//div[@class='aui-rating-label-element']"));
		assertTrue(selenium.isVisible(
				"//a[@class='aui-rating-element aui-rating-element-off aui-rating-thumb-up']"));
		selenium.click(
			"//a[@class='aui-rating-element aui-rating-element-off aui-rating-thumb-up']");
		selenium.waitForText("//div[@class='aui-rating-label-element']",
			"+1 (1 Vote)");
		assertEquals(RuntimeVariables.replace("+1 (1 Vote)"),
			selenium.getText("//div[@class='aui-rating-label-element']"));
		assertTrue(selenium.isVisible(
				"//a[@class='aui-rating-element aui-rating-element-off aui-rating-thumb-up aui-rating-element-on']"));
		selenium.click(
			"//a[@class='aui-rating-element aui-rating-element-off aui-rating-thumb-up aui-rating-element-on']");
		selenium.waitForText("//div[@class='aui-rating-label-element']",
			"0 (0 Votes)");
		assertEquals(RuntimeVariables.replace("0 (0 Votes)"),
			selenium.getText("//div[@class='aui-rating-label-element']"));
		assertTrue(selenium.isVisible(
				"//a[@class='aui-rating-element aui-rating-element-off aui-rating-thumb-down']"));
		selenium.click(
			"//a[@class='aui-rating-element aui-rating-element-off aui-rating-thumb-down']");
		selenium.waitForText("//div[@class='aui-rating-label-element']",
			"-1 (1 Vote)");
		assertEquals(RuntimeVariables.replace("-1 (1 Vote)"),
			selenium.getText("//div[@class='aui-rating-label-element']"));
		assertTrue(selenium.isVisible(
				"//a[@class='aui-rating-element aui-rating-element-off aui-rating-thumb-down aui-rating-element-on']"));
		selenium.click(
			"//a[@class='aui-rating-element aui-rating-element-off aui-rating-thumb-down aui-rating-element-on']");
		selenium.waitForText("//div[@class='aui-rating-label-element']",
			"0 (0 Votes)");
		assertEquals(RuntimeVariables.replace("0 (0 Votes)"),
			selenium.getText("//div[@class='aui-rating-label-element']"));
		assertTrue(selenium.isVisible(
				"//a[@class='aui-rating-element aui-rating-element-off aui-rating-thumb-up']"));
		selenium.click(
			"//a[@class='aui-rating-element aui-rating-element-off aui-rating-thumb-up']");
		selenium.waitForText("//div[@class='aui-rating-label-element']",
			"+1 (1 Vote)");
		assertEquals(RuntimeVariables.replace("+1 (1 Vote)"),
			selenium.getText("//div[@class='aui-rating-label-element']"));
		assertTrue(selenium.isVisible(
				"//a[@class='aui-rating-element aui-rating-element-off aui-rating-thumb-down']"));
		selenium.click(
			"//a[@class='aui-rating-element aui-rating-element-off aui-rating-thumb-down']");
		selenium.waitForText("//div[@class='aui-rating-label-element']",
			"-1 (1 Vote)");
		assertEquals(RuntimeVariables.replace("-1 (1 Vote)"),
			selenium.getText("//div[@class='aui-rating-label-element']"));
		assertTrue(selenium.isVisible(
				"//a[@class='aui-rating-element aui-rating-element-off aui-rating-thumb-down aui-rating-element-on']"));
		selenium.click(
			"//a[@class='aui-rating-element aui-rating-element-off aui-rating-thumb-down aui-rating-element-on']");
		selenium.waitForText("//div[@class='aui-rating-label-element']",
			"0 (0 Votes)");
		assertEquals(RuntimeVariables.replace("0 (0 Votes)"),
			selenium.getText("//div[@class='aui-rating-label-element']"));
		assertTrue(selenium.isVisible(
				"//a[@class='aui-rating-element aui-rating-element-off aui-rating-thumb-down']"));
		selenium.click(
			"//a[@class='aui-rating-element aui-rating-element-off aui-rating-thumb-down']");
		selenium.waitForText("//div[@class='aui-rating-label-element']",
			"-1 (1 Vote)");
		assertEquals(RuntimeVariables.replace("-1 (1 Vote)"),
			selenium.getText("//div[@class='aui-rating-label-element']"));
		assertTrue(selenium.isVisible(
				"//a[@class='aui-rating-element aui-rating-element-off aui-rating-thumb-up']"));
		selenium.click(
			"//a[@class='aui-rating-element aui-rating-element-off aui-rating-thumb-up']");
		selenium.waitForText("//div[@class='aui-rating-label-element']",
			"+1 (1 Vote)");
		assertEquals(RuntimeVariables.replace("+1 (1 Vote)"),
			selenium.getText("//div[@class='aui-rating-label-element']"));
		assertTrue(selenium.isVisible(
				"//a[@class='aui-rating-element aui-rating-element-off aui-rating-thumb-up aui-rating-element-on']"));
		selenium.click(
			"//a[@class='aui-rating-element aui-rating-element-off aui-rating-thumb-up aui-rating-element-on']");
		selenium.waitForText("//div[@class='aui-rating-label-element']",
			"0 (0 Votes)");
		assertEquals(RuntimeVariables.replace("0 (0 Votes)"),
			selenium.getText("//div[@class='aui-rating-label-element']"));
		assertTrue(selenium.isVisible(
				"//a[@class='aui-rating-element aui-rating-element-off aui-rating-thumb-up']"));
		assertTrue(selenium.isVisible(
				"//a[@class='aui-rating-element aui-rating-element-off aui-rating-thumb-down']"));
		assertTrue(selenium.isElementNotPresent(
				"//a[@class='aui-rating-element aui-rating-element-off aui-rating-thumb-up aui-rating-element-on']"));
		assertTrue(selenium.isElementNotPresent(
				"//a[@class='aui-rating-element aui-rating-element-off aui-rating-thumb-down aui-rating-element-on']"));
	}
}