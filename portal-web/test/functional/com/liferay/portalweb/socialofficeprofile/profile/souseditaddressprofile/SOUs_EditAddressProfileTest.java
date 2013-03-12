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

package com.liferay.portalweb.socialofficeprofile.profile.souseditaddressprofile;

import com.liferay.portalweb.portal.BaseTestCase;
import com.liferay.portalweb.portal.util.RuntimeVariables;

/**
 * @author Brian Wing Shun Chan
 */
public class SOUs_EditAddressProfileTest extends BaseTestCase {
	public void testSOUs_EditAddressProfile() throws Exception {
		selenium.selectWindow("null");
		selenium.selectFrame("relative=top");
		selenium.open("/web/socialoffice01/so/profile");
		selenium.waitForVisible("//div[@class='lfr-contact-name']/a");
		assertEquals(RuntimeVariables.replace("Social01 Office01 User01"),
			selenium.getText("//div[@class='lfr-contact-name']/a"));
		selenium.waitForVisible("//div[@data-title='Addresses']/h3");
		assertEquals(RuntimeVariables.replace("Addresses:"),
			selenium.getText("//div[@data-title='Addresses']/h3"));
		assertEquals(RuntimeVariables.replace(
				"Personal 123 Liferay Ln., Ray of Light, 91234, California, United States (Mailing)"),
			selenium.getText("//div[@data-title='Addresses']/ul/li"));
		selenium.clickAt("//div[@data-title='Addresses']",
			RuntimeVariables.replace("Addresses:"));
		selenium.waitForVisible("//input[contains(@id,'addressStreet1')]");
		Thread.sleep(1000);
		selenium.type("//input[contains(@id,'addressStreet1')]",
			RuntimeVariables.replace("456 Liferay Ln."));
		selenium.select("//select[contains(@id,'addressCountry')]",
			RuntimeVariables.replace("label=Canada"));
		selenium.waitForPartialText("//select[contains(@id,'addressRegion')]",
			"Ontario");
		selenium.select("//select[contains(@id,'addressRegion')]",
			RuntimeVariables.replace("label=Ontario"));
		selenium.type("//input[contains(@id,'addressZip')]",
			RuntimeVariables.replace("95678"));
		selenium.type("//input[contains(@id,'addressCity')]",
			RuntimeVariables.replace("New Ray of Light"));
		selenium.select("//select[contains(@id,'addressType')]",
			RuntimeVariables.replace("label=Business"));
		selenium.clickAt("//input[contains(@id,'addressMailing0Checkbox')]",
			RuntimeVariables.replace("Mailing Checkbox"));
		selenium.clickAt("//input[@value='Save']",
			RuntimeVariables.replace("Save"));
		selenium.waitForText("//div[@data-title='Addresses']/ul/li",
			"Business 456 Liferay Ln., New Ray of Light, 95678, Ontario, Canada");
		assertEquals(RuntimeVariables.replace("Addresses:"),
			selenium.getText("//div[@data-title='Addresses']/h3"));
		assertEquals(RuntimeVariables.replace(
				"Business 456 Liferay Ln., New Ray of Light, 95678, Ontario, Canada"),
			selenium.getText("//div[@data-title='Addresses']/ul/li"));
	}
}