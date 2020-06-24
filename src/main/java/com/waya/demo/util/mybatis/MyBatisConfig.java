package com.waya.demo.util.mybatis;

import org.apache.ibatis.datasource.pooled.PooledDataSource;
import org.apache.ibatis.logging.Log;
import org.apache.ibatis.logging.slf4j.Slf4jImpl;
import org.apache.ibatis.mapping.Environment;
import org.apache.ibatis.session.Configuration;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.apache.ibatis.transaction.TransactionFactory;
import org.apache.ibatis.transaction.jdbc.JdbcTransactionFactory;

/**
 * <p>MyBatis配置器
 * @author yejia
 *
 */
public class MyBatisConfig {
	
	public static final String DEFAULT_ID = "development";
	
	/**
	 * <p>当前连接工厂ID
	 */
	private String id;
	/**
	 * <p>数据库驱动
	 */
	private String driver;
	/**
	 * <p>数据库地址全路径
	 */
	private String url;
	/**
	 * <p>数据库登录名
	 */
	private String username;
	/**
	 * <p>数据库登密码
	 */
	private String password;
	/**
	 * <p>心跳查询语句
	 */
	private String poolPingQuery = "SELECT 1 FROM DUAL";
	/**
	 * <p>心跳间隔时间
	 */
	private int poolPingConnectionsNotUsedFor = 1000 * 60 * 60;
	/**
	 * <p>是否启用心跳机制
	 */
	private boolean poolPingEnabled = true;
	/**
	 * <p>在重新尝试连接之前等待的时间
	 */
	private int poolTimeToWait = 20000;
	/**
	 * <p>活动连接的最大数量
	 */
	private int poolMaximumActiveConnections = 10;
	/**
	 * <p>连接在再次被发出之前可以使用的最长时间
	 */
	private int poolMaximumCheckoutTime = 20000;
	/**
	 * <p>最大空闲连接数
	 */
	private int poolMaximumIdleConnections = 5;
	private Class<? extends Log> logImpl = Slf4jImpl.class;
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getDriver() {
		return driver;
	}
	public void setDriver(String driver) {
		this.driver = driver;
	}
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getPoolPingQuery() {
		return poolPingQuery;
	}
	public void setPoolPingQuery(String poolPingQuery) {
		this.poolPingQuery = poolPingQuery;
	}
	public int getPoolPingConnectionsNotUsedFor() {
		return poolPingConnectionsNotUsedFor;
	}
	public void setPoolPingConnectionsNotUsedFor(int poolPingConnectionsNotUsedFor) {
		this.poolPingConnectionsNotUsedFor = poolPingConnectionsNotUsedFor;
	}
	public boolean isPoolPingEnabled() {
		return poolPingEnabled;
	}
	public void setPoolPingEnabled(boolean poolPingEnabled) {
		this.poolPingEnabled = poolPingEnabled;
	}
	public int getPoolTimeToWait() {
		return poolTimeToWait;
	}
	public void setPoolTimeToWait(int poolTimeToWait) {
		this.poolTimeToWait = poolTimeToWait;
	}
	public int getPoolMaximumActiveConnections() {
		return poolMaximumActiveConnections;
	}
	public void setPoolMaximumActiveConnections(int poolMaximumActiveConnections) {
		this.poolMaximumActiveConnections = poolMaximumActiveConnections;
	}
	public int getPoolMaximumCheckoutTime() {
		return poolMaximumCheckoutTime;
	}
	public void setPoolMaximumCheckoutTime(int poolMaximumCheckoutTime) {
		this.poolMaximumCheckoutTime = poolMaximumCheckoutTime;
	}
	public int getPoolMaximumIdleConnections() {
		return poolMaximumIdleConnections;
	}
	public void setPoolMaximumIdleConnections(int poolMaximumIdleConnections) {
		this.poolMaximumIdleConnections = poolMaximumIdleConnections;
	}

	@Override
	public String toString() {
		return "MyBatisConfig [id=" + id + ", driver=" + driver + ", url=" + url + ", username=" + username
				+ ", password=" + password + "]";
	}
	
	//相当于<!-- default属性用来配置默认使用的环境id -->
	//	<environments default="development">
	//	  <!-- 每个环境都有一个id值,用来在environments的default中使用 -->
	//	  <environment id="development">
	//	    <!-- 事务管理器,内置的有JDBC和MANAGED两种,也可以自定义 -->
	//	    <transactionManager type="JDBC">
	//	      <property name="..." value="..."/>
	//	    </transactionManager>
	//	    <!-- 数据源,内置的有UNPOOLED,POOLED和JNDI三种,也可以自定义 -->
	//	    <dataSource type="POOLED">
	//	      <property name="driver" value="${driver}"/>
	//	      <property name="url" value="${url}"/>
	//	      <property name="username" value="${username}"/>
	//	      <property name="password" value="${password}"/>
	//	    </dataSource>
	//	  </environment>
	//	</environments>
	
	public void build(){
		String _id = id == null ? DEFAULT_ID : id;
		PooledDataSource dataSource = new PooledDataSource();
		dataSource.setDriver(driver);
		dataSource.setUrl(url);
		dataSource.setUsername(username);
		dataSource.setPassword(password);
		dataSource.setPoolPingQuery(poolPingQuery);
		dataSource.setPoolPingConnectionsNotUsedFor(poolPingConnectionsNotUsedFor);
		dataSource.setPoolPingEnabled(poolPingEnabled);
		dataSource.setPoolTimeToWait(poolTimeToWait);
		dataSource.setPoolMaximumActiveConnections(poolMaximumActiveConnections);
		dataSource.setPoolMaximumCheckoutTime(poolMaximumCheckoutTime);
		dataSource.setPoolMaximumIdleConnections(poolMaximumIdleConnections);
		TransactionFactory transactionFactory = new JdbcTransactionFactory();//事务
		Environment environment = new Environment(_id, transactionFactory, dataSource);
		Configuration config = new Configuration(environment);
		config.setLogImpl(logImpl);
		PondSqlFactory.put(_id, new SqlSessionFactoryBuilder().build(config));
	
	}


}
