package com.liferay.yubikey.util;

import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.util.PrefsPropsUtil;

public class YubiKeyUtil {

	public static boolean isEnabled(long companyId){
		try {
			if (PrefsPropsUtil.getBoolean(
					companyId, PropsKeys.YUBIKEY_AUTH_ENABLED,
					PropsValues.YUBIKEY_AUTH_ENABLED)) {
				return true;
			}else {
				return false;
			}
		} catch (SystemException e) {
			e.printStackTrace();
		}
		return false;
	}
	
	public static boolean isPwdPortalEnabled(long companyId) {
		try {
			if (PrefsPropsUtil.getBoolean(
					companyId, PropsKeys.YUBIKEY_AUTH_PWD_PORTAL,
					PropsValues.YUBIKEY_AUTH_PWD_PORTAL)) {
				return true;
			}else {
				return false;
			}
		} catch (SystemException e) {
			e.printStackTrace();
		}
		return false;
	}
	
	public static boolean isAutoRegisterEnabled(long companyId){
		try {
			if (PrefsPropsUtil.getBoolean(
					companyId, PropsKeys.YUBIKEY_AUTH_AUTO_REGISTER,
					PropsValues.YUBIKEY_AUTH_AUTO_REGISTER)) {
				return true;
			}
			else {
				return false;
			}
		} catch (SystemException e) {
			e.printStackTrace();
		}
		return false;
	}
	
	public static boolean isAutoUpdateDeviceIdEnabled(long companyId){
		try {
			if (PrefsPropsUtil.getBoolean(
					companyId, PropsKeys.YUBIKEY_AUTH_AUTO_UPDATE_DEVICEID,
					PropsValues.YUBIKEY_AUTH_AUTO_UPDATE_DEVICEID)) {
				return true;
			}else {
				return false;
			}
		} catch (SystemException e) {
			e.printStackTrace();
		}
		return false;
	}
}
