package com.bj.oa.hrm.dao;

import java.util.List;
import java.util.Map;

import com.bj.oa.common.BaseDao;

public interface DeptDao extends BaseDao{

	/**
	 * 
	 * @return
	 * 获取所有的部门信息 ，只需要部门id 以及部门name
	 */
	List<Map<String, String>> findAllDepts();

}
