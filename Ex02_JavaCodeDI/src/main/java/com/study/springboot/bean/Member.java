package com.study.springboot.bean;

public class Member
{
	private String name;
	private String nickname;
	private Printer printer;
	
	//	기본(디폴트)생성자,인수생성자, getter,setter,toString()
	//	메서드를 추가한다. ( 자동생성 메뉴 사용)
	public Member() {}

	/*
		클래스를 통해 객체를 생성할때는 항상 생성자 메서드 호출을 통해 기술한다.
		만약 개발자가 생성자를 정의하지 않으면 컴파일러에 의해 디폴트 생성자(인수,
		실행부가 없는 형태)가 만들어진다. 그런데 개발자가 생성자를 정의하면 디폴트
		생성자는 만들어지지 않으므로 필요에따라 추가해야한다.
	*/
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
