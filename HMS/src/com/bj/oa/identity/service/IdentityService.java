package com.bj.oa.identity.service;

import java.util.List;

import org.springframework.ui.Model;

import com.bj.oa.identity.bean.Role;
import com.bj.oa.identity.bean.User;
import com.bj.oa.util.pager.PageModel;

public interface IdentityService {

	String ajaxLogin(String loginname, String password, String vCode);

	/**
	 * 
	 * @param user
	 * @param pageModel
	 * @return 用户多条件分页查询
	 */
	List<User> findUserByPage(User user, PageModel pageModel);

	/**
	 * 
	 * @return 异步加载所有的部门以及职位信息
	 */
	String ajaxLoadDeptAndJob();

	/**
	 * @param user 用户保存
	 */
	void addUser(User user);

	/*
	 * 角色分页查询
	 * 
	 */
	List<Role> findUserByPage(PageModel pageModel);

	/*
	 * (non-Javadoc) 更新个人信息
	 */
	void updateSelf(User user);

	/*
	 * (non-Javadoc) 根据角色id获取该角色已经绑定了那些用户信息
	 */
	List<User> findbindUserByRoleId(Long id, PageModel pageModel);

	// 解绑用户
	void unbindUser(String userIds, Long id);

	// 角色绑定用户
	void bindUser(Long id, String userIds);

	List<User> findUnBindRoleUser(Long id, PageModel pageModel);

}
