package com.waya.demo.util.mybatis;

import org.apache.ibatis.session.SqlSession;

public class CommonSqlSession {
	
	protected SqlSession sqlSession() {
		return PondSqlFactory.getSqlSessions();
	}
	
}