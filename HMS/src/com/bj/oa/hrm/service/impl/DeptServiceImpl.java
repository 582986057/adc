package com.bj.oa.hrm.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.bj.oa.hrm.bean.Dept;
import com.bj.oa.hrm.dao.DeptDao;
import com.bj.oa.hrm.service.DeptService;

@Service(value = "deptService")
@Transactional
public class DeptServiceImpl implements DeptService{

	@Resource(name="deptDao")
	private DeptDao deptDao;
	@Override 
	public List<Dept> getAllDept() {
		// TODO Auto-generated method stub
		List<Dept> depts = deptDao.find(Dept.class);
		
		return depts;
	}

}
