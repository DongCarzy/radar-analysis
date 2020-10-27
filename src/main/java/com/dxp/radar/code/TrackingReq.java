package com.dxp.radar.code;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.UnpooledByteBufAllocator;


/**
 * 客户端上报的跟踪数据包
 */
public class TrackingReq {

    /**
     * 数据包头,2字节,固定 0x2101
     */
    private short head = 0x2101;
    /**
     * 雷达设备唯一标识符, 2字节
     */
    private int no;
    /**
     * 跟踪识别号, 4字节
     */
    private long trackNo;
    /**
     * X坐标,4字节
     */
    private float x;
    /**
     * y坐标,4字节
     */
    private float y;
    /**
     * y坐标,4字节
     */
    private float z;
    /**
     * 速度, 4字节
     */
    private float speed;
    /**
     * 水平雷达散射面积, 2字节
     */
    private int horizontal;
    /**
     * 垂直雷达散射面积,2字节
     */
    private int vertical;
    /**
     * 触发原因, 1字节
     * <p>
     * 0x00:目标速度低于设定速度；
     * 0x01:目标速度高于设定速度；
     * 0x02:目标速度低于最低限速值；
     * 0x03:目标速度高于设定速度，并且在触发线范围内（正负1m），同时雷达工作在Trigger(Multi) feature mode模式下；
     * 0x04:目标变换车道；
     * 0x05:目标停止；
     * 0x06:目标行驶于错误道路；
     * 0x0B:目标在触发线1范围内（正负1m）；
     * 0x15:目标在触发线2范围内（正负1m）；0x16:正常情况用
     */
    private byte cause;
    /**
     * 车道号, 1字节
     */
    private byte laneNo;
    /**
     * 目标车类型, 1字节
     */
    private byte targetCarType;
    /**
     * 校验和, 1字节, 除了CRC和Footer项
     */
    private byte crc;
    /**
     * 结束符, 1字节, 默认 0xDEFF
     */
    private int footer = 0xDEFF;

    /**
     * 全部字节码.
     */
    private byte[] bytes;

    public short getHead() {
        return head;
    }

    public void setHead(short head) {
        this.head = head;
    }

    public int getNo() {
        return no;
    }

    public void setNo(int no) {
        this.no = no;
    }

    public long getTrackNo() {
        return trackNo;
    }

    public void setTrackNo(long trackNo) {
        this.trackNo = trackNo;
    }

    public float getX() {
        return x;
    }

    public void setX(float x) {
        this.x = x;
    }

    public float getY() {
        return y;
    }

    public void setY(float y) {
        this.y = y;
    }

    public float getZ() {
        return z;
    }

    public void setZ(float z) {
        this.z = z;
    }

    public float getSpeed() {
        return speed;
    }

    public void setSpeed(float speed) {
        this.speed = speed;
    }

    public int getHorizontal() {
        return horizontal;
    }

    public void setHorizontal(int horizontal) {
        this.horizontal = horizontal;
    }

    public int getVertical() {
        return vertical;
    }

    public void setVertical(int vertical) {
        this.vertical = vertical;
    }

    public byte getCause() {
        return cause;
    }

    public void setCause(byte cause) {
        this.cause = cause;
    }

    public byte getLaneNo() {
        return laneNo;
    }

    public void setLaneNo(byte laneNo) {
        this.laneNo = laneNo;
    }

    public byte getTargetCarType() {
        return targetCarType;
    }

    public void setTargetCarType(byte targetCarType) {
        this.targetCarType = targetCarType;
    }

    public byte getCrc() {
        return crc;
    }

    public void setCrc(byte crc) {
        this.crc = crc;
    }

    public int getFooter() {
        return footer;
    }

    public void setFooter(int footer) {
        this.footer = footer;
    }

    public byte[] getBytes() {
        if (bytes == null) {
            synchronized (this) {
                if (bytes == null) {
                    createBytes();
                }
            }
        }
        return bytes;
    }

    public void setBytes(byte[] bytes) {
        this.bytes = bytes;
    }

    private void createBytes() {
        ByteBuf byteBuf = UnpooledByteBufAllocator.DEFAULT.buffer(34);
        try {
            byteBuf.writeShort(0x2101);
            byteBuf.writeShort(this.getNo());
            byteBuf.writeInt((int) (trackNo & 0xFFFF));
            byteBuf.writeFloat(this.getX());
            byteBuf.writeFloat(this.getY());
            byteBuf.writeFloat(this.getZ());
            byteBuf.writeFloat(this.getSpeed());
            byteBuf.writeShort(this.getHorizontal());
            byteBuf.writeShort(this.getVertical());
            byteBuf.writeByte(this.getCause());
            byteBuf.writeByte(this.getLaneNo());
            byteBuf.writeByte(this.getTargetCarType());
            byteBuf.writeByte(this.getCrc());
            byteBuf.writeShort(0xDEFF);
            this.bytes = new byte[34];
            byteBuf.readBytes(bytes);
        } finally {
            byteBuf.readableBytes();
        }
    }

    @Override
    public String toString() {
        return "TrackingReq{" +
                "head=" + head +
                ", no=" + no +
                ", trackNo=" + trackNo +
                ", x=" + x +
                ", y=" + y +
                ", z=" + z +
                ", speed=" + speed +
                ", horizontal=" + horizontal +
                ", vertical=" + vertical +
                ", cause=" + cause +
                ", laneNo=" + laneNo +
                ", targetCarType=" + targetCarType +
                ", crc=" + crc +
                ", footer=" + footer +
                '}';
    }
}
