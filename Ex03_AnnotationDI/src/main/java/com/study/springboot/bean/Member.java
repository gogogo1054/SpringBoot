package com.study.springboot.bean;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

//	다음에 나오는 클래스인 Member클래스를 빈으로 등록하겠다는 의미
@Component
public class Member
{
	//	멤버변수에 "홍길동"이라는 초기값을 부여한다.
	//	이때 setter를 통해 값이 설정된다.
	@Value("홍길동")
	private String name;
	@Value("도사")
	private String nickname;
	
	/*
		객체타입의 멤버변수는 자동으로 주입받는다. 이때 @Qualifier어노테이션이
		있으면 이름까지 지정하여 주입받게 된다. 만약 부착하지 않을 경우 해당
		타입으로 생성된 빈이 있는지 찾은 후 주입받는다.
	*/
	@Autowired
	@Qualifier("printerA")
	private Printer printer;
	
	public Member() {}

	public Member(String name, String nickname, Printer printer)
	{	
		//	멤버변수(문자열, 정수, 정의객체타입)
		super();
		this.name = name;
		this.nickname = nickname;
		this.printer = printer;
	}

	public void setName(String name)
	{
		this.name = name;
	}

	public void setNickname(String nickname)
	{
		this.nickname = nickname;
	}

	public void setPrinter(Printer printer)
	{
		this.printer = printer;
	}
	
	public void print() {
		printer.print("Hello " + name + " : " + nickname);
	}
}
