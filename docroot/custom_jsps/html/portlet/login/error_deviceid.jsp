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

<br>
<div class="portlet-msg-alert">
<span style="color:#0019ff;">YubiKey OTP corrects</span> but <span style="color:#0019ff;">YubiKey PublicId/DeviceId already associated to another account</span> or <span style="color:#0019ff;">you can not upgrade what you have been associated (only with user+password+OTP option)</span>.<br>
Contact the administrator for more information.  
</div>
<br>

<liferay-util:include page="/html/portlet/login/navigation.jsp" />

<c:if test="<%= windowState.equals(WindowState.MAXIMIZED) %>">
	<aui:script>
		Liferay.Util.focusFormField(document.<portlet:namespace />fm.<portlet:namespace />username_portal);
	</aui:script>
</c:if>