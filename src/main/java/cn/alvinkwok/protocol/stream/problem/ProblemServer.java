package cn.alvinkwok.protocol.stream.problem;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;

/**
 * <p>
 * description:
 * </p>
 *
 * @author guopeng
 * @date 10/14/2020
 * @since v1.0.11
 */
public class ProblemServer {
  private int port;

  public ProblemServer(int port) {
    this.port = port;
  }

  public void run() throws Exception {
    EventLoopGroup boss = new NioEventLoopGroup();
    EventLoopGroup worker = new NioEventLoopGroup();
    try {
      ServerBootstrap b = new ServerBootstrap();
      b.group(boss, worker)
        .channel(NioServerSocketChannel.class)
        .childHandler(new ChannelInitializer<SocketChannel>() {
          @Override
          protected void initChannel(SocketChannel ch) throws Exception {
            ch.pipeline().addLast(new ProblemServerHandler());
          }
        })
        .option(ChannelOption.SO_BACKLOG, 128);
      ChannelFuture f = b.bind(port).sync();
      f.channel().closeFuture().sync();
    } finally {
      worker.shutdownGracefully();
      boss.shutdownGracefully();
    }
  }

  public static void main(String[] args) throws Exception {
    ProblemServer problemServer = new ProblemServer(8080);
    problemServer.run();
  }

}
