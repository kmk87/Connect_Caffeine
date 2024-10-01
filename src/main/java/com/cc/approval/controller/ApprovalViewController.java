package com.cc.approval.controller;

import java.awt.print.Pageable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.cc.approval.domain.Approval;
import com.cc.approval.domain.ApprovalDto;
import com.cc.approval.domain.ApprovalLine;
import com.cc.approval.domain.ApprovalLineDto;
import com.cc.approval.domain.TemporaryStorageDto;
import com.cc.approval.repository.ApprovalLineRepository;
import com.cc.approval.service.ApprFormService;
import com.cc.approval.service.ApprovalLineService;
import com.cc.approval.service.ApprovalService;
import com.cc.empGroup.service.EmpGroupService;
import com.cc.employee.domain.Employee;
import com.cc.employee.domain.EmployeeDto;
import com.cc.employee.repository.EmployeeRepository;
import com.cc.employee.service.EmployeeService;
import com.cc.security.vo.SecurityUser;
import com.cc.tree.domain.TreeMenuDto;
import com.cc.tree.service.OrgService;

import jakarta.persistence.EntityNotFoundException;

@Controller
public class ApprovalViewController {
	
	private final ApprovalService approvalService;
	private final ApprFormService apprFormService;
	private final EmployeeService employeeService;
	private final EmpGroupService empGroupService;
	private final ApprovalLineRepository  approvalLineRepository ;
	private final OrgService orgService;
	private final ApprovalLineService approvalLineService;
	
	@Autowired  // EmployeeRepository를 주입
    private EmployeeRepository employeeRepository;
	
	@Autowired
	public ApprovalViewController(ApprovalService approvalService,ApprFormService apprFormService,
			EmployeeService employeeService,EmpGroupService empGroupService,ApprovalLineRepository approvalLineRepository,
			OrgService orgService,ApprovalLineService approvalLineService) {
		this.approvalService = approvalService;
		this.apprFormService = apprFormService;
		this.employeeService = employeeService;
		this.empGroupService = empGroupService;
		this.approvalLineRepository = approvalLineRepository;
		this.orgService = orgService;
		this.approvalLineService = approvalLineService;
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
//	@GetMapping("/userInfo")
//	public String showCreateForm(Model model) {
//		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
//		User user =(User)authentication.getPrincipal();
//		String memId = user.getUsername();
//		System.out.println("memId:"+memId);
//		
//		model.addAttribute("memId",memId);
//		
//      return "approval/createDraft"; 
//	}
	
	
	
	// 기안서 폼 작성
	@GetMapping("/createDraft")
	public String getDataInfo(Model model,@RequestParam("formNo") int formNo,
			ApprovalDto approvalDto,EmployeeDto employeeDto){
	    
		// 로그인한 사용자 정보 가져오기
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		User user = (User) authentication.getPrincipal();
		String username = user.getUsername();
		System.out.println("username:"+username);
		// 로그인한 사용자의 정확한 팀명 가져오기
		String groupName = employeeService.getUserTeamName(username);
		// employee 객체를 데이터베이스에서 가져옴
	    Employee employee = employeeRepository.findByempAccount(username);
		
		ApprovalDto dto = approvalService.getDataInfo(approvalDto);
		System.out.println("dto: "+dto);
		List<String> groupNames = employeeService.getDataInfoName(); // 아까 만든 리스트..
		
	    
	    // 문서 번호 생성
        String documentNumber = approvalService.generateDocumentNumber(groupName);
        
        
        // DTO에 문서번호 설정 (저장은 하지 않음)
        dto.setDocu_no(documentNumber);
        
        System.out.println("employee: "+employee);
        
        model.addAttribute("employee", employee);
        model.addAttribute("username",username);
        model.addAttribute("apprFormNo", formNo);
		model.addAttribute("groupNames", groupName);
		model.addAttribute("documentNumber", documentNumber);
		model.addAttribute("dto",dto);
		
		return "approval/createDraft";
	}
	

	
	
	// 전자결재홈에서 기안서 상세 조회
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
	
	
		
		// 조직도
		@ResponseBody
		@GetMapping("/getOrgChart")
		public ResponseEntity<List<Map<String, Object>>> getOrgChart() {
		    List<Map<String, Object>> orgNodes = orgService.getOrgTree();  // 조직도 데이터를 가져오는 메서드
		    return ResponseEntity.ok(orgNodes);  // JSON 형태로 반환
		}
		
		
		// 결재문서함
		@GetMapping("/apprStorage")
		public String showApprStorage(Model model) {
			List<ApprovalDto> pendingApprovals = approvalService.getPendingApprovalDtosForCurrentUser(10);
	        model.addAttribute("approvals", pendingApprovals);
					        
			return "approval/apprStorage"; 
		}
		
		
			// 결재문서함 상세 조회
			@GetMapping("/apprStorage/{appr_no}")
			public String getApprovalLineByApprNo(Model model,
					@PathVariable("appr_no") Long apprNo) {
				// 현재 로그인한 사용자 정보 가져오기
				SecurityUser loginUser = (SecurityUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

				// 로그인한 사용자의 empCode 가져오기
				Long empCode = loginUser.getDto().getEmp_code();
		     

			    ApprovalDto approvalDto = approvalService.selectapprovalOne(apprNo);
			    System.out.println("approval : "+approvalDto);
			    model.addAttribute("approval", approvalDto);
			    
			    // 결재문서를 작성한 사람의 emp_code를 통해 작성자 정보 가져오기
			    Long apprWriterNo = approvalDto.getAppr_writer_code(); // 작성자의 emp_code

			    // 작성자의 팀명 가져오기
			    String writerTeamName = employeeService.getTeamNameByEmpCode(apprWriterNo); // 작성자의 팀명을 가져옴
			    model.addAttribute("groupNames", writerTeamName);
			    
			    
			    // 결재선 정보 가져오기
			    List<ApprovalLineDto> approvalLines = approvalService.getApprovalLinesByApprNo(apprNo);
			    
				// 1차, 2차 결재자 처리 로직 추가
		        processApprovalButtons(model, approvalLines, empCode);
				return "approval/apprStorageDetail";
			}
			
			
			private void processApprovalButtons(Model model, List<ApprovalLineDto> approvalLines, Long empCode) {
		        ApprovalLineDto firstApprover = approvalLines.stream()
		                .filter(line -> line.getAppr_order() == 1)
		                .findFirst()
		                .orElse(null);

		        ApprovalLineDto secondApprover = approvalLines.stream()
		                .filter(line -> line.getAppr_order() == 2)
		                .findFirst()
		                .orElse(null);

		        // 승인 버튼 노출 여부 설정
		        boolean showFirstApproveButton = (firstApprover != null &&
		                empCode.equals(firstApprover.getEmp_code()) &&
		                firstApprover.getAppr_state().equals("S"));

		        boolean showSecondApproveButton = (secondApprover != null &&
		                empCode.equals(secondApprover.getEmp_code()) &&
		                secondApprover.getAppr_state().equals("S"));

		        model.addAttribute("showFirstApproveButton", showFirstApproveButton);
		        model.addAttribute("showSecondApproveButton", showSecondApproveButton);
		        model.addAttribute("firstApproverName", firstApprover != null ? firstApprover.getApprWriterName() : "결재자 없음");
		        model.addAttribute("secondApproverName", secondApprover != null ? secondApprover.getApprWriterName() : "결재자 없음");
		        model.addAttribute("approvalLines", approvalLines);
		    }

		
		
		
		
		
		
		
		
		
		
		//////////////////////////////////////////////
	
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
