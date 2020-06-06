package org.macula.cloud.login.controller;

import java.security.Principal;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = { "/actuator/log", "/api/log", "/user/log" })
public class PrintLogController {

	@GetMapping
	public ResponseEntity<String> login(HttpServletRequest request, HttpServletResponse response, Authentication authentication,
			Principal principal) {
		StringBuilder sb = new StringBuilder();
		for (LogOption logOption : LogOption.values()) {
			sb.append(logOption.getProfile(request, response));
			sb.append("\r\n");
		}
		sb.append("\r\n============= Authentication ====================\r\n");
		sb.append(authentication);

		sb.append("\r\n============= Principal ====================\r\n");
		sb.append(principal);
		System.out.println(sb);

		return ResponseEntity.ok(sb.toString());
	}

}
