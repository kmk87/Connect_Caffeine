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

import com.cc.approval.domain.ApprForm;
import com.cc.approval.domain.ApprFormDto;
import com.cc.approval.domain.Approval;
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
//	@GetMapping("/approvalHome")
//	public String homeMain() {
//		
//		return "approval/approvalHome";
//	}
	 @GetMapping("/approvalHome")
	    public String showApprovalHome(Model model) {
	        // 데이터베이스에서 결재 진행 문서 리스트를 조회
	        List<ApprovalDto> apprDtoList = approvalService.getAllApprovals(); // 모든 결재 문서 조회 메서드
	        
	     // 상위 5개 항목만 가져오기
	        List<ApprovalDto> top5ApprDtoList = apprDtoList.size() > 5 ? apprDtoList.subList(0, 5) : apprDtoList;
	        
	        model.addAttribute("apprDtoList", top5ApprDtoList); // 모델에 데이터 추가

	        
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
		List<String> groupNames = employeeService.getDataInfoName(); // 아까 만든 리스트..
		
		// 로그인한 사용자 정보 가져오기
	    Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
	    User user = (User) authentication.getPrincipal();
	    String username = user.getUsername();

	    // 로그인한 사용자의 정확한 팀명 가져오기
	    String groupName = employeeService.getUserTeamName(username);
	    // 문서 번호 생성
		String documentNumber = apprFormService.generateDocumentNumber(groupName);
		
		
//		// ApprForm 객체 생성 및 저장
//	    ApprForm apprForm = new ApprForm();
//	    apprForm.setApprDocuNo(documentNumber);
//	    apprForm.setApprFormType(getFormType(formNo)); // formNo에 따라 폼 타입 설정 ("휴가신청서", "사유서", "품의서" 등)
//	    ApprForm savedApprForm = apprFormService.saveApprForm(apprForm); // ApprForm 저장 후 ID 생성됨
//
//	    // Approval 객체 생성 및 저장
//	    Approval approval = new Approval();
//	    approval.setApprForm(savedApprForm); // 저장된 ApprForm 객체를 설정
//	    approval.setApprWriterName(dto.getAppr_writer_name()); // 기안자 이름 설정
//	    approval.setApprTitle(dto.getAppr_title()); // 제목 설정
//	    approvalService.saveApproval(approval); // Approval 저장
//	  
        
        model.addAttribute("apprFormNo", formNo);
		model.addAttribute("groupNames", groupName);
		model.addAttribute("documentNumber", documentNumber);
		model.addAttribute("dto",dto);
		
		return "approval/createDraft";
	}
	
	// 기안서 타입 
	private String getFormType(int formNo) {
	    switch (formNo) {
	        case 1:
	            return "휴가신청서";
	        case 2:
	            return "사유서";
	        case 3:
	            return "품의서";
	        default:
	            return "기타";
	    }
	}
	
	
	// 전자결재 메인 결재대기리스트 조회
//	@GetMapping("/approvalMain")
//    public String showMainPage(Model model) {
//        // 데이터베이스에서 결재 진행 문서 리스트를 조회
//        List<ApprovalDto> apprDtoList = approvalService.getAllApprovals(); // 모든 결재 문서 조회 메서드
//        model.addAttribute("apprDtoList", apprDtoList); // 모델에 데이터 추가
//        
//        System.out.println("Approval List: " + apprDtoList);
//        return "approval/approvalHome"; 
//    }
//	
	
	
	
	
	
}
