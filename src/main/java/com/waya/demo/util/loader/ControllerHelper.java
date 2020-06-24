package com.waya.demo.util.loader;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import com.waya.demo.util.annotation.RequestMapping;
import com.waya.demo.util.bean.Handler;
import com.waya.demo.util.bean.Request;
import com.waya.demo.util.utils.StringUtils;

/**
 * 控制器助手类
 * 相当于SpringMVC里的映射处理器, 为请求URI设置对应的处理器
 */
public final class ControllerHelper {

    /**
     * REQUEST_MAP为 "请求-处理器" 的映射
     */
    private static final Map<Request, Handler> REQUEST_MAP = new HashMap<Request, Handler>();

    static {
        //遍历所有Controller类
        Set<Class<?>> controllerClassSet = ClassHelper.getControllerClassSet();
        String requestPath1 = null;
        if (StringUtils.isNotEmpty(controllerClassSet)) {
            for (Class<?> controllerClass : controllerClassSet) {
            	if(controllerClass.isAnnotationPresent(RequestMapping.class)){
            		 RequestMapping requestMapping = controllerClass.getAnnotation(RequestMapping.class);
                     //请求路径
                      requestPath1 = requestMapping.value();
            	}
                //暴力反射获取所有方法
                Method[] methods = controllerClass.getDeclaredMethods();
                //遍历方法
                if (StringUtils.isNotEmpty(methods)) {
                    for (Method method : methods) {
                        //判断是否带RequestMapping注解
                        if (method.isAnnotationPresent(RequestMapping.class)) {
                            RequestMapping requestMapping = method.getAnnotation(RequestMapping.class);
                            //请求路径
                            String requestPath2 = requestMapping.value();
                            String requestPath = requestPath1 + "/"+ requestPath2;
                            //请求方法
                            String requestMethod = requestMapping.method().name();

                            //封装请求和处理器
                            Request request = new Request(requestMethod, requestPath);
                            Handler handler = new Handler(controllerClass, method);
                            REQUEST_MAP.put(request, handler);
                        }
                    }
                }
            }
        }
    }

    /**
     * 获取 Handler
     */
    public static Handler getHandler(String requestMethod, String requestPath) {
        Request request = new Request(requestMethod, requestPath);
        return REQUEST_MAP.get(request);
    }
}
