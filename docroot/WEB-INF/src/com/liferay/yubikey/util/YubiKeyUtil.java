package com.liferay.yubikey.util;

import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.util.PrefsPropsUtil;

public class YubiKeyUtil {

	public static boolean isEnabled(long companyId) throws SystemException {
		if (PrefsPropsUtil.getBoolean(
				companyId, PropsKeys.YUBIKEY_AUTH_ENABLED,
				PropsValues.YUBIKEY_AUTH_ENABLED)) {
			return true;
		}
		else {
			return false;
		}
	}
}
