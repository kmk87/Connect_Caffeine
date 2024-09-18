package com.cc.calendar.domain;

import java.time.LocalDateTime;

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
public class ColorDto {
	
	private Long color_no;
	private String color_name;
	private String color_code;
	
	public Color toEntity() {
		return Color.builder()
				.colorNo(color_no)
				.colorName(color_name)
				.colorCode(color_code)
				.build();
	}
	
	public ColorDto toDto(Color colors) {
		return ColorDto.builder()
				.color_no(colors.getColorNo())
				.color_name(colors.getColorName())
				.color_code(colors.getColorCode())
				.build();
	}
}
