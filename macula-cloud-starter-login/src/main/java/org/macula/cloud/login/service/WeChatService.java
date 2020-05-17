package org.macula.cloud.login.service;

import java.util.Map;

import org.macula.cloud.login.command.WechatLoginCredential;
import org.macula.cloud.login.util.WxaUtils;
import org.springframework.beans.factory.annotation.Autowired;

import cn.binarywang.wx.miniapp.api.WxMaService;
import cn.binarywang.wx.miniapp.bean.WxMaJscode2SessionResult;
import lombok.extern.slf4j.Slf4j;
import me.chanjar.weixin.common.error.WxErrorException;

@Slf4j
public class WeChatService {

	@Autowired
	private OAuth2ClientFeign oauth2ClientFeign;

	public String token(WechatLoginCredential credential) {
		String clientId = credential.getClientId();
		String code = credential.getCode();
		WxMaService service = WxaUtils.getWxMaService(clientId);

		try {
			WxMaJscode2SessionResult sessionKey = service.getUserService().getSessionInfo(code);
			String openId = sessionKey.getOpenid();
			Map<String, Object> oauth2Result = oauth2ClientFeign.getToken(clientId, "", "password", credential.getBindUsername(), openId);
			return oauth2Result.getOrDefault("access_token", "").toString();
		} catch (WxErrorException e) {
			log.error("Get token  {} error: ", code, e);
			return null;
		}
	}

	public String bindAndLogin(WechatLoginCredential credential) {

		// TODO Auto-generated method stub
		return null;
	}

	public void unbind(WechatLoginCredential credential) {
		// TODO Auto-generated method stub

	}

	public String getAccessToken(WechatLoginCredential credential) {
		// TODO Auto-generated method stub
		return null;
	}

}
