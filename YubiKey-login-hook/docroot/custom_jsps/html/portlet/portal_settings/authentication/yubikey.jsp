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
Integer yubikeyAuthClientId = PrefsPropsUtil.getInteger(company.getCompanyId(), _YUBIKEY_AUTH_CLIENTID_KEY, _YUBIKEY_AUTH_CLIENTID_VALUE);
String yubikeyAuthSecretKey = PrefsPropsUtil.getString(company.getCompanyId(), _YUBIKEY_AUTH_SECRETKEY_KEY , _YUBIKEY_AUTH_SECRETKEY_VALUE);

%>

<aui:fieldset>
	<aui:input label="enabled" name='<%= "settings--" + _YUBIKEY_AUTH_ENABLED_KEY + "--" %>' type="checkbox" value="<%= yubikeyAuthEnabled %>" />
	<aui:input label="client-id" name='<%= "settings--" + _YUBIKEY_AUTH_CLIENTID_KEY + "--" %>' type="text" value="<%= yubikeyAuthClientId %>" />
	<aui:input label="secret-key-api" name='<%= "settings--" + _YUBIKEY_AUTH_SECRETKEY_KEY + "--" %>' type="text" value="<%= yubikeyAuthSecretKey %>" size="50"/>
</aui:fieldset>

<%!
private static final String _YUBIKEY_AUTH_ENABLED_KEY = "yubikey.auth.enabled";
private static final boolean _YUBIKEY_AUTH_ENABLED_VALUE = GetterUtil.getBoolean(PropsUtil.get(_YUBIKEY_AUTH_ENABLED_KEY));
private static final String _YUBIKEY_AUTH_CLIENTID_KEY = "yubikey.auth.clientid";
private static final Integer _YUBIKEY_AUTH_CLIENTID_VALUE = GetterUtil.getInteger(PropsUtil.get(_YUBIKEY_AUTH_CLIENTID_KEY));
private static final String _YUBIKEY_AUTH_SECRETKEY_KEY = "yubikey.auth.secretkey";
private static final String _YUBIKEY_AUTH_SECRETKEY_VALUE = GetterUtil.getString(PropsUtil.get(_YUBIKEY_AUTH_SECRETKEY_KEY));
%>