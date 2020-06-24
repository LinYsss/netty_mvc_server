package com.waya.demo.util.mybatis;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>公共DAO
 * @author <a href="http://www.wayakeji.net/"> Waya Co.,Ltd tracy</a>
 * @see CommonSqlSession
 * @since 1.8
 * @version 1.0
 * @time 2019年4月17日
 */
public class CommonDao extends CommonSqlSession {

	/**
	 * <p>查询一条数据，不传入查询语句条件
	 * @param statement Mapper namespace.id
	 * @return 泛型，与Mapper中result类型匹配
	 * @throws Throwable
	 */
	public <T> T selectOne(String statement) throws Throwable {
		return selectOne(statement, null);
	}
	
	/**
	 * <p>查询一条数据，需要传入查询语句条件，参数可以传入null
	 * @param statement Mapper namespace.id
	 * @param parameter 参数，与Mapper中parameter类型匹配
	 * @return 泛型，与Mapper中result相匹配
	 * @throws Throwable
	 */
	public <T> T selectOne(String statement, Object parameter) throws Throwable {
		return sqlSession().selectOne(statement, parameter);
	}
	
	/**
	 * <p>查询多条数据，不传入查询语句条件
	 * @param statement Mapper namespace.id
	 * @return 泛型，与Mapper中result类型匹配的{@link List}集合
	 * @throws Throwable
	 */
	public <T> List<T> selectList(String statement) throws Throwable {
		return selectList(statement, null);
	}
	
	/**
	 * <p>查询多条数据，需要传入查询语句条件，参数可以传入null
	 * @param statement Mapper namespace.id
	 * @param parameter 参数，与Mapper中parameter类型匹配
	 * @return 泛型，与Mapper中result类型匹配的{@link List}集合
	 * @throws Throwable
	 */
	public <T> List<T> selectList(String statement, Object parameter) throws Throwable {
		return sqlSession().selectList(statement, parameter);
	}
	
	/**
	 * <p>查询一个字段，且只有一条数据的数字，不传入查询语句条件
	 * @param statement Mapper namespace.id
	 * @return int数字，Mapper中result类型需要为int或者_int
	 * @throws Throwable
	 */
	public int selectInt(String statement) throws Throwable {
		return selectInt(statement, null);
	}
	
	/**
	 * <p>查询一个字段，且只有一条数据的数字，需要传入查询语句条件，参数可以传入null
	 * @param statement Mapper namespace.id
	 * @param parameter 参数，与Mapper中parameter类型匹配
	 * @return int数字，Mapper中result类型需要为int或者_int
	 * @throws Throwable
	 */
	public int selectInt(String statement, Object parameter) throws Throwable {
		Object obj = selectOne(statement, parameter);
		if(obj == null) {
			return 0;
		}
		return Integer.parseInt(obj.toString());
	}
	
	/**
	 * <p>修改数据，不传入sql语句条件
	 * @param statement Mapper namespace.id
	 * @return 受影响的数据条数，可以根据此返回值判断是否完成修改数据
	 * @throws Throwable
	 */
	public int update(String statement) throws Throwable {
		return update(statement, null);
	}
	
	/**
	 * <p>修改数据，需要传入sql语句条件，参数可以传入null，可以根据参数批量修改数据
	 * @param statement Mapper namespace.id
	 * @param parameter 参数，与Mapper中parameter类型匹配
	 * @return 受影响的数据条数，可以根据此返回值判断是否完成修改数据
	 * @throws Throwable
	 */
	public int update(String statement, Object parameter) throws Throwable {
		return sqlSession().update(statement, parameter);
	}
	
	/**
	 * <p>删除数据，不传入sql语句条件
	 * @param statement Mapper namespace.id
	 * @return 受影响的数据条数，可以根据此返回值判断是否完成删除数据
	 * @throws Throwable
	 */
	public int delete(String statement) throws Throwable {
		return delete(statement, null);
	}
	
	/**
	 * <p>删除数据，需要传入sql语句条件，参数可以传入null，可以根据参数批量删除数据
	 * @param statement Mapper namespace.id
	 * @param parameter 参数，与Mapper中parameter类型匹配
	 * @return 受影响的数据条数，可以根据此返回值判断是否完成删除数据
	 * @throws Throwable
	 */
	public int delete(String statement, Object parameter) throws Throwable {
		return sqlSession().delete(statement, parameter);
	}
	
	/**
	 * <p>新增（添加、插入）数据，需要传入sql语句条件，参数可以传入null，可以根据参数批量删除数据
	 * @param statement Mapper namespace.id
	 * @return 受影响的数据条数，可以根据此返回值判断是否完成添加数据
	 * @throws Throwable
	 */
	public int insert(String statement) throws Throwable {
		return insert(statement, null);
	}
	
	/**
	 * <p>新增（添加、插入）数据，不传入sql语句条件
	 * @param statement Mapper namespace.id
	 * @param parameter 参数，与Mapper中parameter类型匹配
	 * @return 受影响的数据条数，可以根据此返回值判断是否完成添加数据
	 * @throws Throwable
	 */
	public int insert(String statement, Object parameter) throws Throwable {
		return sqlSession().insert(statement, parameter);
	}
	
	public <T> Map<String, Object> queryPage(String statement, Object parameter) throws Throwable {
		int count = selectInt(statement + "Count", parameter);
		List<T> list = selectList(statement, parameter);
		Map<String, Object> map = new HashMap<>();
		map.put("count", count);
		map.put("list", list);
		return map;
	}
	
	public <T> Map<String, Object> queryPage(String statement, QueryPaging parameter) throws Throwable {
		int count = selectInt(statement + "Count", parameter);
		List<T> list = selectList(statement, parameter);
		Map<String, Object> map = new HashMap<>();
		map.put("count", count);
		map.put("list", list);
		return map;
	}
	
}