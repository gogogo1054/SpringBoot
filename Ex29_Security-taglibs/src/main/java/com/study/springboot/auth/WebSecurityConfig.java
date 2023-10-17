package com.study.springboot.auth;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.factory.PasswordEncoderFactories; 
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;

import jakarta.servlet.DispatcherType; 

@Configuration
public class WebSecurityConfig  {  
	
	@Autowired
	public AuthenticationFailureHandler authenticationFailureHandler;
	
	@Bean
	protected SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
		http.csrf((csrf) -> csrf.disable())
			.cors((cors) -> cors.disable())
			.authorizeHttpRequests(request -> request
				.dispatcherTypeMatchers(DispatcherType.FORWARD).permitAll()
				.requestMatchers("/").permitAll()
				.requestMatchers("/css/*", "/js/**", "/img/**").permitAll()
				.requestMatchers("/guest/**").permitAll()
				.requestMatchers("/member/**").hasAnyRole("USER", "ADMIN")
				.requestMatchers("/admin/**").hasRole("ADMIN")
				.anyRequest().authenticated()	//	어떠한 요청이라도 인증필요
			);
 
        http.formLogin()
        	// 이것이 없으면 기본 폼이 나옴.
			.loginPage("/loginForm") 			// default : /login
		    .loginProcessingUrl("/j_spring_security_check")
		    //	실패시 이동할 경로
//	        .failureUrl("/loginForm?error") 			// default : /login?error
		    .failureHandler(authenticationFailureHandler) // 2단계 지정 선언
		    //	성공시 이동할 경로
	        //.defaultSuccessUrl("/")
		    //	아이디 입력상자의 name속성
	        .usernameParameter("j_username")	// default : j_username
	        //	비밀번호 입력상자의 name속성
	        .passwordParameter("j_password") 	// default : j_password
	        .permitAll();
 
        http.logout()
    		// 이것이 없으면 기본 폼이 나옴.
	        .logoutUrl("/logout") // default
	        .logoutSuccessUrl("/")	// 처음 페이지로
	        .permitAll();
        
        // ssl을 사용하지 않으면 true로 사용
        // 테스트 할때는 disable로 해야함. 우리가 localhost를 사용하기때문에 enabled이면 로그인 안됨.
        http.csrf().disable(); // csrf : 사이트간 요청 위조
        
		return http.build();  
    }
 
	@Bean
	protected UserDetailsService users() {
		UserDetails user = User.builder()
				.username("user")
				.password(passwordEncoder().encode("1234"))
				.roles("USER")	// ROLE_USER 에서 ROLE_는 자동으로 붙는다.
				.build();
		UserDetails admin = User.builder()
				.username("admin")
				.password(passwordEncoder().encode("1234"))
				.roles("USER", "ADMIN")	
				.build();
		//	메모리에 사용자 정보를 담는다.
		return new InMemoryUserDetailsManager(user, admin);
	}
    
    // passwordEncoder() 추가   //버전업 되면서 아래와 같이 수정됨.
    public PasswordEncoder passwordEncoder() {
      return PasswordEncoderFactories.createDelegatingPasswordEncoder();
    }
}
