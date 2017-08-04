package com.wanhuhealth.rules;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ImportResource;

@SpringBootApplication
@ImportResource(value ={"classpath:thread.xml","classpath:drools.xml"})
public class WanhuhealthRulesApplication {

	public static void main(String[] args) {
		SpringApplication.run(WanhuhealthRulesApplication.class, args);
	}

}
