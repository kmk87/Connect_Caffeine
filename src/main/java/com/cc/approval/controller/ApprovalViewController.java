package com.cc.approval.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import com.cc.approval.domain.ApprForm;
import com.cc.approval.domain.ApprFormDto;
import com.cc.approval.domain.Approval;
import com.cc.approval.domain.ApprovalDto;
import com.cc.approval.domain.TemporaryStorageDto;
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

	// 전자결재 홈
	 @GetMapping("/approvalHome")
	 public String showApprovalHome(Model model) {
	        // 데이터베이스에서 결재 진행 문서 리스트를 조회
	        List<ApprovalDto> top5Approvals = approvalService.getAllApprovals(5); 
	        
	        // 상위 5개 항목만 가져오기-내림차순
	        //List<ApprovalDto> top5ApprDtoList = apprDtoList.size() > 5 ? apprDtoList.subList(0, 5) : apprDtoList;
	        
	        model.addAttribute("apprDtoList", top5Approvals); 

	        
	        return "approval/approvalHome"; 
	 }
	
	
	
	// 로그인한 사용자 정보 가져오기
	@GetMapping("/userInfo")
	public String showCreateForm(Model model) {
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
        System.out.println("기안서 번호: "+formNo);
		
        model.addAttribute("apprFormNo", formNo);
		model.addAttribute("groupNames", groupName);
		model.addAttribute("documentNumber", documentNumber);
		model.addAttribute("dto",dto);
		
		return "approval/createDraft";
	}
	
	
	// 기안서 상세 조회
	@GetMapping("/approval/{appr_no}")
	public String selectapprovalOne(Model model,
			@PathVariable("appr_no") Long appr_no) {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
	    User user = (User) authentication.getPrincipal();
	    String username = user.getUsername();
	    String groupName = employeeService.getUserTeamName(username);
	    model.addAttribute("groupNames", groupName);
		ApprovalDto approvalDto = approvalService.selectapprovalOne(appr_no);
		model.addAttribute("dto",approvalDto);
		return "approval/apprDetail";
	}
	
	

	// 임시저장함 데이터리스트
	@GetMapping("/apprTempStorage")
	public String showApprTempStorage(Model model) {
		// 데이터베이스에서 결재 진행 문서 리스트를 조회
        List<TemporaryStorageDto> tempDtoList = approvalService.getAllTemporaryStorage(10); 
        
        model.addAttribute("tempDtoList", tempDtoList); 

		return "approval/apprTempStorage"; 
	}
	
	// 임시저장 상세조회
	@GetMapping("/temporaryStorage/{tem_no}")
	public String selecttemproaryOne(@PathVariable("tem_no") Long tem_no,
			Model model) {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
	    User user = (User) authentication.getPrincipal();
	    String username = user.getUsername();
	    String groupName = employeeService.getUserTeamName(username);
	    model.addAttribute("groupNames", groupName);
	    // 임시 저장된 제목과 내용을 가져옴
	    TemporaryStorageDto temporaryStorageDto = approvalService.selecttemproaryOne(tem_no);

	    // 결재 관련 정보(문서번호, 팀명, 기안일, 기안자)를 가져옴
	    ApprovalDto approvalDto = approvalService.selectapprovalOne(temporaryStorageDto.getAppr_form_no());
	    
	    
	    
	    
		model.addAttribute("apprDto",approvalDto);
		model.addAttribute("tempDto",temporaryStorageDto);
		return "approval/tempDetail";
	}
	
	
	// 기안문서함
	@GetMapping("/draftStorage")
	public String showDraftStorage(Model model) {
		// 데이터베이스에서 결재 진행 문서 리스트를 조회
        List<ApprovalDto> top10Approvals  = approvalService.getAllApprovals(10); 
        
        // 상위 10개 항목만 가져오기-내림차순
        //List<ApprovalDto> top5ApprDtoList = apprDtoList.size() > 10 ? apprDtoList.subList(0, 10) : apprDtoList;
        
        model.addAttribute("apprDraftDtoList", top10Approvals);
			        
		return "approval/draftStorage"; 
	}
	
	// 결재문서함
	@GetMapping("/apprStorage")
	public String showApprStorage() {
				
				        
		return "approval/apprStorage"; 
	}
	
	// 참조문서함
	@GetMapping("/referenceStorage")
	public String showReferebceStorage() {
					
					        
		return "approval/referenceStorage"; 
	}
	
	// 결재대기문서
	@GetMapping("/standByDraft")
	public String showStandByDraft() {
					
					        
		return "approval/standByDraft"; 
	}
	
	// 결재수신문서
	@GetMapping("/receiveDraft")
	public String showReceiveDraft() {
					
					        
		return "approval/receiveDraft"; 
	}
	
	// 전자결재 환경설정
		 @GetMapping("/signSetting")
		 public String showsignSetting(Model model) {
		        

		        
		        return "approval/signSetting"; 
		 }
	
}
