package com.dxp.radar.handler;

import com.dxp.radar.code.TrackingReq;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.util.internal.logging.InternalLogger;
import io.netty.util.internal.logging.InternalLoggerFactory;

/**
 * 服务端处理.
 * 看业务处理,暂时可以用单例.  注意线程安全
 */
@ChannelHandler.Sharable
public class ServerHandler extends SimpleChannelInboundHandler<TrackingReq> {

    private static final InternalLogger logger = InternalLoggerFactory.getInstance(ServerHandler.class);

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, TrackingReq msg) throws Exception {
        logger.info(msg.toString());
    }
}
