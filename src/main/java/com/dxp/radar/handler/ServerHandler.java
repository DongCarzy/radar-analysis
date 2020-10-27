package com.dxp.radar.handler;

import com.dxp.radar.Application;
import com.dxp.radar.code.TrackingReq;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.util.internal.logging.InternalLogger;
import io.netty.util.internal.logging.InternalLoggerFactory;

/**
 * 服务端处理.
 */
public class ServerHandler extends SimpleChannelInboundHandler<TrackingReq> {

    private static final InternalLogger logger = InternalLoggerFactory.getInstance(Application.class);

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, TrackingReq msg) throws Exception {
        logger.info(msg.toString());
    }
}
