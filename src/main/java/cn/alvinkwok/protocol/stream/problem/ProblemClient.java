package cn.alvinkwok.protocol.stream.problem;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;

/**
 * <p>
 * description:
 * </p>
 *
 * @author guopeng
 * @date 10/14/2020
 * @since v1.0.11
 */
public class ProblemClient {

  public static void main(String[] args) throws InterruptedException {
    int port = 8080;
    EventLoopGroup worker = new NioEventLoopGroup();
    try {
      Bootstrap b = new Bootstrap();
      b.group(worker)
        .channel(NioSocketChannel.class)
        .option(ChannelOption.SO_KEEPALIVE, true)
        .handler(new ChannelInitializer<SocketChannel>() {
          @Override
          protected void initChannel(SocketChannel ch) throws Exception {
            ch.pipeline().addLast(new ProblemClientHandler());
          }
        });
      ChannelFuture f = b.connect("localhost", port).sync();
      f.channel().closeFuture().sync();
    } finally {
      worker.shutdownGracefully();
    }
  }
}
