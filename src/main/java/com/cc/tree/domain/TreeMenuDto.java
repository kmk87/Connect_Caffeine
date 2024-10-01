package com.cc.tree.domain;

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
public class TreeMenuDto {

	 private String id;			// tree 내 id
	 private String parent;		// 부모 노드 id
	 private String text;		// 이름
	 private String primaryKey; // 실제 pk
	 private String jobCode;	// 실제 job_code(pk, 정렬 시 활용)
	 private String type;		// 아이콘 변경 시 활용
	 private boolean opened;
	 
}