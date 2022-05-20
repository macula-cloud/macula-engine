package org.macula.engine.j2cache;

import org.macula.engine.j2cache.annotation.EnableJ2Cache;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@EnableJ2Cache
public class J2CacheApplication {

	public static void main(String[] args) {
		SpringApplication.run(J2CacheApplication.class, args);
	}

}
