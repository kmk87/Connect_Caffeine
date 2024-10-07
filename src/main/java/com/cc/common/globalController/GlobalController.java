package com.cc.common.globalController;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ModelAttribute;

import com.cc.employee.domain.EmployeeDto;
import com.cc.employee.service.EmployeeService;

@ControllerAdvice
public class GlobalController {
   @Autowired
    private EmployeeService employeeService;

   // GlobalController에 로그 추가 예시
   @ModelAttribute
    public void addUserAttributes(Model model) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        // 인증 정보가 유효한지 확인
        if (authentication != null && authentication.isAuthenticated() 
                && !(authentication instanceof AnonymousAuthenticationToken)) {
            User user = (User) authentication.getPrincipal();
            String empAccount = user.getUsername();
            Long empCode = employeeService.findEmpCodeByEmpName(empAccount);

            EmployeeDto userDto = employeeService.selectEmployeeOne(empCode);
            if (userDto != null) {
                // 로그로 확인
                System.out.println("Logged in User: " + userDto.getEmp_name());
            }
            model.addAttribute("userDto", userDto);
        }
    }

}
