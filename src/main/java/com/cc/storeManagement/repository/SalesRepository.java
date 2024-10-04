package com.cc.storeManagement.repository;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.cc.storeManagement.domain.Sale;
import com.cc.storeManagement.domain.SaleDto;

public interface SalesRepository extends JpaRepository<Sale, Long> {
	
	
//	@Query("SELECT new com.cc.storeManagement.domain.SaleDto(s.store.storeName, s.store.region, COUNT(s.saleId), SUM(s.totalPrice)) " +
//		       "FROM Sale s " +
//		       "GROUP BY s.store.storeName, s.store.region " +
//		       "ORDER BY SUM(s.totalPrice) DESC")
//
//		List<SaleDto> findStoreSalesRank();

	// 월간 매출 순위 조회
	@Query(value = "SELECT new com.cc.storeManagement.domain.SaleDto(st.storeName, st.region, COUNT(s.saleId), SUM(s.totalPrice)) " +
	               "FROM Sale s JOIN s.store st " +
	               "WHERE YEAR(s.saleDate) = :year AND MONTH(s.saleDate) = :month " +
	               "GROUP BY st.storeName, st.region " +
	               "ORDER BY SUM(s.totalPrice) DESC")
	List<SaleDto> findMonthlySalesRank(@Param("year") int year, @Param("month") int month);

    
	// 주간 매출 순위 조회
	@Query(value = "SELECT new com.cc.storeManagement.domain.SaleDto(st.storeName, st.region, WEEK(s.saleDate), COUNT(s.saleId), SUM(s.totalPrice)) " +
	               "FROM Sale s JOIN s.store st " +
	               "WHERE YEAR(s.saleDate) = :year AND MONTH(s.saleDate) = :month " +
	               "GROUP BY st.storeName, st.region, WEEK(s.saleDate) " +
	               "ORDER BY SUM(s.totalPrice) DESC")
	List<SaleDto> findWeeklySalesRank(@Param("year") int year, @Param("month") int month);


	// 일별 매출 순위 조회
	@Query(value = "SELECT new com.cc.storeManagement.domain.SaleDto(st.storeName, st.region, s.saleDate, COUNT(s.saleId), SUM(s.totalPrice)) " +
	               "FROM Sale s JOIN s.store st " +
	               "WHERE YEAR(s.saleDate) = :year AND MONTH(s.saleDate) = :month " +
	               "GROUP BY st.storeName, st.region, s.saleDate " +
	               "ORDER BY SUM(s.totalPrice) DESC")
	List<SaleDto> findDailySalesRank(@Param("year") int year, @Param("month") int month);
	
	// 일별 매출 조회
    @Query("SELECT new com.cc.storeManagement.domain.SaleDto(p.productId, p.productName, SUM(s.quantity), SUM(s.totalPrice)) " +
           "FROM Sale s JOIN s.product p " +
           "WHERE YEAR(s.saleDate) = :year AND MONTH(s.saleDate) = :month " +
           "GROUP BY p.productId, p.productName ORDER BY SUM(s.totalPrice) DESC")
    List<SaleDto> findDailySales(@Param("year") int year, @Param("month") int month);

    // 주별 매출 조회
    @Query("SELECT new com.cc.storeManagement.domain.SaleDto(p.productId, p.productName, WEEK(s.saleDate), SUM(s.quantity), SUM(s.totalPrice)) " +
           "FROM Sale s JOIN s.product p " +
           "WHERE YEAR(s.saleDate) = :year AND WEEK(s.saleDate) = :week " +
           "GROUP BY p.productId, p.productName ORDER BY SUM(s.totalPrice) DESC")
    List<SaleDto> findWeeklySales(@Param("year") int year, @Param("week") int week);

    // 월별 매출 조회
    @Query("SELECT new com.cc.storeManagement.domain.SaleDto(p.productId, p.productName, SUM(s.quantity), SUM(s.totalPrice)) " +
           "FROM Sale s JOIN s.product p " +
           "WHERE YEAR(s.saleDate) = :year AND MONTH(s.saleDate) = :month " +
           "GROUP BY p.productId, p.productName ORDER BY SUM(s.totalPrice) DESC")
    List<SaleDto> findMonthlySales(@Param("year") int year, @Param("month") int month);


}


