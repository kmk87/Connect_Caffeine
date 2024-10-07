package com.cc.employee.controller;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.cc.employee.domain.Employee;
import com.cc.employee.domain.EmployeeDto;
import com.cc.employee.service.EmployeeService;
import com.cc.employee.service.FileService;

@Controller
public class EmployeeApiController {

	private final FileService fileService;
	private final EmployeeService employeeService;
//	private final EmpGroupService empGroupService;

	@Autowired
	public EmployeeApiController(FileService fileService, EmployeeService employeeService) {
		this.fileService = fileService;
		this.employeeService = employeeService;
//		this.empGroupService = empGroupService;
	}

	// 1. 등록
	 @PostMapping("/employeeCreate")
	 public String createEmployee(EmployeeDto dto, @RequestPart(name = "emp_img_file") MultipartFile file) {

		String savedFileName = fileService.upload(file);
        
        dto.setEmp_img_file_name(file.getOriginalFilename());
        dto.setEmp_img_file_path(savedFileName);

        Employee savedEmployee = employeeService.createEmployee(dto);
         
	    return "redirect:/employeeList"; 
	}
	

	// 3. 수정
	@ResponseBody
	@PostMapping("/employeeUpdate/{emp_code}")
	public Map<String, String> updateEmployee(@RequestBody EmployeeDto dto) {

		Map<String, String> resultMap = new HashMap<String, String>();

		resultMap.put("res_code", "404");
		resultMap.put("res_msg", "사원 정보 수정 중 오류가 발생하였습니다.");

		if (employeeService.updateEmployee(dto) != null) {
			resultMap.put("res_code", "200");
			resultMap.put("res_msg", "사원 정보가 성공적으로 수정되었습니다.");

		}
		return resultMap;
	}

	// 4. 퇴사 처리(delete)
	@ResponseBody
	@PostMapping("/employeeDelete/{emp_code}")
	public Map<String, String> deleteEmployee(@RequestBody EmployeeDto dto) {
		System.out.println("컨트롤러로 넘어온 dto: " + dto);
		Map<String, String> resultMap = new HashMap<String, String>();

		resultMap.put("res_code", "404");
		resultMap.put("res_msg", "퇴사 처리 중 오류가 발생하였습니다.");


		if (employeeService.deleteEmployee(dto) != null) {
			resultMap.put("res_code", "200");
			resultMap.put("res_msg", "퇴사 처리 성공적으로 수행되었습니다.");
		}
		return resultMap;

	}
	
	
	// 5. 프로필 수정
	 @PostMapping("/profileUpdate")
	 public String profileUpdate(EmployeeDto dto, @RequestPart(name = "emp_img_file") MultipartFile file) {
		System.out.println("컨트롤러로 넘어온 dto 객체: " + dto);		 
	    // 파일 이름 추출
	    String fileName = file.getOriginalFilename();
	    // 파일 이름을 DTO에 설정
	    dto.setEmp_img_file_name(fileName);
	    
	    // 파일 저장 경로 설정하는 로직
	    String savedFilePath = fileService.upload(file); // fileService는 파일 저장을 담당
	    dto.setEmp_img_file_path(savedFilePath);
	    
	    // 프로필 업데이트
	    employeeService.updateProfile(dto);

	    return "redirect:/employeeProfile";
	}
	
}
