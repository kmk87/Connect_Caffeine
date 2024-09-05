package com.cc.security.service;
import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import com.cc.employee.domain.Employee;
import com.cc.employee.domain.EmployeeDto;
import com.cc.employee.repository.EmployeeRepository;
import com.cc.security.vo.SecurityUser;
@Service
public class SecurityService implements UserDetailsService{
	private final EmployeeRepository employeeRepository;
	
	@Autowired
	public SecurityService(EmployeeRepository employeeRepository) {
		this.employeeRepository = employeeRepository;
	}
	
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		Employee employee = employeeRepository.findByempAccount(username);
		if(employee != null) {
			EmployeeDto dto = new EmployeeDto().toDto(employee);
			
			List<GrantedAuthority> authorities = new ArrayList<GrantedAuthority>();
			authorities.add(new SimpleGrantedAuthority(employee.getJobCode()));
			dto.setAuthorities(authorities);
			
			System.out.println("로그인 정보");
			System.out.println(dto);
			
			return new SecurityUser(dto);
		} else {
			throw new UsernameNotFoundException(username);
		}
	}
}