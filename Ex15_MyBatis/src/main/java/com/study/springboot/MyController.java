package com.study.springboot;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.study.springboot.jdbc.IMyUserDao;

import jakarta.servlet.http.HttpServletRequest;

@Controller
public class MyController
{
	//	MyBatis 사용을 위한 자동 주입
	@Autowired
	private IMyUserDao userDao;	//	인터페이스 객체 사용
	
	@RequestMapping("/")
	public @ResponseBody String root() throws Exception {
		return "MyBatis 사용하기";
	}
	
	//	게시물 목록
	@RequestMapping("/user")	
	public String userlistPage(Model model) {
		//	DAO(Mapper)의 getUser()메서드를 호출해서 게시물 목록을 출력한다.
		model.addAttribute("users", userDao.getUser());
		return "userlist";
	}
}
