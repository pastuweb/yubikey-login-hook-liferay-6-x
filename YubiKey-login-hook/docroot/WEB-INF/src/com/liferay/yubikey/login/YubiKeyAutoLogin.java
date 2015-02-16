package com.liferay.yubikey.login;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

import com.liferay.portal.NoSuchUserException;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.portal.model.User;
import com.liferay.portal.security.auth.AutoLogin;
import com.liferay.portal.security.auth.AutoLoginException;
import com.liferay.portal.service.UserLocalServiceUtil;
import com.liferay.portal.theme.ThemeDisplay;
import com.yubico.client.v2.YubicoClient;
import com.yubico.client.v2.YubicoResponse;
import com.yubico.client.v2.YubicoResponseStatus;
import com.yubico.client.v2.exceptions.YubicoValidationException;
import com.yubico.client.v2.exceptions.YubicoValidationFailure;

public class YubiKeyAutoLogin implements AutoLogin {

	private final static Logger logger = Logger.getLogger(YubiKeyAutoLogin.class.getName()); 

	public String[] login(HttpServletRequest req, HttpServletResponse res)
			throws AutoLoginException {
		
		String[] credentials = null;
		
		ThemeDisplay themeDisplay = (ThemeDisplay)req.getAttribute(
				WebKeys.THEME_DISPLAY);
		
		String username_portal = req.getParameter("username_portal");
		String client_id = req.getParameter("client_id");
		String otp = req.getParameter("otp");

		if (username_portal == null || client_id == null || otp == null ){
	    	 logger.info("Error from YubiKey AutoLogin");
	         return null;
	    }
		
		try {
			if(validate(otp,Integer.parseInt(client_id))){
		    	 if (Validator.isNotNull(username_portal) &&Validator.isNotNull(client_id)  && Validator.isNotNull(otp) ) {
		    		 credentials = new String[3];
		             User user = UserLocalServiceUtil.getUserByScreenName(themeDisplay.getCompanyId(), username_portal);
		             if(user != null){
		            	 credentials[0] = String.valueOf(user.getUserId());
		                 credentials[1] = user.getPassword();
		          		 credentials[2] = String.valueOf(user.isPasswordEncrypted());
		                 return credentials;
		             }
		    	 }
			}else{
				return null;
			}
	                                 
        }catch (Exception e) {
            e.printStackTrace();
            throw new AutoLoginException(e);
        }
		
		return null;

	}
	
	
	private static boolean validate(String otp, int clientId) {
	   YubicoClient client = YubicoClient.getClient(clientId);
	   YubicoResponse response;
	   try {
			response = client.verify(otp);
			return response.getStatus() == YubicoResponseStatus.OK;
		} catch (YubicoValidationException e) {
			e.printStackTrace();
			return false;
		} catch (YubicoValidationFailure e) {
			e.printStackTrace();
			return false;
		}
	   
	}
	
}
