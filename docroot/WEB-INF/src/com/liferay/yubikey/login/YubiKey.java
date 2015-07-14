package com.liferay.yubikey.login;

import javax.portlet.ActionRequest;
import javax.portlet.ActionResponse;
import javax.portlet.PortletConfig;
import javax.portlet.RenderRequest;
import javax.portlet.RenderResponse;
import javax.portlet.ResourceRequest;
import javax.portlet.ResourceResponse;

import org.apache.log4j.Logger;

import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.struts.BaseStrutsPortletAction;
import com.liferay.portal.kernel.struts.StrutsPortletAction;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.PrefsPropsUtil;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.portal.theme.ThemeDisplay;
import com.liferay.yubikey.util.PropsKeys;
import com.liferay.yubikey.util.PropsValues;
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
	        ThemeDisplay themeDisplay =
	            (ThemeDisplay)actionRequest.getAttribute(WebKeys.THEME_DISPLAY);

	        logger.info("Wrapped processAction");
	        
	        String email_portal = ParamUtil.getString(actionRequest, "email_portal");
			String yubikey_otp = ParamUtil.getString(actionRequest, "yubikey_otp");
			
			logger.info("email_portal="+email_portal);
			logger.info("yubikey_otp="+yubikey_otp);

			if(validate(yubikey_otp, themeDisplay.getCompanyId())){
				logger.info("OTP accepted.");
				actionResponse.sendRedirect(themeDisplay.getCDNBaseURL()+"?email_portal="+email_portal+"&company_id="+themeDisplay.getCompanyId());
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
	    		return "/portlet/login/error_yubikey.jsp";
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
	    
	    private static boolean validate(String otp, long companyId) {
			logger.info("into validate OTP");
		   try {
			    YubicoClient client = YubicoClient.getClient(PrefsPropsUtil.getInteger(
						companyId, PropsKeys.YUBIKEY_AUTH_CLIENTID,
						PropsValues.YUBIKEY_AUTH_CLIENTID));
			    YubicoResponse response;
				response = client.verify(otp);
				return response.getStatus() == YubicoResponseStatus.OK;
			} catch (YubicoValidationException e) {
				logger.warn(e.getMessage());
				return false;
			} catch (YubicoValidationFailure e) {
				logger.warn(e.getMessage());
				return false;
			} catch (SystemException e) {
				logger.warn(e.getMessage());
				return false;
			} catch (Exception e) {
				logger.warn(e.getMessage());
				return false;
			}
		   
		}
	
	
	
}
