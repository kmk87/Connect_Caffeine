package com.cc;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class CcWebConfig implements WebMvcConfigurer {

	private String employeeMapping = "/uploadImg/**";
	private String employeeLocation = "file:///C:/employee/upload/";
  
	private String approvalMapping = "/upload/**";
	private String approvalLocation = "file:///C:/approval/upload/";

	@Override
	public void addResourceHandlers(ResourceHandlerRegistry registry) {
		registry.addResourceHandler(employeeMapping).addResourceLocations(employeeLocation);
		registry.addResourceHandler(approvalMapping).addResourceLocations(approvalLocation);
	}
}
