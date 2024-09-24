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
	private Long emp_code;
	private String appr_title;
	private String appr_content;
	private Long appr_form_no;
	private String formName;
	private Integer appr_holi_use_count;
	
	// 결재 양식 이름 설정 메소드
    public void setFormName() {
        if (this.appr_form_no != null) {
            switch (this.appr_form_no .intValue()) {
                case 1:
                    this.formName = "휴가신청서";
                    break;
                case 2:
                    this.formName = "사유서";
                    break;
                case 3:
                    this.formName = "품의서";
                    break;
                default:
                    this.formName = "알 수 없음"; 
                    break;
            }
        } else {
            this.formName = "알 수 없음";
        }
    }
	
	
	public TemporaryStorage toEntity(Employee employee,ApprForm apprform) {
		return TemporaryStorage.builder()
				.temNo(tem_no)
				.apprTitle(appr_title)
	            .apprContent(appr_content)
	            .apprHoliUseCount(appr_holi_use_count)
				.employee(employee)
				.apprForm(apprform)
				.build();
	}
	
	
	public TemporaryStorageDto toDto(TemporaryStorage temporaryStorage) {
		TemporaryStorageDto dto = TemporaryStorageDto.builder()
		        .tem_no(temporaryStorage.getTemNo())
		        .emp_code(temporaryStorage.getEmployee().getEmpCode())
		        .appr_title(temporaryStorage.getApprTitle())
		        .appr_content(temporaryStorage.getApprContent())
		        .appr_holi_use_count(temporaryStorage.getApprHoliUseCount())
		        .appr_form_no(temporaryStorage.getApprForm().getApprFormNo()) // appr_form_no 값 설정
		        .build();
		    
		    // formName 설정 메소드 호출
		    dto.setFormName();
		    
		    return dto;
	}
	
	
	//System.out.println("temporaryStorageDto.appr_form_no: " + temporaryStorageDto.getAppr_form_no());
	
	
}
