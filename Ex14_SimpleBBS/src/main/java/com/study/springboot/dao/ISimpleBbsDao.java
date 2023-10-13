package com.study.springboot.dao;

import java.util.List;

import com.study.springboot.dto.SimpleBbsDto;

//	서비스 인터페이스 : 컨트롤러와 모델(DAO) 사이에서 매개역할을 한다.
//	나중에 사용하기 때문에 여기서 먼저 사용
public interface ISimpleBbsDao
{
	/*
		추상메서드 정의시 매개변수는 일과적으로 DTO객체를 사용한다.
		커맨드 객체를 사용하면 인수를 한꺼번에 받아 전달할 수 있고,
		인수의 개수에 변경이 있더라도 DTO객체만 수정하면 되므로
		프로그램 작성이 쉬워진다.
	*/
	public List<SimpleBbsDto> listDao();
	public SimpleBbsDto viewDao(String id);
	public int writeDao(String writer, String title, String content);
	public int deleteDao(String id);
}
