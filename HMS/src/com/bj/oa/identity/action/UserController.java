package com.bj.oa.identity.action;

import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.bj.oa.identity.bean.User;
import com.bj.oa.identity.service.IdentityService;
import com.bj.oa.util.pager.PageModel;

@Controller
@RequestMapping("/identity/user")
public class UserController {

	// @Resource 是按照byName的形式注入
	@Resource(name = "identityService")
	private IdentityService identityService;

	// 用户异步登陆
	@ResponseBody
	@RequestMapping(value = "/ajaxLogin.jspx", produces = { "appliction/text;charset=utf-8" })
	public String ajaxLogin(@RequestParam("loginName") String loginName, @RequestParam("password") String password,
			@RequestParam("vCode") String vCode) {
		System.out.println("loginName：" + loginName + " password: " + password + " vCode: " + vCode);
		// 根据账号及密码进行异步登陆
		String message = identityService.ajaxLogin(loginName, password, vCode);
		return message;
	}

	// 用户多条件分页查询
	@RequestMapping(value = "/selectUser.jspx")
	public String selectUser(User user, PageModel pageModel, Model model) {

		List<User> users = identityService.findUserByPage(user, pageModel);
		// 将查询到的数据存储在model中
		model.addAttribute("users", users);

		return "/identity/user/user";
	}

	// 展示添加用户页面
	@RequestMapping(value = "/showAddUser.jspx")
	public String showAddUser() {

		return "/identity/user/addUser";
	}

	// 用户异步登陆
	@ResponseBody
	@RequestMapping(value = "/ajaxLoadDeptAndJob.jspx", produces = { "appliction/json;charset=utf-8" })
	public String ajaxLoadDeptAndJob() {
		String result = "{}";
		try {
			result = identityService.ajaxLoadDeptAndJob();
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		return result;
	}

	// 用户退出
	@RequestMapping(value = "/logout.jspx")
	public String logout(HttpSession session) {
		// 将session中的用户信息清楚
		session.removeAttribute("session_user");
		// 跳转至登陆页面
		return "login";
	}

	// 用户保存
	@RequestMapping(value = "/addUser.jspx")
	public String addUser(User user, Model model) {
		try {
			identityService.addUser(user);
			model.addAttribute("message", "添加成功");
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			model.addAttribute("message", "添加失败");
		}

		// 添加成功， 继续跳转至添加用户页面
		return "/identity/user/addUser";

	}

	// 展示更新个人信息页面
	@RequestMapping(value = "/home.jspx")
	public String home() {
		

		// 展示个人信息页面
		return "home";
	}
	
	
	// 更新个人信息页面
	@RequestMapping(value = "updateSelf.jspx")
	public String updateSelf(Model model,User user) {
		try {
			
			identityService.updateSelf(user);
			model.addAttribute("message","更新成功");
		} catch (Exception e) {
			// TODO: handle exception
			model.addAttribute("message", e.getMessage());
		}

		// 更新个人信息页面
		return "home";
	}
}
