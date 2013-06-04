<%--
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
--%>

<%@ include file="/html/portlet/sites_admin/init.jsp" %>

<%
long groupId = GetterUtil.getLong((String)request.getAttribute("merge_alert.jsp-groupId"));
LayoutSet layoutSet = (LayoutSet)request.getAttribute("merge_alert.jsp-layoutSet");
String redirect = (String)request.getAttribute("merge_alert.jsp-redirect");

Layout mergeFailFriendlyURLLayout = SitesUtil.getMergeFailFriendlyURLLayout(layoutSet.getLayoutSetId());
%>

<c:if test="<%= mergeFailFriendlyURLLayout != null %>">

	<%
	String randomNamespace = PortalUtil.generateRandomKey(request, "portlet_sites_admin_merge_alert").concat(StringPool.UNDERLINE);

	PortletURL retryMergeURL = liferayPortletResponse.createActionURL();

	retryMergeURL.setParameter("redirect", redirect);
	retryMergeURL.setParameter("struts_action", "/sites_admin/edit_site");
	retryMergeURL.setParameter(Constants.CMD, "retry_merge");
	retryMergeURL.setParameter("groupId", String.valueOf(groupId));
	retryMergeURL.setParameter("privateLayoutSet", String.valueOf(layoutSet.isPrivateLayout()));
	%>

	<span class="portlet-msg-alert">
		<liferay-ui:message arguments='<%= new Object[] {"site-template", "friendly-url"} %>' key="the-propagation-of-changes-from-the-x-has-been-disabled-temporarily-because-of-a-colliding-x" />

		<br>

		<liferay-portlet:renderURL portletName="<%= PortletKeys.GROUP_PAGES %>" var="editLayoutsURL">
			<portlet:param name="struts_action" value="/group_pages/edit_layouts" />
			<portlet:param name="backURL" value="<%= redirect %>" />
			<portlet:param name="closeRedirect" value="<%= redirect %>" />
			<portlet:param name="groupId" value="<%= String.valueOf(groupId) %>" />
			<portlet:param name="redirect" value="<%= redirect %>" />
			<portlet:param name="selPlid" value="<%= String.valueOf(mergeFailFriendlyURLLayout.getPlid()) %>" />
			<portlet:param name="tabs1" value='<%= layoutSet.isPrivateLayout() ? "private-pages" : "public-pages" %>' />
		</liferay-portlet:renderURL>

		<liferay-ui:message arguments='<%= new Object[] {"page", "site-template-administrator"} %>' key="please-modify-the-following-x-or-contact-the-x" />&nbsp;<a href="<%= editLayoutsURL %>"><%= mergeFailFriendlyURLLayout.getName(themeDisplay.getLocale()) %></a>

		<liferay-ui:message arguments="site-template" key="then-click-propagate-to-propagate-changes-from-the-x" />

		<span class="aui-button-holder">
			<aui:button id='<%= randomNamespace + "retryMergeButton" %>' value="propagate" />
		</span>
	</span>

	<aui:script use="aui-base">
		var retryMergeButton= A.one('#<%= randomNamespace %>retryMergeButton');

		retryMergeButton.on(
			'click',
			function(event) {
				submitForm(document.hrefFm, '<%= retryMergeURL.toString() %>');
			}
		);
	</aui:script>
</c:if>