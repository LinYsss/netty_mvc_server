package com.waya.demo.util.loader;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.alibaba.fastjson.JSONObject;
import com.waya.demo.util.http.HttpServerRequest;

import io.netty.handler.codec.http.HttpMethod;

/**
 * 请求助手类
 */
public final class RequestHelper {
	
	private static Logger logger = LoggerFactory.getLogger(RequestHelper.class);
	
    /**
     * 获取请求参数
     */
	public static Object createParam(HttpServerRequest request) {
		String contentType = request.headers().get("Content-Type");
		Object msg = null;
		if(contentType.equals("application/json")){
			if(HttpMethod.GET.equals(request.httpMethod())){
				msg = request.parameters();
			}else{
				 msg = JSONObject.parseObject(request.body());
			}
		}else{
			logger.error("请求数据格式错误");
		}
		return msg;
	}

}
