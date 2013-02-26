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

package com.liferay.portalweb.socialofficehome.sites.privatesite.searchmbthreaddeletesitetypeprivate;

import com.liferay.portalweb.portal.BaseTestSuite;
import com.liferay.portalweb.socialoffice.users.user.addsouser.AddSOUserTest;
import com.liferay.portalweb.socialoffice.users.user.addsouser.TearDownSOUserTest;
import com.liferay.portalweb.socialoffice.users.user.editsouserpassword.EditSOUserPasswordTest;
import com.liferay.portalweb.socialoffice.users.user.selectregularrolessouser.SelectRegularRolesSOUserTest;
import com.liferay.portalweb.socialoffice.users.user.signinso.SOUs_SignInSOTest;
import com.liferay.portalweb.socialoffice.users.user.signinso.SOUs_SignOutSOTest;
import com.liferay.portalweb.socialoffice.users.user.signinso.SignInSOTest;
import com.liferay.portalweb.socialoffice.users.user.signinso.SignOutSOTest;
import com.liferay.portalweb.socialofficehome.sites.privatesite.addsitessitetypeprivate.AddSitesSiteTypePrivateTest;
import com.liferay.portalweb.socialofficehome.sites.privatesite.deletesitetypeprivate.DeleteSiteTypePrivateTest;
import com.liferay.portalweb.socialofficehome.sites.privatesite.sousconfirminvitesitetypeprivate.InviteSOUserSiteTypePrivateTest;
import com.liferay.portalweb.socialofficehome.sites.privatesite.sousconfirminvitesitetypeprivate.SOUs_ConfirmInviteSiteTypePrivateTest;
import com.liferay.portalweb.socialofficehome.sites.site.addsitessite.TearDownSOSitesTest;

import junit.framework.Test;
import junit.framework.TestSuite;

/**
 * @author Brian Wing Shun Chan
 */
public class SearchMBThreadDeleteSiteTypePrivateTests extends BaseTestSuite {
	public static Test suite() {
		TestSuite testSuite = new TestSuite();
		testSuite.addTestSuite(AddSOUserTest.class);
		testSuite.addTestSuite(SelectRegularRolesSOUserTest.class);
		testSuite.addTestSuite(EditSOUserPasswordTest.class);
		testSuite.addTestSuite(AddSitesSiteTypePrivateTest.class);
		testSuite.addTestSuite(InviteSOUserSiteTypePrivateTest.class);
		testSuite.addTestSuite(SignOutSOTest.class);
		testSuite.addTestSuite(SOUs_SignInSOTest.class);
		testSuite.addTestSuite(SOUs_ConfirmInviteSiteTypePrivateTest.class);
		testSuite.addTestSuite(SOUs_SignOutSOTest.class);
		testSuite.addTestSuite(SignInSOTest.class);
		testSuite.addTestSuite(SignOutSOTest.class);
		testSuite.addTestSuite(SOUs_SignInSOTest.class);
		testSuite.addTestSuite(SOUs_PostNewMBThreadSiteTypePrivateTest.class);
		testSuite.addTestSuite(SOUs_SignOutSOTest.class);
		testSuite.addTestSuite(SignInSOTest.class);
		testSuite.addTestSuite(DeleteSiteTypePrivateTest.class);
		testSuite.addTestSuite(AddPageSearchSOTest.class);
		testSuite.addTestSuite(AddPortletSearchSOTest.class);
		testSuite.addTestSuite(SearchMBThreadDeleteSiteTypePrivateTest.class);
		testSuite.addTestSuite(TearDownSOUserTest.class);
		testSuite.addTestSuite(TearDownSOSitesTest.class);
		testSuite.addTestSuite(TearDownPageSearchSOTest.class);

		return testSuite;
	}
}