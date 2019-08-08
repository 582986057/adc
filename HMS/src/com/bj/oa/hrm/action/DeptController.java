package com.bj.oa.hrm.action;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.bj.oa.hrm.bean.Dept;
import com.bj.oa.hrm.service.DeptService;

/**
 *    
 *
 */
@Controller
@RequestMapping("/hrm/dept")
public class DeptController {
	
	//@Resource是按照byName的形式注入
	@Resource(name="deptService")
	private DeptService deptService;
	
	
	//获取所有的部门信息
	@ResponseBody
	@RequestMapping("/getAllDept.jspx")
	public String getAllDept() {
		List<Dept> depts = deptService.getAllDept();
		System.out.println("部门的个数："+depts.size());
		return null;
	}
}	
