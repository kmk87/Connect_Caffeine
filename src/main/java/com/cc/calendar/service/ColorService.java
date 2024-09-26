package com.cc.calendar.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cc.calendar.domain.Color;
import com.cc.calendar.repository.ColorRepository;

@Service
public class ColorService {
	
	private ColorRepository colorRepository;
	
	
    @Autowired
    public ColorService(ColorRepository colorRepository) {
    	this.colorRepository = colorRepository;
    	
    }

    // 색상 목록을 가져오는 메서드
    public List<String> getAllColors() {
        return colorRepository.findAllColors(); // 레포지토리에서 색상 리스트를 가져옴
    }
    
 // color_code로 color_no를 찾는 메서드
    public Long findColorNoByColorCode(String colorCode) {
        Color color = colorRepository.findByColorCode(colorCode);
        if (color != null) {
            return color.getColorNo(); // color_no 반환
        } else {
            throw new IllegalArgumentException("Invalid color code: " + colorCode);
        }
    }
    
 // colorNo로 Color 엔티티를 조회하는 메서드
    public Color findColorById(Long colorNo) {
        Optional<Color> colorOpt = colorRepository.findById(colorNo);
        return colorOpt.orElseThrow(() -> new IllegalArgumentException("해당하는 colorNo가 존재하지 않습니다."));
    }
    
    
    



    
   
}

