package com.cc.employee.service;

import java.time.LocalDate;
import java.time.LocalDateTime;
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

import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;

@Service
public class EmployeeService {
	
//	private final PasswordEncoder passwordEncoder;
	private final EmployeeRepository employeeRepository;
	private final EmpGroupRepository empGroupRepository;
	private final JobRepository jobRepository;
	
	public EmployeeService(EmployeeRepository employeeRepository, 
			EmpGroupRepository empGroupRepository, JobRepository jobRepository) {
//		this.passwordEncoder = passwordEncoder;
		this.employeeRepository = employeeRepository;
		this.empGroupRepository = empGroupRepository;
		this.jobRepository = jobRepository;
	}
	
	// 0. 메인
	public EmployeeDto getEmployeeOne(String emp_account) {
		
		return null;
	}
	
	
	// 1. 등록(create)
	public Employee createEmployee(EmployeeDto dto) {
		
		Employee emp = null;
		
		EmpGroup empGroup = empGroupRepository.findBygroupNo(dto.getGroup_no());
		
		Job jobTemp = jobRepository.findByjobCode(dto.getEmp_job_code());
		
		
		if(dto.getEmp_img_file_name() != null && "".equals(dto.getEmp_img_file_name()) == false){	
			
			// 문자열을 LocalDateTime으로 변환
			DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
	        LocalDateTime emp_hiredate_iso = LocalDateTime.parse(dto.getEmp_hiredate() + " 00:00:00", formatter);
//	        LocalDateTime emp_resigndate_iso = LocalDateTime.parse(dto.getEmp_resigndate(), formatter);
			
		emp = Employee.builder()
				.empCode(dto.getEmp_code())
				.empJobCode(dto.getEmp_job_code())
				.empJobName(jobTemp.getJobName())
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
				.empHiredate(emp_hiredate_iso)
				.empImgFileName(dto.getEmp_img_file_name())
				.empImgFilePath(dto.getEmp_img_file_path())
				.empResign(dto.getEmp_resign())
				.empHoliday(dto.getEmp_holiday())
				.build();
		}
		
		return employeeRepository.save(emp);
	}
    
    
		// 1-1. 사원번호 카운트 부여
	public String getInputAccount() {
		String inputAccount = employeeRepository.getInputAccount();
		return inputAccount;
	}
	
    
	// 2-1. 목록(list)
	public List<EmployeeDto> selectEmployeeList() {
		
		List<Employee> employeeList = employeeRepository.findAll();
		
		List<EmployeeDto> employeeDtoList = new ArrayList<EmployeeDto>();
		
		for(Employee e : employeeList) {
			
			EmployeeDto dto = new EmployeeDto().toDto(e);
			employeeDtoList.add(dto);
		}
		return employeeDtoList;
  }
	
	// 2-2. 상세 조회(detail), 수정(update)
	public EmployeeDto selectEmployeeOne(Long emp_code) {
		Employee emp = employeeRepository.findByempCode(emp_code);
		EmployeeDto dto = new EmployeeDto().toDto(emp);
		
		return dto;
	}
	
	public EmployeeDto findByempName(String emp_name) {
		Employee employee = employeeRepository.findByempName(emp_name);
		
		EmployeeDto dto = EmployeeDto.builder()
						.emp_code(employee.getEmpCode())
						.build();
		return dto;
	}
	
	 public Long findEmpCodeByEmpName(String empAccount) {
	        return employeeRepository.findEmpCodeByEmpName(empAccount);
	    }
	
	// empCode를 이용해 Employee 객체를 조회하는 메서드
	    public Employee findByEmpCode(Long empCode) {
	        return employeeRepository.findByempCode(empCode);   
	    }
	

	    
	 // 그룹 번호 가져오는 메소드
	 		public Long getGroupNoByEmpCode(Long emp_code) {
	 			Employee emp = employeeRepository.findById(emp_code).orElseThrow();
	 			Long groupNo = emp.getEmpGroup().getGroupNo();
	 			return groupNo;
	 		}

	
	
	
	
	
	// 3. 수정
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

		// 팀명 가져오기
		public List<String> getDataInfoName() {
	        // 모든 Employee 데이터를 가져온 후 필터링
	        List<Employee> employees = employeeRepository.findAll();

	        List<String> groupNames = new ArrayList<>();
	        for (Employee emp : employees) {
	            // emp.getEmpGroup()이 null이 아니고 group_parent_no도 null이 아닌 경우
	            if (emp.getEmpGroup() != null && emp.getEmpGroup().getGroupParentNo() != null) {
	                groupNames.add(emp.getEmpGroup().getGroupName()); // group_name을 추가
	            }
	        }
	        return groupNames;
		
		}
		
		// 로그인한 사용자의 팀명 가져오기
	    public String getUserTeamName(String username) {
	        Employee employee = employeeRepository.findByempAccount(username);
	        // 해당 사용자의 그룹이 존재하고, group_parent_no가 있는 경우에만 팀명 반환
	        if (employee != null && employee.getEmpGroup() != null && employee.getEmpGroup().getGroupParentNo() != null) {
	            return employee.getEmpGroup().getGroupName();
	        }
	        return "DEFAULT"; // 기본값 설정
	    }
		
		// 4. 삭제
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
			
			
		
			//모든 직원 조회 
	        public List<Employee> getAllEmployees(){
	           return employeeRepository.findAll();
	        }

	        // 부서 번호로 직원 목록을 조회하는 메서드 (group_parent_no 기준)
	        public List<Employee> getEmployeesByDeptNo(Long deptNo) {
	            return employeeRepository.findByEmpGroup_GroupParentNo(deptNo);  // group_parent_no 기준으로 직원 조회
	        }
    
	        // 팀 번호로 직원 목록을 조회하는 메서드
	        public List<Employee> getEmployeesByTeamNo(Long groupNo) {
	            return employeeRepository.findByEmpGroup_GroupNo(groupNo);
	        }
    
	//////////전자결재 사용////////////
    // 전자서명 설정
    @Transactional
    public boolean updateEmployeeSignatureByAccount(String empAccount, String filePath) {
        int updatedRows = employeeRepository.updateEmployeeSignatureByAccount(empAccount, filePath);
        return updatedRows > 0;
    }
    
    // 결재올린 사람의 팀명 가져오기
    public String getTeamNameByEmpCode(Long empCode) {
        // 특정 emp_code를 가진 Employee를 조회한 후 팀명 반환
        Employee employee = employeeRepository.findByempCode(empCode);
        if (employee != null && employee.getEmpGroup() != null && employee.getEmpGroup().getGroupParentNo() != null) {
            return employee.getEmpGroup().getGroupName();
        }
        return null; // 팀명이 없을 경우 null 반환
    }
    

    
}