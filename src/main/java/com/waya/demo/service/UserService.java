package com.waya.demo.service;

import java.util.List;
import java.util.Set;

import com.waya.demo.pojo.UserBean;

public interface UserService {
	
	public List<UserBean> getUserAll() throws Throwable;

	  /**
     * 创建用户
     * @param user
     */
    public UserBean createUser(UserBean user);

    public UserBean updateUser(UserBean user);

    public void deleteUser(Long userId);

    /**
     * 修改密码
     * @param userId
     * @param newPassword
     */
    public void changePassword(Long userId, String newPassword);


    UserBean findOne(Long userId);

    List<UserBean> findAll();

    /**
     * 根据用户名查找用户
     * @param username
     * @return
     */
    public UserBean findByUsername(String username);

    /**
     * 根据用户名查找其角色
     * @param username
     * @return
     */
    public Set<String> findRoles(String username);

    /**
     * 根据用户名查找其权限
     * @param username
     * @return
     */
    public Set<String> findPermissions(String username);
	
	

}
