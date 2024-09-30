package com.cc.reservation.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cc.reservation.domain.Building;
import com.cc.reservation.domain.BuildingDto;
import com.cc.reservation.repository.BuildingRepository;

@Service
public class BuildingService {
	private final BuildingRepository buildingRepository;
	
	@Autowired
	public BuildingService(BuildingRepository buildingRepository) {
		this.buildingRepository = buildingRepository;
	}
	
	//빌딩을 삭제하는 메서드
    public boolean deleteBuilding(Long buildingNo) {
        try {
            buildingRepository.deleteById(buildingNo);
            return true;
        } catch (Exception e) {
            return false;
        }
    }
	
	
	//빌딩의 정보를 업데이트 하는 메서드
	  public Building updateBuilding(BuildingDto dto) {
	  BuildingDto buildingDto = selectBuildingOne(dto.getBuilding_no());
	  buildingDto.setBuilding_name(dto.getBuilding_name());
	  buildingDto.setBuilding_postcode(dto.getBuilding_postcode());
	  buildingDto.setBuilding_address(dto.getBuilding_address());
	  buildingDto.setBuilding_addr_detail(dto.getBuilding_addr_detail());
	  Building building = buildingDto.toEntity();
	  Building result = buildingRepository.save(building);
	  return result;
	  }
	 
	
	
	//빌딩의 정보를 가져오는 메서드(일단 모든 정보가져옴)
	public List<BuildingDto> selectBuildingList() {
		List<Building> buildingList = null;
		
		buildingList = buildingRepository.findAll();
		
		List<BuildingDto> buildingDtoList = new ArrayList<BuildingDto>();
		for(Building b : buildingList) {
			BuildingDto dto = new BuildingDto().toDto(b);
			buildingDtoList.add(dto);
		}
		return buildingDtoList;
	}
	
	
	
	//빌딩을 생성하는 메서드(이름, 주소, 우편번호, 상세주소를 나타냄)
	public Building createBuilding(BuildingDto dto) {
		Building building = Building.builder()
				.buildingName(dto.getBuilding_name())
				.buildingAddress(dto.getBuilding_address())
				.buildingPostcode(dto.getBuilding_postcode())
				.buildingAddrDetail(dto.getBuilding_addr_detail())
				.build();
				return buildingRepository.save(building);
	}
	
	
	//빌딩의 정보를 가져오는 메서드(내가 누른 빌딩 정보만가져오는.)
	public BuildingDto selectBuildingOne(Long buildingNo){
		Building building = buildingRepository.findBybuildingNo(buildingNo);
		BuildingDto dto = BuildingDto.builder()
				.building_no(building.getBuildingNo())
				.building_name(building.getBuildingName())
				.building_postcode(building.getBuildingPostcode())
				.building_address(building.getBuildingAddress())
				.building_addr_detail(building.getBuildingAddrDetail())
				.build();
		return dto;
	}
}
