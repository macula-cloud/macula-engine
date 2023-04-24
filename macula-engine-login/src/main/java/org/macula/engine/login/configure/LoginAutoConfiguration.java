package org.macula.engine.login.configure;

import jakarta.annotation.PostConstruct;

import lombok.extern.slf4j.Slf4j;

import org.springframework.context.annotation.Configuration;

//package org.macula.engine.login.configure;
//
//import java.util.Properties;
//
//import org.macula.cloud.security.configure.ClientFeignSecurityAutoConfiguration;
//import org.springframework.boot.autoconfigure.AutoConfigureAfter;
//import org.springframework.cloud.openfeign.EnableFeignClients;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.ComponentScan;
//import org.springframework.context.annotation.Configuration;
//
//import com.google.code.kaptcha.impl.DefaultKaptcha;
//import com.google.code.kaptcha.util.Config;
//
@Slf4j
@Configuration
//@ComponentScan({ "org.macula.engine.login" })
//@EnableFeignClients(basePackages = "org.macula.engine.login")
public class LoginAutoConfiguration {

	@PostConstruct
	public void postConstruct() {
		log.debug("[Macula] |- Engine [Login] Auto Configure.");
	}
	//
	//	@Bean
	//	public DefaultKaptcha getKaptchaBean() {
	//		DefaultKaptcha defaultKaptcha = new DefaultKaptcha();
	//		Properties properties = new Properties();
	//		properties.setProperty("kaptcha.border", "no");
	//		properties.setProperty("kaptcha.border.color", "255,255,255");
	//		properties.setProperty("kaptcha.textproducer.font.color", "blue");
	//		properties.setProperty("kaptcha.image.width", "125");
	//		properties.setProperty("kaptcha.image.height", "45");
	//		properties.setProperty("kaptcha.session.key", "code");
	//		properties.setProperty("kaptcha.textproducer.char.length", "4");
	//		Config config = new Config(properties);
	//		defaultKaptcha.setConfig(config);
	//		return defaultKaptcha;
	//	}
}
