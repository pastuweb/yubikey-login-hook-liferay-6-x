package com.liferay.yubikey.util;

import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.PropsUtil;

public class PropsValues {

	public static final boolean YUBIKEY_AUTH_ENABLED = GetterUtil.getBoolean(
			PropsUtil.get(PropsKeys.YUBIKEY_AUTH_ENABLED));
}
