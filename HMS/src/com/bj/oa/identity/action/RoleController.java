package com.bj.oa.identity.action;

import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.bj.oa.identity.bean.Role;
import com.bj.oa.identity.bean.User;
import com.bj.oa.identity.dao.RoleDao;
import com.bj.oa.identity.service.IdentityService;
import com.bj.oa.util.pager.PageModel;

@Controller
@RequestMapping("/identity/role")
public class RoleController {

	// @Resource 是按照byName的形式注入
	@Resource(name = "identityService")
	private IdentityService identityService;
	
	@Resource(name="roleDao")
	private RoleDao roleDao;

	// 角色分页查询
	@RequestMapping(value = "/selectRole.jspx")
	public String selectUser(PageModel pageModel,Model model) {
		
		List<Role> roles = identityService.findUserByPage(pageModel);
		//将查询到的数据存储在model中
		model.addAttribute("roles", roles);
		
		return "/identity/role/role";
	}
	
	//根据角色id获取该角色已经绑定了那些用户信息，将已经绑定的用户信息展示给用户看
	
	@RequestMapping(value="/selectRoleUser.jspx")
	public String selectRoleUser(PageModel pageModel,@RequestParam("id")Long id, @RequestParam("name")String name,Model model) {
		
		List<User> users = identityService.findbindUserByRoleId(id,pageModel);
		model.addAttribute("users", users);
		model.addAttribute("name", name);
		model.addAttribute("id", id);
		return "identity/role/bindUser/roleUser";
		
	}
	//解绑用户
	@RequestMapping(value="/unbindUser.jspx")
	public String unbindUser(@RequestParam("userIds")String userIds,@RequestParam("id")Long id,Model model) {
		try {
			identityService.unbindUser(userIds,id);
			model.addAttribute("message","解绑成功");
		} catch (Exception e) {
			// TODO: handle exception
			model.addAttribute("message", e.getMessage());
			}
		
		//解绑成功之后继续查询操作
		return "forward:/identity/role/selectRoleUser.jspx";
	}
	
	//展示角色未绑定的用户信息
	@RequestMapping(value="/showUnBindRoleUser.jspx")
	public String showUnBindRoleUser(@RequestParam("id")Long id,PageModel pageModel,Model model) {
		try {
			List<User> users = identityService.findUnBindRoleUser(id,pageModel);
			
			//将查询到的数据存储到model中
			model.addAttribute("users", users);
			model.addAttribute("id", id);
		} catch (Exception e) {
			// TODO: handle exception
			model.addAttribute("message", e.getMessage());
		}
		
		//解绑成功之后继续查询操作
		return "identity/role/bindUser/bindUser";
	}
	
	//角色绑定用户
	@RequestMapping(value="/bindUser.jspx")
	public String bindUser(@RequestParam("userIds")String userIds,@RequestParam("id")Long id,Model model) {
		try {
			identityService.bindUser(id,userIds);
			model.addAttribute("message", "绑定成功!");
		} catch (Exception e) {
			// TODO: handle exception
			model.addAttribute("message", e.getMessage());
		}
		
		//绑定成功后继续查询操作
		return "forward:/identity/role/showUnBindRoleUser.jspx";
		
	}
}
