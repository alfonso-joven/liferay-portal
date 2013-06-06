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

package com.liferay.portal.service;

import com.liferay.portal.ReservedUserEmailAddressException;
import com.liferay.portal.kernel.test.ExecutionTestListeners;
import com.liferay.portal.kernel.transaction.Transactional;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.kernel.util.ReflectionUtil;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.model.User;
import com.liferay.portal.model.UserGroupRole;
import com.liferay.portal.security.auth.PrincipalThreadLocal;
import com.liferay.portal.test.EnvironmentExecutionTestListener;
import com.liferay.portal.test.LiferayIntegrationJUnitTestRunner;
import com.liferay.portal.test.TransactionalCallbackAwareExecutionTestListener;
import com.liferay.portal.util.PropsValues;
import com.liferay.portal.util.TestPropsValues;

import java.lang.reflect.Field;

import java.util.Calendar;
import java.util.List;
import java.util.Locale;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * @author Brian Wing Shun Chan
 */
@ExecutionTestListeners(
	listeners = {
		EnvironmentExecutionTestListener.class,
		TransactionalCallbackAwareExecutionTestListener.class
	})
@RunWith(LiferayIntegrationJUnitTestRunner.class)
@Transactional
public class UserServiceTest {

	@Test
	public void testAddUser() throws Exception {
		addUser(true);
	}

	@Test
	public void testCompanySecurityStrangersWithMX1() throws Exception {
		Field field = ReflectionUtil.getDeclaredField(
			PropsValues.class, "COMPANY_SECURITY_STRANGERS_WITH_MX");

		Object value = field.get(null);

		String name = PrincipalThreadLocal.getName();

		try {
			field.set(null, Boolean.FALSE);

			PrincipalThreadLocal.setName(0);

			addUser(true);

			Assert.fail();
		}
		catch (ReservedUserEmailAddressException rueae) {
		}
		finally {
			field.set(null, value);

			PrincipalThreadLocal.setName(name);
		}
	}

	@Test
	public void testCompanySecurityStrangersWithMX2() throws Exception {
		Field field = ReflectionUtil.getDeclaredField(
			PropsValues.class, "COMPANY_SECURITY_STRANGERS_WITH_MX");

		Object value = field.get(null);

		String name = PrincipalThreadLocal.getName();

		try {
			field.set(null, Boolean.FALSE);

			User user = addUser(false);

			PrincipalThreadLocal.setName(user.getUserId());

			updateUser(user);

			Assert.fail();
		}
		catch (ReservedUserEmailAddressException rueae) {
		}
		finally {
			field.set(null, value);

			PrincipalThreadLocal.setName(name);
		}
	}

	@Test
	public void testCompanySecurityStrangersWithMX3() throws Exception {
		Field field = ReflectionUtil.getDeclaredField(
			PropsValues.class, "COMPANY_SECURITY_STRANGERS_WITH_MX");

		Object value = field.get(null);

		String name = PrincipalThreadLocal.getName();

		try {
			field.set(null, Boolean.FALSE);

			User user = addUser(false);

			PrincipalThreadLocal.setName(user.getUserId());

			String emailAddress =
				"UserServiceTest." + ServiceTestUtil.nextLong() +
					"@liferay.com";

			UserServiceUtil.updateEmailAddress(
				user.getUserId(), user.getPassword(), emailAddress,
				emailAddress, new ServiceContext());

			Assert.fail();
		}
		catch (ReservedUserEmailAddressException rueae) {
		}
		finally {
			field.set(null, value);

			PrincipalThreadLocal.setName(name);
		}
	}

	@Test
	public void testDeleteUser() throws Exception {
		User user = addUser(true);

		UserServiceUtil.deleteUser(user.getUserId());
	}

	@Test
	public void testGetUser() throws Exception {
		User user = addUser(true);

		UserServiceUtil.getUserByEmailAddress(
			TestPropsValues.getCompanyId(), user.getEmailAddress());
	}

