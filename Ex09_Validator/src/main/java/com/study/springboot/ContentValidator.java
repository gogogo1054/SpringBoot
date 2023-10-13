package com.study.springboot;

import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

//	폼값 검증을 위해 Validator 인터페이스를 구현한다.
public class ContentValidator implements Validator
{
	/*
		폼값 검증을 위해 DTO객체를 전달하게되면 해당 메서드가 먼저 호출되어
		전달된 DTO가 검증에 필요한 조건을 가진 커맨드객체인지 검사한다.
		True를 반환하면 Validate()메서드가 자동으로 호출되고, 만약 False를
		반환하면 검증이 이루어지지 않는다.
	*/
	@Override
	public boolean supports(Class<?> arg0) {
		//	검증할 객체의 클래스 타입 정보
		return ContentDto.class.isAssignableFrom(arg0);	
	}
	
	/*
		폼값 검증을 진행하기 위한 메서드로 여기서는 if문을 통한 검증 
		방법을 사용하고 있다.
	*/
	@Override	//	이 방법으로 검증
	public void validate(Object obj, Errors errors) {
		ContentDto dto = (ContentDto)obj;
		
		//	제목을 검증
		String sWriter = dto.getWriter();
		//	제목을 입력한 값이 null 혹은 빈 값인지 확인
		if(sWriter == null || sWriter.trim().isEmpty()) {
			System.out.println("Writer is null or empty");
			/*
				폼값 검증에 오류가 있는 경우 처리 형식
				error객체.rejectValue(폼의 name 속성, 에러객체명, 디폴트 메세지)
			*/
			errors.rejectValue("writer", "trouble");
		}
		
		//	내용을 검증 - 위와 동일
		String sContent = dto.getContent();
		if(sContent == null || sContent.trim().isEmpty()) {
			System.out.println("Content is null or empty");
			errors.rejectValue("Content", "trouble");
		}
	}
}
