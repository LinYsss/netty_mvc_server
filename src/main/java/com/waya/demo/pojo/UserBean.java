package com.waya.demo.pojo;

import java.util.Date;
import java.util.List;

public class UserBean {
	
	private Long id; //编号
    private String loginName; //用户名
    private String loginPwd; //密码
    private String email;
    private String phone;
    private String nickName;//昵称
    private String realName;//真实姓名
    private String pwdSalt;//加密密码的盐
    private String tokenSecret;//用户的密钥，此字段只能在服务器使用，千万不能泄露在前台
    private String organization;//组织
    private Date createTime;//createTime
    private Date lastLoginTime;//账号最后登陆时间
    private String lastLoginIp;//账号最后登陆ip
    private String lastLoginAddress;//账号最后登陆地址
    private Integer disabled;//该用户是否被禁用，0--禁用，1--启用
    private String belong;
    private String salt; //加密密码的盐
    private List<Long> roleIds; //拥有的角色列表
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getLoginName() {
		return loginName;
	}
	public void setLoginName(String loginName) {
		this.loginName = loginName;
	}
	public String getLoginPwd() {
		return loginPwd;
	}
	public void setLoginPwd(String loginPwd) {
		this.loginPwd = loginPwd;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
	public String getNickName() {
		return nickName;
	}
	public void setNickName(String nickName) {
		this.nickName = nickName;
	}
	public String getRealName() {
		return realName;
	}
	public void setRealName(String realName) {
		this.realName = realName;
	}
	public String getPwdSalt() {
		return pwdSalt;
	}
	public void setPwdSalt(String pwdSalt) {
		this.pwdSalt = pwdSalt;
	}
	public String getTokenSecret() {
		return tokenSecret;
	}
	public void setTokenSecret(String tokenSecret) {
		this.tokenSecret = tokenSecret;
	}
	public String getOrganization() {
		return organization;
	}
	public void setOrganization(String organization) {
		this.organization = organization;
	}
	public Date getCreateTime() {
		return createTime;
	}
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	public Date getLastLoginTime() {
		return lastLoginTime;
	}
	public void setLastLoginTime(Date lastLoginTime) {
		this.lastLoginTime = lastLoginTime;
	}
	public String getLastLoginIp() {
		return lastLoginIp;
	}
	public void setLastLoginIp(String lastLoginIp) {
		this.lastLoginIp = lastLoginIp;
	}
	public String getLastLoginAddress() {
		return lastLoginAddress;
	}
	public void setLastLoginAddress(String lastLoginAddress) {
		this.lastLoginAddress = lastLoginAddress;
	}
	public Integer getDisabled() {
		return disabled;
	}
	public void setDisabled(Integer disabled) {
		this.disabled = disabled;
	}
	public String getBelong() {
		return belong;
	}
	public void setBelong(String belong) {
		this.belong = belong;
	}
	public String getSalt() {
		return salt;
	}
	public void setSalt(String salt) {
		this.salt = salt;
	}
	public List<Long> getRoleIds() {
		return roleIds;
	}
	public void setRoleIds(List<Long> roleIds) {
		this.roleIds = roleIds;
	}
	@Override
	public String toString() {
		return "UserBean [id=" + id + ", loginName=" + loginName + ", loginPwd=" + loginPwd + ", email=" + email
				+ ", phone=" + phone + ", nickName=" + nickName + ", realName=" + realName + ", pwdSalt=" + pwdSalt
				+ ", tokenSecret=" + tokenSecret + ", organization=" + organization + ", createTime=" + createTime
				+ ", lastLoginTime=" + lastLoginTime + ", lastLoginIp=" + lastLoginIp + ", lastLoginAddress="
				+ lastLoginAddress + ", disabled=" + disabled + ", belong=" + belong + ", salt=" + salt + ", roleIds="
				+ roleIds + "]";
	}
	//加盐
	public String getCredentialsSalt() {
        return loginName + salt;
    }
	
}
