package com.waya.demo;

import java.util.ArrayList;
import java.util.List;

import com.waya.demo.util.config.CustomConfig;
import com.waya.demo.util.loader.BeanHelper;
import com.waya.demo.util.loader.ClassHelper;
import com.waya.demo.util.loader.ConfigHelper;
import com.waya.demo.util.loader.ControllerHelper;
import com.waya.demo.util.loader.IocHelper;
import com.waya.demo.util.mybatis.MyBatisConfig;
import com.waya.demo.util.netty.NettyLoader;

public class App {
	
	public static void main(String[] args) {
		CustomConfig custom = new CustomConfig();
		basic(custom);
		mybatis(custom);
		loaders(custom);
		new NettyLoader(custom).start();
	}
	
	
    /**
     * <p>
     * 设置基本属性
     *
     * @param custom
     *
     */
    private static void basic(CustomConfig custom) {
    	custom.setScan(ConfigHelper.getString("base_package"));
        custom.setDao(ConfigHelper.getString("mapper"));
        custom.setPort(ConfigHelper.getInt("port"));//端口号
    }
    
    
    /**
     * <p>
     * 设置mybatis数据库连接
     *
     * @param custom
     */
    private static void mybatis(CustomConfig custom) {
    	List<MyBatisConfig> mybatis = new ArrayList<MyBatisConfig>();
        MyBatisConfig config = new MyBatisConfig();
        config.setDriver(ConfigHelper.getString("driver"));
        config.setUrl(ConfigHelper.getString("url"));
        config.setUsername(ConfigHelper.getString("username"));
        config.setPassword(ConfigHelper.getString("password"));
        mybatis.add(config);
        custom.setMybatis(mybatis);
    }
    
    /**
     * 加载这五个类, 目的是为了执行类里的静态代码块
     */
    public static void loaders(CustomConfig custom) {
        Class<?>[] classList = {
            ClassHelper.class,
            BeanHelper.class,
            IocHelper.class,
            ControllerHelper.class
        };
        custom.setClassList(classList);
        
    }
    
}
