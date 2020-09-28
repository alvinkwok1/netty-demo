/*
 * @(#)EchoServer.java      1.0   2018年8月6日
 *
 * Copyright (c) 2009 fingard System Engineering Co., Ltd.
 * All rights reserved.
 *
 * This software is the confidential and proprietary information of
 * fingard System Engineering Co., Ltd. ("Confidential Information").
 * You shall not disclose such Confidential Information and shall use it
 * only in accordance with the terms of the license agreement you entered
 * into with fingard.
 */
package cn.fruitd.close;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.util.CharsetUtil;


/**
 * EchoServer
 *
 * @author guopeng
 */
public class EchoServerHandler extends ChannelInboundHandlerAdapter {

    public static String str;
    static {
        StringBuilder sb = new StringBuilder();
        for (int i=0;i<10000;i++) {
            sb.append("1");
        }
        str= sb.toString();

    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        ByteBuf in = (ByteBuf ) msg;
        System.out.println("Server received" + in.toString(CharsetUtil.UTF_8));
        ctx.writeAndFlush(str);
    }

    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
        System.out.println("客户端主动关闭连接");
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        cause.printStackTrace();
        ctx.close();
    }
}
