package org.macula.cloud.login.util;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import cn.binarywang.wx.miniapp.api.WxMaService;
import cn.binarywang.wx.miniapp.api.impl.WxMaServiceImpl;
import cn.binarywang.wx.miniapp.config.impl.WxMaDefaultConfigImpl;

public class WxaUtils {

	private static final Map<String, WxMaService> WxMaServiceMap = new HashMap<String, WxMaService>();

	public static WxMaService registWxMaService(String clientId, List<Map<String, String>> vo) {
		if (!WxMaServiceMap.containsKey(clientId)) {
			vo.forEach(map -> {
				String appId = map.get("appId");
				String appSecret = map.get("appSecret");
				if (clientId != null && appId != null && appSecret != null) {
					WxMaDefaultConfigImpl config = new WxMaDefaultConfigImpl();
					config.setAppid(appId);
					config.setSecret(appSecret);
					final WxMaServiceImpl service = new WxMaServiceImpl();
					service.setWxMaConfig(config);
					WxMaServiceMap.put(clientId, service);
				}
			});
		}
		return getWxMaService(clientId);
	}

	public static WxMaService getWxMaService(String clientId) {
		return WxMaServiceMap.get(clientId);
	}

}
