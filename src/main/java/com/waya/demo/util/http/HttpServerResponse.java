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

import com.waya.demo.util.resultData.RestResult;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.handler.codec.http.FullHttpResponse;
import io.netty.handler.codec.http.HttpHeaders;

public abstract class HttpServerResponse {

	protected FullHttpResponse response;
	protected HttpHeaders headers;
	
	/**
	 * <p>添加响应消息头(header)
	 * @param name 消息头名称
	 * @param value 消息头值
	 */
	public abstract void setHeader(String name, Object value);
	
	/**
	 * <p>设置响应的内容类型(Content-Type)
	 * @param value 值
	 */
	public abstract void setContentType(Object value);
	
	/**
	 * <p>向客户端写数据
	 * <p>需要注意, write方法只能被调用一次, 后面再调用将不会生效
	 * <p>调用write后应立即调用{@link #flush()}和{@link #close()}
	 * @param buf
	 */
	public abstract void write(ByteBuf buf);
	
	/**
	 * <p>向客户端写数据
	 * <p>需要注意, write方法只能被调用一次, 后面再调用将不会生效
	 * <p>调用write后应立即调用{@link #flush()}和{@link #close()}
	 * @param byt
	 */
	public abstract void write(byte[] byt);
	
	/**
	 * <p>向客户端写数据
	 * <p>需要注意, write方法只能被调用一次, 后面再调用将不会生效
	 * <p>调用write后应立即调用{@link #flush()}和{@link #close()}
	 * @param res
	 */
	public abstract void write(RestResult res);
	
	/**
	 * <p>完成写数据
	 * <p>调用此方法后请立即调用{@link #close()}方法进行终止流程
	 * @return
	 */
	public FullHttpResponse flush() {
		write(Unpooled.EMPTY_BUFFER);
		response.headers().set(headers);
		return response;
	}
	
	/**
	 * <p>关闭通道, 结束流程
	 */
	public abstract void close();
}
