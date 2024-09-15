package com.cc.approval.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.cc.approval.domain.ApprovalDto;
import com.cc.approval.service.ApprFormService;
import com.cc.approval.service.ApprovalService;
import com.cc.empGroup.domain.EmpGroupDto;
import com.cc.employee.domain.EmployeeDto;
import com.cc.employee.service.EmployeeService;

@Controller
public class ApprovalViewController {
	
	private final ApprovalService approvalService;
	private final ApprFormService apprFormService;
	private final EmployeeService employeeService;
	
	@Autowired
	public ApprovalViewController(ApprovalService approvalService,ApprFormService apprFormService,
			EmployeeService employeeService) {
		this.approvalService = approvalService;
		this.apprFormService = apprFormService;
		this.employeeService = employeeService;
	}
	
	
	// 기안서 작성
//	@GetMapping("/create")
//	public String getDraftInfoOne(Model model,ApprovalDto approvalDto,ApprFormDto apprFormDto) {
//		Approval dto = approvalService.getDraftInfoOne(approvalDto);
////		ApprFormDto formDto = apprFormService.getDocuOne(apprFormDto);
////		
//		model.addAttribute("dto",dto);
////		model.addAttribute("formDto",formDto);
//		System.out.println("dto : "+dto);
//		return "approval/createVacation";
//	}
	
	// 전자결재 홈
	@GetMapping("/approvalHome")
	public String homeMain() {
		
		return "approval/approvalHome";
	}
	
	@GetMapping("/userInfo")
	public String showCreateForm(Model model) {
		// 로그인한 사용자 정보 가져오기
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		User user =(User)authentication.getPrincipal();
		String memId = user.getUsername();
		System.out.println("memId:"+memId);
		
		model.addAttribute("memId",memId);
		
      return "approval/createDraft"; 
  }
	
	// 기안서 폼 작성
	@GetMapping("/createDraft")
	public String getDataInfo(Model model,@RequestParam("formNo") int formNo,
			ApprovalDto approvalDto,EmployeeDto employeeDto){
		
		ApprovalDto dto = approvalService.getDataInfo(approvalDto);
		List<String> groupNames = employeeService.getDataInfoName();
		//String documentNumber  = apprFormService.generateDocumentNumber(groupName);
		//ApprFormDto formDto = apprFormService.getDataInfo(apprFormDto);

		
		// ApprovalDto에서 appr_writer_code를 가져와서 사용
//        Long apprWriterCode = dto.getAppr_writer_code(); // dto에 appr_writer_code가 있다고 가정
        
        model.addAttribute("apprFormNo", formNo);
		model.addAttribute("groupNames", groupNames);
		
		//model.addAttribute("documentNumber",documentNumber);
		model.addAttribute("dto",dto);
		
		return "approval/createDraft";
	}
	
	// 사유서
//	@GetMapping("/dataInfoStat")
//	public String getDataInfoStat(Model model,
//			ApprovalDto approvalDto){
//		
//		ApprovalDto dto = approvalService.getDataInfoStat(approvalDto);
//		//ApprFormDto formDto = apprFormService.getDataInfo(apprFormDto);
//
//		
//		System.out.println("컨트롤러dto : "+dto);
//		
//		model.addAttribute("dto",dto);
////		model.addAttribute("formDto",formDto);
//		
//		return "approval/createStatement";
//	}
	
	
}
