package com.bj.oa.identity.action;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.bj.oa.identity.service.IdentityService;

@Controller
@RequestMapping("identity/module")
public class ModuleController {
	
	//@Resource  是按照byName的形式进行注入
	@Resource(name="identityService")
	private IdentityService identityService;
	
	//跳转至模块主页面
	@RequestMapping(value="/mgrModule.jspx")
	public String mgrModule() {
		
		
		
		
		return "/identity/module/mgrModule";
		
	}
}	
