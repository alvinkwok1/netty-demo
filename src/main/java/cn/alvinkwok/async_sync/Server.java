
package cn.alvinkwok.async_sync;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.buffer.ByteBuf;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.util.CharsetUtil;

public class Server {

    public static void main(String[] args) throws InterruptedException {
        ServerBootstrap bootstrap = new ServerBootstrap();
        EventLoopGroup group = new NioEventLoopGroup();
        bootstrap.group(group)
            .channel(NioServerSocketChannel.class)
            .childHandler(new ChannelInitializer<SocketChannel>() {
                @Override
                protected void initChannel(SocketChannel ch) throws Exception {
                    ch.pipeline().addLast(new ChannelInboundHandlerAdapter() {
                        @Override
                        public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
                            System.out.println("接收到消息" + ((ByteBuf) msg).toString(CharsetUtil.UTF_8));
                            Thread.sleep(5000);
                            ctx.channel().writeAndFlush(msg).addListener(ChannelFutureListener.CLOSE);
                        }
                    });
                }
            });
        ChannelFuture future = bootstrap.bind(8000).sync();
        future.channel().closeFuture().sync();
        group.shutdownGracefully();
    }
}
