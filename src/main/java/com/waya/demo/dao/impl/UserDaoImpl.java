package com.waya.demo.dao.impl;

import java.util.List;

import com.waya.demo.dao.UserDao;
import com.waya.demo.pojo.UserBean;
import com.waya.demo.util.annotation.Autowired;
import com.waya.demo.util.annotation.Repository;
import com.waya.demo.util.mybatis.CommonDao;

@Repository
public class UserDaoImpl implements UserDao{
	
	private @Autowired CommonDao dao;
	
	private String mapper(String key) {
		return "LinY.UserMapper." + key;
	}

	
	public List<UserBean> getUserAll() throws Throwable {
		return dao.selectList(mapper("getUserAll"));
	}

	
	public UserBean findByUsername(String username) {
		
		return null;
	}

}
