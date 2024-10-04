package com.cc.approval.controller;


import java.awt.print.Pageable;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
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
import org.springframework.security.core.userdetails.UserDetails;
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

import jakarta.servlet.http.HttpServletRequest;


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
	 public String showApprovalHome(HttpServletRequest request, Model model) { 
	        // 데이터베이스에서 결재 진행 문서 리스트를 조회
	        List<ApprovalDto> top5Approvals = approvalService.getAllApprovals(5); 
	        
	        // 상위 5개 항목만 가져오기-내림차순
	        //List<ApprovalDto> top5ApprDtoList = apprDtoList.size() > 5 ? apprDtoList.subList(0, 5) : apprDtoList;
	        String currentUri = request.getRequestURI();
		     model.addAttribute("currentUri", currentUri);
		     
	        model.addAttribute("apprDtoList", top5Approvals); 

	        
	        return "approval/approvalHome"; 
	 }

	
	
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
	    
	    // 현재 로그인한 사용자 정보 가져오기
	    Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
	    
	    // 사용자 이름을 가져올 변수 선언
	    String username = null;
	    
	    // principal이 String 또는 UserDetails일 경우를 처리
	    if (authentication != null) {
	        Object principal = authentication.getPrincipal();
	        
	        // principal이 String 타입일 경우 (username 자체)
	        if (principal instanceof String) {
	            username = (String) principal;
	        }
	        // principal이 UserDetails 타입일 경우
	        else if (principal instanceof UserDetails) {
	            UserDetails userDetails = (UserDetails) principal;
	            username = userDetails.getUsername(); // 로그인된 사용자의 username을 가져옴
	        } else {
	            throw new RuntimeException("알 수 없는 사용자 유형입니다.");
	        }
	    } else {
	        throw new RuntimeException("로그인 정보가 없습니다.");
	    }
	    
	    // 로그인된 사용자의 팀명 가져오기
	    String groupName = employeeService.getUserTeamName(username);
	    model.addAttribute("groupNames", groupName);
	    
	    
	    
	    // 기안서 상세 정보 가져오기
	    ApprovalDto approvalDto = approvalService.selectapprovalOne(appr_no);
	    model.addAttribute("dto", approvalDto);
	    
	    System.out.println("전자결재홈 기안서 상세조회: "+approvalDto);
	    // 문서번호 가져오기
	    String documentNumber = approvalDto.getDocu_no();  // docu_no를 직접 가져옴
	    model.addAttribute("documentNumber", documentNumber);
	    
	    // 결재수신문서를 작성한 사람의 emp_code를 통해 작성자 정보 가져오기
	    Long apprWriterNo = approvalDto.getAppr_writer_code(); 
	    
	    String writerTeamName = employeeService.getTeamNameByEmpCode(apprWriterNo); // 작성자의 팀명을 가져옴
	    model.addAttribute("groupNames", writerTeamName);
	    
	    // 기안서의 휴가 시작일과 종료일 가져오기
	    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
	    String apprHoliStart = approvalDto.getAppr_holi_start() != null ? approvalDto.getAppr_holi_start().format(formatter) : "";
	    String apprHoliEnd = approvalDto.getAppr_holi_end() != null ? approvalDto.getAppr_holi_end().format(formatter) : "";
	    
	    model.addAttribute("apprHoliStart", apprHoliStart);
	    model.addAttribute("apprHoliEnd", apprHoliEnd);
	    
	    // 결재선 및 참조선 정보 가져오기
	    Map<String, List<ApprovalLine>> approvalLines = approvalService.getApprovalLines(approvalDto.getDocu_no());
	    model.addAttribute("approvers", approvalLines.get("approvers"));
	    model.addAttribute("referers", approvalLines.get("referers"));
	    
	    return "approval/draftStorageDetail";
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
	public String selectTemproaryOne(@PathVariable("tem_no") Long tem_no, Model model) {
	    // 로그인된 사용자 정보 가져오기
	    Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
	    
	    String username = null;
	    
	    // principal이 String 또는 UserDetails일 경우를 처리
	    if (authentication != null) {
	        Object principal = authentication.getPrincipal();
	        
	        // principal이 String 타입일 경우 (username 자체)
	        if (principal instanceof String) {
	            username = (String) principal;
	        }
	        // principal이 UserDetails 타입일 경우
	        else if (principal instanceof UserDetails) {
	            UserDetails userDetails = (UserDetails) principal;
	            username = userDetails.getUsername(); // 로그인된 사용자의 username을 가져옴
	        } else {
	            throw new RuntimeException("알 수 없는 사용자 유형입니다.");
	        }
	    } else {
	        throw new RuntimeException("로그인 정보가 없습니다.");
	    }

	    // 로그인된 사용자의 팀명 가져오기
	    String groupName = employeeService.getUserTeamName(username);
	    model.addAttribute("groupNames", groupName);
	    
	    // 로그인된 사용자의 이름 가져오기
	    String apprName = employeeService.getUserEmpName(username);
	    model.addAttribute("apprName", apprName);
	    
	    // 임시 저장된 제목과 내용을 가져옴
	    TemporaryStorageDto temporaryStorageDto = approvalService.selecttemproaryOne(tem_no);
	    
	    // 문서번호 생성 로직을 호출하여 문서번호 생성
	    String documentNumber = approvalService.generateDocumentNumber(groupName);
	    
	    // 문서번호와 함께 결재 관련 정보(팀명, 기안일, 기안자)를 가져옴
	    ApprovalDto approvalDto = ApprovalDto.builder()
	        .docu_no(documentNumber) // 문서번호 추가
	        .draft_day(LocalDate.now()) // 기안일(당일)
	        .appr_writer_name(apprName) // 기안자(로그인된 사용자 이름)
	        .build();
	    
	    System.out.println("approvalDto 임시저장 상세: "+approvalDto);
	    model.addAttribute("apprDto", approvalDto);
	    model.addAttribute("tempDto", temporaryStorageDto);
	    
	    return "approval/tempDetail";
	}
	
	// 기안문서함
	@GetMapping("/draftStorage")
	public String showDraftStorage(Model model) {
		// 현재 로그인한 사용자 정보 가져오기
	    Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
	    String empAccount = authentication.getName();  // emp_account (로그인한 사용자 정보)

	    // emp_account로 기안서 목록 조회
	    List<ApprovalDto> userDrafts = approvalService.getDraftListByEmpAccount(empAccount);

	    // 모델에 리스트 추가
	    model.addAttribute("apprDraftDtoList", userDrafts);

	    return "approval/draftStorage"; 
	}
	
	// 기안문서함 기안서 상세 조회
		@GetMapping("/draftStorageDetail/{appr_no}")
		public String selectDraftStorageOne(Model model,
				@PathVariable("appr_no") Long appr_no) {
			Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		    User user = (User) authentication.getPrincipal();
		    String username = user.getUsername();
		    String groupName = employeeService.getUserTeamName(username);
		    model.addAttribute("groupNames", groupName);
		    
		    // 기안서 상세 정보 가져오기
			ApprovalDto approvalDto = approvalService.selectapprovalOne(appr_no);
			model.addAttribute("dto",approvalDto);
			
			// 문서번호 가져오기
			String documentNumber = approvalDto.getDocu_no();  // docu_no를 직접 가져옴
		    model.addAttribute("documentNumber", documentNumber);
		    
		    // 기안서의 휴가 시작일과 종료일 가져오기
		    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
		    String apprHoliStart = approvalDto.getAppr_holi_start() != null ? approvalDto.getAppr_holi_start().format(formatter) : "";
		    String apprHoliEnd = approvalDto.getAppr_holi_end() != null ? approvalDto.getAppr_holi_end().format(formatter) : "";
		    model.addAttribute("apprHoliStart", apprHoliStart);
		    model.addAttribute("apprHoliEnd", apprHoliEnd);
		    
			// 결재선 및 참조선 정보 가져오기
		    Map<String, List<ApprovalLine>> approvalLines = approvalService.getApprovalLines(approvalDto.getDocu_no());
		
		    model.addAttribute("approvers", approvalLines.get("approvers"));
		    model.addAttribute("referers", approvalLines.get("referers"));
			return "approval/draftStorageDetail";
		}
	
		
		// 조직도
		@ResponseBody
		@GetMapping("/getOrgChart")
		public ResponseEntity<List<Map<String, Object>>> getOrgChart() {
		    List<Map<String, Object>> orgNodes = orgService.getOrgTree();  // 조직도 데이터를 가져오는 메서드
		    return ResponseEntity.ok(orgNodes);  // JSON 형태로 반환
		}
		
		
		// 결재수신문서
				@GetMapping("/receiveDraft")
				public String showApprStorage(Model model) {
				    // 결재 상태가 'S'인 문서만 조회
				    List<ApprovalDto> pendingApprovals = approvalService.getPendingApprovalDtosForCurrentUser(10);

				    // 모델에 결재대기 문서 리스트 추가
				    model.addAttribute("approvals", pendingApprovals);

				    return "approval/receiveDraft"; 
				}
				
				
				
					// 결재수신문서 상세 조회
					@GetMapping("/receiveDraftDetail/{appr_no}")
					public String getApprovalLineByApprNo(Model model, @PathVariable("appr_no") Long apprNo) {
					    // 현재 로그인한 사용자 정보 가져오기
					    SecurityUser loginUser = (SecurityUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

					    // 로그인한 사용자의 empCode 가져오기
					    Long empCode = loginUser.getDto().getEmp_code();
					    
					    // 결재수신문서 상세 정보 가져오기
					    ApprovalDto approvalDto = approvalService.selectapprovalOne(apprNo);
					    
					    // apprNo 확인용 로그
					    System.out.println("Approval Number (appr_no): " + approvalDto.getAppr_no());
					    System.out.println("approval : " + approvalDto);
					    model.addAttribute("approval", approvalDto);
					    
					    // 결재수신문서를 작성한 사람의 emp_code를 통해 작성자 정보 가져오기
					    Long apprWriterNo = approvalDto.getAppr_writer_code(); // 작성자의 emp_code
					    
					    // 작성자의 팀명 가져오기
					    String writerTeamName = employeeService.getTeamNameByEmpCode(apprWriterNo); // 작성자의 팀명을 가져옴
					    model.addAttribute("groupNames", writerTeamName);
					    
					    // 기안서의 휴가 시작일과 종료일 가져오기
					    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
					    String apprHoliStart = approvalDto.getAppr_holi_start() != null ? approvalDto.getAppr_holi_start().format(formatter) : "";
					    String apprHoliEnd = approvalDto.getAppr_holi_end() != null ? approvalDto.getAppr_holi_end().format(formatter) : "";
					    model.addAttribute("apprHoliStart", apprHoliStart);
					    model.addAttribute("apprHoliEnd", apprHoliEnd);

					    
					    // 결재선 및 참조선 정보 가져오기
					    Map<String, List<ApprovalLine>> approvalLines = approvalService.getApprovalLines(approvalDto.getDocu_no());
					    
					    System.out.println("결재선Approvers: " + approvalLines.get("approvers"));
					    System.out.println("참조선Referers: " + approvalLines.get("referers"));
					    
					    model.addAttribute("approvers", approvalLines.get("approvers"));
					    model.addAttribute("referers", approvalLines.get("referers"));
					    
					    // 1차, 2차 결재자 처리 로직 추가
					    processApprovalButtons(model, approvalLines.get("approvers"), empCode);
					    
					    return "approval/receiveDraftDetail";
					}
					
					
					private void processApprovalButtons(Model model, List<ApprovalLine> approvalLines, Long empCode) {
						// 결재선 데이터 로그 출력
					    approvalLines.forEach(line -> {
					        System.out.println("결재자: " + line.getEmployee().getEmpName() + ", 결재 순서: " + line.getApprOrder() + ", 상태: " + line.getApprState());
					    });

					    ApprovalLine firstApprover = approvalLines.stream()
					            .filter(line -> line.getApprOrder() == 1)
					            .findFirst()
					            .orElse(null);

					    ApprovalLine secondApprover = approvalLines.stream()
					            .filter(line -> line.getApprOrder() == 2)
					            .findFirst()
					            .orElse(null);

					    // 승인 버튼 노출 여부 설정
					    boolean showFirstApproveButton = (firstApprover != null &&
					            empCode.equals(firstApprover.getEmployee().getEmpCode()) && // Employee의 empCode
					            "S".equals(firstApprover.getApprState()));

					    boolean showSecondApproveButton = (secondApprover != null &&
					            empCode.equals(secondApprover.getEmployee().getEmpCode()) && // Employee의 empCode
					            "S".equals(secondApprover.getApprState()));

					    // 로그로 확인
					    System.out.println("1차 승인 버튼 표시 여부: " + showFirstApproveButton);
					    System.out.println("2차 승인 버튼 표시 여부: " + showSecondApproveButton);

					    model.addAttribute("showFirstApproveButton", showFirstApproveButton);
					    model.addAttribute("showSecondApproveButton", showSecondApproveButton);

					    model.addAttribute("firstApproverName", firstApprover != null ? firstApprover.getEmployee().getEmpName() : "결재자 없음");
					    model.addAttribute("secondApproverName", secondApprover != null ? secondApprover.getEmployee().getEmpName() : "결재자 없음");
					}
		
		
		
					// 반려사유 가져오기
					@GetMapping("/getRejectReason")
					public ResponseEntity<Map<String, String>> getRejectReason(@RequestParam("apprNo") Long apprNo) {
					    Approval approval = approvalService.getApprovalByStateAndNo("R", apprNo);  // 반려 상태("R")와 결재 번호로 조회

					    Map<String, String> response = new HashMap<>();
					    response.put("rejectContent", approval.getRejectContent() != null ? approval.getRejectContent() : "반려 사유가 없습니다.");

					    return ResponseEntity.ok(response);
					}
		
	
	// 참조문서함
		@GetMapping("/referenceStorage")
		public String showReferenceStorage(Model model) {
			// 현재 로그인한 사용자 정보 가져오기
		    Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		    String empAccount = authentication.getName();  // emp_account (로그인한 사용자 정보)

		    // emp_account로 참조자 목록 조회
		    List<ApprovalDto> referenceDrafts = approvalService.getReferenceListByEmpAccount(empAccount);

		    // 모델에 리스트 추가
		    model.addAttribute("referenceDraftDtoList", referenceDrafts);
					        
		return "approval/referenceStorage"; 
	}
		
	// 참조문서함 상세조회
		@GetMapping("/referenceStorageDetail/{appr_no}")
		public String selectReferenceStorageOne(Model model,
				@PathVariable("appr_no") Long appr_no) {
			Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		    User user = (User) authentication.getPrincipal();
		    String username = user.getUsername();
		    String groupName = employeeService.getUserTeamName(username);
		    model.addAttribute("groupNames", groupName);
		    
		    // 기안서 상세 정보 가져오기
			ApprovalDto approvalDto = approvalService.selectapprovalOne(appr_no);
			model.addAttribute("dto",approvalDto);
			
			// 결재문서를 작성한 사람의 emp_code를 통해 작성자 정보 가져오기
		    Long apprWriterNo = approvalDto.getAppr_writer_code();
		    
		    // 작성자의 팀명 가져오기
		    String writerTeamName = employeeService.getTeamNameByEmpCode(apprWriterNo); // 작성자의 팀명을 가져옴
		    model.addAttribute("groupNames", writerTeamName);
		    
			
			// 문서번호 가져오기
			String documentNumber = approvalDto.getDocu_no();  // docu_no를 직접 가져옴
		    model.addAttribute("documentNumber", documentNumber);
		    
		    // 기안서의 휴가 시작일과 종료일 가져오기
		    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
		    String apprHoliStart = approvalDto.getAppr_holi_start() != null ? approvalDto.getAppr_holi_start().format(formatter) : "";
		    String apprHoliEnd = approvalDto.getAppr_holi_end() != null ? approvalDto.getAppr_holi_end().format(formatter) : "";
		    model.addAttribute("apprHoliStart", apprHoliStart);
		    model.addAttribute("apprHoliEnd", apprHoliEnd);
		    
			// 결재선 및 참조선 정보 가져오기
		    Map<String, List<ApprovalLine>> approvalLines = approvalService.getApprovalLines(approvalDto.getDocu_no());
		
		    model.addAttribute("approvers", approvalLines.get("approvers"));
		    model.addAttribute("referers", approvalLines.get("referers"));
			return "approval/referenceStorageDetail";
		}
		
	// 결재대기문서
	@GetMapping("/standByDraft")
	public String showStandByDraftStorage(Model model) {
	    // 현재 로그인한 사용자 정보 가져오기
	    String empAccount = SecurityContextHolder.getContext().getAuthentication().getName();  // 로그인한 사용자

	    // 결재대기 상태인 문서들 조회
	    List<ApprovalDto> standByDrafts = approvalService.getStandByDraftListByEmpAccount(empAccount);

	    // 모델에 추가
	    model.addAttribute("apprDraftDtoList", standByDrafts);

	    return "approval/standByDraft"; 
	}
	
	

	// 결재문서함
	@GetMapping("/apprStorage")
	public String showReceiveDraft(Model model) {
		// 현재 로그인한 사용자 정보 가져오기
	    String empAccount = SecurityContextHolder.getContext().getAuthentication().getName();  // 로그인한 사용자

	    // 결재 한 모든 문서들 조회
	    List<ApprovalDto> apprDrafts = approvalService.getAllApprovalsByEmpCode(empAccount);

	    // 모델에 추가
	    model.addAttribute("apprDtoList", apprDrafts);
			
					        
		return "approval/apprStorage"; 
	}
	
	// 결재문서함 상세 조회
	@GetMapping("/apprStorageDetail/{appr_no}")
	public String getApprDraftDetail(Model model, @PathVariable("appr_no") Long apprNo) {
	    // 현재 로그인한 사용자 정보 가져오기
	    SecurityUser loginUser = (SecurityUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

	    // 로그인한 사용자의 empCode 가져오기
	    Long empCode = loginUser.getDto().getEmp_code();
	    
	    
	    // 결재문서 상세 정보 가져오기
	    ApprovalDto approvalDto = approvalService.selectapprovalOne(apprNo);
	    
	    // apprNo 확인용 로그
	    System.out.println("Approval Number (appr_no): " + approvalDto.getAppr_no());
	    System.out.println("dto : " + approvalDto);
	    model.addAttribute("dto", approvalDto);
	    
	    // 결재문서를 작성한 사람의 emp_code를 통해 작성자 정보 가져오기
	    Long apprWriterNo = approvalDto.getAppr_writer_code(); // 작성자의 emp_code
	    
	    String documentNumber = approvalDto.getDocu_no();  // docu_no를 직접 가져옴
	    model.addAttribute("documentNumber", documentNumber);
	    
	    // 작성자의 팀명 가져오기
	    String writerTeamName = employeeService.getTeamNameByEmpCode(apprWriterNo); // 작성자의 팀명을 가져옴
	    model.addAttribute("groupNames", writerTeamName);
	    
	    // 기안서의 휴가 시작일과 종료일 가져오기
	    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
	    String apprHoliStart = approvalDto.getAppr_holi_start() != null ? approvalDto.getAppr_holi_start().format(formatter) : "";
	    String apprHoliEnd = approvalDto.getAppr_holi_end() != null ? approvalDto.getAppr_holi_end().format(formatter) : "";
	    model.addAttribute("apprHoliStart", apprHoliStart);
	    model.addAttribute("apprHoliEnd", apprHoliEnd);

	    
	    // 결재선 및 참조선 정보 가져오기
	    Map<String, List<ApprovalLine>> approvalLines = approvalService.getApprovalLines(approvalDto.getDocu_no());
	    
	    model.addAttribute("approvers", approvalLines.get("approvers"));
	    model.addAttribute("referers", approvalLines.get("referers"));
	    
	    // 1차, 2차 결재자 처리 로직 추가
	    processApprovalButtons(model, approvalLines.get("approvers"), empCode);
	    
	    return "approval/apprStorageDetail";
	}
	
	
	
	
	//////////////////////////////
	// 전자결재 환경설정
	@GetMapping("/signSetting")
	public String showsignSetting(Model model) {
		        

		        
		return "approval/signSetting"; 
	}
	
}
