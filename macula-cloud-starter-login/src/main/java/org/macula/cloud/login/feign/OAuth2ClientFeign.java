package org.macula.cloud.login.feign;

import java.util.Map;

import org.macula.cloud.core.command.CreateSocialUserCommand;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(value = "macula-cloud-oauth2")
public interface OAuth2ClientFeign {

	@PostMapping("/oauth/token")
	Map<String, Object> getToken(@RequestParam("client_id") String clientId, @RequestParam("client_secret") String secret,
			@RequestParam(value = "grant_type", defaultValue = "password") String grantType, @RequestParam("username") String username,
			@RequestParam("password") String password);

	@PutMapping("/public/v1/user/social")
	String createSocialUser(@RequestBody CreateSocialUserCommand command);

}
