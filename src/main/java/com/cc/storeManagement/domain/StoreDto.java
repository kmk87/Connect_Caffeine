package com.cc.storeManagement.domain;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Date;

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
public class StoreDto {

    private Long storeId;
    private String storeName;
    private String address;
    private String region;
    private String storePhoneNumber;
    private LocalDate openDate;   // LocalDate로 처리
    private LocalDate closeDate;  // LocalDate로 처리
    private String managerName;
    private String managerContact;
    private Long totalEmployees;
    private LocalTime businessHoursStart;  // 시간 처리
    private LocalTime businessHoursEnd;    // 시간 처리

    // DTO -> 엔티티 변환 메서드
    public Store toEntity() {
    	return Store.builder()
    			.storeId(storeId)
    			.storeName(storeName)
    			.address(address)
    			.region(region)
    			.storePhoneNumber(storePhoneNumber)
    			.openDate(openDate)  // LocalDate 그대로 사용
    			.closeDate(closeDate)  // LocalDate 그대로 사용
    			.managerName(managerName)
    			.managerContact(managerContact)
    			.totalEmployees(totalEmployees)
    			.businessHoursStart(businessHoursStart)  // LocalTime 그대로 사용
    			.businessHoursEnd(businessHoursEnd)  // LocalTime 그대로 사용
    			.build();
    }
    
    // 엔티티 -> DTO 변환 메서드
    public StoreDto toDto(Store store) {
        return StoreDto.builder()
            .storeId(store.getStoreId())
            .storeName(store.getStoreName())
            .address(store.getAddress())
            .region(store.getRegion())
            .storePhoneNumber(store.getStorePhoneNumber())
            .openDate(store.getOpenDate())  // LocalDate 그대로 사용
            .closeDate(store.getCloseDate())  // LocalDate 그대로 사용
            .managerName(store.getManagerName())
            .managerContact(store.getManagerContact())
            .totalEmployees(store.getTotalEmployees())
            .businessHoursStart(store.getBusinessHoursStart())  // LocalTime 그대로 사용
            .businessHoursEnd(store.getBusinessHoursEnd())  // LocalTime 그대로 사용
            .build();
    }

}
