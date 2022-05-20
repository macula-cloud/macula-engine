package org.macula.engine.mybatis;

import org.mybatis.spring.annotation.MapperScan;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@MapperScan
@SpringBootApplication
public class MybatisPlusApplication {

	public static void main(String[] args) {
		SpringApplication.run(MybatisPlusApplication.class, args);
	}

}