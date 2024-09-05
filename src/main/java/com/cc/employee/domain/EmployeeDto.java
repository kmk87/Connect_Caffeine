package com.cc.employee.domain;
import java.time.LocalDateTime;
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
		
		private Long emp_code;
		private String job_code;
		private Long group_no;
		private String emp_name;
		private String emp_account;
		private String emp_pw;
		private String emp_addr;
		private String emp_reg_no;
		private String emp_email;
		private String emp_phone;
		private String emp_desk_phone;
		private LocalDateTime emp_hiredate;
		private String emp_resign;
		private String emp_img_file_name;
		private String emp_img_file_path;
		private String emp_memo;
		private Long emp_holiday;
		
		private List<GrantedAuthority> authorities;
		
		public Employee toEntity() {
			return Employee.builder()
					.empCode(emp_code)
					.jobCode(job_code)
					.groupNo(group_no)
					.empName(emp_name)
					.empAccount(emp_account)
					.empPw(emp_pw)
					.empAddr(emp_addr)
					.empRegNo(emp_reg_no)
					.empEmail(emp_email)
					.empPhone(emp_phone)
					.empDeskPhone(emp_desk_phone)
					.empHiredate(emp_hiredate)
					.empResign(emp_resign)
					.empImgFileName(emp_img_file_name)
					.empImgFilePath(emp_img_file_path)
					.empMemo(emp_memo)
					.empHoliday(emp_holiday)
					.build();
		}
		
		public EmployeeDto toDto(Employee employee){
			return EmployeeDto.builder()
					.emp_code(employee.getEmpCode())
					.job_code(employee.getJobCode())
					.group_no(employee.getGroupNo())
					.emp_name(employee.getEmpName())
					.emp_account(employee.getEmpAccount())
					.emp_pw(employee.getEmpPw())
					.emp_addr(employee.getEmpAddr())
					.emp_reg_no(employee.getEmpRegNo())
					.emp_email(employee.getEmpEmail())
					.emp_phone(employee.getEmpPhone())
					.emp_desk_phone(employee.getEmpDeskPhone())
					.emp_hiredate(employee.getEmpHiredate())
					.emp_resign(employee.getEmpResign())
					.emp_img_file_name(employee.getEmpImgFileName())
					.emp_img_file_path(employee.getEmpImgFilePath())
					.emp_memo(employee.getEmpMemo())
					.emp_holiday(employee.getEmpHoliday())
					.build();
		}
}

