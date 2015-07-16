package com.liferay.yubikey.login;

import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.portlet.ActionRequest;
import javax.portlet.ActionResponse;
import javax.portlet.PortletConfig;
import javax.portlet.RenderRequest;
import javax.portlet.RenderResponse;
import javax.portlet.ResourceRequest;
import javax.portlet.ResourceResponse;

import org.apache.log4j.Logger;

import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.struts.BaseStrutsPortletAction;
import com.liferay.portal.kernel.struts.StrutsPortletAction;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.PrefsPropsUtil;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.portal.kernel.workflow.WorkflowConstants;
import com.liferay.portal.model.CompanyConstants;
import com.liferay.portal.model.User;
import com.liferay.portal.service.ServiceContext;
import com.liferay.portal.service.ServiceContextFactory;
import com.liferay.portal.service.UserLocalServiceUtil;
import com.liferay.portal.theme.ThemeDisplay;
import com.liferay.yubikey.util.PropsKeys;
import com.liferay.yubikey.util.PropsValues;
import com.liferay.yubikey.util.YubiKeyUtil;
import com.yubico.client.v2.YubicoClient;
import com.yubico.client.v2.YubicoResponse;
import com.yubico.client.v2.YubicoResponseStatus;
import com.yubico.client.v2.exceptions.YubicoValidationException;
import com.yubico.client.v2.exceptions.YubicoValidationFailure;

public class YubiKey extends BaseStrutsPortletAction {

	private final static Logger logger = Logger.getLogger(YubiKey.class.getName()); 

	 public void processAction(
	            StrutsPortletAction originalStrutsPortletAction,
	            PortletConfig portletConfig, ActionRequest actionRequest,
	            ActionResponse actionResponse)
	        throws Exception {
	        ThemeDisplay themeDisplay = (ThemeDisplay)actionRequest.getAttribute(WebKeys.THEME_DISPLAY);

	        logger.info("Wrapped processAction");
	        
	        
	        String email_portal = ParamUtil.getString(actionRequest, "email_portal");
			String yubikey_otp = ParamUtil.getString(actionRequest, "yubikey_otp");
			String password_portal = ParamUtil.getString(actionRequest, "password_portal");
			String username = email_portal.split("@")[0];
			logger.info("email_portal="+email_portal+" yubikey_otp="+yubikey_otp);

			if(validateYubiKeyOTP(yubikey_otp, themeDisplay.getCompanyId())){
				
				String deviceId = YubicoClient.getPublicId(yubikey_otp);
				logger.info("YubiKey OTP VALID, deviceId = "+deviceId);
				
				//validate user on the portal
				boolean isValidUser = validateUser(themeDisplay.getCompanyId(), username, email_portal, password_portal, actionRequest, deviceId);
				if(isValidUser){
					//check deviceId
					if(validateDeviceId(UserLocalServiceUtil.getUserByEmailAddress(themeDisplay.getCompanyId(), email_portal), deviceId)){
						actionResponse.sendRedirect(themeDisplay.getCDNBaseURL()+"?email_portal="+email_portal+"&company_id="+themeDisplay.getCompanyId());
					}else{
						actionResponse.setRenderParameter("struts_action", "/login/yubikey"); 
						actionRequest.setAttribute("cmd", "error_deviceid");
					}
				}else{
					actionResponse.setRenderParameter("struts_action", "/login/yubikey"); 
					actionRequest.setAttribute("cmd", "error_user");
				}

			}else{
				actionResponse.setRenderParameter("struts_action", "/login/yubikey"); 
				actionRequest.setAttribute("cmd", "error_otp");
			}
			
			/*
	        originalStrutsPortletAction.processAction(
	            originalStrutsPortletAction, portletConfig, actionRequest,
	            actionResponse);
	        */
			
	    }
	 
	    public String render(
	            StrutsPortletAction originalStrutsPortletAction,
	            PortletConfig portletConfig, RenderRequest renderRequest,
	            RenderResponse renderResponse)
	        throws Exception {
	    	
	    	logger.info("Wrapped render ");
	    	
	    	String cmd = (String)renderRequest.getAttribute("cmd");
	    	logger.info("cmd = "+cmd);
	    	
	    	if(cmd.equals("error_otp")){
	    		return "/portlet/login/error_otp.jsp";
	    	}else if(cmd.equals("error_deviceid")){
	    		return "/portlet/login/error_deviceid.jsp";
	    	}else if(cmd.equals("error_user")){
	    		return "/portlet/login/error_user.jsp";
	    	}else{
	    		return "/portlet/login/yubikey.jsp";
	    	}

	    }

	    public void serveResource(
	            StrutsPortletAction originalStrutsPortletAction,
	            PortletConfig portletConfig, ResourceRequest resourceRequest,
	            ResourceResponse resourceResponse)
	        throws Exception {

	        originalStrutsPortletAction.serveResource(
	            originalStrutsPortletAction, portletConfig, resourceRequest,
	            resourceResponse);

	    }
	    
