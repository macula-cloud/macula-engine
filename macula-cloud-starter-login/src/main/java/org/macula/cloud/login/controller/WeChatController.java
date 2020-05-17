package org.macula.cloud.login.controller;

import org.macula.cloud.login.command.WechatLoginCredential;
import org.macula.cloud.login.service.WeChatService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.annotations.ApiOperation;

@RestController
@RequestMapping(value = "/v1/login/wechat")
public class WeChatController {

	private WeChatService weChatService;

	@Autowired
	public WeChatController(WeChatService weChatService) {
		this.weChatService = weChatService;
	}

	@ApiOperation(value = "用户登录并关联用户")
	@PostMapping(value = "/login")
	public ResponseEntity<String> login(@RequestBody WechatLoginCredential loginCredential) {
		return new ResponseEntity<String>(weChatService.bindAndLogin(loginCredential), HttpStatus.OK);
	}

	@ApiOperation(value = "获取用户token")
	@PostMapping(value = "/token")
	public ResponseEntity<String> token(@RequestBody WechatLoginCredential loginCredential) {
		return new ResponseEntity<String>(weChatService.token(loginCredential), HttpStatus.OK);
	}

	@ApiOperation(value = "取消关联用户")
	@DeleteMapping(value = "/unbind")
	public ResponseEntity<?> unBind(@RequestBody WechatLoginCredential loginCredential) {
		weChatService.unbind(loginCredential);
		return new ResponseEntity<>(HttpStatus.OK);
	}

	@ApiOperation(value = "获取微信access_token")
	@GetMapping(value = "/access_token")
	public ResponseEntity<String> getAccessToken(@RequestBody WechatLoginCredential loginCredential) {
		return new ResponseEntity<String>(weChatService.getAccessToken(loginCredential), HttpStatus.OK);
	}
}
