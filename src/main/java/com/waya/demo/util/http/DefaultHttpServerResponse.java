package com.waya.demo.util.http;

import static io.netty.handler.codec.http.HttpHeaderNames.CONTENT_TYPE;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.waya.demo.util.resultData.RestResult;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.handler.codec.http.DefaultFullHttpResponse;
import io.netty.handler.codec.http.DefaultHttpHeaders;
import io.netty.handler.codec.http.HttpHeaderNames;
import io.netty.handler.codec.http.HttpHeaderValues;
import io.netty.handler.codec.http.HttpResponseStatus;
import io.netty.handler.codec.http.HttpVersion;

public class DefaultHttpServerResponse extends HttpServerResponse{

	public static final String DEFAULT_CONTENT_TYPE = "application/json";
	public static final String HEADER_NAME_SERVER = "server";
	public static final String HEADER_NAME_APP_VERSION = "app-version";
	private static Logger logger = LoggerFactory.getLogger(DefaultHttpServerResponse.class);
	
	public DefaultHttpServerResponse() {
		headers = new DefaultHttpHeaders();
		headers.set(HttpHeaderNames.CONTENT_TYPE, DEFAULT_CONTENT_TYPE);
	}

	@Override
	public void setHeader(String name, Object value) {
		headers.set(name, value);
		
	}

	@Override
	public void setContentType(Object value) {
		headers.set(HttpHeaderNames.CONTENT_TYPE, value);
		
	}

	@Override
	public void write(ByteBuf buf) {
		if(response == null) {
			response = new DefaultFullHttpResponse(
					HttpVersion.HTTP_1_1,
					HttpResponseStatus.OK,
					buf
					);
			headers.set(CONTENT_TYPE, "application/json");
			headers.set(HttpHeaderNames.CONNECTION, HttpHeaderValues.KEEP_ALIVE);
			headers.set(HttpHeaderNames.CONTENT_LENGTH, response.content().readableBytes());		
		}	
	}

	@Override
	public void write(byte[] byt) {
		write(Unpooled.wrappedBuffer(byt));
		
	}

	@Override
	public void write(RestResult res) {
		logger.debug("code: {}, msg: {}", res.getCode(), res.getMessage());
		write(JSONObject.toJSONBytes(res, SerializerFeature.WriteDateUseDateFormat));
		
	}

	@Override
	public void close() {
		
		
	}

}
