package com.dxp.radar.handler;

import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;

/**
 * 初始化 TCP 处理链路
 */
public class ServerInitializer extends ChannelInitializer<NioSocketChannel> {

    /**
     * 线程安全的handler, 均采用单例,节约内存
     */
    private static final LoggingHandler LOGGING_HANDLER = new LoggingHandler(LogLevel.DEBUG);
    private static final ServerHandler serverHandler = new ServerHandler();

    @Override
    protected void initChannel(NioSocketChannel ch) throws Exception {
        ChannelPipeline pipeline = ch.pipeline();

        // 日志
        pipeline.addLast(LOGGING_HANDLER);

        // 解码器
        pipeline.addLast(new RadarDecoder());

        // 业务处理
        pipeline.addLast(serverHandler);
    }
}
