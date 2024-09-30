package com.cc.calendar.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.cc.calendar.domain.Color;
import com.cc.calendar.domain.UserScheduleColor;
import com.cc.calendar.domain.UserScheduleColorDto;
import com.cc.calendar.repository.ColorRepository;
import com.cc.calendar.repository.UserScheduleColorRepository;
import com.cc.employee.domain.Employee;
import com.cc.employee.service.EmployeeService;

@Service
public class UserScheduleColorService {
	
private final UserScheduleColorRepository userScheduleColorRepository;
private final ColorRepository colorRepository;
private final EmployeeService employeeService;
private final ColorService colorService;
	public UserScheduleColorService(UserScheduleColorRepository userScheduleColorRepository,ColorRepository colorRepository,
			EmployeeService employeeService,ColorService colorService) {
		this.userScheduleColorRepository = userScheduleColorRepository;
		this.colorRepository = colorRepository;
		this.employeeService = employeeService;
		this.colorService = colorService;
	}    
	
	
	 
	 // 일정 타입별로 색상 코드를 가져오는 메서드
    public String getColorForSpecificScheduleType(Long empCode, Long scheduleType) {
        Employee employee = employeeService.findByEmpCode(empCode);
        UserScheduleColor userScheduleColor = userScheduleColorRepository.findByEmployeeAndScheduleType(employee, scheduleType);
        if (userScheduleColor != null && userScheduleColor.getColor() != null) {
            return userScheduleColor.getColor().getColorCode();
        }
        System.out.println(userScheduleColor);
        return "#FFFFBA";  // 기본 색상 반환 (색상이 없을 경우)
    }
    
  
    
 // DTO를 통해 색상 업데이트 로직
    public UserScheduleColor updateColorForScheduleType(UserScheduleColorDto dto) {
        // DTO에서 받은 color_code로 color_no 조회
        Long colorNo = colorService.findColorNoByColorCode(dto.getColor_code());

        if (colorNo == null) {
            return null; // color_no가 유효하지 않으면 null 반환
        }

        // empCode와 scheduleType을 이용해 해당 엔티티를 조회한 후 색상 업데이트
        UserScheduleColor userScheduleColor = userScheduleColorRepository.findByEmployee_EmpCodeAndScheduleType(dto.getEmp_code(), dto.getSchedule_type());

        if (userScheduleColor != null) {
            // Color 엔티티를 조회  
            Color color = colorService.findColorById(colorNo);
            if (color != null) {
                userScheduleColor.setColor(color); // 색상 업데이트
                return userScheduleColorRepository.save(userScheduleColor); // 저장 후 반환
            }
        }

        return null; // 해당 엔티티가 없거나 색상을 찾지 못한 경우 null 반환
    }


}
