package br.com.fiap.api_security;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

@SpringBootApplication
@EnableCaching //Habilita o suporte do Cache
public class ApiSecurityApplication {

	public static void main(String[] args) {
		SpringApplication.run(ApiSecurityApplication.class, args);
	}

}
