package com.study.springboot.auth;

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
import jakarta.servlet.DispatcherType;

@Configuration
public class WebSecurityConfig
{
	@Bean
	protected SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
		http.csrf((csrf) -> csrf.disable())
			.cors((cors) -> cors.disable())
			.authorizeHttpRequests(request -> request
				/*
					스프링부트 3.0부터 적용된 스프링 시큐리티 6.0에서
					forward방식 페이지 이동에도 default 인증이 걸리도록
					변경되어서 JSP나 타임리프 등 컨트롤러에서 화일 파일명을
					리턴해 ViewResolver가 작동해 페이지 이동을 하는 경우
					이처럼 설정을 해줘야한다.
				*/
				// permitAll() : 권한 없이 접근 권한 부여
				.dispatcherTypeMatchers(DispatcherType.FORWARD).permitAll()
				// / : 모든 요청명에 대해 권한 없이 접근할 수 있다.
				.requestMatchers("/").permitAll()
				// 정적 리소스(static하위)에는 권한 없이 접근할 수 있다.
				.requestMatchers("/css/*", "/js/**", "/img/**").permitAll()
				// 권한 없이 접근할 수 있다.
				.requestMatchers("/guest/**").permitAll()	//	모두 허용
				//	USER, ADMIN 권한 중 하나가 있어야 접근할 수 있다.
				.requestMatchers("/member/**").hasAnyRole("USER", "ADMIN")
				//	ADMIN 권한만 접근할 수 있다.
				.requestMatchers("/admin/**").hasRole("ADMIN")
				.anyRequest().authenticated()	//	어떠한 요청이라도 인증필요
			);
		// 로그인 페이지 설정(시큐리티의 디폴트 페이지를 사용한다.)
		http.formLogin((formLogin) ->
						formLogin.permitAll());	//	기본 로그인 페이지
		http.logout((logout) -> logout.permitAll());	//	로그아웃 기본설정
				
		return http.build();
	}
	
	@Bean
	//	users() 메서드는 빠른 테스트를 위해 등록이 간단한 inMemory방식의
	//	유저 등록
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
	
	/*
		시큐리티 5에서는 패스워드를 반드시 암호화해서 저장해야 한다.
		encode()를 호출할 때마다 패스워드가 변경된다.
	*/
	//	passwordEncoder()
	//	버전업 되면서 아래와 같이 수정됨.
	//	내부적으로 접두어를 붙일 수 있도록 직접 패스워드인코더를 지정하지 않는다.
	public PasswordEncoder passwordEncoder() {
		return PasswordEncoderFactories.createDelegatingPasswordEncoder();
	}
}
