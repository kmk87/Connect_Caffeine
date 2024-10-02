//package com.cc.storeManagement.domain;
//
//import lombok.AllArgsConstructor;
//import lombok.Builder;
//import lombok.Getter;
//import lombok.NoArgsConstructor;
//import lombok.Setter;
//import lombok.ToString;
//
//@NoArgsConstructor
//@AllArgsConstructor
//@Getter
//@Setter
//@ToString
//@Builder
//public class StoreDto {
//    
//    private Long storeId;
//    private String storeName;
//    private String address;
//    private String region;
//    private String storePhoneNumber;
//    private String status;
//    private String openDate;
//    private String closeDate;
//    private String managerName;
//    private String managerContact;
//    private Long totalEmployees;
//    private String businessHours;
//
//    // 엔티티 -> DTO 변환 메서드
//    public StoreDto toDto(Store store) {
//        return StoreDto.builder()
//            .storeId(store.getStoreId())
//            .storeName(store.getStoreName())
//            .address(store.getAddress())
//            .region(store.getRegion())
//            .storePhoneNumber(store.getStorePhoneNumber())
//            .status(store.getStatus().toString())  // Enum을 문자열로 변환
//            .openDate(store.getOpenDate() != null ? store.getOpenDate().toString() : null)
//            .closeDate(store.getCloseDate() != null ? store.getCloseDate().toString() : null)
//            .managerName(store.getManagerName())
//            .managerContact(store.getManagerContact())
//            .totalEmployees(store.getTotalEmployees())
//            .businessHours(store.getBusinessHours())
//            .build();
//    }
//
//    // DTO -> 엔티티 변환 메서드
//    public Store toEntity() {
//        return Store.builder()
//            .storeId(storeId)
//            .storeName(storeName)
//            .address(address)
//            .region(region)
//            .storePhoneNumber(storePhoneNumber)
//            .status(Store.Status.valueOf(status))  // 문자열을 Enum으로 변환
//            .openDate(openDate != null ? java.sql.Date.valueOf(openDate) : null)
//            .closeDate(closeDate != null ? java.sql.Date.valueOf(closeDate) : null)
//            .managerName(managerName)
//            .managerContact(managerContact)
//            .totalEmployees(totalEmployees)
//            .businessHours(businessHours)
//            .build();
//    }
//}
