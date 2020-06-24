package com.waya.demo.util.netty;

import com.waya.demo.util.http.HttpMsgHandler;
import com.waya.demo.util.utils.Logger;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.codec.http.FullHttpRequest;
import io.netty.handler.codec.http.HttpResponseEncoder;
import io.netty.handler.codec.http.websocketx.WebSocketFrame;


public class HttpServerHandler extends SimpleChannelInboundHandler<Object> {
	
	private SocketChannel channel;
	private MsgHandler handler;

	public HttpServerHandler(SocketChannel socketChannel) {
		this.channel = socketChannel;
	}

	@Override
	protected void channelRead0(ChannelHandlerContext ctx, Object msg) throws Exception {
		if(msg instanceof FullHttpRequest) {
			// 响应转码器
			channel.pipeline().addLast("http-encoder", new HttpResponseEncoder());
			handler = new HttpMsgHandler();

		}else if(msg instanceof WebSocketFrame) {
			Logger.info("msg instanceof WebSocketFrame");
		}else {
			ctx.close();
			channel.close();
			return;
		}
		transmit(ctx,msg);
	}

	
	public void transmit(ChannelHandlerContext ctx, Object msg) throws Exception {
		handler.channelRead(ctx, msg);
	} 
    
    @Override
    public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {
        ctx.flush();
    }
    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        ctx.close();
        cause.printStackTrace();
    }
	
	

}