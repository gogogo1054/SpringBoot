package com.study.springboot.auth;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.factory.PasswordEncoderFactories; 
import org.springframework.security.crypto.password.PasswordEncoder;  
import org.springframework.security.web.SecurityFilterChain; 

@Configuration
@EnableWebSecurity
public class WebSecurityConfig  {  

	@Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {  
        http.authorizeRequests()
    		.antMatchers("/").permitAll()
            .antMatchers("/css/**", "/js/**", "/img/**").permitAll()
            .antMatchers("/guest/**").permitAll()
            .antMatchers("/member/**").hasAnyRole("USER", "ADMIN")
            .antMatchers("/admin/**").hasRole("ADMIN")
            .anyRequest().authenticated();
 
        http.formLogin()
        	// 이것이 없으면 기본 폼이 나옴.
			.loginPage("/loginForm") 			// default : /login
		    .loginProcessingUrl("/j_spring_security_check")	// 바꾸면 안됨.
	        .failureUrl("/loginError") 			// default : /login?error
	        //.defaultSuccessUrl("/")
	        .usernameParameter("j_username")	// default : j_username
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
 
    @Autowired
    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
        auth.inMemoryAuthentication()
        	.withUser("user").password(passwordEncoder().encode("1234")).roles("USER")
        	.and()
        	.withUser("admin").password(passwordEncoder().encode("1234")).roles("ADMIN");
        	// ROLE_ADMIN 에서 ROLE_는 자동으로 붙는다.
    }
    
    // passwordEncoder() 추가   //버전업 되면서 아래와 같이 수정됨.
    public PasswordEncoder passwordEncoder() {
      return PasswordEncoderFactories.createDelegatingPasswordEncoder();
    }
}
