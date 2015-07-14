package com.liferay.yubikey.login;

import java.io.IOException;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.log4j.Logger;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.model.User;
import com.liferay.portal.security.auth.AutoLogin;
import com.liferay.portal.service.ServiceContext;
import com.liferay.portal.service.ServiceContextFactory;
import com.liferay.portal.service.UserLocalServiceUtil;
import com.liferay.util.PwdGenerator;


public class YubiKeyAutoLogin implements AutoLogin {

	private final static Logger logger = Logger.getLogger(YubiKeyAutoLogin.class.getName()); 

	public String[] login(HttpServletRequest req, HttpServletResponse res) {
		
		logger.info("into login of YubiKeyAutoLogin");
		
		String[] credentials = null;
		
		String email_portal = req.getParameter("email_portal");
		long company_id = Long.parseLong(req.getParameter("company_id"));
		String username = email_portal.split("@")[0];
		
		logger.info("email_portal = "+email_portal);
		logger.info("username = "+username);
		logger.info("company_id = "+company_id);
		
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
	        //user not exist, will create - TO-DO
	       	logger.info("Warning - user not exist, will creat with this data:");
	       	logger.info("username = "+username);
	       	logger.info("email = "+email_portal);
	       	String pwd = PwdGenerator.getPassword();
	       	logger.info("pwd = "+pwd);
	       	addUserLiferay(username, email_portal, pwd, req, company_id);
			try {
				User user = UserLocalServiceUtil.getUserByEmailAddress(company_id, email_portal);
				credentials = new String[3];
	            credentials[0] = String.valueOf(user.getUserId());
	            credentials[1] = pwd;
	    		credentials[2] = String.valueOf(user.isPasswordEncrypted());
	    		return credentials;
			} catch (PortalException e1) {
				logger.warn(e.getMessage());
			} catch (SystemException e1) {
				logger.warn(e.getMessage());
			}
	       	
			
        } catch (SystemException e) {
        	logger.warn(e.getMessage());
		}
		return null;

	}

	
	
	private static void addUserLiferay(String username, String email, String pwd, HttpServletRequest req, long companyId){
		
		long creatorUserId = 0;
		boolean autoPassword = false;
        boolean autoScreenName = false;
        String password1 = pwd;
        String password2 = pwd;
        String emailAddress = email;
        Locale locale = Locale.ITALIAN;
        String screenName = username;
        String firstName = username;
        String middleName = null;
        String lastName = ".";
        int prefixId = 0;
		int suffixId = 0;
        boolean male = true;
        Calendar cal = Calendar.getInstance();
		cal.setTime(new Date());
		cal.add(Calendar.YEAR, -30);
		int birthdayMonth = cal.get(Calendar.MONTH);
		int birthdayDay = cal.get(Calendar.DAY_OF_MONTH);
		int birthdayYear = cal.get(Calendar.YEAR);
        String jobTitle = null;
        boolean sendEmail = false;
        String openId = "";
        long facebookId = 0;
        long[] groupIds = null;
		long[] organizationIds = null;
		long[] roleIds = null;
		long[] userGroupIds = null;
					         
        /* in Liferay 6.1+ */
		 try {
			ServiceContext serviceContext = ServiceContextFactory.getInstance(req);
			User user = UserLocalServiceUtil.addUser(creatorUserId,
			        companyId,
			        autoPassword,
			        password1,
			        password2,
			        autoScreenName,
			        screenName,
			        emailAddress,
			        facebookId,
			        openId,
			        locale,
			        firstName,
			        middleName,
			        lastName,
			        prefixId,
			        suffixId,
			        male,
			        birthdayMonth,
			        birthdayDay,
			        birthdayYear,
			        jobTitle,
			        groupIds,
			        organizationIds,
			        roleIds,
			        userGroupIds,
			        sendEmail,
			        serviceContext);
			logger.info("Liferay user CREATED.");
		} catch (PortalException e) {
			logger.warn(e.getMessage());
		} catch (SystemException e) {
			logger.warn(e.getMessage());
		}
	}
	
}
