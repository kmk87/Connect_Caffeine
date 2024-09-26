package com.cc;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class CcWebConfig implements WebMvcConfigurer {
	// 인사 관리
	private String mapping = "/uploadImg/**";
	private String location = "file:///C:/employee/upload/";
	
	// 전자 결재

	@Override
	public void addResourceHandlers(ResourceHandlerRegistry registry) {
		registry.addResourceHandler(mapping).addResourceLocations(location);
		// registry.addResourceHandler().addResourceLocations();
	}
}
