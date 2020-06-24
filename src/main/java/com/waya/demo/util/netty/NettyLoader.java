package com.waya.demo.util.netty;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.waya.demo.App;
import com.waya.demo.util.config.CustomConfig;
import com.waya.demo.util.loader.ConfigHelper;
import com.waya.demo.util.loader.MappingHelper;
import com.waya.demo.util.mybatis.MyBatisConfig;
import com.waya.demo.util.utils.ClassUtil;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.http.HttpObjectAggregator;
import io.netty.handler.codec.http.HttpServerCodec;
import io.netty.handler.stream.ChunkedWriteHandler;

public class NettyLoader {
	
	private static Logger logger = LoggerFactory.getLogger(App.class);
	private int port;

    public NettyLoader(CustomConfig custom) {
        this.port = custom.getPort();
        init(custom);
    }
    
    private static void init(CustomConfig custom) {
    	Class<?>[] classList = custom.getClassList();
        for (Class<?> cls : classList) {
            ClassUtil.loadClass(cls.getName());
        }
        setMyBatis(custom.getMybatis());
        MappingHelper.scanClass(ConfigHelper.getString("mapper"));
    }
    
    private static void setMyBatis(List<MyBatisConfig> list) {
		if(list != null && list.size() > 0) {
			for(MyBatisConfig myBatisConfig : list) {
				myBatisConfig.build();
			}
		}
	}
    
    private void bind() {
		 EventLoopGroup boss = new NioEventLoopGroup();
	        EventLoopGroup worker = new NioEventLoopGroup();

	        try {
	            ServerBootstrap bootstrap = new ServerBootstrap();//Bootstrap启动器类
	            bootstrap.group(boss, worker);
	            bootstrap.channel(NioServerSocketChannel.class);//channel 方法用于指定服务器端监听套接字通道 NioServerSocketChannel，其内部管理了一个 Java NIO 中的ServerSocketChannel实例。
	            bootstrap.option(ChannelOption.SO_BACKLOG, 1024); // 连接数
	            bootstrap.option(ChannelOption.TCP_NODELAY, true); // 不延迟，消息立即发送
	            bootstrap.childOption(ChannelOption.SO_KEEPALIVE, true); // 长连接 是否开启TCP底层心跳机制，true为开启，false为关闭。
	            bootstrap.childHandler(new ChannelInitializer<SocketChannel>() {
	                @Override
	                protected void initChannel(SocketChannel socketChannel)//子通道
	                        throws Exception {
	                    ChannelPipeline p = socketChannel.pipeline();//一个Netty通道拥有一条Handler处理器流水线，成员的名称叫作pipeline。
	                    //处理http消息的编解码
	            		/**
	            		 * 所以我们这里使用 Netty 自带的 Http 编解码组件 HttpServerCodec 对通信数据进行编解码，
	            		 * HttpServerCodec 是 HttpRequestDecoder 和 HttpResponseEncoder 的组合，
	            		 * 因为在处理 Http 请求时这两个类是经常使用的，所以 Netty 直接将他们合并在一起更加方便使用。
	            		 * 我们替换成如下两行也是可以的。
	            		 * pipeline.addLast("httpResponseEndcoder", new HttpResponseEncoder());
	            		 * pipeline.addLast("HttpRequestDecoder", new HttpRequestDecoder());
	            		 */
	                    p.addLast("http-serverCodec", new HttpServerCodec());
	                    p.addLast("http-aggregator", new HttpObjectAggregator(Integer.MAX_VALUE));
	                	// 解决大码流的问题，ChunkedWriteHandler
						p.addLast("chunked-handler", new ChunkedWriteHandler());
	                    //添加自定义的ChannelHandler 
	                    p.addLast("socket-channel-handler", new HttpServerHandler(socketChannel));// 用来处理Server端接收和处理消息的逻辑
	                }
	            });
	            //channelFuture 回调监听器，
	            ChannelFuture channelFuture = bootstrap.bind(port).sync();//调用的sync()的目的就是保证ChannelFuture已经完成了。
	            if (channelFuture.isSuccess()) {
	            	logger.info("启动Netty服务成功，端口号：" + this.port);
	            }
	            // 关闭连接
	            //自我阻塞，直到通道关闭的异步任务结束
	            channelFuture.channel().closeFuture().sync();

	        } catch (Exception e) {
	        	logger.info("启动Netty服务异常，异常信息：" + e.getMessage());
	            e.printStackTrace();
	        } finally {
	        	// 优雅退出，释放线程池资源
	            boss.shutdownGracefully();
	            worker.shutdownGracefully();
	        }
		
	}
	
	public void start(){
		bind();
	} 

}
