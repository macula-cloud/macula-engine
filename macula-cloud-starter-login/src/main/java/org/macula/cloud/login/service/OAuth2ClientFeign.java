package org.macula.cloud.login.service;

import java.util.Map;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name = "macula-cloud-oauth2")
public interface OAuth2ClientFeign {

	@PostMapping("/oauth/token")
	Map<String, Object> getToken(@RequestParam("client_id") String clientId, @RequestParam("client_secret") String secret,
			@RequestParam(value = "grant_type", defaultValue = "password") String grantType, @RequestParam("username") String username,
			@RequestParam("password") String password);

}
