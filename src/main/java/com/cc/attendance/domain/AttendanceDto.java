package com.cc.attendance.domain;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.security.core.GrantedAuthority;

import com.fasterxml.jackson.annotation.JsonFormat;

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
public class AttendanceDto {

	private Long attn_no;
	
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
	private LocalDateTime attn_start;
	
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
	private LocalDateTime attn_end;
	
	private Long worktime;
	private String calc_worktime;
	private String attn_status;
	
	// 검색
	private int search_type = 1;
	private String search_text;
	private List<GrantedAuthority> authorities;
	
	
	// 쿼리문에 필요한 생성자
    public AttendanceDto(Long attn_no, LocalDateTime attn_start, LocalDateTime attn_end, 
    		Long worktime, String attn_status) {
        this.attn_no = attn_no;
        this.attn_start = attn_start;
        this.attn_end = attn_end;
        this.worktime = worktime;
        this.attn_status = attn_status;
    }
	
	
	// DTO -> Entity
	public Attendance toEntity() {
		
		/*
		 * // DateTimeFormatter를 이용해 문자열을 파싱할 형식 지정 DateTimeFormatter formatter =
		 * DateTimeFormatter.ofPattern("yyyy-MM-dd");
		 * 
		 * // 문자열을 LocalDateTime으로 변환 LocalDateTime attn_start_iso =
		 * LocalDateTime.parse(attn_start, formatter); LocalDateTime attn_end_iso =
		 * LocalDateTime.parse(attn_end, formatter);
		 */
		
		return Attendance.builder()
				.attnNo(attn_no)
				.attnStart(attn_start)
				.attnEnd(attn_end)
				.worktime(worktime)
				.attnStatus(attn_status)
				.build();
	}
	
	
	// Entity -> DTO
	public AttendanceDto toDto(Attendance attendance){

		/*
		 * // DateTimeFormatter를 이용해 원하는 형식 지정 DateTimeFormatter formatter =
		 * DateTimeFormatter.ofPattern("yyyy-MM-dd");
		 * 
		 * // LocalDateTime을 String으로 변환 String attnStartStr = null;
		 * if(attendance.getAttnStart() != null) attnStartStr =
		 * attendance.getAttnStart().format(formatter);
		 * 
		 * String attnEndtStr = null; if(attendance.getAttnEnd() != null) attnEndtStr =
		 * attendance.getAttnEnd().format(formatter);
		 */
		
		
		return AttendanceDto.builder()
				.attn_no(attendance.getAttnNo())
				.attn_start(attendance.getAttnStart())
				.attn_end(attendance.getAttnEnd())
				.worktime(attendance.getWorktime())
				.attn_status(attendance.getAttnStatus())
				.build();
	}
	
	
}
