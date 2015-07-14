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


<portlet:actionURL var="yubiKeyURL">
	<portlet:param name="struts_action" value="/login/yubikey" />
</portlet:actionURL>


<div style="padding:10px;">
<aui:form action="<%= yubiKeyURL %>" method="post" name="fm">
	
	<aui:fieldset>
		<aui:input cssClass="yubikey" name="email_portal" type="text" value="" size="50" inlineField="true"/>
		<img src="/html/portlet/login/email_signin.png" alt="Email for portal" title="Email for portal" style="width:35px;"/>
		<br>
		<aui:input cssClass="yubikey" name="yubikey_otp" type="password" value="" size="50" inlineField="true"/>
		<img src="/html/portlet/login/yubikey_signin.png" alt="YubiKey OTP" title="YubiKey OTP" style="width:35px;"/>

		<aui:button-row>
			<aui:button type="submit" value="sign-in" />
		</aui:button-row>
	</aui:fieldset>
</aui:form>
</div>

<liferay-util:include page="/html/portlet/login/navigation.jsp" />

<c:if test="<%= windowState.equals(WindowState.MAXIMIZED) %>">
	<aui:script>
		Liferay.Util.focusFormField(document.<portlet:namespace />fm.<portlet:namespace />username_portal);
	</aui:script>
</c:if>