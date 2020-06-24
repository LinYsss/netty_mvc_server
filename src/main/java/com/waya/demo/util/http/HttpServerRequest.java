/**
 * Copyright 2013-2033 Xia Jun(3979434@qq.com).
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 ***************************************************************************************
 *                                                                                     *
 *                        Website : http://www.farsunset.com                           *
 *                                                                                     *
 ***************************************************************************************
 */
package com.waya.demo.util.http;

import java.io.UnsupportedEncodingException;
import java.net.InetSocketAddress;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import io.netty.buffer.ByteBuf;
import io.netty.channel.Channel;
import io.netty.handler.codec.http.FullHttpRequest;
import io.netty.handler.codec.http.HttpHeaderNames;
import io.netty.handler.codec.http.HttpHeaders;
import io.netty.handler.codec.http.HttpMethod;
import io.netty.handler.codec.http.QueryStringDecoder;

public class HttpServerRequest {
	
	private static Logger log = LoggerFactory.getLogger(HttpServerRequest.class);
	private final String url;
	private final Map<String, Object> parameters;
	private final String uri;
	private final HttpHeaders headers;
	private final HttpMethod method;
	private final ByteBuf content;
	private String body;
	private Channel channel;
	public HttpServerRequest(FullHttpRequest request, Channel channel) {
		uri = request.uri();
		url = initUrl(uri);
		headers = request.headers();
		method = request.method();
		content = request.content();
		parameters = new HashMap<>();
		initParameter();
		this.channel = channel;
	}
	
	public String uri() {
		return uri;
	}
	public HttpMethod httpMethod() {
		return method;
	}
	public HttpHeaders headers() {
		return headers;
	}
	public String getHeader(String name) {
		return headers.get(name);
	}
	public String getContentType() {
		return headers.get(HttpHeaderNames.CONTENT_TYPE);
	}
	public ByteBuf content() {
		return content;
	}

	private String initUrl(String uri) {
		StringBuilder strb = new StringBuilder();
		for (int i = 0; i < uri.length(); i++) {
			char c = uri.charAt(i);
			if (c == '?' || c == '#') {
				break;
			}
			strb.append(c);
		}
		return strb.toString();
	}
	
	public String body() {
		if(body == null) {
			byte[] byteContent = new byte[content.readableBytes()];//返回表示ByteBuf当前可读取的字节数，它的值等于writerIndex减去readerIndex。
			content.readBytes(byteContent);//读取ByteBuf中的数据。将数据从ByteBuf读取到dst字节数组中，这里dst字节数组的大小，通常等于readableBytes()。
			try {
				body = new String(byteContent, "UTF-8");
			}catch (UnsupportedEncodingException e) {
				throw new RuntimeException("Error in parameter parsing (system coding error) ? ", e);
			}
		}
		return body;
	}

	private void initParameter() {
		QueryStringDecoder decoder = new QueryStringDecoder(uri());
		Map<String, List<String>> paramList = decoder.parameters();
		for (Map.Entry<String, List<String>> entry : paramList.entrySet()) {
			parameters.put(entry.getKey(), entry.getValue().get(0));
		}
		log.debug("GET 请求参数：{}", parameters.toString());
	}

	/**
	 * <p>获取请求地址（映射地址）
	 * @return 请求地址（映射地址）
	 */
	public String url() {
		return url;
	}
	
	/**
	 * <p>写入参数，方法不对外开放，仅仅用在当出现"{parameter}"规则的映射时使用
	 * @param key 参数名称
	 * @param value 参数值
	 */
	void setParameter(String key, String value) {
		parameters.put(key, value);
	}

	/**
	 * <p>获取get请求参数
	 * @param key 参数名称
	 * @return 参数值
	 */
	public Object parameter(String key) {
		return parameters.get(key);
	}

	/**
	 * <p>获取get请求参数
	 * @return 所有参数的JSON集合
	 */
	public Map<String, Object> parameters() {
		return parameters;
	}
	
	/**
	 * <p>获取客户端ip
	 * @return 客户端ip
	 */
	public String getRemoteAddr() {
		return getSocketAddr().getAddress().getHostAddress();
	}
	
	/**
	 * <p>获取客户端连接信息
	 * @return 客户端信息
	 */
	public InetSocketAddress getSocketAddr() {
		return (InetSocketAddress) channel.remoteAddress();
	}

}
