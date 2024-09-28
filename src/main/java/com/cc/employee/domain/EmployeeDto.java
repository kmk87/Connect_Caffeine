package com.cc.employee.domain;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

import org.springframework.security.core.GrantedAuthority;

import com.cc.empGroup.domain.EmpGroup;

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
		
		private Long emp_code;
		private Long group_no;
		// dto에 추가된 필드
		private String group_name;
		private String emp_job_code;
		private String emp_job_name;
		private String emp_name;
		private String emp_account;
		private String emp_pw;
		private Long emp_postcode;
		private String emp_addr;
		private String emp_addr_detail;
		private String emp_reg_no;
		private String emp_email;
		private String emp_phone;
		private String emp_desk_phone;
		private String emp_hiredate;
		private String emp_resign;
		private LocalDateTime emp_resigndate;
		private String emp_img_file_name;
		private String emp_img_file_path;
		private String emp_memo;
		private Long emp_holiday;
		private Long group_parent_no;
		
		
		// 검색
		private int search_type = 1;
		private String search_text;
		
		private List<GrantedAuthority> authorities;

		// DTO -> Entity
		public Employee toEntity() {
			
			EmpGroup empGroup = EmpGroup.builder()
					.groupNo(group_no)
					.build();

			  // DateTimeFormatter를 이용해 문자열을 파싱할 형식 지정
	        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

	        // 문자열을 LocalDateTime으로 변환
	        LocalDateTime emp_hiredate_iso = LocalDateTime.parse(emp_hiredate, formatter);
	       // LocalDateTime emp_resigndate_iso = LocalDateTime.parse(emp_resigndate, formatter);

			return Employee.builder()
					.empCode(emp_code)
					.empGroup(empGroup)
					.empJobCode(emp_job_code)
					.empJobName(emp_job_name)
					.empName(emp_name)
					.empAccount(emp_account)
					.empPw(emp_pw)
					.empPostcode(emp_postcode)
					.empAddr(emp_addr)
					.empAddrDetail(emp_addr_detail)
					.empRegNo(emp_reg_no)
					.empEmail(emp_email)
					.empPhone(emp_phone)
					.empDeskPhone(emp_desk_phone)
					.empHiredate(emp_hiredate_iso)
					.empResign(emp_resign)
					.empResigndate(emp_resigndate)
					.empImgFileName(emp_img_file_name)
					.empImgFilePath(emp_img_file_path)
//					.empResigndate(emp_resigndate_iso)
					.empMemo(emp_memo)
					.empHoliday(emp_holiday)
					.build();
		}
		
		// Entity -> DTO
		public EmployeeDto toDto(Employee employee){
			
			
			// DateTimeFormatter를 이용해 원하는 형식 지정
	        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

	        // LocalDateTime을 String으로 변환
	        String empHiredateStr = null;
	        if(employee.getEmpHiredate() != null)
	        	empHiredateStr = employee.getEmpHiredate().format(formatter);
	        
	        String empResigndateStr = null;
	        if(employee.getEmpResigndate() != null)
	        	empResigndateStr = employee.getEmpResigndate().format(formatter);
			
			return EmployeeDto.builder()
					.emp_code(employee.getEmpCode())
					.group_name(employee.getEmpGroup().getGroupName())
					.emp_job_code(employee.getEmpJobCode())
					.emp_job_name(employee.getEmpJobName())
					.emp_name(employee.getEmpName())
					.emp_account(employee.getEmpAccount())
					.emp_pw(employee.getEmpPw())
					.emp_postcode(employee.getEmpPostcode())
					.emp_addr(employee.getEmpAddr())
					.emp_addr_detail(employee.getEmpAddrDetail())
					.emp_reg_no(employee.getEmpRegNo())
					.emp_email(employee.getEmpEmail())
					.emp_phone(employee.getEmpPhone())
					.emp_desk_phone(employee.getEmpDeskPhone())
					.emp_hiredate(empHiredateStr)
					.emp_resign(employee.getEmpResign())
					.emp_resigndate(employee.getEmpResigndate())
					.emp_img_file_name(employee.getEmpImgFileName())
					.emp_img_file_path(employee.getEmpImgFilePath())
//					.emp_resigndate(empResigndateStr)
					.emp_memo(employee.getEmpMemo())
					.emp_holiday(employee.getEmpHoliday())
					.group_name(employee.getEmpGroup().getGroupName())
					.group_parent_no(employee.getEmpGroup().getGroupParentNo())
					.build();
		}
}