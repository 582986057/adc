/**
 * 
 */
package com.bj.oa.hrm.dao.impl;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.bj.oa.common.impl.BaseDaoImpl;
import com.bj.oa.hrm.dao.DeptDao;

/**
 * @author 罗老师
 * @from 柠檬学院：http://www.bjlemon.com/
 * Version:1.0 
 * 2019年1月2日
 */
@Repository("deptDao")
public class DeptDaoImpl extends BaseDaoImpl implements DeptDao {

	/*
	 * (non-Javadoc)
	 * @see com.bj.oa.hrm.dao.DeptDao#findAllDepts()
	 */
	@Override
	public List<Map<String, String>> findAllDepts() {
		// TODO Auto-generated method stub
		String hql = "select new map(d.id as id,d.name as name) from Dept d ";		
		
		
		return this.find(hql);
	
	
	}

}
