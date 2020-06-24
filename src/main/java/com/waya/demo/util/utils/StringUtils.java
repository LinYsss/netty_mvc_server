package com.waya.demo.util.utils;

import java.util.Collection;
import java.util.IdentityHashMap;
import java.util.Map;

/**
 * 工具类
 * @author 
 *
 */
public class StringUtils {
	
	private static final Map<Class<?>, Class<?>> PRIMITIVE_WRAPPER_TYPE = new IdentityHashMap<Class<?>, Class<?>>(9);
	
	static {
		PRIMITIVE_WRAPPER_TYPE.put(Boolean.class, boolean.class);
		PRIMITIVE_WRAPPER_TYPE.put(Byte.class, byte.class);
		PRIMITIVE_WRAPPER_TYPE.put(Character.class, char.class);
		PRIMITIVE_WRAPPER_TYPE.put(Double.class, double.class);
		PRIMITIVE_WRAPPER_TYPE.put(Float.class, float.class);
		PRIMITIVE_WRAPPER_TYPE.put(Integer.class, int.class);
		PRIMITIVE_WRAPPER_TYPE.put(Long.class, long.class);
		PRIMITIVE_WRAPPER_TYPE.put(Short.class, short.class);
		PRIMITIVE_WRAPPER_TYPE.put(String.class, String.class);
	}
	
	/**
	 * 判断是否为空
	 * @param type
	 * @return true 为空，false不为空
	 */
	 public static  Boolean isEmpty(Object type){
         if(type != null && !"".equals(type)){
             return false;
         }
         return true;
     }
	 
	 /**
	  * 判断是否不为空
	  * @param type
	  * @return
	  */
	 public static  Boolean isNotEmpty(Object type){
         if(type == null && "".equals(type)){
             return false;
         }
         return true;
     }
	 
	 /**
	  * 判断是否为controllerClass
	  * @param coll
	  * @return
	  */
	 public static boolean isNotEmpty(final Collection<?> coll) {
	        return !isEmpty(coll);
	 }
	 
	 /**
	  * 判断是否为array
	  * @param array
	  * @return
	  */
	 public static <T> boolean isNotEmpty(final T[] array) {
         return (array != null && array.length != 0);
     }
	 
	 /**
	  * 判断是否为Map
	  * @param map
	  * @return
	  */
	 public static boolean isNotEmpty(final Map<?,?> map) {
	        return !StringUtils.isEmpty(map);
     }
	 
	/**
	 * <p>判断Class是否是简单类型
	 * @param obj {@link Class}
	 * @return true/false
	 */
	 public static boolean isCommonDataType(Class<?> clazz){
	
		return clazz.isPrimitive();   
	 }
	 
	 
	 /**
	  * 判断Class是否是基础数据类型的包装类型
	  *
	  * @param clz
	  * @return
	  */                                        
	public static boolean isWrapClass(Class<?> clz) {
	
		
	    try {
	        return ((Class<?>) clz.getField("TYPE").get(null)).isPrimitive();
	    } catch (Exception e) {
	        return false;
	    }
	}
	
	
	//判断Class是否是基础数据类型的包装类型
	public static boolean isWrapClasse(Class<?> clz){
		return PRIMITIVE_WRAPPER_TYPE.containsKey(clz);
		
	}


}
