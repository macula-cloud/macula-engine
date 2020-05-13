package org.macula.cloud.login.controller;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class LoginController {

	@PostMapping("/check_user_login")
	public boolean checkUserLogin(@RequestParam(name = "user_login") String userLogin) {
		return true;
	}

}
