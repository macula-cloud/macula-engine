package org.macula.cloud.login.configure;

import java.util.Properties;

import org.macula.cloud.security.configure.ClientFeignSecurityAutoConfiguration;
import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

import com.google.code.kaptcha.impl.DefaultKaptcha;
import com.google.code.kaptcha.util.Config;

@Configuration
@ComponentScan({ "org.macula.cloud.login" })
@EnableFeignClients(basePackages = "org.macula.cloud.login")
@AutoConfigureAfter(ClientFeignSecurityAutoConfiguration.class)
public class LoginConfiguration {

	@Bean
	public DefaultKaptcha getKaptchaBean() {
		DefaultKaptcha defaultKaptcha = new DefaultKaptcha();
		Properties properties = new Properties();
		properties.setProperty("kaptcha.border", "no");
		properties.setProperty("kaptcha.border.color", "255,255,255");
		properties.setProperty("kaptcha.textproducer.font.color", "blue");
		properties.setProperty("kaptcha.image.width", "125");
		properties.setProperty("kaptcha.image.height", "45");
		properties.setProperty("kaptcha.session.key", "code");
		properties.setProperty("kaptcha.textproducer.char.length", "4");
		Config config = new Config(properties);
		defaultKaptcha.setConfig(config);
		return defaultKaptcha;
	}
}
