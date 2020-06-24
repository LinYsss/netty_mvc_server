package com.waya.demo.util.config;

import java.util.Arrays;
import java.util.List;

import com.waya.demo.util.mybatis.MyBatisConfig;

public class CustomConfig {
	
	private int port;
	private  List<MyBatisConfig> mybatis;
	private  String Scan;//包
	private String Dao;//mapper
	private Class<?>[] classList;
	public int getPort() {
		return port;
	}
	public void setPort(int port) {
		this.port = port;
	}
	public List<MyBatisConfig> getMybatis() {
		return mybatis;
	}
	public void setMybatis(List<MyBatisConfig> mybatis) {
		this.mybatis = mybatis;
	}
	public  String getScan() {
		return Scan;
	}
	public void setScan(String scan) {
		Scan = scan;
	}
	public String getDao() {
		return Dao;
	}
	public void setDao(String dao) {
		Dao = dao;
	}
	public Class<?>[] getClassList() {
		return classList;
	}
	public void setClassList(Class<?>[] classList) {
		this.classList = classList;
	}
	@Override
	public String toString() {
		return "CustomConfig [port=" + port + ", mybatis=" + mybatis + ", Scan=" + Scan + ", Dao=" + Dao
				+ ", classList=" + Arrays.toString(classList) + "]";
	}

}
