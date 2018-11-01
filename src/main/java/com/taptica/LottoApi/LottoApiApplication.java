package com.taptica.LottoApi;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class LottoApiApplication {

	public static void main(String[] args) {
		SpringApplication.run(LottoApiApplication.class, args);
	}
}
