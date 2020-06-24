package com.waya.demo.dao;

import java.util.List;

import com.waya.demo.pojo.UserBean;

public interface UserDao {

	List<UserBean> getUserAll() throws Throwable;

	UserBean findByUsername(String username);

}
