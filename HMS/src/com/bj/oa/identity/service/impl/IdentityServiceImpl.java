package com.bj.oa.identity.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import com.alibaba.fastjson.JSONObject;
import com.bj.oa.hrm.dao.DeptDao;
import com.bj.oa.hrm.dao.JobDao;
import com.bj.oa.identity.bean.Role;
import com.bj.oa.identity.bean.User;
import com.bj.oa.identity.dao.RoleDao;
import com.bj.oa.identity.dao.UserDao;
import com.bj.oa.identity.service.IdentityService;
import com.bj.oa.util.pager.PageModel;

import jdk.internal.dynalink.support.RuntimeContextLinkRequestImpl;
import netscape.javascript.JSObject;

@Service(value="identityService")
@Transactional
public class IdentityServiceImpl<JsonObject> implements IdentityService{
	
	@Resource(name="userDao")
	private UserDao userDao; 
	
	@Resource(name="deptDao")
	private DeptDao deptDao;

	@Resource(name="jobDao")
	private JobDao jobDao;
	
	@Resource(name="roleDao")
	private RoleDao roleDao;

	@Override
	public String ajaxLogin(String loginName, String password, String vCode) {
		
		//1.判断验证码是否正确
		//从session中取出验证码 和用户输入的验证码进行对比
		HttpSession session = ((ServletRequestAttributes)RequestContextHolder.getRequestAttributes()).getRequest().getSession();
		String sessionVcode = (String)session.getAttribute("ycode");
		
		if(!sessionVcode.equals(vCode)) {
			return "你输入的验证码不正确，请重新输入";
		}else {	
			//根据账号获取用户信息
			User user = userDao.get(User.class,loginName);
			if(user==null) {
				return "你输入的账号不正确";
			}else if(!user.getPassWord().equals(password)){
				return "你输入的密码不正确，请重新输入";
			}
			//如果上述信息验证都通过了，则将用户信息存放在session中
			session.setAttribute("session_user", user);
			return "";
		}
		
		
	}

