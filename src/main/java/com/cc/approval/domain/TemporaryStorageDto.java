package com.cc.approval.domain;

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
public class TemporaryStorageDto {
	
	private Long tem_no;
	private int appr_no;
	private int emp_code;
	private String appr_title;
	private String appr_content;
	
	
	
	
	public TemporaryStorage toEntity() {
		return TemporaryStorage.builder()
				.temNo(tem_no)
				.build();
	}
	
	
	public TemporaryStorageDto toDto(TemporaryStorage temporaryStorage) {
		return TemporaryStorageDto.builder()
				.tem_no(temporaryStorage.getTemNo())
				.build();
	}
	
}
