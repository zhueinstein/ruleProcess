package com;

import com.wanhuhealth.rules.utils.SpringContextHolder;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ImportResource;

@SpringBootApplication
@ImportResource("classpath:thread.xml")
public class WanhuhealthRulesApplication {

	public static void main(String[] args) {
		SpringApplication.run(WanhuhealthRulesApplication.class, args);
	}

	@Bean
	public SpringContextHolder getSpringCt(){
		return new SpringContextHolder();
	}
}
