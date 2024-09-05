package com.cc.employee.domain;


import java.util.List;

import org.springframework.security.core.GrantedAuthority;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
@Builder
public class EmployeeDto {
		
		private Long mem_no;
		private String mem_id;
		private String mem_pw;
		private String mem_auth;

		
		private List<GrantedAuthority> authorities;
		
		public Employee toEntity() {
			return Employee.builder()
					.memNo(mem_no)
					.memId(mem_id)
					.memPw(mem_pw)
					.memAuth(mem_auth)
					.build();
		}
		
		public EmployeeDto toDto(Employee employee){
			return EmployeeDto.builder()
					.mem_no(employee.getMemNo())
					.mem_id(employee.getMemId())
					.mem_pw(employee.getMemPw())
					.mem_auth(employee.getMemAuth())
					.build();
		}
}
