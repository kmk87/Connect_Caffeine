package com.cc.approval.domain;

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
public class TemporaryStorageDto {
	
	private Long tem_no;
	private Long appr_no;
	private Long emp_code;
	private String appr_title;
	private String appr_content;
	private Long appr_form_no;

	
	
	
	
	public TemporaryStorage toEntity(Approval approval,Employee employee,ApprForm apprform) {
		return TemporaryStorage.builder()
				.temNo(tem_no)
				.apprTitle(appr_title)
				.apprContent(appr_content)
				.approval(approval)
				.employee(employee)
				.apprForm(apprform)
				.build();
	}
	
	
	public TemporaryStorageDto toDto(TemporaryStorage temporaryStorage) {
		return TemporaryStorageDto.builder()
				.tem_no(temporaryStorage.getTemNo())
				.appr_no(temporaryStorage.getApproval().getApprNo())
                .emp_code(temporaryStorage.getEmployee().getEmpCode())
                .appr_title(temporaryStorage.getApprTitle())
                .appr_content(temporaryStorage.getApprContent())
				.build();
	}
	
	
}
