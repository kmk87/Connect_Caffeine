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
public class ProductDto {
	
	private Long product_id;
	private String product_name;
	private String price;
	private String category;
	
    // DTO -> 엔티티 변환 메서드
    public Product toEntity() {
    	return Product.builder()
    			.productId(product_id)
    			.productName(product_name) 
    			.price(price)
    			.category(category)
    			.build();
    }
    
    // 엔티티 -> DTO 변환 메서드
    public static ProductDto toDto(Product product) {
        return ProductDto.builder()
            .product_id(product.getProductId())
            .product_name(product.getProductName()) 
            .price(product.getPrice())
            .category(product.getCategory())
            .build();
    }
	
}
