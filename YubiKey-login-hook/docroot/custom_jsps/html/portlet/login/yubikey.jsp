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
<%!
private static final String _YUBIKEY_AUTH_PWD_PORTAL_KEY = "yubikey.auth.pwd.portal";
private static final boolean _YUBIKEY_AUTH_PWD_PORTAL_VALUE = GetterUtil.getBoolean(PropsUtil.get(_YUBIKEY_AUTH_PWD_PORTAL_KEY));
%>


<portlet:actionURL var="yubiKeyURL">
	<portlet:param name="struts_action" value="/login/yubikey" />
</portlet:actionURL>

<div style="padding:10px;">
<aui:form action="<%= yubiKeyURL %>" method="post" name="fm">
	
	
	<aui:fieldset>
		<aui:input cssClass="yubikey" name="email_portal" type="text" value="" size="40" inlineField="true"/>
		<img src="/html/portlet/login/email_signin.png" alt="Email for portal" title="Email for Portal" style="width:35px;"/>
		<br>
		<%
		boolean yubikeyAuthPwdPortalEnabled = PrefsPropsUtil.getBoolean(company.getCompanyId(), _YUBIKEY_AUTH_PWD_PORTAL_KEY, _YUBIKEY_AUTH_PWD_PORTAL_VALUE);
		if(yubikeyAuthPwdPortalEnabled){ %>
		<aui:input cssClass="yubikey" name="password_portal" type="password" value="" size="40" inlineField="true"/>
		<img src="/html/portlet/login/password_signin.png" alt="Password for portal" title="Password for Portal" style="width:35px;"/>
		<div class="portlet-msg-info">
			If you are a NEW user, you will register with <span style="color:#0019ff;">username="first part of email address"</span> and <span style="color:#0019ff;">password="Password for Portal"</span>.<br>
			If you are ALREADY REGISTERED in the portal and you will digit e new OTP Key, your PublicId/DeviceId will be overwritten (but only if it is not already associated with some other account).
		</div>
		<%}else{ %>
		<div class="portlet-msg-info">
			If you are a NEW user, you will register with <span style="color:#0019ff;">username="first part of email address"</span> and <span style="color:#0019ff;">password="YubiKey PubblicId/DeviceId"</span>.<br>
			If you are ALREADY REGISTERED in the portal and you will digit e new OTP Key, your PublicId/DeviceId will be overwritten (but only if it is not already associated with some other account).
		</div>
		<%} %>
		<br>
		<aui:input cssClass="yubikey" name="yubikey_otp" type="password" value="" size="40" inlineField="true"/>
		<img src="/html/portlet/login/yubikey_signin.png" alt="YubiKey OTP" title="YubiKey OTP" style="width:35px;"/>
		<br>
		<div class="portlet-msg-alert">
			EACH USER will be a <span style="color:#0019ff;">special field with YubiKey PublicId/DeviceId</span> in this way <span style="color:#0019ff;">for each user will be accepted only one OTP YubiKey</span>.
		</div>
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