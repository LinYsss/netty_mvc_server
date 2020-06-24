package com.waya.demo.util.http;

import java.lang.reflect.Method;
import java.lang.reflect.Parameter;

import org.apache.ibatis.session.SqlSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.waya.demo.util.bean.Handler;
import com.waya.demo.util.loader.BeanHelper;
import com.waya.demo.util.loader.ControllerHelper;
import com.waya.demo.util.loader.RequestHelper;
import com.waya.demo.util.mybatis.MyBatisConfig;
import com.waya.demo.util.mybatis.PondSqlFactory;
import com.waya.demo.util.netty.MsgHandler;
import com.waya.demo.util.resultData.RestResult;
import com.waya.demo.util.resultData.ResultGenerator;
import com.waya.demo.util.utils.ReflectionUtil;
import com.waya.demo.util.utils.StringUtils;

import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.http.FullHttpRequest;
import io.netty.handler.codec.http.FullHttpResponse;

public class HttpMsgHandler implements MsgHandler{
	
	private static Logger logger = LoggerFactory.getLogger(HttpMsgHandler.class);
	private ChannelHandlerContext context;
	private HttpServerResponse response;

	@Override
	public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
		context = ctx;
		final HttpServerRequest request = new HttpServerRequest((FullHttpRequest) msg, ctx.channel());
		response = new DefaultHttpServerResponse();
		
		String requestPath = request.url();//路径  ：/user/login
		String requestMethod = request.httpMethod().toString();//请求： POST
//		System.out.println(request.getRemoteAddr());//地址：127.0.0.1
	
        //这里根据Tomcat的配置路径有两种情况, 一种是 "/userList", 另一种是 "/context地址/userList".
        String[] splits = requestPath.split("/");
        if (splits.length > 2) {
            requestPath = splits[1] +"/"+ splits[2];
        }
        //根据请求获取处理器(这里类似于SpringMVC中的映射处理器)
        Handler handler = ControllerHelper.getHandler(requestMethod, requestPath);
        if(handler == null){
        	logger.error("Request address not found");
        }else{
        	 Class<?> controllerClass = handler.getControllerClass();//获取controller类 --class com.waya.demo.controller.DemoController
             Object controllerBean = BeanHelper.getBean(controllerClass);//获取controller对象 --com.waya.demo.controller.DemoController@116a9534
           
             //初始化参数
             Object  param = RequestHelper.createParam(request);//---{"loginPwd":"111111","loginName":"tiaotiao"}
             //映射到controller方法中的参数
             Object[] params = converter(handler, param,  request);
         
             //调用与请求对应的方法(这里类似于SpringMVC中的处理器适配器)
             Method actionMethod = handler.getControllerMethod();
             Object result =  invoking(controllerBean,actionMethod,params,param);
         
             //跳转页面或返回json数据(这里类似于SpringMVC中的视图解析器)
             if(result != null) {
				// 像客户端返回数据
//                write(result);
            	FullHttpResponse response = NettyHttpResponse.ok(result.toString());
     			ctx.writeAndFlush(response).addListener(ChannelFutureListener.CLOSE);
     			ctx.close();
			 }
        }
	}

	
	private Object[] converter(Handler handler, Object param, HttpServerRequest request) {
		 //public com.waya.demo.util.resultData.RestResult com.waya.demo.controller.DemoController.login(java.lang.String,java.lang.String)
		Method method = handler.getControllerMethod();
		Parameter[] parameters = method.getParameters();//[java.lang.String arg0, java.lang.String arg1]
		Object[] objs = new Object[parameters.length];//[null, null]
		for(int i = 0; i < parameters.length; i++) {
			Class<?> clazs = parameters[i].getType();
			if(clazs.equals(HttpServerRequest.class)) {
				objs[i] = request;
			}else if(clazs.equals(HttpServerResponse.class)) {
				objs[i] = response;
			}else {
				objs[i] = read(request, parameters[i], param);
			}
		}
		return objs;
	}
	
	public Object read(HttpServerRequest request,Parameter parameter, Object msg) {
		Class<?> clazs = parameter.getType();
		JSONObject json = (JSONObject) msg;
		if(StringUtils.isCommonDataType(clazs) || StringUtils.isWrapClasse(clazs)){
			String str = (String) json.get(parameter.getName());
			return str;
		}
		return json.toJavaObject(clazs);
	}
	
	
	public Object invoking(Object controllerBean, Method actionMethod, Object[] params, Object param){
		// 构建sqlSession
		Object res = null;
		SqlSession sql = null;
		sql = PondSqlFactory.addSqlSessions(MyBatisConfig.DEFAULT_ID);
		if (param == null) {
			 res = (Object[]) ReflectionUtil.invokeMethod(controllerBean, actionMethod);
	    } else {
	    	 res = ReflectionUtil.invokeMethod(controllerBean, actionMethod, params);
	    }
		sql.commit();
		sql.close();
		return res;
	}
	
	public void write(Object obj) {
		if(obj == null) {
			writeAndFlush();
		}else {
			byte[] byt;
			if(obj instanceof RestResult) {
				RestResult sr = (RestResult) obj;
				logger.debug("Response DEBUG: [code: {}, msg: {}, data: {}]", sr.getCode(), sr.getMessage(), sr.getData());
				byt = JSONObject.toJSONBytes(obj, SerializerFeature.WriteDateUseDateFormat);
			}else if(obj instanceof byte[]) {
				byt = (byte[]) obj;
			}else {
				ResultGenerator resp = new ResultGenerator();
				resp.getSuccessResult(obj);
				logger.debug("code: {}, status: {}, msg: {}", resp.getCode(), resp.getMessage());
				byt = JSONObject.toJSONBytes(resp, SerializerFeature.WriteDateUseDateFormat);
			}
			response.write(byt);
			writeAndFlush();
		}
	}

	public void writeAndFlush() {
		context.writeAndFlush(response.response).addListener(ChannelFutureListener.CLOSE);//当flush完成后关闭channel
		context.close();
	}
	
	

	
	
	

}
