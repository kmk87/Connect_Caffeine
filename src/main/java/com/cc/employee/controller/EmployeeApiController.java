package com.cc.employee.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.cc.employee.domain.Employee;
import com.cc.employee.domain.EmployeeDto;
import com.cc.employee.service.EmployeeService;
import com.cc.employee.service.FileService;
import com.cc.group.service.GroupService;

@Controller
public class EmployeeApiController {
	
//	private final FileService fileService;
	private final EmployeeService employeeService;
//	private final GroupService groupService;
	
	@Autowired
	public EmployeeApiController(EmployeeService employeeService) {
//		this.fileService = fileService;
		this.employeeService = employeeService;
//		this.groupService = groupService;
	}
	
	// 등록
	@ResponseBody
	@PostMapping("/employee")
	public Map<String, String> createEmployee(@RequestBody EmployeeDto dto
			//, @RequestParam("file") MultipartFile file
			){
	
		Map<String, String> resultMap = new HashMap<String, String>();
		resultMap.put("res_code", "404");
		resultMap.put("res_msg", "사원 정보 등록 중 오류가 발생하였습니다.");
		
		if(employeeService.createEmployee(dto) != null) {
			resultMap.put("res_code","200"); 
			resultMap.put("res_msg", "사원 정보가 성공적으로 등록되었습니다."); 
		}
		
		//String savedFilename = fileService.upload(file);
		
//		if(savedFilename != null) {
//			dto.setEmp_img_file_name(file.getOriginalFilename());
//			dto.setEmp_img_file_path(savedFilename); 
//		}else { 
//			resultMap.put("res_msg", "파일 업로드에 실패하였습니다."); 
		
		return resultMap;
	}
	
	// 수정
//	@ResponseBody
//	@PostMapping("/employee/{emp_code}")
//	public Map<String, String> updateEmployee(EmployeeDto dto,
//			@RequestParam(name="file", required=false)MultipartFile file){
//		Map<String, String> resultMap = new HashMap<String, String>();
//		resultMap.put("res_code", "404");
//		resultMap.put("res_msg", "사원 정보 수정 중 오류가 발생하였습니다.");
//		
//		/*
//		 * if(file != null && "".equals(file.getOriginalFilename()) == false) { String
//		 * savedFilename = fileService.upload(file); if(savedFilename != null) {
//		 * dto.setEmp_img_file_name(savedFilename);
//		 * dto.setEmp_img_file_path(savedFilename);
//		 * 
//		 * if(fileService.delete(dto.getEmp_code()) > 0) { resultMap.put("res_msg",
//		 * "기존 파일이 정상적으로 삭제되었습니다."); } }else { resultMap.put("res_msg",
//		 * "게시글이 성공적으로 수정되었습니다."); } }
//		 */
//		
//		if(employeeService.updateEmployee(dto) != null) {
//			resultMap.put("res_code", "200");
//			resultMap.put("res_msg", "사원 정보가 성공적으로 수정되었습니다.");
//		}
//		return resultMap;
//	}
	
	
}
