
package com.cc;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class CcWebConfig implements WebMvcConfigurer {

	private String mapping = "/uploadImg/**";
	private String location = "file:///C:/employee/upload/";
  
  private String appr_mapping = "/approvalUploadImg/**";
	private String appr_location = "file:///C:/approval/upload/";

	@Override
	public void addResourceHandlers(ResourceHandlerRegistry registry) {
		registry.addResourceHandler(mapping).addResourceLocations(location);
    registry.addResourceHandler(appr_mapping).addResourceLocations(appr_location);
	}
}

