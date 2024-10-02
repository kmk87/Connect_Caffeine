package com.cc.annualLeave.domain;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.security.core.GrantedAuthority;

import com.cc.attendance.domain.Attendance;
import com.cc.attendance.domain.AttendanceDto;

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
public class AnnualLeaveDto {

		private Long annual_leave_no;
		private LocalDateTime annual_leave_start;
		private LocalDateTime annual_leave_end;
		private Long annual_leave_use_count;
		private Long annual_leave_left_count;
	
	
		// 검색
		private int search_type = 1;
		private String search_text;
		private List<GrantedAuthority> authorities;
		
		
		// 쿼리문에 필요한 생성자
		
		
		
		// DTO -> Entity
		public AnnualLeave toEntity() {

			return AnnualLeave.builder()
					.annualLeaveNo(annual_leave_no)
					.annualLeaveStart(annual_leave_start)
					.annualLeaveEnd(annual_leave_end)
					.annualLeaveUseCount(annual_leave_use_count)
					.annualLeaveLeftCount(annual_leave_left_count)
					.build();
		}
		
		
		// Entity -> DTO
		public AnnualLeaveDto toDto(AnnualLeave annualLeave){
		
			return AnnualLeaveDto.builder()
					.annual_leave_no(annualLeave.getAnnualLeaveNo())
					.annual_leave_start(annualLeave.getAnnualLeaveStart())
					.annual_leave_end(annualLeave.getAnnualLeaveEnd())
					.annual_leave_use_count(annualLeave.getAnnualLeaveUseCount())
					.annual_leave_left_count(annualLeave.getAnnualLeaveLeftCount())
					.build();
		}	
}
