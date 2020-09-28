/*
 * @(#)Server.java      1.0   2018年8月6日
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
package cn.fruitd.test;

import java.io.IOException;
import java.io.InputStream;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * Server
 *
 * @author guopeng
 */
public class Server {
    public static void main(String[] args) throws IOException, InterruptedException {
        ServerSocket serverSocket = null;
            serverSocket = new ServerSocket();
        InetSocketAddress inetSocketAddress = new InetSocketAddress(20000);
        serverSocket.bind(inetSocketAddress);
        Socket socket = serverSocket.accept();
        InputStream is = socket.getInputStream();
        byte[] b = new byte[10];
        is.read(new byte[10]);

        Thread.sleep(5000);
        // 客户端主动关闭
        socket.close();
    }
}
