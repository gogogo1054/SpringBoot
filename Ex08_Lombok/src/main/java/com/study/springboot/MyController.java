package com.study.springboot;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class MyController
{
	@RequestMapping("/")
	public @ResponseBody String root() throws Exception {
		return "Lombok";
	}
	
	@RequestMapping("/test1")	//	localhost:8081/test1
	public String test1(Member member, Model model) {
			
		//	파라미터와 일치하는 빈을 만들어서 사용할 수 있다.
		//	View 페이지에서 model을 사용하지 않고 member를 사용한다.
		System.out.println(member);
		return "test1";			
	}
}
