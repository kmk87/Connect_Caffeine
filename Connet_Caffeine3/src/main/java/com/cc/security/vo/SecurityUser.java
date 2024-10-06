package com.cc.security.vo;
import org.springframework.security.core.userdetails.User;

import com.cc.employee.domain.EmployeeDto;

import lombok.Getter;
@Getter
public class SecurityUser extends User{
	private static final long serialVersionUID = 1L;
	
	private EmployeeDto dto;
	
	public SecurityUser(EmployeeDto dto) {
		super(dto.getEmp_account(), dto.getEmp_pw(),dto.getAuthorities());
		this.dto = dto;
	}
}