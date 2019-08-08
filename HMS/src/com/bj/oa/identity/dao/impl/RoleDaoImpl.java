package com.bj.oa.identity.dao.impl;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.bj.oa.common.impl.BaseDaoImpl;
import com.bj.oa.identity.bean.User;
import com.bj.oa.identity.dao.RoleDao;
import com.bj.oa.util.pager.PageModel;

@Repository("roleDao")
public class RoleDaoImpl extends BaseDaoImpl implements RoleDao{

	/*
	 * (non-Javadoc)
	 * 根据角色id获取该角色已经绑定了那些用户信息 
	 */
	@Override
	public List<User> findBindUserByRoleId(Long id, PageModel pageModel) {
		// TODO Auto-generated method stub
		
		StringBuffer hql = new StringBuffer();
		hql.append(" select u from User u where u.userId in ( ");
		
		//通过子查询获取该角色 已经绑定的用户的id信息
		hql.append(" select u.userId from User u inner join u.roles r where r.id = "+id+")");
		return this.findByPage(hql.toString(), pageModel, null);
	}
	//展示角色未绑定的用户信息
	@Override
	public List<User> findUnBindRoleUser(Long id, PageModel pageModel) {
		// TODO Auto-generated method stub
		StringBuffer hql = new StringBuffer();
		hql.append("select u from User u where u.userId not in ( ");
		//通过子查询获取该角色 已经绑定的用户的id信息
		hql.append(" select u.userId from User u inner join u.roles r where r.id = "+id+")");
		return this.findByPage(hql.toString(), pageModel, null);
	}

}
