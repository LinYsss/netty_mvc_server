package com.waya.demo.util.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @Target 注解
	功能：指明了修饰的这个注解的使用范围，即被描述的注解可以用在哪里。
	ElementType的取值包含以下几种： 
	TYPE:类，接口或者枚举
	FIELD:域，包含枚举常量
	METHOD:方法
	PARAMETER:参数
	CONSTRUCTOR:构造方法
	LOCAL_VARIABLE:局部变量
	ANNOTATION_TYPE:注解类型
	PACKAGE:包
	@Retention 注解
	功能：指明修饰的注解的生存周期，即会保留到哪个阶段。
	RetentionPolicy的取值包含以下三种：
	SOURCE：源码级别保留，编译后即丢弃。
	CLASS:编译级别保留，编译后的class文件中存在，在jvm运行时丢弃，这是默认值。
	RUNTIME： 运行级别保留，编译后的class文件中存在，在jvm运行时保留，可以被反射调用。
	@Documented 注解
	功能：指明修饰的注解，可以被例如javadoc此类的工具文档化，只负责标记，没有成员取值。
	@Inherited注解
	功能：允许子类继承父类中的注解。
	注意！：
	@interface意思是声明一个注解，方法名对应参数名，返回值类型对应参数类型。
 *
 */
/**
 * <p>绑定请求映射地址的注解，将此注解放在类或方法上可以根据value值绑定请求路径
 * @author LY
 */
@Target({ElementType.METHOD, ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface RequestMapping {
	
	/**
	 * @return 需要绑定请求映射路径地址
	 */
	public String value() default "";
	
	/**
	 * @return 规定请求的类型
	 */
	public RequestMethod method() default RequestMethod.GET;
	
	/**
	 * @return 规定消息的请求头
	 */
	public String headers() default "content-type=application/json";
	
	/**
	 * @return 规定响应的消息头
	 */
	public String produces() default "application/json";
	
}