	/*
	 * (non-Javadoc)
		用户多条件分页查询	 */
	@Override
	public List<User> findUserByPage(User user, PageModel pageModel) {
		// TODO Auto-generated method stub
		try {
			//定义StringBufffer对象，用于拼装hql语句
			StringBuffer hql = new StringBuffer();
			
			//创建集合用于封装查询的参数信息
			List<Object> params = new ArrayList<>();
			hql.append(" from User where 1=1 ");
			
			if(!StringUtils.isEmpty(user.getName())) {
				hql.append(" and name like ? ");
				params.add("%"+user.getName()+"%");
			}
			
			if(!StringUtils.isEmpty(user.getPhone())) {
				hql.append(" and phone like ? ");
				params.add("%"+user.getPhone()+"%");
			}
			if(!StringUtils.isEmpty(user.getDept())&&user.getDept().getId()!=null&&user.getDept().getId()!=0) {
				hql.append(" and dept.id = ? ");
				params.add(user.getDept().getId());
			}
			
			if(!StringUtils.isEmpty(user.getJob()) && !StringUtils.isEmpty(user.getJob().getCode())&&!user.getJob().getCode().equals("0")) {
				hql.append(" and job.code = ? ");
				params.add(user.getJob().getCode());
			}
			
			return userDao.findByPage(hql.toString(), pageModel, params);
			
		
		
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		
		
		
		return null;
	}

	
	/*
	 * (non-Javadoc)
	 *异步加载所有的部门以及职位信息
	 */
	@Override
	public String ajaxLoadDeptAndJob() {
		// 
		try {
			List<Map<String,String>> depts = deptDao.findAllDepts();

			List<Map<String,String>> jobs = jobDao.findAllDepts();
			
			JSONObject obj = new JSONObject();
			obj.put("depts", depts);
			obj.put("jobs", jobs);
			
			System.out.println("obj.toJSONString()==:"+obj.toJSONString());
			return obj.toJSONString();
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		return null;
	}

	/*
	 * (non-Javadoc)
	 * 用户保存
	 */
	@Override
	public void addUser(User user) {
		// TODO Auto-generated method stub
		try {
			//设置创建时间
			user.getCreateDate();
			HttpSession session = ((ServletRequestAttributes)RequestContextHolder.getRequestAttributes()).getRequest().getSession();
			User u = (User)session.getAttribute("session_user");
			//设置创建人
			user.setCreater(u);
			
			userDao.save(user); 
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			throw new RuntimeException("用户保存失败："+e.getMessage());
		}
	}

	// 角色分页查询
	@Override
	public List<Role> findUserByPage(PageModel pageModel) {
		// TODO Auto-generated method stub
		try {
			String hql=" from Role ";
			return roleDao.findByPage(hql, pageModel,null);
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			
		}
		return null;
		
	}

	/*
	 * (non-Javadoc)
	 * 更新个人信息
	 */
	
	@Override
	public void updateSelf(User user) {
		// TODO Auto-generated method stub
		try {
			//根据用户的账号获取用户信息 此时的u是持久化状态
			User u = userDao.get(User.class, user.getUserId());
			
			//设置修改个人以及修改时间
			u.setModifyDate(new Date());
			
			User modifierUser = (User)((ServletRequestAttributes)RequestContextHolder.getRequestAttributes()).getRequest().getSession().getAttribute("session_user");
			u.setModifier(modifierUser);
			u.setName(user.getName());
			u.setEmail(user.getEmail());
			u.setAnswer(user.getAnswer());
			u.setPhone(user.getPhone());
			u.setQqNum(user.getQqNum());
			u.setQuestion(user.getQuestion());
			u.setTel(user.getTel());
			
			//将session中的用户信息更新
			((ServletRequestAttributes)RequestContextHolder.getRequestAttributes()).getRequest().getSession().setAttribute("session_user", u);
			
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			throw new RuntimeException("更新失败："+e.getMessage());
		}
	} 
	
	/*
	 * (non-Javadoc)
	 *  根据角色id获取该角色已经绑定了那些用户信息
	 */

	@Override
	public List<User> findbindUserByRoleId(Long id, PageModel pageModel) {
		// TODO Auto-generated method stub
		try {
			List<User> users = roleDao.findBindUserByRoleId(id,pageModel);
			
			return users;
		} catch (Exception e) {
			// TODO: handle exception
			throw new RuntimeException("用户信息加载失败："+e.getMessage());
		}
		
	}
	
	/*
	 * (non-Javadoc)
	 * 解绑用户
	 */
	@Override
	public void unbindUser(String userIds, Long id) {
		// TODO Auto-generated method stub
		try {
			//根据角色id获取用户角色信息
			Role role = roleDao.get(Role.class, id);
			
			//获取该角色已经绑定的用户信息
			Set<User> users = role.getUsers();
			//H获取需要帮定的用户id\账号
			String[] uIds = userIds.split(",");
			
			for(String uId:uIds) {
				//根据用户的账号获取用户信息
				User user = userDao.get(User.class, uId);
				//将用户信息从已关联|绑定的用户集合中移除
				users.remove(user);
			}
			
		} catch (Exception e) {
			// TODO: handle exception
			throw new RuntimeException("用户信息加载失败："+e.getMessage());
		}
	}
	/* (non-Javadoc)
	 * 根据角色id获取该角色已经绑定了哪些用户信息
	 */
	@Override
	public List<User> findUnBindRoleUser(Long id, PageModel pageModel) {
		// TODO Auto-generated method stub
		try {
			List<User> users = roleDao.findUnBindRoleUser(id,pageModel);
			
		return users;
		
		} catch (Exception e) {
			// TODO: handle exception
			throw new RuntimeException("用户信息加载失败："+e.getMessage());
		}
	}
	//角色绑定用户
	@Override
	public void bindUser(Long id, String userIds) {
		// TODO Auto-generated method stub
		try {
			//根据角色id获取角色信息
			Role role = roleDao.get(Role.class, id);
			
			//获取该角色已经绑定的用户信息
			Set<User> users = role.getUsers();
			//获取需要解绑的用户的id|账号
			String[] uIds = userIds.split(",");
			for(String uId:uIds) {
				//根据用户的账号获取用户信息
				User user = userDao.get(User.class, uId);
				//将用户信息与角色建立关联关系
				users.add(user);
				
			}
		} catch (Exception e) {
			// TODO: handle exception
			throw new RuntimeException("用户绑定失败："+e.getMessage());
		}
	
	}
	

	
}
