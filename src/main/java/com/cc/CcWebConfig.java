package com.cc;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class CcWebConfig implements WebMvcConfigurer {
	
	private String mapping = "/approvalUploadImg/**";
	private String location = "file:///C:/approval/upload/";
	
	@Override
	public void addResourceHandlers(ResourceHandlerRegistry registry) {
		registry.addResourceHandler(mapping)
		.addResourceLocations(location);
		
	}

}
