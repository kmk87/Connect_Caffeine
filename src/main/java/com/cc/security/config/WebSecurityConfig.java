package com.cc.security.config;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.security.servlet.PathRequest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.rememberme.JdbcTokenRepositoryImpl;
import org.springframework.security.web.authentication.rememberme.PersistentTokenRepository;

@Configuration
public class WebSecurityConfig {
   
   private final DataSource dataSource;
   
   @Autowired
   public WebSecurityConfig(DataSource dataSource) {
      this.dataSource = dataSource;
   }
   
   
   @Bean // 이 메소드를 Bean으로 등록하여 spring에서 관리
   public SecurityFilterChain defaultSecurityFilterChain(HttpSecurity http)throws Exception{
      http.authorizeHttpRequests(request -> // 요청에 대한 접근권한을 설정


				request
				.requestMatchers("/login","/static/**", "/employee", "/employeeCreate", "/employeeList", "/bootstrap/**", "/employeeUpdate/**", "/employeeUpdate"
						, "/employeeDelete",  "/employeeDelete/**", "/empGroupList", "/empGroupList/**"
						, "/empGroupCreate", "/empGroupCreate/**", "/css/**", "/draft", "/uploadImg/**","/approvalUploadImg/**","/error/**","/upload/**",
						"/empGroupUpdate", "/empGroupUpdate/**", "/empGroupDelete", "/empGroupDelete/**", "employeeCreate","/approval/**","/createDraft").permitAll()

                               
            //.requestMatchers("/**").permitAll()
//            .requestMatchers("/calendar/**").authenticated()
			
            .anyRequest().authenticated()
            ) // 루트 URL("/")에 대한 접근을 모든 사용자에게 허용
                  
         .formLogin(login -> // 폼 기반의 인증 설정
               login.loginPage("/login") // 사용자정의 로그인 페이지 설정,"/login"경로의 페이지를 로그인 페이지로 사용
                   .loginProcessingUrl("/login") // 로그인 처리를 수행할 URL을 설정. 폼에서 로그인 요청이 "/login"으로 보내질때 처리
                   .usernameParameter("emp_account") // 로그인 폼에서 아이디 필드의 이름을 설정
                   .passwordParameter("emp_pw") // 로그인 폼에서 비밀번호 필드의 이름을 설정
                   
                   .permitAll() // 로그인 페이지의 접근을 모든 사용자에게 허용
                   .failureHandler(new MyLoginFailureHandler())
                   .successHandler(new MyLoginSuccessHandler()))
                  
         .logout(logout -> logout.permitAll()) // 로그아웃 기능 활성화하고, 로그아웃에 대한 접근을 모든 사용자에게 허용
      .rememberMe(rememberMe -> 
		rememberMe.rememberMeParameter("remember-me")
		.tokenValiditySeconds(86400*7)
		.alwaysRemember(false)
		.tokenRepository(tokenRepository()))
	.httpBasic(Customizer.withDefaults());
      return http.build(); // 설정된 HttpSecurity 객체를 빌드하여 securityFilterChain을 반환
   }
   
   
   // 자동로그인
	@Bean
	public PersistentTokenRepository tokenRepository() {
		JdbcTokenRepositoryImpl jdbcTokenRepository = new JdbcTokenRepositoryImpl();
		jdbcTokenRepository.setDataSource(dataSource);
		return jdbcTokenRepository;
	}
	
	
   @Bean
   public WebSecurityCustomizer webSecurityCustomizer() {
      return (web ->
         web.ignoring()
            .requestMatchers(
                  PathRequest.toStaticResources().atCommonLocations()
            ));
      
   }
   
   
   // 재활용하겠다 꼭 있어야 함
//      @Bean
//      public PasswordEncoder passwordEncoder() {
//         return new BCryptPasswordEncoder();
//      }
   
}
