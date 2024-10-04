package com.cc.attendance.domain;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
@Builder
public class MonthlyLeaveDto {
	
	private int month;  
    private int usedLeave;         // 사용한 연차
    private int remainingLeave;    // 남은 연차

    
}
