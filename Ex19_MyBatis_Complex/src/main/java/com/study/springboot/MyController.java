package com.study.springboot;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.study.springboot.jdbc.IDao;


//	껍데기만 있어 알맹이만 넣어주는 작업만 하면된다. 코딩이 쉬워짐
@Controller
public class MyController
{
	@Autowired
	IDao userDao;	
	
	@RequestMapping("/")
	public @ResponseBody String root() throws Exception {
		return "MyBatis : 복잡한(join) 쿼리 결과 출력하기";
	}
	
	@RequestMapping("/emp")
	public String userlistPage(Model model) {
		model.addAttribute("emp", userDao.getEmployee());
		
		return "/emplist";
	}
}

