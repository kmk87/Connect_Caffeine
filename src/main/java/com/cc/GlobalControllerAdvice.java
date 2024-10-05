package com.cc;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ModelAttribute;

import com.cc.employee.service.EmployeeService;
import com.cc.notification.domain.NotificationDto;
import com.cc.notification.service.NotificationService;
import com.cc.tree.service.OrgService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

@ControllerAdvice
public class GlobalControllerAdvice {

    private final EmployeeService employeeService;
    private final OrgService orgService;
    private final NotificationService notificationService;

    public GlobalControllerAdvice(EmployeeService employeeService, OrgService orgService,
    		NotificationService notificationService) {
        this.employeeService = employeeService;
        this.orgService = orgService;
        this.notificationService = notificationService;
    }

 // 트리 조직도 만들기
    @ModelAttribute
    public void treeToModel(Model model) {
    	// 1. 팀 정보
    	List<Map<String, Object>> teamResult = orgService.getOrgTeamTree();

		// 앞에 전사, 사장, 부사장 정보 추가
		Map<String, Object> teamNode1 = new HashMap<>(); // 전사

		teamNode1.put("id", 1);
		teamNode1.put("parent", "#");
		teamNode1.put("text", "전사");
		teamNode1.put("primaryKey", "없음.");
		teamNode1.put("type", "master");
		/*
		 * // 두번째 앞에 추가할 새로운 객체 생성 Map<String, Object> teamNode2 = new HashMap<>(); //
		 * 사장
		 * 
		 * teamNode2.put("id", 2); teamNode2.put("parent", String.valueOf(1));
		 * teamNode2.put("text", "마동석"); teamNode2.put("primaryKey", "사장");
		 * 
		 * // 세번째 앞에 추가할 새로운 객체 생성 Map<String, Object> teamNode3 = new HashMap<>(); //
		 * 부사장
		 * 
		 * teamNode3.put("id", 3); teamNode3.put("parent", String.valueOf(1));
		 * teamNode3.put("text", "박보영"); teamNode3.put("primaryKey", "부사장");
		 * 
		 * 
		 *  teamResult.add(1, teamNode2); teamResult.add(2,
		 * teamNode3);
		 */
		teamResult.add(0, teamNode1);
		
		String teamObj = null;

		try {
			teamObj = new ObjectMapper().writeValueAsString(teamResult);

		} catch (JsonProcessingException e) {
			e.printStackTrace();
		}

		
		// 2. 사원 정보
		List<Map<String, Object>> empResult = orgService.getOrgEmpTree();

		String empObj = null;

		try {
			empObj = new ObjectMapper().writeValueAsString(empResult);

		} catch (JsonProcessingException e) {
			e.printStackTrace();
		}

		model.addAttribute("teamObj", teamObj);
		model.addAttribute("empObj", empObj);
		
		
		// 알림
		 Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
	        if (authentication != null && authentication.getPrincipal() instanceof User) {
	            User user = (User) authentication.getPrincipal();
	            String empAccount = user.getUsername();
	            Long empCode = employeeService.findEmpCodeByEmpName(empAccount);
		// 읽지 않은 알림을 가져와서 모델에 추가
        List<NotificationDto> unreadNotifications = notificationService.getUnreadNotifications(empCode);
        model.addAttribute("unreadNotifications", unreadNotifications);
    }
    }
    }
    
    
