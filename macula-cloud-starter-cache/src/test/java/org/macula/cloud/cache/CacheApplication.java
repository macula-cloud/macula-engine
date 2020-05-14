package org.macula.cloud.cache;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

import lombok.extern.slf4j.Slf4j;

@SpringBootApplication
@EnableCaching
@EnableScheduling
@Slf4j
public class CacheApplication {

	@Autowired
	private MessageCacheService service;

	public static void main(String[] args) {
		SpringApplication.run(CacheApplication.class, args);
	}

	@Scheduled(fixedDelay = 2000L)
	public void print() {
		log.info(service.getMessage("wilson"));
	}
}
