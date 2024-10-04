package com.cc.storeManagement.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.cc.storeManagement.domain.SaleDto;
import com.cc.storeManagement.service.SalesService;

@Controller
public class SalesViewController {

    private final SalesService salesService;

    @Autowired
    public SalesViewController(SalesService salesService) {
        this.salesService = salesService;
    }

    // 페이지 로딩 시 전체 매출 데이터를 조회하는 기본 메소드
    @GetMapping("/salesByStore")
    public String getSales(
            @RequestParam(name = "rankType", defaultValue = "monthly") String rankType,
            @RequestParam(name = "year", defaultValue = "0") int year,
            @RequestParam(name = "month", defaultValue = "0") int month,
            Model model) {

        // 현재 연도 및 월이 지정되지 않은 경우, 현재 날짜 기준으로 설정
        year = resolveYear(year);
        month = resolveMonth(month);

        List<SaleDto> storeSalesRank = salesService.getFilteredSales(rankType, year, month);
        model.addAttribute("storeSalesRank", storeSalesRank);
        model.addAttribute("rankType", rankType);
        model.addAttribute("year", year);
        model.addAttribute("month", month);
        return "storeManagement/salesByStore";
    }

    // Fetch API 요청을 처리하여 필터링된 데이터를 반환하는 메소드
    @GetMapping("/sales/filter")
    @ResponseBody
    public List<SaleDto> getFilteredSales(
            @RequestParam(name = "rankType", defaultValue = "monthly") String rankType,
            @RequestParam(name = "year", defaultValue = "0") int year,
            @RequestParam(name = "month", defaultValue = "0") int month) {

        year = resolveYear(year);
        month = resolveMonth(month);

        return salesService.getFilteredSales(rankType, year, month);
    }

    @GetMapping("/salesByProduct")
    public String getSalesByProduct(
            @RequestParam(name = "rankType", defaultValue = "monthly") String rankType,
            @RequestParam(name = "year", defaultValue = "0") int year,
            @RequestParam(name = "month", defaultValue = "0") int month,
            Model model) {

        year = resolveYear(year);
        month = resolveMonth(month);

        List<SaleDto> productSales = salesService.getSalesByProduct(rankType, year, month);
        model.addAttribute("productSales", productSales);
        model.addAttribute("rankType", rankType);
        model.addAttribute("year", year);
        model.addAttribute("month", month);

        return "storeManagement/salesByProduct"; // 템플릿 이름
    }

    // Fetch API 요청을 처리하여 상품별 매출 데이터를 반환하는 메소드
    @GetMapping("/sales/product")
    @ResponseBody // JSON 응답을 보장합니다.
    public List<SaleDto> getSalesByProductApi(
            @RequestParam(name = "rankType", defaultValue = "monthly") String rankType,
            @RequestParam(name = "year", defaultValue = "0") int year,
            @RequestParam(name = "month", defaultValue = "0") int month) {

        year = resolveYear(year);
        month = resolveMonth(month);

        return salesService.getSalesByProduct(rankType, year, month); // List<SaleDto> 반환
    }

    // 연도 및 월을 설정하는 메소드
    private int resolveYear(int year) {
        return (year == 0) ? java.time.Year.now().getValue() : year;
    }

    private int resolveMonth(int month) {
        return (month == 0) ? java.time.LocalDate.now().getMonthValue() : month;
    }
}
