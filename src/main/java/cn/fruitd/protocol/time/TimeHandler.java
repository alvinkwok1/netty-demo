/*
 * @(#)TimeHandler.java      1.0   2018年8月6日
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
package cn.fruitd.protocol.time;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

/**
 * TimeHandler
 *
 * @author guopeng
 */
public class TimeHandler  extends ChannelInboundHandlerAdapter {
    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        final ByteBuf time = ctx.alloc().buffer(4);
        time.writeInt((int)(System.currentTimeMillis() / 1000L + 2208988800L));
        ctx.writeAndFlush(time).addListener(ChannelFutureListener.CLOSE);
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        cause.printStackTrace();
        ctx.close();
    }
}
