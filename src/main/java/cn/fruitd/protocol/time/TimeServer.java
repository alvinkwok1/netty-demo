/*
 * @(#)TimeServer.java      1.0   2018年8月6日
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

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;

/**
 * TimeServer
 *
 * @author guopeng
 */
public class TimeServer {
    private int port;

    public TimeServer(int port) {
        this.port = port;
    }

    public void run () throws Exception{
        EventLoopGroup boss = new NioEventLoopGroup();
        EventLoopGroup worker = new NioEventLoopGroup();
        try {
            ServerBootstrap b = new ServerBootstrap();
            b.group(boss,worker)
                .channel(NioServerSocketChannel.class)
                .childHandler(new ChannelInitializer<SocketChannel>() {
                    @Override
                    protected void initChannel(SocketChannel ch) throws Exception {
                        ch.pipeline().addLast(new TimeHandler());
                    }
                })
            .option(ChannelOption.SO_BACKLOG,128);
            ChannelFuture f = b.bind(port).sync();
            f.channel().closeFuture().sync();
        }finally {
            worker.shutdownGracefully();
            boss.shutdownGracefully();
        }
    }

    public static void main(String[] args) throws Exception {
        TimeServer timeServer = new TimeServer(8080);
        timeServer.run();
    }
}
