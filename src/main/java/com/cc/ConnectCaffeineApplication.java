package com.cc;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling  // 스케줄러 활성화 어노테이션 추가
public class ConnectCaffeineApplication {

	public static void main(String[] args) {
		SpringApplication.run(ConnectCaffeineApplication.class, args);
	}

}
