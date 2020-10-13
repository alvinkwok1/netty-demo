package cn.fruitd.protocol.discard;

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
 * @date 10/12/2020
 * @since v1.0.11
 */
public class DiscardServer {

  private int port;

  public DiscardServer(int port) {
    this.port = port;
  }

  public void run()  throws Exception{
    EventLoopGroup bossGroup = new NioEventLoopGroup();
    EventLoopGroup workGroup = new NioEventLoopGroup();
    try{
      ServerBootstrap b = new ServerBootstrap();
      b.group(bossGroup,workGroup)
        .channel(NioServerSocketChannel.class)
        .childHandler(new ChannelInitializer<SocketChannel>() {
          protected void initChannel(SocketChannel ch) throws Exception {
           ch.pipeline().addLast(new DiscardServerHandler());
          }
        })
        // 设置等待队列大小
      .option(ChannelOption.SO_BACKLOG,128) // (5)
        // 设置TCP连接为保活连接
      .childOption(ChannelOption.SO_KEEPALIVE,true); //(6)

      // 绑定端口,并开始接收连接
      ChannelFuture f = b.bind(port).sync(); //(7)

      // 等待服务器的Socket关闭
      f.channel().closeFuture().sync();
    }finally {
      workGroup.shutdownGracefully();
      bossGroup.shutdownGracefully();
    }
  }

  public static void main(String[] args) throws Exception {
    int port = 8080;
    if (args.length > 0) {
      port = Integer.valueOf(args[0]);
    }
    DiscardServer discardServer = new DiscardServer(8080);
    discardServer.run();
  }
}
