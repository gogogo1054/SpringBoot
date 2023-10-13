package com.study.springboot;

import lombok.Data;

//	컨트롤러로 전송되는 폼값을 한꺼번에 저장하기위한 커맨드객체로 사용
@Data
public class ContentDto
{
	private int id;
	private String writer;
	private String content;
}
