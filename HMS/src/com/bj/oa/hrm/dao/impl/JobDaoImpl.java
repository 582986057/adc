package com.bj.oa.hrm.dao.impl;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.bj.oa.common.impl.BaseDaoImpl;
import com.bj.oa.hrm.dao.JobDao;

@Repository("jobDao")
public class JobDaoImpl extends BaseDaoImpl implements JobDao {

	@Override
	public List<Map<String, String>> findAllDepts() {
		// TODO Auto-generated method stub
		String hql = " select new map(j.code as code, j.name as name) from Job j";
		return this.find(hql);
	}


}
