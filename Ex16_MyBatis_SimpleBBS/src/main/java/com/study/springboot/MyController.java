package com.study.springboot;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.study.springboot.dao.ISimpleBbsDao;

import jakarta.servlet.http.HttpServletRequest;

//	껍데기만 있어 알맹이만 넣어주는 작업만 하면된다. 코딩이 쉬워짐
@Controller
public class MyController
{
	//	MyBatis 사용을 위해 자동 주입
	@Autowired
	ISimpleBbsDao dao;	//	약한 결합으로 주입받는다.
	
	@RequestMapping("/")
	public String root() throws Exception {
//		MyBatis : SimpleBBS
		return "redirect:list";
	}
	
	//	게시물 목록
	@RequestMapping("/list")	//	프론트 컨트롤러
	public String userlistPage(Model model) {
		//	dao(Mapper)의 listDao() 메서드를 호출해서 게시물 목록을 인출
		model.addAttribute("list", dao.listDao());
		return "/list";
	}
	
	//	게시물 확인
	@RequestMapping("/view")
	public String view(HttpServletRequest request, Model model) {
		String sId = request.getParameter("id");
		
		model.addAttribute("dto", dao.viewDao(sId));
		return "/view";
	}
	
	//	게시물 등록
	@RequestMapping("/writeForm")
	public String writeForm() {
		return "/writeForm";
	}
	
	@RequestMapping("/write")
	public String write(HttpServletRequest request, Model model) {
		dao.writeDao(request.getParameter("writer"),
					 request.getParameter("title"),
					 request.getParameter("content"));
		return "redirect:list";
	}
	
	@RequestMapping("/delete")
	public String delete(HttpServletRequest request, Model model) {
		dao.deleteDao(request.getParameter("id"));
		return "redirect:list";
	}
	
}

