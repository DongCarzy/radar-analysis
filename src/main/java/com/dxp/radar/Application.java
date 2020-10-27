package com.dxp.radar;

import com.dxp.radar.handler.ServerInitializer;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.util.concurrent.DefaultThreadFactory;
import io.netty.util.concurrent.ThreadPerTaskExecutor;
import io.netty.util.internal.logging.InternalLogger;
import io.netty.util.internal.logging.InternalLoggerFactory;

import java.nio.ByteOrder;

/**
 * 工程启动类
 */
public class Application {

    private static final InternalLogger logger = InternalLoggerFactory.getInstance(Application.class);

    /**
     * 服务器监听的TCP端口
     */
    private static final int port = 8711;

    public static void main(String[] args) {

        EventLoopGroup bossGroup = new NioEventLoopGroup(1,
                new ThreadPerTaskExecutor(new DefaultThreadFactory("BG", Thread.MAX_PRIORITY)));//always one thread
        EventLoopGroup workerGroup = new NioEventLoopGroup(1,
                new ThreadPerTaskExecutor(new DefaultThreadFactory("WG", Thread.MAX_PRIORITY)));

        // netty启动器
        ServerBootstrap b = new ServerBootstrap();
        b.group(bossGroup, workerGroup)
                .channel(NioServerSocketChannel.class);
        b.option(ChannelOption.SO_BACKLOG, 512);
        b.childHandler(new ServerInitializer());

        try {
            // 监听端口
            ChannelFuture f = b.bind(port).sync();

            // 监听服务退出事件
            f.channel().closeFuture().addListener((future) -> {
                if (future.isSuccess()) {
                    logger.info("server exited suc");
                } else {
                    logger.error("server exited Exception: ", future.cause());
                }
            });
        } catch (Throwable e) {
            logger.error("start waterService exception: {}", e);
            workerGroup.shutdownGracefully();
            bossGroup.shutdownGracefully();
        }
        logger.info("waterService server on: " + port);
    }
}
