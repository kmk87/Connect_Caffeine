package com.cc;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import jakarta.servlet.http.HttpServletRequest;


@SpringBootApplication
@EnableScheduling  // 스케줄러 활성화 어노테이션 추가
@ComponentScan(basePackages = {"com.cc"}) // 원하는 패키지를 스캔하도록 설정
public class ConnectCaffeineApplication implements ErrorController {

	public static void main(String[] args) {
		SpringApplication.run(ConnectCaffeineApplication.class, args);
		
	}

	
//	@RequestMapping("/error")
//    public String handleError(HttpServletRequest request, Model model) {
//        // 오류 상태 코드 확인
//        Integer statusCode = (Integer) request.getAttribute("javax.servlet.error.status_code");
//
//        if (statusCode == 404) {
//            // 404 에러 처리
//            model.addAttribute("errorMessage", "페이지를 찾을 수 없습니다.");
//            return "error/404"; // 404.html 또는 Thymeleaf 템플릿 사용
//        } else if (statusCode == 500) {
//            // 500 에러 처리
//            model.addAttribute("errorMessage", "서버 내부 오류가 발생했습니다.");
//            return "error/500"; // 500.html 또는 Thymeleaf 템플릿 사용
//        } else {
//            // 그 외 에러 처리
//            model.addAttribute("errorMessage", "알 수 없는 오류가 발생했습니다.");
//            return "error/error"; // 공통 에러 페이지
//        }
//    }

  
	
	
	
}
