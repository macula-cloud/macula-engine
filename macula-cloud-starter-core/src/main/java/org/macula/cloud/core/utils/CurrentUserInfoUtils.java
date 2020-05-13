package org.macula.cloud.core.utils;

import java.nio.charset.Charset;

import javax.servlet.http.HttpServletRequest;

import org.macula.cloud.core.entity.CurrentUserInfo;
import org.macula.cloud.core.exception.MaculaCloudException;
import org.springframework.util.Base64Utils;
import org.springframework.util.StringUtils;

import com.alibaba.fastjson.JSONObject;

/**
 * Created by linqina on 2018/12/23 4:21 PM.
 */
public class CurrentUserInfoUtils {
	public static final String HEADER_MACULA_USERINFO = "HEADER-USERINFO";

	public static final String HEADER_MACULA_OPEN_APPLICATION = "HEADER-OPEN-APPLICATION";

	public static CurrentUserInfo getCurrentUserInfo(HttpServletRequest request) {
		return getCurrentUserInfoByHeader(request);
	}

	public static long getCurrentUserId(HttpServletRequest request) {
		CurrentUserInfo userInfo = getCurrentUserInfoByHeader(request);
		return userInfo.getUserId();
	}

	private static CurrentUserInfo getCurrentUserInfoByHeader(HttpServletRequest request) {
		CurrentUserInfo userInfo = null;
		String userHeader = request.getHeader(HEADER_MACULA_USERINFO);
		String openAppHeader = request.getHeader(HEADER_MACULA_OPEN_APPLICATION);
		if (StringUtils.isEmpty(userHeader)) {
			if (StringUtils.isEmpty(openAppHeader)) {
				throw new MaculaCloudException("请先登录!");
			}
			else {
				userInfo = new CurrentUserInfo();
				userInfo.setUserId(1L);
			}
		}
		else {
			String userInfoStr = new String(Base64Utils.decodeFromString(userHeader), Charset.forName("utf-8"));
			userInfo = JSONObject.parseObject(userInfoStr, CurrentUserInfo.class);
		}

		return userInfo;
	}

}
