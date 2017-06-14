package com.xerpass.logsafe;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;

import com.xerpass.logsafe.filters.AuthFilter;

@SpringBootApplication
public class LogsafeApplication {
	
	@Bean
	public FilterRegistrationBean filtroJwt(){
		FilterRegistrationBean frb = new FilterRegistrationBean();
		frb.setFilter(new AuthFilter());
		frb.addUrlPatterns("/api/*");
		
		return frb;
	}

	public static void main(String[] args) {
		SpringApplication.run(LogsafeApplication.class, args);
	}
}
