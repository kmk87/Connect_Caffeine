package com.cc.employee.controller;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;

import com.cc.employee.domain.EmployeeDto;
import com.cc.employee.service.EmployeeService;

@Controller
public class EmployeeApiController {

//	private final FileService fileService;
	private final EmployeeService employeeService;
//	private final EmpGroupService empGroupService;

	@Autowired
	public EmployeeApiController(EmployeeService employeeService) {
//		this.fileService = fileService;
		this.employeeService = employeeService;
//		this.empGroupService = empGroupService;
	}

	// 등록
	@ResponseBody
	@PostMapping("/employee")
	public Map<String, String> createEmployee(@RequestBody EmployeeDto dto
	// , @RequestParam("file") MultipartFile file
	) {

		Map<String, String> resultMap = new HashMap<String, String>();
		resultMap.put("res_code", "404");
		resultMap.put("res_msg", "사원 정보 등록 중 오류가 발생하였습니다.");

		if (employeeService.createEmployee(dto) != null) {
			resultMap.put("res_code", "200");
			resultMap.put("res_msg", "사원 정보가 성공적으로 등록되었습니다.");
		}

		// String savedFilename = fileService.upload(file);

//		if(savedFilename != null) {
//			dto.setEmp_img_file_name(file.getOriginalFilename());
//			dto.setEmp_img_file_path(savedFilename); 
//		}else { 
//			resultMap.put("res_msg", "파일 업로드에 실패하였습니다."); 

		return resultMap;
	}

	// 수정
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
		System.out.println("update 컨트롤러로 넘어온 객체: "+dto);
		return resultMap;
	}

	// 퇴사 처리(delete)
	@ResponseBody
	@PostMapping("/employeeDelete/{emp_code}")
	public Map<String, String> deleteEmployee(@RequestBody EmployeeDto dto){
		
		Map<String, String> resultMap = new HashMap<String, String>();
		 
		resultMap.put("res_code", "404");
		resultMap.put("res_msg", "퇴사 처리 중 오류가 발생하였습니다.");
		
		System.out.println("컨트롤러로 넘어온 객체: "+dto);
		
		if(employeeService.deleteEmployee(dto) != null) {
			resultMap.put("res_code", "200");
			resultMap.put("res_msg", "퇴사 처리 성공적으로 수행되었습니다.");
		}
		return resultMap;
	
	}
}
