package com.cc.storeManagement.domain;

import java.time.LocalDate;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class SaleDto {

    private Long saleId;
    private Long storeId;
    private Long productId;
    private LocalDate saleDate;   // 일별 및 월별 매출용
    private Long quantity;        // 주문 수량
    private Long orderCount;      // 주문 건수
    private Long totalPrice;      // 매출액
    private String storeName;
    private String region;
    private int week;             // 주별 매출용
    private String productName;

    // 월별 매출 순위를 위한 생성자
    public SaleDto(String storeName, String region, Long orderCount, Long totalPrice) {
        this.storeName = storeName;
        this.region = region;
        this.orderCount = orderCount;   // 주문 건수
        this.totalPrice = totalPrice;   // 매출액
    }

    // 주별 계산을 위한 생성자
    public SaleDto(String storeName, String region, int week, Long orderCount, Long totalPrice) {
        this.storeName = storeName;
        this.region = region;
        this.week = week;
        this.orderCount = orderCount;   // 주문 건수
        this.totalPrice = totalPrice;   // 매출액
    }

    // 일별 및 월별 계산을 위한 생성자
    public SaleDto(String storeName, String region, LocalDate saleDate, Long orderCount, Long totalPrice) {
        this.storeName = storeName;
        this.region = region;
        this.saleDate = saleDate;
        this.orderCount = orderCount;   // 주문 건수
        this.totalPrice = totalPrice;   // 매출액
    }
    
 // 월별 매출 순위를 위한 생성자
    public SaleDto(Long productId, String productName, Long orderCount, Long totalPrice) {
        this.productId = productId;
        this.productName = productName; // 제품 이름
        this.orderCount = orderCount;   // 주문 건수
        this.totalPrice = totalPrice;   // 매출액
    }

    // 주별 계산을 위한 생성자
    public SaleDto(Long productId, String productName, int week, Long orderCount, Long totalPrice) {
        this.productId = productId;
        this.productName = productName; // 제품 이름
        this.week = week;
        this.orderCount = orderCount;   // 주문 건수
        this.totalPrice = totalPrice;   // 매출액
    }

    // 일별 및 월별 계산을 위한 생성자
    public SaleDto(Long productId, String productName, LocalDate saleDate, Long orderCount, Long totalPrice) {
        this.productId = productId;
        this.productName = productName; // 제품 이름
        this.saleDate = saleDate;
        this.orderCount = orderCount;   // 주문 건수
        this.totalPrice = totalPrice;   // 매출액
    }

    // DTO -> 엔티티 변환 메서드
    public Sale toEntity(Store store, Product product) {
        return Sale.builder()
                .saleId(saleId)
                .store(store)
                .product(product)
                .saleDate(saleDate)
                .quantity(quantity)     // 제품 주문 수량 그대로 저장
                .totalPrice(totalPrice) // 매출액 그대로 저장
                .build();
    }

    // 엔티티 -> DTO 변환 메서드
    public static SaleDto toDto(Sale sale) {
        return SaleDto.builder()
                .saleId(sale.getSaleId())
                .storeId(sale.getStore().getStoreId())  // Store 엔티티에서 storeId를 가져옴
                .productId(sale.getProduct().getProductId())
                .saleDate(sale.getSaleDate())
                .quantity(sale.getQuantity())           // 제품 주문 수량
                .orderCount(1L)                         // 주문 건수는 1건이므로 1로 설정 (단일 주문의 경우)
                .totalPrice(Long.valueOf(sale.getTotalPrice())) // 매출액
                .storeName(sale.getStore().getStoreName())
                .region(sale.getStore().getRegion())
                .productName(sale.getProduct().getProductName())
                .build();
    }
}
