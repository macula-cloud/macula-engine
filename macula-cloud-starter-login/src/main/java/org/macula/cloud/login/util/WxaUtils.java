package org.macula.cloud.login.util;

import org.macula.cloud.login.configure.WxMaConfiguration;

import cn.binarywang.wx.miniapp.api.WxMaService;

public class WxaUtils {

	public static WxMaService getWxMaService(String clientId) {
		return WxMaConfiguration.getMaService(clientId);
	}

}
