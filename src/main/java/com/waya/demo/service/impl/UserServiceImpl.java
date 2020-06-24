package com.waya.demo.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import com.waya.demo.dao.UserDao;
import com.waya.demo.pojo.UserBean;
import com.waya.demo.service.UserService;
import com.waya.demo.util.annotation.Autowired;
import com.waya.demo.util.annotation.Service;

@Service
public class UserServiceImpl implements UserService{
	
	private @Autowired UserDao dao;

	@Override
	public List<UserBean> getUserAll() {
		List<UserBean> list = new ArrayList<UserBean>();
		try {
			list = dao.getUserAll();
		} catch (Throwable e) {
			e.printStackTrace();
		}
		return list;
	}

	 /**
     * 根据用户名查找用户
     * @param username
     * @return
     */
	public UserBean findByUsername(String username) {
		// TODO Auto-generated method stub
		return dao.findByUsername(username);
	}

	@Override
	public Set<String> findRoles(String username) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Set<String> findPermissions(String username) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public UserBean createUser(UserBean user) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public UserBean updateUser(UserBean user) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void deleteUser(Long userId) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void changePassword(Long userId, String newPassword) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public UserBean findOne(Long userId) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<UserBean> findAll() {
		// TODO Auto-generated method stub
		return null;
	}

}
