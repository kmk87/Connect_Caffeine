//package com.cc.storeManagement.domain;
//
//import java.time.LocalDateTime;
//import java.util.Date;
//
//import org.hibernate.annotations.CreationTimestamp;
//import org.hibernate.annotations.UpdateTimestamp;
//
//import jakarta.persistence.Column;
//import jakarta.persistence.Entity;
//import jakarta.persistence.Enumerated;
//import jakarta.persistence.EnumType;
//import jakarta.persistence.GeneratedValue;
//import jakarta.persistence.GenerationType;
//import jakarta.persistence.Id;
//import jakarta.persistence.Table;
//import lombok.AccessLevel;
//import lombok.AllArgsConstructor;
//import lombok.Builder;
//import lombok.Getter;
//import lombok.NoArgsConstructor;
//import lombok.Setter;
//
//@Entity
//@Table(name = "stores")
//@NoArgsConstructor(access = AccessLevel.PROTECTED)
//@AllArgsConstructor(access = AccessLevel.PROTECTED)
//@Getter
//@Setter
//@Builder
//public class Store {
//
//    @Id
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
//    @Column(name = "store_id")
//    private Long storeId;
//
//    @Column(name = "store_name")
//    private String storeName;
//
//    @Column(name = "address")
//    private String address;
//
//    @Column(name = "region")
//    private String region;
//
//    @Column(name = "store_phone_number")
//    private String storePhoneNumber;
//
//    // Status 열거형 정의
//    public enum Status {
//        OPEN,
//        CLOSED,
//        RENOVATING,
//        TEMPORARILY_CLOSED
//    }
//
//    @Enumerated(EnumType.STRING)
//    @Column(name = "status")
//    private Status status;
//
//    @Column(name = "open_date")
//    private Date openDate;
//
//    @Column(name = "close_date")
//    private Date closeDate;
//
//    @Column(name = "manager_name")
//    private String managerName;
//
//    @Column(name = "manager_contact")
//    private String managerContact;
//
//    @Column(name = "total_employees")
//    private Long totalEmployees;
//
//    @Column(name = "business_hours")
//    private String businessHours;
//
//    @CreationTimestamp
//    @Column(name = "created_at", updatable = false)
//    private LocalDateTime createdAt;
//
//    @UpdateTimestamp
//    @Column(name = "updated_at")
//    private LocalDateTime updatedAt;
//}
