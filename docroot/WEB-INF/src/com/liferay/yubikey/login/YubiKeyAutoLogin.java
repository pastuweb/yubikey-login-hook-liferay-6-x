package com.liferay.yubikey.login;


import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.log4j.Logger;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.model.User;
import com.liferay.portal.security.auth.AutoLogin;
import com.liferay.portal.service.UserLocalServiceUtil;



public class YubiKeyAutoLogin implements AutoLogin {
	
	private final static Logger logger = Logger.getLogger(YubiKeyAutoLogin.class); 

	public String[] login(HttpServletRequest req, HttpServletResponse res) {
		
		logger.info("into login of YubiKeyAutoLogin");
		
		String[] credentials = null;
		String email_portal = req.getParameter("email_portal");
		long company_id = Long.parseLong(req.getParameter("company_id"));
		
		logger.info("email_portal="+email_portal+" company_id="+company_id);
		
		if (email_portal == null){
	    	 logger.error("Error from YubiKey AutoLogin");
	         return null;
	    }

		try {
	    	 if (Validator.isNotNull(email_portal)) {
	    		 credentials = new String[3];
	             User user = UserLocalServiceUtil.getUserByEmailAddress(company_id, email_portal);
	             if(user != null){
	            	 logger.info("user exist");
	            	 credentials[0] = String.valueOf(user.getUserId());
	                 credentials[1] = user.getPassword();
	          		 credentials[2] = String.valueOf(user.isPasswordEncrypted());
	                 return credentials;
	             }
	    	 }
	    	 
        }catch (PortalException e) {
        	logger.warn(e.getMessage());
        } catch (SystemException e) {
        	logger.warn(e.getMessage());
		}
		return null;
	}

}
