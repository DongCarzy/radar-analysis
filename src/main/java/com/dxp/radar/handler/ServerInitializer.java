package com.dxp.radar.handler;

import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.ServerSocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.LengthFieldBasedFrameDecoder;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;

import java.nio.ByteOrder;

/**
 * 初始化 TCP 处理链路
 */
public class ServerInitializer extends ChannelInitializer<NioSocketChannel> {

    private static final LoggingHandler LOGGING_HANDLER = new LoggingHandler(LogLevel.DEBUG);

    @Override
    protected void initChannel(NioSocketChannel ch) throws Exception {
        ChannelPipeline pipeline = ch.pipeline();

        // 日志
        pipeline.addLast(LOGGING_HANDLER);

        // 解码器
        pipeline.addLast(new RadarDecoder());

        // 业务处理
        pipeline.addLast(new ServerHandler());
    }
}
