package com.dxp.radar.handler;

import com.dxp.radar.code.TrackingReq;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageDecoder;
import io.netty.util.internal.logging.InternalLogger;
import io.netty.util.internal.logging.InternalLoggerFactory;

import java.util.List;

/**
 * 雷达数据解码器.
 */
public class RadarDecoder extends ByteToMessageDecoder {

    private static final InternalLogger logger = InternalLoggerFactory.getInstance(RadarDecoder.class);

    /**
     * 待组装的数据包
     */
    private TrackingReq req = null;

    @Override
    protected void decode(ChannelHandlerContext ctx, ByteBuf in, List<Object> out) throws Exception {
        // 待数据包大于等于34个字节时才开始处理解析.
        if (in.readableBytes() >= 34) {
            decode0(in, ctx);
        }

        if (req != null) {
            out.add(req);
            req = null;
        }
    }

    /**
     * in的字节长度必定大于34字节.
     *
     * @param in ByteBuf 客户端发送上来的字节码
     */
    private void decode0(ByteBuf in, ChannelHandlerContext ctx) {
        if (in.readUnsignedShortLE() != 0x2101) {
            logger.error("read head data is not 0x2101");
            logger.info("try to close channel. ", ctx.channel().toString());
            ctx.close();
        }
        // 除去头还有32字节
        req = new TrackingReq();
        req.setNo(in.readUnsignedShortLE());
        req.setTrackNo(in.readUnsignedIntLE());
        req.setX(in.readFloatLE());
        req.setY(in.readFloatLE());
        req.setZ(in.readFloatLE());
        req.setSpeed(in.readFloatLE());
        req.setHorizontal(in.readUnsignedShortLE());
        req.setVertical(in.readUnsignedShortLE());
        req.setCause(in.readByte());
        req.setLaneNo(in.readByte());
        req.setTargetCarType(in.readByte());
        req.setCrc(in.readByte());
        byte crc = checkCrc();
        if (crc != req.getCrc()) {
            logger.error("check crc err, sum {}, data {}", crc, req.toString());
        }
        // 尾巴
        req.setFooter(in.readUnsignedShortLE());
    }

    private byte checkCrc() {
        byte[] bytes = req.getBytes();
        int sum = 0;
        for (int i = 0; i < bytes.length - 3; i++) {
            sum += bytes[i];
        }
        return (byte) (sum & 0xff);
    }
}
