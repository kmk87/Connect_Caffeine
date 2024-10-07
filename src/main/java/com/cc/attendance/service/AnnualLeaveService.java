package com.cc.attendance.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import com.cc.attendance.domain.AnnualLeave;
import com.cc.attendance.domain.MonthlyLeaveDto;
import com.cc.attendance.repository.AnnualLeaveRepository;
import com.cc.employee.domain.Employee;

import jakarta.transaction.Transactional;

@Service
public class AnnualLeaveService {

private final AnnualLeaveRepository annualLeaveRepository;
	
	public AnnualLeaveService(AnnualLeaveRepository annualLeaveRepository) {
		this.annualLeaveRepository = annualLeaveRepository;
	}
	
	// 월별 연차 현황
	 public List<MonthlyLeaveDto> getMonthlyLeaveUsage(Long empCode) {
		 
		    List<Object[]> result = annualLeaveRepository.findMonthlyLeaveUsage(empCode);
		    List<MonthlyLeaveDto> monthlyLeaveUsage = new ArrayList<>();

		    int totalLeave = 15; // 예를 들어 연간 15일의 연차가 주어진다고 가정
		    int remainingLeave = totalLeave;

		    // 1월부터 12월까지 데이터를 초기화
		    for (int month = 1; month <= 12; month++) {
		        MonthlyLeaveDto dto = new MonthlyLeaveDto(month, 0, remainingLeave);  // 남은 연차 초기값
		        monthlyLeaveUsage.add(dto);
		    }

		    // 쿼리 결과를 월별로 세팅하면서 남은 연차 계산
		    for (Object[] row : result) {
		        Integer month = ((Number) row[0]).intValue();  
		        Integer usedCount = ((Number) row[1]).intValue();  
		        MonthlyLeaveDto dto = monthlyLeaveUsage.get(month - 1); 
		        dto.setUsedLeave(usedCount);

		        // 남은 연차 계산 및 업데이트
		        remainingLeave -= usedCount;
		        dto.setRemainingLeave(remainingLeave);
		    }
		    
		    // 사용 연차가 없는 월도 이전 달의 남은 연차를 그대로 반영
		    for (int i = 1; i < 12; i++) {
		        if (monthlyLeaveUsage.get(i).getUsedLeave() == 0) {
		            monthlyLeaveUsage.get(i).setRemainingLeave(monthlyLeaveUsage.get(i - 1).getRemainingLeave());
		        }
		    }

		    return monthlyLeaveUsage;
	    }
	
//	 // 연차 쓴 날짜 데이터
//	    public List<AnnualLeave> findByEmpCode(Long empCode) {
//	        return annualLeaveRepository.findByEmpCode(empCode);
//	    }
}
