package com.waya.demo.util.mybatis;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;

/**
 * <p>SqlSession工厂池
 * @author <a href="http://www.wayakeji.net/"> Waya Co.,Ltd Hutrace</a>
 * @see SqlSessionFactory
 * @see SqlSession
 * @since 1.8
 * @version 1.0
 * @time 2019年9月24日
 */
public class PondSqlFactory {

    static Map<String, SqlSessionFactory> sqlFactoryMap;
	static Map<Thread, SqlSession> sqlSessions;
	
	static {
		sqlFactoryMap = new ConcurrentHashMap<>();
		sqlSessions = new HashMap<>();
	}
	
	/**
	 * <p>添加SqlSessionFactory
	 * @param key
	 * @param value
	 */
	public static void put(String key, SqlSessionFactory value) {
		sqlFactoryMap.put(key, value);
	}
	
	/**
	 * <p>获取SqlSessionFactory
	 * @param key
	 * @return
	 */
	public static SqlSessionFactory get(String key) {
		return sqlFactoryMap.get(key);
	}
	
	/**
	 * <p>获取SqlSession
	 * @param key
	 * @return
	 */
	public static SqlSession addSqlSessions(String sqlFactoryId) {
		SqlSession session = sqlFactoryMap.get(sqlFactoryId).openSession();
		sqlSessions.put(Thread.currentThread(), session);
		return session;
	}
	
	/**
	 * <p>获取SqlSession
	 * @param key
	 * @return
	 */
	public static SqlSession getSqlSessions() {
		return sqlSessions.get(Thread.currentThread());
	}
	
	/**
	 * <p>删除SqlSession
	 * @param key
	 * @return
	 */
	public static void removeSqlSessions() {
		sqlSessions.remove(Thread.currentThread());
	}

	public static Map<String, SqlSessionFactory> getSqlFactoryMap() {
		return sqlFactoryMap;
	}

	public static void setSqlFactoryMap(Map<String, SqlSessionFactory> sqlFactoryMap) {
		PondSqlFactory.sqlFactoryMap = sqlFactoryMap;
	}

	public static void setSqlSessions(Map<Thread, SqlSession> sqlSessions) {
		PondSqlFactory.sqlSessions = sqlSessions;
	}
	
}