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


<%@ include file="/html/portlet/login/init.jsp" %>

<%
String username_portal = ParamUtil.getString(request, "username_portal");
String yubikey_client_id = ParamUtil.getString(request, "yubikey_client_id");
String yubikey_otp = ParamUtil.getString(request, "yubikey_otp");
%>


<portlet:actionURL var="yubiKeyURL">
	<portlet:param name="saveLastPath" value="0" />
	<portlet:param name="struts_action" value="/login/yubikey_auth" />
</portlet:actionURL>

<aui:form action="<%= yubiKeyURL %>" method="post" name="fm">
	<liferay-ui:error exception="<%= MessageException.class %>" message="an-error-occurred-while-communicating-with-the-open-id-provider" />

	<aui:fieldset>
		<aui:input cssClass="yubikey" name="username_portal" type="text" value="<%= username_portal %>" />
	    <aui:input cssClass="yubikey" name="yubikey_client_id" type="text" value="<%= yubikey_client_id %>" />
		<aui:input cssClass="yubikey" name="yubikey_otp" type="password" value="<%= yubikey_otp %>" />

		<aui:button-row>
			<aui:button type="submit" value="sign-in" />
		</aui:button-row>
	</aui:fieldset>
</aui:form>

<liferay-util:include page="/html/portlet/login/navigation.jsp" />

<c:if test="<%= windowState.equals(WindowState.MAXIMIZED) %>">
	<aui:script>
		Liferay.Util.focusFormField(document.<portlet:namespace />fm.<portlet:namespace />username_portal);
	</aui:script>
</c:if>