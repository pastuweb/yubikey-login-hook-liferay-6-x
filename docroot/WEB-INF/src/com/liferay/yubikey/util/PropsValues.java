package com.liferay.yubikey.util;

import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.PropsUtil;

public class PropsValues {

	public static final boolean YUBIKEY_AUTH_ENABLED = GetterUtil.getBoolean(
			PropsUtil.get(PropsKeys.YUBIKEY_AUTH_ENABLED));
	
	public static final boolean YUBIKEY_AUTH_PWD_PORTAL = GetterUtil.getBoolean(
			PropsUtil.get(PropsKeys.YUBIKEY_AUTH_PWD_PORTAL));
	
	public static final boolean YUBIKEY_AUTH_AUTO_REGISTER = GetterUtil.getBoolean(
			PropsUtil.get(PropsKeys.YUBIKEY_AUTH_AUTO_REGISTER));
	
	public static final boolean YUBIKEY_AUTH_AUTO_UPDATE_DEVICEID = GetterUtil.getBoolean(
			PropsUtil.get(PropsKeys.YUBIKEY_AUTH_AUTO_UPDATE_DEVICEID));
	
	public static final Integer YUBIKEY_AUTH_CLIENTID = GetterUtil.getInteger(
			PropsUtil.get(PropsKeys.YUBIKEY_AUTH_CLIENTID));
	
	public static final String YUBIKEY_AUTH_SECRETKEY = GetterUtil.getString(
			PropsUtil.get(PropsKeys.YUBIKEY_AUTH_SECRETKEY));
}
