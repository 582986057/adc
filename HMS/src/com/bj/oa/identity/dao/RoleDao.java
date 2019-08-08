package com.bj.oa.identity.dao;

import java.util.List;

import com.bj.oa.common.BaseDao;
import com.bj.oa.identity.bean.User;
import com.bj.oa.util.pager.PageModel;

public interface RoleDao  extends BaseDao{

	/**
	 * 
	 * @param id
	 * @param pageModel
	 * @return
	 * 根据角色id获取该角色已经绑定了那些用户信息
	 */
	List<User> findBindUserByRoleId(Long id, PageModel pageModel);
	//展示角色未绑定的用户信息
	List<User> findUnBindRoleUser(Long id, PageModel pageModel);

}
