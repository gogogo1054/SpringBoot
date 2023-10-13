package com.study.springboot;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.study.springboot.service.BuyAndLogService;
import com.study.springboot.service.IBuyTicketService;

@Controller
public class MyController
{
	
	@RequestMapping("/")
	public @ResponseBody String root() throws Exception {
		return "Security (1)";
	}
	
	@RequestMapping("/guest/welcome")
	public String welcome1() {
		
		return "guest/welcome1";
	}
	
	@RequestMapping("/member/welcome")
	public String welcome2() {
		
		return "member/welcome2";
	}
	
	@RequestMapping("/admin/welcome")
	public String welcome3() {
		
		return "admin/welcome3";
	}
	
}

