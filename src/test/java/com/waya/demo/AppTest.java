package com.waya.demo;

import java.sql.Connection;
import java.sql.DriverManager;


public class AppTest {

	public static void main(String args[]) {
		String driverName="com.mysql.jdbc.Driver";//这是要连接的数据库加载器
		String dbURL="jdbc:mysql://localhost:3306/demo-shiro?serverTimezone=Asia/Shanghai&useUnicode=true&characterEncoding=utf8&useSSL=false";//这是要连接的端口号以及数据库名称
		String userName="root";//用户名
		String userpwd="root";//用户密码
		try {
			Class.forName(driverName);
			System.out.println("加载驱动成功");
		}catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			System.out.println("加载驱动失败");
		}
		try {
			Connection dbConn=DriverManager.getConnection(dbURL,userName,userpwd);
			System.out.println("连接数据库成功");
		}catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			System.out.println("数据库连接失败");
		}
	}


}
