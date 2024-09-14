package com.cc.employee.service;


import org.springframework.stereotype.Service;

import com.cc.employee.domain.Employee;
import com.cc.employee.domain.EmployeeDto;
import com.cc.employee.repository.EmployeeRepository;


@Service
public class EmployeeService {
	
//	private final PasswordEncoder passwordEncoder;
	private final EmployeeRepository employeeRepository;
	
	public EmployeeService(EmployeeRepository employeeRepository) {
//		this.passwordEncoder = passwordEncoder;
		this.employeeRepository = employeeRepository;
	}
	
	// 목록
//	public List<EmployeeDto> selectEmployeeList() {
//		
//		List<Employee> employeeList = employeeRepository.findAllEmpList();
//		
//		List<EmployeeDto> employeeDtoList = new ArrayList<EmployeeDto>();
//		
//		for(Employee e : employeeList) {
//					
//			EmployeeDto dto = new EmployeeDto().toDto(e);
//			employeeDtoList.add(dto);
//		}
//		return employeeDtoList;
//	}
	
	// Employee 기준으로 deptName을 가져오는 메소드
//	private String getDeptNameForEmployee(Employee emp) {
//		EmployeeDto empDto = employeeRepository.findDtoByempCode(emp.getEmpCode());
//		 return empDto != null ? empDto.getDept_name() : "Unknown";
//	}
	
	
	
	// 상세 조회
	public EmployeeDto selectEmployeeOne(Long emp_code) {
		Employee emp = employeeRepository.findByempCode(emp_code);
		EmployeeDto dto = EmployeeDto.builder()
				.emp_code(emp.getEmpCode())
				.emp_name(emp.getEmpName())
				.emp_account(emp.getEmpAccount())
				.emp_pw(emp.getEmpPw())
				.emp_postcode(emp.getEmpPostcode())
				.emp_addr(emp.getEmpAddr())
				.emp_addr_detail(emp.getEmpAddrDetail())
				.emp_reg_no(emp.getEmpRegNo())
				.emp_email(emp.getEmpEmail())
				.emp_phone(emp.getEmpPhone())
				.emp_desk_phone(emp.getEmpDeskPhone())
				.emp_hiredate(emp.getEmpHiredate())
				//.emp_img_file_name(emp.getEmpImgFileName())
				//.emp_img_file_path(emp.getEmpImgFilePath())
				.emp_resign(emp.getEmpResign())
				.emp_resigndate(emp.getEmpResigndate())
				.emp_holiday(emp.getEmpHoliday())
				.build();
		return dto;
	}
	
	// 등록
	public Employee createEmployee(EmployeeDto dto) {
		Employee emp = null;
		
		//if(dto.getEmp_img_file_name() != null && "".equals(dto.getEmp_img_file_name()) == false){	
		emp = Employee.builder()
				.empCode(dto.getEmp_code())
				.empJobCode(dto.getEmp_job_code())
				.empJobName(dto.getEmp_job_name())
				.empGroup(dto.getGroup_no())
				.empName(dto.getEmp_name())
				.empAccount(dto.getEmp_account())
				.empPw(dto.getEmp_pw())
				.empPostcode(dto.getEmp_postcode())
				.empAddr(dto.getEmp_addr())
				.empAddrDetail(dto.getEmp_addr_detail())
				.empRegNo(dto.getEmp_reg_no())
				.empEmail(dto.getEmp_email())
				.empPhone(dto.getEmp_phone())
				.empDeskPhone(dto.getEmp_desk_phone())
				.empHiredate(dto.getEmp_hiredate())
				//.empImgFileName(dto.getEmp_img_file_name())
				//.empImgFilePath(dto.getEmp_img_file_path())
				.empResign(dto.getEmp_resign())
				.empResigndate(dto.getEmp_resigndate())
				.empHoliday(dto.getEmp_holiday())
				.build();
		
		return employeeRepository.save(emp);
	}
	
	
	// 수정
//	@Transactional
//	public Employee updateEmployee(EmployeeDto dto) {
//		EmployeeDto temp = selectEmployeeOne(dto.getEmp_code());
//		temp.setJob_code(dto.getJob_code());
//		temp.setGroup_no(dto.getGroup_no());
//		temp.setEmp_addr(dto.getEmp_account());
//		temp.setEmp_phone(dto.getEmp_desk_phone());
//		temp.setEmp_desk_phone(dto.getEmp_desk_phone());
//		temp.setEmp_resign(dto.getEmp_pw());
//		
//		/*
//		 * if(dto.getEmp_img_file_name() != null &&
//		 * "".equals(dto.getEmp_img_file_name()) == false){
//		 * temp.setEmp_img_file_name(dto.getEmp_img_file_name());
//		 * temp.setEmp_img_file_path(dto.getEmp_img_file_path()); }
//		 */
//		
//		Employee employee = temp.toEntity();
//		Employee result = employeeRepository.save(employee);
//		
//		return result;
//		}
	
	// 삭제
	public int deleteEmployee(Long emp_code) {
		int result = 0;
		try {
			employeeRepository.deleteById(emp_code);
		}catch(Exception e) {
			e.printStackTrace();
		}
		return result;
	}
}
