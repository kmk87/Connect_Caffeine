package com.cc.calendar.domain;

import com.cc.employee.domain.Employee;

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
public class UserScheduleColorDto {
	
	private Long schedule_color_no;
	private Long schedule_type;
	private Long emp_code;
	private Long color_no;
	private String color_code; // 클라이언트에서 colorCode 전달

	// 엔티티 변환 메서드 (Employee와 Color 객체를 받아서 변환)
	public UserScheduleColor toEntity(Employee employee, Color color) {
		return UserScheduleColor.builder()
				.scheduleColorNo(schedule_color_no)
				.scheduleType(schedule_type)
				.employee(employee)
				.color(color)
				.build();
	}

	// 엔티티에서 DTO로 변환하는 메서드 (Color 엔티티에서 color_code 값을 받아옴)
	public static UserScheduleColorDto toDto(UserScheduleColor userScheduleColor) {
		return UserScheduleColorDto.builder()
				.schedule_color_no(userScheduleColor.getScheduleColorNo())
				.schedule_type(userScheduleColor.getScheduleType())
				.emp_code(userScheduleColor.getEmployee().getEmpCode()) // Employee 정보 가져오기
				.color_no(userScheduleColor.getColor().getColorNo())    // Color 정보 가져오기
				.color_code(userScheduleColor.getColor().getColorCode()) // color_code 추가
				.build();
	}
}

