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
import java.io.InputStream;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

/**
 * Client
 *
 * @author guopeng
 */
public class Client {

    public static void main(String[] args) throws IOException, InterruptedException {
        Runnable r = new Runnable() {
            @Override
            public void run() {
                while (true) {
                    try {
                        Socket socket = new Socket();
                        socket.setReuseAddress(true);
                        InetSocketAddress socketAddress = new InetSocketAddress("10.60.45.84", 9000);
                        socket.connect(socketAddress);

                        OutputStream os = socket.getOutputStream();
                        os.write("test".getBytes());
                        InputStream is = socket.getInputStream();
                        while (is.available() > 0) {
                            is.read();
                        }
                        os.close();
                        is.close();
                        socket.close();
                        Thread.sleep(20);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
        };
        Executor pool = Executors.newCachedThreadPool();
        for (int i=0;i<4;i++) {
            pool.execute(r);
        }
    }
}
