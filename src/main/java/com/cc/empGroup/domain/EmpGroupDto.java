package com.cc.empGroup.domain;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.security.core.GrantedAuthority;

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
public class EmpGroupDto {

	private Long group_no;
	private Long group_parent_no;
	private String group_name;
	private Long group_leader_code;
	private Long group_headcount;
	private String group_location;
	private String group_status;
	private String group_level;
	private String group_explain;
	
	private List<GrantedAuthority> authorities;
	
	public EmpGroup toEntity() {
		return EmpGroup.builder()
				.groupNo(group_no)
				.groupParentNo(group_parent_no)
				.groupName(group_name)
				.groupLeaderCode(group_leader_code)
				.groupHeadcount(group_headcount)
				.groupLocation(group_location)
				.groupStatus(group_status)
				.groupLevel(group_level)
				.groupExplain(group_explain)
				.build();
	}
	
	public EmpGroupDto toDto(EmpGroup empGroup) {
		return EmpGroupDto.builder()
				.group_no(empGroup.getGroupNo())
				.group_parent_no(empGroup.getGroupParentNo())
				.group_name(empGroup.getGroupName())
				.group_leader_code(empGroup.getGroupLeaderCode())
				.group_headcount(empGroup.getGroupHeadcount())
				.group_location(empGroup.getGroupLocation())
				.group_status(empGroup.getGroupStatus())
				.group_level(empGroup.getGroupLevel())
				.group_explain(empGroup.getGroupExplain())
				.build();
	}
	
}
	