	protected User addUser(boolean secure) throws Exception {
		boolean autoPassword = true;
		String password1 = StringPool.BLANK;
		String password2 = StringPool.BLANK;
		boolean autoScreenName = true;
		String screenName = StringPool.BLANK;
		long facebookId = 0;
		String openId = StringPool.BLANK;
		Locale locale = LocaleUtil.getDefault();
		String firstName = "UserServiceTest";
		String middleName = StringPool.BLANK;
		String lastName = "UserServiceTest";
		int prefixId = 0;
		int suffixId = 0;
		boolean male = true;
		int birthdayMonth = Calendar.JANUARY;
		int birthdayDay = 1;
		int birthdayYear = 1970;
		String jobTitle = StringPool.BLANK;
		long[] groupIds = null;
		long[] organizationIds = null;
		long[] roleIds = null;
		long[] userGroupIds = null;
		boolean sendMail = false;

		ServiceContext serviceContext = new ServiceContext();

		if (secure) {
			String emailAddress =
				"UserServiceTest." + ServiceTestUtil.nextLong() +
					"@liferay.com";

			return UserServiceUtil.addUser(
				TestPropsValues.getCompanyId(), autoPassword, password1,
				password2, autoScreenName, screenName, emailAddress, facebookId,
				openId, locale, firstName, middleName, lastName, prefixId,
				suffixId, male, birthdayMonth, birthdayDay, birthdayYear,
				jobTitle, groupIds, organizationIds, roleIds, userGroupIds,
				sendMail, serviceContext);
		}
		else {
			String emailAddress =
				"UserServiceTest." + ServiceTestUtil.nextLong() + "@test.com";

			return UserLocalServiceUtil.addUser(
				TestPropsValues.getUserId(), TestPropsValues.getCompanyId(),
				autoPassword, password1, password2, autoScreenName, screenName,
				emailAddress, facebookId, openId, locale, firstName, middleName,
				lastName, prefixId, suffixId, male, birthdayMonth, birthdayDay,
				birthdayYear, jobTitle, groupIds, organizationIds, roleIds,
				userGroupIds, sendMail, serviceContext);
		}
	}

	protected User updateUser(User user) throws Exception {
		String oldPassword = StringPool.BLANK;
		String newPassword1 = StringPool.BLANK;
		String newPassword2 = StringPool.BLANK;
		Boolean passwordReset = false;
		String reminderQueryQuestion = StringPool.BLANK;
		String reminderQueryAnswer = StringPool.BLANK;
		String screenName = "TestUser" + ServiceTestUtil.nextLong();
		String emailAddress =
			"UserServiceTest." + ServiceTestUtil.nextLong() + "@liferay.com";
		long facebookId = 0;
		String openId = StringPool.BLANK;
		String languageId = StringPool.BLANK;
		String timeZoneId = StringPool.BLANK;
		String greeting = StringPool.BLANK;
		String comments = StringPool.BLANK;
		String firstName = "UserServiceTest";
		String middleName = StringPool.BLANK;
		String lastName = "UserServiceTest";
		int prefixId = 0;
		int suffixId = 0;
		boolean male = true;
		int birthdayMonth = Calendar.JANUARY;
		int birthdayDay = 1;
		int birthdayYear = 1970;
		String smsSn = StringPool.BLANK;
		String aimSn = StringPool.BLANK;
		String facebookSn = StringPool.BLANK;
		String icqSn = StringPool.BLANK;
		String jabberSn = StringPool.BLANK;
		String msnSn = StringPool.BLANK;
		String mySpaceSn = StringPool.BLANK;
		String skypeSn = StringPool.BLANK;
		String twitterSn = StringPool.BLANK;
		String ymSn = StringPool.BLANK;
		String jobTitle = StringPool.BLANK;
		long[] groupIds = null;
		long[] organizationIds = null;
		long[] roleIds = null;
		List<UserGroupRole> userGroupRoles = null;
		long[] userGroupIds = null;

		ServiceContext serviceContext = new ServiceContext();

		return UserServiceUtil.updateUser(
			user.getUserId(), oldPassword, newPassword1, newPassword2,
			passwordReset, reminderQueryQuestion, reminderQueryAnswer,
			screenName, emailAddress, facebookId, openId, languageId,
			timeZoneId, greeting, comments, firstName, middleName, lastName,
			prefixId, suffixId, male, birthdayMonth, birthdayDay, birthdayYear,
			smsSn, aimSn, facebookSn, icqSn, jabberSn, msnSn, mySpaceSn,
			skypeSn, twitterSn, ymSn, jobTitle, groupIds, organizationIds,
			roleIds, userGroupRoles, userGroupIds, serviceContext);
	}

}