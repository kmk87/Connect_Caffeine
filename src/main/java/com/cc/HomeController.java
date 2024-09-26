package com.cc;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.cc.employee.domain.EmployeeDto;
import com.cc.employee.service.EmployeeService;


@Controller
public class HomeController {
	
	private final EmployeeService employeeService;
	
	@Autowired
	public HomeController(EmployeeService employeeService) {
		
		this.employeeService = employeeService;
	}
	
	
	@GetMapping({"","/"})
	public String home(Model model) {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
	    User user = (User) authentication.getPrincipal();
	    String username = user.getUsername();
		
		EmployeeDto dto = employeeService.getEmployeeOne(username);
		
		model.addAttribute("dto", dto);

		return "index"; 
	}
}
