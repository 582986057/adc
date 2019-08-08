package com.bj.oa.identity.action;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class MainController {

	//跳转至首页
	@RequestMapping(value="/main.jspx")
	public  String main() {
	
		//    /WEB-INF/jsp/main.jsp
		return "main";
		
	}
	
}
