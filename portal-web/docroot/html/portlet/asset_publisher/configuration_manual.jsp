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

<%@ include file="/html/portlet/asset_publisher/init.jsp" %>

<%
List<AssetRendererFactory> classTypesAssetRendererFactories = (List<AssetRendererFactory>) request.getAttribute("configuration.jsp-classTypesAssetRendererFactories");
PortletURL configurationRenderURL = (PortletURL) request.getAttribute("configuration.jsp-configurationRenderURL");
String redirect = (String) request.getAttribute("configuration.jsp-redirect");
String rootPortletId = (String) request.getAttribute("configuration.jsp-rootPortletId");
String selectScope = (String) request.getAttribute("configuration.jsp-selectScope");
%>

<liferay-ui:panel-container extended="<%= true %>" id="assetPublisherSelectionStylePanelContainer" persistState="<%= true %>">
	<liferay-ui:panel collapsible="<%= true %>" extended="<%= true %>" id="assetPublisherSelectionStylePanel" persistState="<%= true %>" title="selection">
		<aui:fieldset label="scope">
			<%= selectScope %>
		</aui:fieldset>

		<aui:fieldset>

			<%
			classNameIds = availableClassNameIds;

			String portletId = portletResource;

			for (long groupId : groupIds) {
			%>

				<div class="add-asset-selector">
					<div class="lfr-meta-actions edit-controls">
						<%@ include file="/html/portlet/asset_publisher/add_asset.jspf" %>

						<liferay-ui:icon-menu align="left" cssClass="select-existing-selector" icon='<%= themeDisplay.getPathThemeImages() + "/common/search.png" %>' message="select-existing" showWhenSingleIcon="<%= true %>">

							<%
							for (AssetRendererFactory curRendererFactory : AssetRendererFactoryRegistryUtil.getAssetRendererFactories()) {
								if (curRendererFactory.isSelectable()) {
									String taglibURL = "javascript:" + renderResponse.getNamespace() + "selectionForType('" + groupId + "', '" + curRendererFactory.getClassName() + "')";
								%>

									<liferay-ui:icon
										message="<%= ResourceActionsUtil.getModelResource(locale, curRendererFactory.getClassName()) %>" src="<%= curRendererFactory.getIconPath(renderRequest) %>" url="<%= taglibURL %>"
									/>

								<%
								}
							}
							%>

						</liferay-ui:icon-menu>
					</div>
				</div>

			<%
			}

			List<AssetEntry> assetEntries = AssetPublisherUtil.getAssetEntries(renderRequest, portletPreferences, permissionChecker, groupIds, assetEntryXmls, true, enablePermissions);
			%>

			<liferay-ui:search-container
				emptyResultsMessage="no-assets-selected"
				iteratorURL="<%= configurationRenderURL %>"
			>
				<liferay-ui:search-container-results>

					<%
					int end = (assetEntries.size() < searchContainer.getEnd()) ? assetEntries.size() : searchContainer.getEnd();

					pageContext.setAttribute("total", assetEntries.size());
					pageContext.setAttribute("results", assetEntries.subList(searchContainer.getStart(), end));
					%>

				</liferay-ui:search-container-results>

				<liferay-ui:search-container-row
					className="com.liferay.portlet.asset.model.AssetEntry"
					escapedModel="<%= true %>"
					keyProperty="entryId"
					modelVar="assetEntry"
				>

					<%
					AssetRendererFactory assetRendererFactory = AssetRendererFactoryRegistryUtil.getAssetRendererFactoryByClassName(assetEntry.getClassName());

					AssetRenderer assetRenderer = assetRendererFactory.getAssetRenderer(assetEntry.getClassPK());
					%>

					<liferay-ui:search-container-column-text name="title">
						<img alt="" src="<%= assetRenderer.getIconPath(renderRequest) %>" /><%= assetRenderer.getTitle(locale) %>
					</liferay-ui:search-container-column-text>

					<liferay-ui:search-container-column-text
						name="type"
						value="<%= ResourceActionsUtil.getModelResource(locale, assetRendererFactory.getClassName()) %>"
					/>

					<liferay-ui:search-container-column-jsp
						align="right"
						path="/html/portlet/asset_publisher/asset_selection_action.jsp"
					/>
				</liferay-ui:search-container-row>

				<liferay-ui:search-iterator paginate="<%= total > SearchContainer.DEFAULT_DELTA %>" />
			</liferay-ui:search-container>

			<c:if test='<%= SessionMessages.contains(renderRequest, "deletedMissingAssetEntries") %>'>
				<div class="portlet-msg-info">
					<liferay-ui:message key="the-selected-assets-have-been-removed-from-the-list-because-they-do-not-belong-in-the-scope-of-this-portlet" />
				</div>
			</c:if>

		</aui:fieldset>
	</liferay-ui:panel>
	<liferay-ui:panel collapsible="<%= true %>" extended="<%= true %>" id="assetPublisherSelectionDisplaySettingsPanel" persistState="<%= true %>" title="display-settings">
		<%@ include file="/html/portlet/asset_publisher/display_settings.jspf" %>
	</liferay-ui:panel>
</liferay-ui:panel-container>

<aui:button-row>
	<aui:button onClick='<%= renderResponse.getNamespace() + "saveSelectBoxes();" %>' type="submit" />
</aui:button-row>

<aui:script>
	function <portlet:namespace />selectionForType(groupId, type) {
		document.<portlet:namespace />fm.<portlet:namespace />groupId.value = groupId;
		document.<portlet:namespace />fm.<portlet:namespace />typeSelection.value = type;
		document.<portlet:namespace />fm.<portlet:namespace />assetEntryOrder.value = -1;

		submitForm(document.<portlet:namespace />fm, '<%= configurationRenderURL.toString() %>');
	}
</aui:script>