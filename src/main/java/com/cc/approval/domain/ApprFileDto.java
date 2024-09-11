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
public class ApprFileDto {
	private Long appr_file_no;
	private String file_name;
	private String file_path;
	
	public ApprFile toEntity() {
		return ApprFile.builder()
				.apprFileNo(appr_file_no)
				.fileName(file_name)
				.filePath(file_path)
				.build();
	}
	
	
	public ApprFileDto toDto(ApprFile apprFile) {
		return ApprFileDto.builder()
				.appr_file_no(apprFile.getApprFileNo())
				.file_name(apprFile.getFileName())
				.file_path(apprFile.getFilePath())
				.build();
	}
	
}
