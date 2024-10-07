package com.cc;


import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.cc.approval.service.ApprovalService;
import com.cc.attendance.domain.AttendanceDto;
import com.cc.attendance.service.AttendanceService;
import com.cc.employee.domain.EmployeeDto;
import com.cc.employee.service.EmployeeService;
import com.cc.reservation.domain.MeetingRoomDto;
import com.cc.reservation.domain.ReservationDto;
import com.cc.reservation.service.MeetingRoomService;
import com.cc.reservation.service.ReservationService;
import com.cc.notice.domain.NoticeDto;
import com.cc.notice.service.NoticeService;
import com.cc.tree.service.OrgService;
@Controller
public class HomeController {

	private final EmployeeService employeeService;
	private final OrgService orgService;
	private final AttendanceService attendanceService;
	private final ReservationService reservationService;
	private final MeetingRoomService meetingRoomService;
	@Autowired
	public HomeController(EmployeeService employeeService, OrgService orgService, AttendanceService attendanceService, ReservationService reservationService,MeetingRoomService meetingRoomService) {
	private final ApprovalService approvalService;
	private final NoticeService noticeService;

	@Autowired

	public HomeController(EmployeeService employeeService, OrgService orgService, AttendanceService attendanceService
			,NoticeService noticeService,ApprovalService approvalService) {
		this.noticeService = noticeService;
		this.employeeService = employeeService;
		this.orgService = orgService;
		this.attendanceService = attendanceService;
		this.reservationService = reservationService;
		this.meetingRoomService = meetingRoomService;
		this.approvalService = approvalService;
	}

	@GetMapping({ "", "/" })
	public String home(Model model) {
		// 1. 로그인, 사용자 정보
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		User user = (User) authentication.getPrincipal();
		String empAccount = user.getUsername();
		Long empCode = employeeService.findEmpCodeByEmpName(empAccount);
		
		EmployeeDto userDto = employeeService.selectEmployeeOne(empCode);
		model.addAttribute("userDto", userDto);
		
		
		// 2. 오늘 날짜 출퇴근
		AttendanceDto attnDto = attendanceService.getTodayAttendance(empCode);
        model.addAttribute("attnDto", attnDto);
        
        // 예약 정보
        List<ReservationDto> reservations = reservationService.getAllReservations();
        List<MeetingRoomDto> meeting = meetingRoomService.selectMeetingRoomList();
        model.addAttribute("meetings",meeting);
        model.addAttribute("reservations", reservations);  // reservations로 수정
		
        // 3. 결재 현황 (결재 대기 건수와 결재 완료 건수)
        long waitingCount = approvalService.countByStatus("S", empCode);
        long completedCount = approvalService.countByStatus("C", empCode);
        model.addAttribute("waitingCount", waitingCount);
        model.addAttribute("completedCount", completedCount);


        List<NoticeDto> noticeList = noticeService.selectNoticeList();
		model.addAttribute("noticeList", noticeList);
        
		return "index";
	}
}