	    private static boolean validateYubiKeyOTP(String otp, long companyId) {
			logger.info("into validate YubiKey OTP");
			
		    try {
			    YubicoClient client = YubicoClient.getClient(PrefsPropsUtil.getInteger(
						companyId, PropsKeys.YUBIKEY_AUTH_CLIENTID,
						PropsValues.YUBIKEY_AUTH_CLIENTID));
			    YubicoResponse response;
				response = client.verify(otp);
				if(response.getStatus() == YubicoResponseStatus.OK){
					return true;
				}
				return false;
			} catch (YubicoValidationException e) {
				logger.warn(e.getMessage());
				return false;
			} catch (YubicoValidationFailure e) {
				logger.warn(e.getMessage());
				return false;
			} catch (SystemException e) {
				e.printStackTrace();
				return false;
			} catch (Exception e) {
				e.printStackTrace();
				return false;
			}
		   
		}
	    
	    private static boolean validateUser(long company_id, String username, String email_portal, String password_portal, ActionRequest actionRequest, String deviceId){
	    	
	    	try {
				User user = UserLocalServiceUtil.getUserByEmailAddress(company_id, email_portal);
				if(user != null && YubiKeyUtil.isPwdPortalEnabled(company_id) && (password_portal != null && !password_portal.isEmpty())){
		           	 logger.info("user exist - check input password");
		           	 
		           	 //check password
		           	 if(UserLocalServiceUtil.authenticateForBasic(company_id, CompanyConstants.AUTH_TYPE_EA, email_portal, password_portal)  != 0){
		           		 return true;
		           	 }else{
		           		 return false;
		           	 }
		           	 
				}else if(user != null && YubiKeyUtil.isPwdPortalEnabled(company_id) == false && (password_portal == null || password_portal.isEmpty())){
					logger.info("user exist - no password in input");
					
					return true;
				}
				
			} catch (PortalException e) {
				logger.warn(e.getMessage());
				logger.info("user NOT EXIST, will be created with this data:");		       	
		       	String pwd = deviceId;
		       	if(password_portal != null && !password_portal.isEmpty()){
		       		pwd = password_portal;
		       	}
		       	logger.info("username="+username+ " email="+email_portal);
		       	//before to create user, i have to check if already exist a user with this deviceId
		       	if(isNewDeviceId(deviceId)){
		       		addUserLiferay(username, email_portal, pwd, actionRequest, company_id, deviceId);
		       		return true;
		       	}
		       	
			} catch (SystemException e) {
				e.printStackTrace();
			}
	    	
	    	return false;
	    }

	    private static boolean isNewDeviceId(String deviceId){
	    	try {
				for(User u : UserLocalServiceUtil.getUsers(0,UserLocalServiceUtil.getUsersCount())){
					if(u.getComments().contains(deviceId)){
						return false;
					}
				}
			} catch (SystemException e) {
				e.printStackTrace();
			}
	    	return true;
	    }
	    
	    private static boolean validateDeviceId(User user, String YubiKeyDeviceId){
	    	
	    	try {
				if(user.getComments() != null && !user.getComments().isEmpty()){
					Pattern pattern = Pattern.compile("#(.*)#");
					Matcher matcher = pattern.matcher(user.getComments());
					String array[] = new String[2];
					if (matcher.find())
						array = matcher.group(1).split(":");

					if(array[0].equals("YubiKeyDeviceId") && array[1].length() > 0 && array[1].equals(YubiKeyDeviceId)){
						logger.info("YubiKeyDeviceId VALID");
						return true;
					}else{
						logger.info("YubiKeyDeviceId VALID but NOT MATCH, but the user is OK, so maybe he has another OTP Key");
						//check YubiKeyDeviceId
						if(isNewDeviceId(YubiKeyDeviceId)){
							user.setComments("#YubiKeyDeviceId:"+YubiKeyDeviceId+"#");
							UserLocalServiceUtil.updateUser(user);
							return true;
						}else{
							logger.info("YubiKeyDeviceId already associated to another account.");
							return false;
						}
					}	
				}else{
					user.setComments("#YubiKeyDeviceId:"+YubiKeyDeviceId+"#");
					UserLocalServiceUtil.updateUser(user);
	   				logger.info("YubiKeyDeviceId="+user.getComments());
					return true;
				}
			} catch (SystemException e1) {
				e1.printStackTrace();
			}
	    	return false;
	    }
	    
	    
	    private static void addUserLiferay(String username, String email, String pwd, ActionRequest req, long companyId, String deviceId){
			
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
	        String lastName = username;
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
				user.setPasswordReset(false);
				user.setAgreedToTermsOfUse(true);
				user.setComments("#YubiKeyDeviceId:"+deviceId+"#");
				UserLocalServiceUtil.updateUser(user);
				UserLocalServiceUtil.updateStatus(user.getUserId(), WorkflowConstants.STATUS_APPROVED);
				logger.info("Liferay user CREATED and STATUS_APPROVED");
			} catch (PortalException e) {
				logger.warn(e.getMessage());
			} catch (SystemException e) {
				e.printStackTrace();
			}
		}
	
	
	
}
