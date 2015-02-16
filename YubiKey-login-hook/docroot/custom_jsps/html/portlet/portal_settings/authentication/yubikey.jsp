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

<%@ include file="/html/portlet/portal_settings/init.jsp" %>

<%
boolean yubikeyAuthEnabled = PrefsPropsUtil.getBoolean(company.getCompanyId(), _YUBIKEY_AUTH_ENABLED_KEY, _YUBIKEY_AUTH_ENABLED_VALUE);
%>

<aui:fieldset>
	<aui:input label="enabled" name='<%= "settings--" + _YUBIKEY_AUTH_ENABLED_KEY + "--" %>' type="checkbox" value="<%= yubikeyAuthEnabled %>" />
</aui:fieldset>

<%!
private static final String _YUBIKEY_AUTH_ENABLED_KEY = "yubikey.auth.enabled";
private static final boolean _YUBIKEY_AUTH_ENABLED_VALUE = GetterUtil.getBoolean(PropsUtil.get(_YUBIKEY_AUTH_ENABLED_KEY));
%>