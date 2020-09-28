/*
 * @(#)Client.java      1.0   2018年8月6日
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
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.net.Socket;

/**
 * Client
 *
 * @author guopeng
 */
public class Client {

    public static void main(String[] args) throws IOException, InterruptedException {
        Socket socket = new Socket();
        InetSocketAddress socketAddress = new InetSocketAddress("localhost",20000);
        socket.connect(socketAddress);

        OutputStream os = socket.getOutputStream();
        os.write(new byte[10]);

        Thread.sleep(3000);
        os.close();
        socket.close();
    }
}
