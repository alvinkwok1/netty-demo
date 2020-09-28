/*
 * @(#)FgFcIdleStateHandler.java      1.0   2018年10月16日
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

import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.timeout.IdleStateEvent;
import io.netty.handler.timeout.IdleStateHandler;

import java.util.concurrent.TimeUnit;

/**
 * @author wangxr
 */
public class ReaderIdleStateHandler extends IdleStateHandler {

	private static final int READER_IDLE_TIME = 10;

    public ReaderIdleStateHandler() {
        super(READER_IDLE_TIME, 0, 0, TimeUnit.SECONDS);
    }

    @Override
    protected void channelIdle(ChannelHandlerContext ctx, IdleStateEvent evt) {
        System.out.println("超时自动关闭");
        ctx.channel().close();
    }

}
