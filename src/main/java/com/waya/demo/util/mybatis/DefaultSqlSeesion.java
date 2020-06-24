package com.waya.demo.util.mybatis;

import org.apache.ibatis.session.SqlSession;

/**
 * <p>获取SqlSession
 * <p>通过{@link PondSqlFactory}获取SqlSession
 * @author <a href="http://www.wayakeji.net/"> Waya Co.,Ltd Hutrace</a>
 * @see PondSqlFactory
 * @since 1.8
 * @version 1.0
 * @time 2019年6月10日
 */
public class DefaultSqlSeesion {

	public static SqlSession get() {
		return get(MyBatisConfig.DEFAULT_ID);
	}
	
	public static SqlSession get(boolean autoCommit) {
		return get(MyBatisConfig.DEFAULT_ID, autoCommit);
	}
	
	public static SqlSession get(String sqlSessionFactoryId) {
		return PondSqlFactory.get(sqlSessionFactoryId).openSession();
	}
	
	public static SqlSession get(String sqlSessionFactoryId, boolean autoCommit) {
		return PondSqlFactory.get(sqlSessionFactoryId).openSession(autoCommit);
	}
	
}
