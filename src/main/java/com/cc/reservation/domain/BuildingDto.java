package com.cc.reservation.domain;

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
public class BuildingDto {

	private Long building_no;
	private String building_name;
	private String building_address;
	private Long building_postcode;
	private String building_addr_detail ;
	
	
	public Building toEntity() {
		return Building.builder()
				.buildingNo(building_no)
				.buildingName(building_name)
				.buildingAddress(building_address)
				.buildingPostcode(building_postcode)
				.buildingAddrDetail(building_addr_detail)
				.build();
	}
	
	public BuildingDto toDto(Building building) {
		return BuildingDto.builder()
				.building_no(building.getBuildingNo())
				.building_name(building.getBuildingName())
				.building_address(building.getBuildingAddress())
				.building_postcode(building.getBuildingPostcode())
				.building_addr_detail(building.getBuildingAddrDetail())
				.build();
	}
}
