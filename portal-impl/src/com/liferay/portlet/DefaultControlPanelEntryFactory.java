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

package com.liferay.portlet;

import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.util.ClassUtil;
import com.liferay.portal.kernel.util.InstanceFactory;
import com.liferay.portal.security.pacl.PACLClassLoaderUtil;
import com.liferay.portal.util.PropsValues;

/**
 * @author Brian Wing Shun Chan
 */
public class DefaultControlPanelEntryFactory {

	public static ControlPanelEntry getInstance() {
		if (_originalControlPanelEntry == null) {
			if (_log.isDebugEnabled()) {
				_log.debug(
					"Instantiate " +
						PropsValues.CONTROL_PANEL_DEFAULT_ENTRY_CLASS);
			}

			ClassLoader classLoader =
				PACLClassLoaderUtil.getPortalClassLoader();

			try {
				_originalControlPanelEntry =
					(ControlPanelEntry)InstanceFactory.newInstance(
						classLoader,
						PropsValues.CONTROL_PANEL_DEFAULT_ENTRY_CLASS);
			}
			catch (Exception e) {
				_log.error(e, e);
			}
		}

		if (_controlPanelEntry == null) {
			_controlPanelEntry = _originalControlPanelEntry;
		}

		if (_log.isDebugEnabled()) {
			_log.debug("Return " + ClassUtil.getClassName(_controlPanelEntry));
		}

		return _controlPanelEntry;
	}

	public static void setInstance(ControlPanelEntry controlPanelEntryFactory) {
		if (_log.isDebugEnabled()) {
			_log.debug(
				"Set " + ClassUtil.getClassName(controlPanelEntryFactory));
		}

		if (controlPanelEntryFactory == null) {
			_controlPanelEntry = _originalControlPanelEntry;
		}
		else {
			_controlPanelEntry = controlPanelEntryFactory;
		}
	}

	private static Log _log = LogFactoryUtil.getLog(
		DefaultControlPanelEntryFactory.class);

	private static ControlPanelEntry _controlPanelEntry;
	private static ControlPanelEntry _originalControlPanelEntry;

}