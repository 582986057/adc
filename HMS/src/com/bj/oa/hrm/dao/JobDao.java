package com.bj.oa.hrm.dao;

import java.util.List;
import java.util.Map;

import com.bj.oa.common.BaseDao;
import com.bj.oa.identity.bean.User;

public interface JobDao extends BaseDao{

	List<Map<String, String>> findAllDepts();



}
