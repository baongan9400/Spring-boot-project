package com.ngandang.intern;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing
public class WsVeChaiApplication {

	public static void main(String[] args) {
		SpringApplication.run(WsVeChaiApplication.class, args);
	}

}
