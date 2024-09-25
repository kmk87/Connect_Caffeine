package com.cc.approval.controller;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import java.io.OutputStream;
import java.util.Base64;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.cc.approval.domain.ApprForm;
import com.cc.approval.domain.Approval;
import com.cc.approval.domain.ApprovalDto;
import com.cc.approval.domain.TemporaryStorageDto;
import com.cc.approval.service.ApprFormService;
import com.cc.approval.service.ApprovalService;
import com.cc.employee.domain.Employee;
import com.cc.employee.service.EmployeeService;




@Controller
public class ApprovalApiController {
	
	private final ApprovalService approvalService;
	private final ApprFormService apprFormService;
	private final EmployeeService employeeService;
	
	@Autowired
	public ApprovalApiController(ApprovalService approvalService,
			ApprFormService apprFormService,EmployeeService employeeService) {
		this.approvalService = approvalService;
		this.apprFormService = apprFormService;
		this.employeeService = employeeService;
	}

	
	
	// 기안서 폼 생성
	@ResponseBody
	@PostMapping("/draft")
	public Map<String,String> createDraft(@RequestBody ApprovalDto dto){
		
		Map<String,String> resultMap = new HashMap<String,String>();
		resultMap.put("res_code", "404");
		resultMap.put("res_msg","결재요청 중 오류가 발생했습니다.");
		
		System.out.println("Api dto: "+dto);
		
		if(approvalService.getDraftInfoOne(dto) != null) {
			resultMap.put("res_code", "200");
			resultMap.put("res_msg", "결재요청이 완료되었습니다.");
		}
		
		return resultMap;
	}
	
	
	// 기안서 삭제 -> 비활성화
//	@PostMapping("/disable/{approvalId}")
//	public ResponseEntity<String> disableApproval(@PathVariable Long approvalId){
//		try {
//			approvalService.disableApproval(approvalId);
//			return ResponseEntity.ok("기안서가 삭제되었습니다.");
//		} catch(Exception e) {
//			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("삭제에 실패했습니다.");
//		}
//	}
//	
	
	// 기안서 임시저장
	@ResponseBody
	@PostMapping("/apprSave")
	public Map<String,String> updateAppr(@RequestBody TemporaryStorageDto dto){
		Map<String, String> resultMap = new HashMap<String, String>();
		resultMap.put("res_code", "404");
		resultMap.put("res_msg","임시저장 중 오류가 발생했습니다.");
		
		// DTO에 appr_form_no 설정
	    //dto.setAppr_form_no(apprFormNo);
		
		// 서비스에서 로그인된 사용자의 empCode(사원 코드)를 가져와 DTO에 설정
	    if (approvalService.updateApprWithEmpCode(dto)) {
	        resultMap.put("res_code","200");
	        resultMap.put("res_msg", "임시저장 되었습니다.");
	    }
	    
		return resultMap;
		
	
	}
	
	// 기안서 임시저장에서 삭제
	@ResponseBody
	@DeleteMapping("/deleteTemp/{tem_no}")
	public Map<String,String> deleteTempStorage(@PathVariable("tem_no") Long tem_no){
		
		
		Map<String,String> map = new HashMap<String,String>();
		map.put("res_code", "404");
		map.put("res_msg","삭제 중 오류가 발생했습니다.");
		
		if(approvalService.deleteTempStorage(tem_no) > 0) {
			map.put("res_code", "200");
			map.put("res_msg", "정상적으로 삭제 되었습니다.");
		}
		return map;
	}
	
	
	// 전자 서명 설정
	@PostMapping("/uploadSignature")
    public ResponseEntity<?> uploadSignature(@RequestBody Map<String, String> data) {
        // Base64 데이터 추출
        String base64Image = data.get("image").split(",")[1]; 
        byte[] decodedBytes = Base64.getDecoder().decode(base64Image);

        // 이미지 파일 경로 설정
        // 이미지 파일 경로 설정
        String directoryPath = "C:/approval/upload/";
        String filePath = directoryPath + "signature.png";
        
        // 디렉토리 확인 및 생성
        File directory = new File(directoryPath);
        if (!directory.exists()) {
            directory.mkdirs();  // 경로가 없다면 디렉토리 생성
        }

        
        try (OutputStream stream = new FileOutputStream(filePath)) {
            stream.write(decodedBytes);
        } catch (IOException e) {
            e.printStackTrace();
        }

        // 현재 로그인된 사용자 정보 가져오기
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String empAccount;

        if (authentication != null && authentication.getPrincipal() instanceof UserDetails) {
            UserDetails userDetails = (UserDetails) authentication.getPrincipal();
            empAccount = userDetails.getUsername(); // 로그인된 사용자의 empAccount
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("로그인 정보가 없습니다.");
        }

        // 서비스로 empAccount와 이미지 경로 전달
        boolean isUpdated = employeeService.updateEmployeeSignatureByAccount(empAccount, filePath);
        if (!isUpdated) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("사원을 찾을 수 없습니다.");
        }

        return ResponseEntity.ok("서명이 저장되었습니다.");
    }
	
	
}
