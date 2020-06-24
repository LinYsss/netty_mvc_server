package com.waya.demo.util.utils;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 反射工具类
 */
public final class ReflectionUtil {

    private static final Logger LOGGER = LoggerFactory.getLogger(ReflectionUtil.class);

    /**
     * 创建实例
     */
    public static Object newInstance(Class<?> cls) {
        Object instance;
        try {
            instance = cls.newInstance();//newInstance相当于new
        } catch (Exception e) {
            LOGGER.error("new instance failure", e);
            throw new RuntimeException(e);
        }
        return instance;
    }

    /**
     * 创建实例（根据类名）
     */
    public static Object newInstance(String className) {
        Class<?> cls = ClassUtil.loadClass(className);
        return newInstance(cls);
    }

    /**
     * 调用方法
     */
    public static Object invokeMethod(Object obj, Method method, Object... args) {
        Object result;
        try {
            method.setAccessible(true);
            result = method.invoke(obj, args);//invoke 方法.invoke(controller对象,获取值)
        } catch (Exception e) {
            LOGGER.error("invoke method failure", e);
            throw new RuntimeException(e);
        }
        return result;
    }

    /**
     * 设置成员变量的值
     */
    public static void setField(Object obj, Field field, Object value) {
        try {
            field.setAccessible(true); //去除私有权限
            field.set(obj, value);
        } catch (Exception e) {
            LOGGER.error("set field failure", e);
            throw new RuntimeException(e);
        }
    }
    
    
    /**
     * 获得调用方法的名称
     *
     * @return 方法名称
     */
    public static String getCallMethod(){
        StackTraceElement[] stack = Thread.currentThread().getStackTrace();
        // 获得调用方法名
        String method = stack[3].getMethodName();
        return method;
    }

    /**
     * 获得调用方法的类名+方法名,带上中括号
     *
     * @return 方法名称
     */
    public static String getCallClassMethod(){
        StackTraceElement stack[] = Thread.currentThread().getStackTrace();
        // 获得调用方法名
        String[] className = stack[3].getClassName().split("\\.");
        String fullName = className[className.length - 1] + ":" + stack[3].getMethodName();
        return fullName;
    }

    /**
     * 获得调用方法的类名+方法名
     *
     * @return 方法名称
     */
    public static String getNakeCallClassMethod(){
        StackTraceElement stack[] = Thread.currentThread().getStackTrace();
        // 获得调用方法名
        String[] className = stack[3].getClassName().split("\\.");
        String fullName = className[className.length - 1] + "." + stack[3].getMethodName();
        return fullName;
    }

    /**
     * 取得父类所有的接口
     *
     * @param targetClass
     * @return
     */
    public static Class<?>[] getInterfaces(Class<?> targetClass){
        Set<Class<?>> interfaceSet = new HashSet<>();
        //数组转成list
        List<Class<?>> subList = Arrays.asList(targetClass.getInterfaces());
        if (subList.size() > 0)
            interfaceSet.addAll(subList);
        Class superClass = targetClass.getSuperclass();
        while (null != superClass)
        {
            subList = Arrays.asList(superClass.getInterfaces());

            if (subList.size() > 0)
                interfaceSet.addAll(subList);

            superClass = superClass.getSuperclass();
        }
        //set 转成 数组
        return interfaceSet.toArray(new Class<?>[0]);

    }

    public static Object newProxyInstance(Object targetObject, InvocationHandler handler){
        Class targetClass = targetObject.getClass();

        ClassLoader loader = targetClass.getClassLoader();

        //被代理类实现的接口
        Class<?>[] targetInterfaces = ReflectionUtil.getInterfaces(targetClass);

        Object proxy = Proxy.newProxyInstance(loader, targetInterfaces, handler);
        return proxy;
    }
}
