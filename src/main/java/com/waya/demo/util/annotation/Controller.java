package com.waya.demo.util.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;


/**
 * <p>作用在控制器上的注解，添加此注解会将此类添加至控制器
 * @author LY
 *
 */
@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface Controller {
	
	/**
	 * @return 当前接口（http接口）类的标识符（键key）
	 */
	public String value() default "";
	

	
}
