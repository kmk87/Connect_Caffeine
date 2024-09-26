package com.cc.calendar.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.cc.calendar.domain.Color;
import com.cc.calendar.domain.ColorDto;

@Repository
public interface ColorRepository extends JpaRepository<Color, Long> {

    @Query("SELECT c.colorCode FROM Color c") // 색상 코드만 가져오는 쿼리
    List<String> findAllColors();
    
 // color_no로 해당 색상 코드(color_code) 가져오는 메서드
    Color findByColorNo(Long colorNo);
    
 // color_code를 통해 color_no를 조회하는 메서드
    Color findByColorCode(String colorCode);
}

