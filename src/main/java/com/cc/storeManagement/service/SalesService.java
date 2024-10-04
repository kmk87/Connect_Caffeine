package com.cc.storeManagement.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cc.storeManagement.domain.SaleDto;
import com.cc.storeManagement.repository.SalesRepository;

@Service
public class SalesService {

    private final SalesRepository salesRepository;

    @Autowired
    public SalesService(SalesRepository salesRepository) {
        this.salesRepository = salesRepository;
    }
    
//    public List<SaleDto> getStoreSalesRank() {
//        return salesRepository.findStoreSalesRank();
//    }
    
    public List<SaleDto> getFilteredSales(String rankType, int year, int month) {
        if ("daily".equals(rankType)) {
            return salesRepository.findDailySalesRank(year, month);
        } else if ("weekly".equals(rankType)) {
            return salesRepository.findWeeklySalesRank(year, month);
        } else {
            return salesRepository.findMonthlySalesRank(year, month);
        }
    }
    
    public List<SaleDto> getSalesByProduct(String rankType, int year, int month) {
    	if ("daily".equals(rankType)) {
            return salesRepository.findDailySales(year, month);
        } else if ("weekly".equals(rankType)) {
            return salesRepository.findWeeklySales(year, month);
        } else {
            return salesRepository.findMonthlySales(year, month);
        }
    }



}
