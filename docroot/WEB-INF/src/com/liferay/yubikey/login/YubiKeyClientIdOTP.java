package com.liferay.yubikey.login;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.liferay.portal.kernel.struts.BaseStrutsAction;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.portal.theme.ThemeDisplay;

public class YubiKeyClientIdOTP extends BaseStrutsAction {

	
	public String execute(
			HttpServletRequest request, HttpServletResponse response)
		throws Exception {

		ThemeDisplay themeDisplay = (ThemeDisplay)request.getAttribute(
			WebKeys.THEME_DISPLAY);

		String username_portal = ParamUtil.getString(request, "username_portal");
		String client_id = ParamUtil.getString(request, "yubikey_client_id");
		String otp = ParamUtil.getString(request, "yubikey_otp");

		//now you can enter in the portal
		response.sendRedirect(themeDisplay.getCDNBaseURL()+"?username_portal="+username_portal+"&client_id="+client_id+"&otp="+otp);

		return null;
		
	}

	
	
	
}
