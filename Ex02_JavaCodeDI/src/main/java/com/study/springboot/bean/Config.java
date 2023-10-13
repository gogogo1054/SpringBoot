package com.study.springboot.bean;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/*
	Spring Lagacy에서 XML설정파일로 만들었던 것을 Boot에서는 XML을 사용하지
	않으므로 아래와 같이 Java파일로 만든다. 이때 설정파일의 역할을 부여하기
	위해 @Configuration 어노테이션을 부착한다.
*/
@Configuration
public class Config
{	
	//	빈(bean) : Spring이 IOC방식으로 관리하는 객체
	//	빈 팩토리(beanFactory) : 스프링의 IOC를 담당하는 핵심 컨테이너
	//	어플리케이션 컨텍스트(ApplicationContext) : 빈 팩토리를 확장한 IOC컨테이너
	
	/*
		@Bean 어노테이션을 통해 자바빈을 생성한다. 이떄 참조변수명은 별도의
		설정이 없으므로 메서드명인 member1로 생성된다.
	*/
	@Bean
	public Member member1() {
		//	Setter Injection (Setter 메서드를 이용한 의존성 주입)
		//	기본 생성자를 통해 객체생성 후 setter를 통해 초기화한다.
		Member member1 = new Member();
		member1.setName("홍길동");
		member1.setNickname("도사");
		member1.setPrinter(new PrinterA());
		
		return member1;
	}
	
	/*
		위와 동일하게 자바빈을 생성하되 name속성을 부여했으므로
		해당명인 hello로 생성된다.
	*/
	@Bean(name="hello")
	public Member member2() {
		//	Constructor Injection (생성자를 이용한 의존성 주입)
		//	인수생성자를 통해 초기화한다.
		return new Member("전우치", "도사", new PrinterA());
	}
	
	@Bean
	public PrinterA printerA() {
		return new PrinterA();
	}
	
	@Bean
	public PrinterB printerB() {
		return new PrinterB();
	}
	
}
