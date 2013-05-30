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

package com.liferay.portalweb.socialofficehome.tasks.task.sousresolvetaskstaskassignedtoconnection;

import com.liferay.portalweb.portal.BaseTestCase;
import com.liferay.portalweb.portal.util.RuntimeVariables;

/**
 * @author Brian Wing Shun Chan
 */
public class ViewResolveTasksTaskAssignedToConnectionTest extends BaseTestCase {
	public void testViewResolveTasksTaskAssignedToConnection()
		throws Exception {
		int label = 1;

		while (label >= 1) {
			switch (label) {
			case 1:
				selenium.selectWindow("null");
				selenium.selectFrame("relative=top");
				selenium.open("/user/joebloggs/so/dashboard/");
				selenium.clickAt("//div[@id='dockbar']",
					RuntimeVariables.replace("Dockbar"));
				selenium.waitForElementPresent(
					"//script[contains(@src,'/aui/aui-editable/aui-editable-min.js')]");
				assertTrue(selenium.isElementPresent(
						"//li[@id='_145_notificationsMenu']"));
				assertEquals(RuntimeVariables.replace("1"),
					selenium.getText("//span[@class='notification-count']"));
				selenium.mouseOver("//li[@id='_145_notificationsMenu']");
				selenium.waitForVisible("//div[@class='notification-entry']");
				assertEquals(RuntimeVariables.replace(
						"Social01 Office01 User01 resolved the task."),
					selenium.getText(
						"//div[@class='notification-entry']/div[@class='title']"));
				assertEquals(RuntimeVariables.replace("Task Description"),
					selenium.getText(
						"//div[@class='notification-entry']/div[@class='body']"));
				assertEquals(RuntimeVariables.replace("Mark All as Read"),
					selenium.getText("//span[@class='dismiss-notifications']/a"));
				selenium.clickAt("//span[@class='dismiss-notifications']/a",
					RuntimeVariables.replace("Mark All as Read"));
				selenium.waitForText("//span[@class='notification-count']", "0");
				assertEquals(RuntimeVariables.replace("0"),
					selenium.getText("//span[@class='notification-count']"));
				selenium.waitForVisible(
					"//nav/ul/li[contains(.,'Tasks')]/a/span");
				selenium.clickAt("//nav/ul/li[contains(.,'Tasks')]/a/span",
					RuntimeVariables.replace("Tasks"));
				selenium.waitForPageToLoad("30000");
				assertEquals(RuntimeVariables.replace("Tasks"),
					selenium.getText(
						"xPath=(//span[@class='portlet-title-default'])[contains(.,'Tasks')]"));
				assertEquals(RuntimeVariables.replace("I Have Created"),
					selenium.getText("link=I Have Created"));
				selenium.clickAt("link=I Have Created",
					RuntimeVariables.replace("I Have Created"));
				selenium.waitForPageToLoad("30000");
				selenium.waitForVisible("//td[1]/input");

				boolean showCompleted1Checked = selenium.isChecked(
						"//td[1]/input");

				if (showCompleted1Checked) {
					label = 2;

					continue;
				}

				selenium.clickAt("//td[1]/input",
					RuntimeVariables.replace("Check Show Completed Tasks"));

			case 2:
				selenium.waitForVisible("link=Task Description");
				assertEquals(RuntimeVariables.replace("Task Description"),
					selenium.getText("link=Task Description"));
				selenium.clickAt("link=Task Description",
					RuntimeVariables.replace("Task Description"));
				selenium.waitForText("//h1[@class='header-title']",
					"Task Description");
				assertEquals(RuntimeVariables.replace("Task Description"),
					selenium.getText("//h1[@class='header-title']"));
				assertEquals(RuntimeVariables.replace(
						"Assigned to Social01 Office01 User01"),
					selenium.getText("//div[@class='task-data assignee']"));
				assertEquals(RuntimeVariables.replace("Resolved"),
					selenium.getText("//div[@class='task-data status']"));
				assertEquals(RuntimeVariables.replace("Normal"),
					selenium.getText("//div[@class='task-data normal']"));
				assertEquals("Reopen",
					selenium.getValue("//input[@value='Reopen']"));

			case 100:
				label = -1;
			}
		}
	}
}