package com.liferay.yubikey.login;

import java.net.URL;

import javax.portlet.ActionRequest;
import javax.portlet.ActionResponse;
import javax.portlet.PortletConfig;
import javax.portlet.RenderRequest;
import javax.portlet.RenderResponse;
import javax.portlet.ResourceRequest;
import javax.portlet.ResourceResponse;
import org.apache.log4j.Logger;
import com.liferay.portal.kernel.struts.BaseStrutsPortletAction;
import com.liferay.portal.kernel.struts.StrutsPortletAction;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.portal.theme.ThemeDisplay;

import com.liferay.yubikey.util.HTTPUtil;

public class YubiKeyForm extends BaseStrutsPortletAction {

	private final static Logger logger = Logger.getLogger(YubiKeyForm.class.getName()); 

	 public void processAction(
	            StrutsPortletAction originalStrutsPortletAction,
	            PortletConfig portletConfig, ActionRequest actionRequest,
	            ActionResponse actionResponse)
	        throws Exception {
	        ThemeDisplay themeDisplay = (ThemeDisplay)actionRequest.getAttribute(WebKeys.THEME_DISPLAY);

	        logger.info("Wrapped processAction");
	        
	        originalStrutsPortletAction.processAction(
	            originalStrutsPortletAction, portletConfig, actionRequest,
	            actionResponse);
	    }

	    public String render(
	            StrutsPortletAction originalStrutsPortletAction,
	            PortletConfig portletConfig, RenderRequest renderRequest,
	            RenderResponse renderResponse)
	        throws Exception {

	    	logger.info("Wrapped render");

	        return "/portlet/login/yubikey.jsp";

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
	
	
	
}
