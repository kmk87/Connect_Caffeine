package com.cc.employee.service;


import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import com.cc.empGroup.domain.EmpGroup;
import com.cc.empGroup.repository.EmpGroupRepository;
import com.cc.employee.domain.Employee;
import com.cc.employee.domain.EmployeeDto;
import com.cc.employee.repository.EmployeeRepository;
import com.cc.job.domain.Job;
import com.cc.job.repository.JobRepository;


@Service
public class EmployeeService {
	
//	private final PasswordEncoder passwordEncoder;
	private final EmployeeRepository employeeRepository;
	private final EmpGroupRepository empGroupRepository;
	private final JobRepository jobRepository;
	
	public EmployeeService(EmployeeRepository employeeRepository, EmpGroupRepository empGroupRepository
			, JobRepository jobRepository) {
//		this.passwordEncoder = passwordEncoder;
		this.employeeRepository = employeeRepository;
		this.empGroupRepository = empGroupRepository;
		this.jobRepository = jobRepository;
	}
	
	
	// 등록(create)
	public Employee createEmployee(EmployeeDto dto) {
		Employee emp = null;
		EmpGroup empGroup = empGroupRepository.findBygroupNo(dto.getGroup_no());
		
		//if(dto.getEmp_img_file_name() != null && "".equals(dto.getEmp_img_file_name()) == false){	
		emp = Employee.builder()
				.empCode(dto.getEmp_code())
				.empJobCode(dto.getEmp_job_code())
				.empJobName(dto.getEmp_job_name())
				.empGroup(empGroup)
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
	
	// 목록(list)
	public List<EmployeeDto> selectEmployeeList() {
		
		List<Employee> employeeList = employeeRepository.findAll();
		
		List<EmployeeDto> employeeDtoList = new ArrayList<EmployeeDto>();
		
		for(Employee e : employeeList) {
			
			EmployeeDto dto = new EmployeeDto().toDto(e);
			employeeDtoList.add(dto);
		}
		return employeeDtoList;
	}
	
	
	// 상세 조회(detail), 수정(update)
	public EmployeeDto selectEmployeeOne(Long emp_code) {
		Employee emp = employeeRepository.findByempCode(emp_code);
		EmployeeDto dto = new EmployeeDto().toDto(emp);
		
		return dto;
	}
	
	
	
	
	// 수정
	public Employee updateEmployee(EmployeeDto dto) {
		EmployeeDto temp = selectEmployeeOne(dto.getEmp_code());
		
		Job job = jobRepository.findByjobCode(dto.getEmp_job_code());
		String jobName = job.getJobName();
		
		temp.setGroup_no(dto.getGroup_no());
		temp.setEmp_job_code(dto.getEmp_job_code());
		temp.setEmp_job_name(jobName);
		temp.setEmp_name(dto.getEmp_name());
		temp.setEmp_account(dto.getEmp_account());
		temp.setEmp_pw(dto.getEmp_pw());
		temp.setEmp_postcode(dto.getEmp_postcode());
		temp.setEmp_addr(dto.getEmp_addr());
		temp.setEmp_addr_detail(dto.getEmp_addr_detail());
		temp.setEmp_reg_no(dto.getEmp_reg_no());
		temp.setEmp_email(dto.getEmp_email());
		temp.setEmp_phone(dto.getEmp_phone());
		temp.setEmp_desk_phone(dto.getEmp_desk_phone());
		temp.setEmp_hiredate(dto.getEmp_hiredate());
		temp.setEmp_resign(dto.getEmp_resign());
		
		Employee emp = temp.toEntity();
		Employee result = employeeRepository.save(emp);
		
		return result;
	   
	}
	
	
	// 삭제
	public Employee deleteEmployee(EmployeeDto dto) {
		EmployeeDto temp = selectEmployeeOne(dto.getEmp_code());
		
		Job job = jobRepository.findByjobCode(dto.getEmp_job_code());
		String jobName = job.getJobName();
		
		temp.setGroup_no(dto.getGroup_no());
		temp.setEmp_job_code(dto.getEmp_job_code());
		temp.setEmp_job_name(jobName);
		temp.setEmp_name(dto.getEmp_name());
		temp.setEmp_account(dto.getEmp_account());
		temp.setEmp_pw(dto.getEmp_pw());
		temp.setEmp_postcode(dto.getEmp_postcode());
		temp.setEmp_addr(dto.getEmp_addr());
		temp.setEmp_addr_detail(dto.getEmp_addr_detail());
		temp.setEmp_reg_no(dto.getEmp_reg_no());
		temp.setEmp_email(dto.getEmp_email());
		temp.setEmp_phone(dto.getEmp_phone());
		temp.setEmp_desk_phone(dto.getEmp_desk_phone());
		temp.setEmp_hiredate(dto.getEmp_hiredate());
		temp.setEmp_resign(dto.getEmp_resign());
		temp.setEmp_resigndate(dto.getEmp_resigndate());
		
		Employee emp = temp.toEntity();
		Employee result = employeeRepository.save(emp);
		
		return result;
	}
	
	
	// 주민번호 문자열 데이터를 생년월일로 변환하는 메소드
		public String formatEmpRegNo(Long emp_code) {
			Employee emp = employeeRepository.findByempCode(emp_code);
			String empRegNo = emp.getEmpRegNo();
			String yearPrefix = empRegNo.substring(0,2);
			String oldOrNew = empRegNo.substring(7, 8);
			String birtYear = null;

			if("1".equals(oldOrNew) || "2".equals(oldOrNew)) {
				birtYear = 19 + yearPrefix;
			}else {
				birtYear = 20 + yearPrefix;
			}
			
			String birtMonth = empRegNo.substring(2, 4);
			String birthDay = empRegNo.substring(4, 6);
			
			// LocalDate로 변환
			LocalDate birthDate = LocalDate.of(Integer.parseInt(birtYear),
									Integer.parseInt(birtMonth), Integer.parseInt(birthDay));
			
			// 원하는 형식으로 변환
			DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
			
			return birthDate.format(formatter);
		}
		
		// 부서명 가져오는 메소드
		public String getDeptNameByEmpCode(Long emp_code) {
			Employee emp = employeeRepository.findById(emp_code).orElseThrow();
			EmpGroup team = empGroupRepository.findBygroupNo(emp.getEmpGroup().getGroupNo());
			EmpGroup dept = empGroupRepository.findBygroupNo(team.getGroupParentNo());
			String deptName = dept.getGroupName();
			
			return deptName;
		}
		
		// 그룹 번호 가져오는 메소드
		public Long getGroupNoByEmpCode(Long emp_code) {
			Employee emp = employeeRepository.findById(emp_code).orElseThrow();
			Long groupNo = emp.getEmpGroup().getGroupNo();
			return groupNo;
		}
	
}
