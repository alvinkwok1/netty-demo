package cn.alvinkwok.async_sync;

import io.netty.bootstrap.Bootstrap;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.util.CharsetUtil;
import io.netty.util.ReferenceCountUtil;

import java.nio.charset.StandardCharsets;


public class Client {

    public static void main(String[] args) throws InterruptedException {
        Bootstrap bootstrap = new Bootstrap();
        EventLoopGroup group = new NioEventLoopGroup();
        bootstrap.group(group)
            .channel(NioSocketChannel.class)
            .handler(new ChannelInitializer<SocketChannel>() {
                @Override
                protected void initChannel(SocketChannel ch) throws Exception {
                    ch.pipeline().addLast(new ChannelInboundHandlerAdapter() {
                        @Override
                        public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
                            try {
                                String result = ((ByteBuf)msg).toString(CharsetUtil.UTF_8);
                                DefaultFuture.receive(ctx.channel(), result);
                            } finally {
                                ReferenceCountUtil.release(msg);
                            }
                        }
                    });
                }
            });
        ChannelFuture future = bootstrap.connect("127.0.0.1", 8000).sync();

        DefaultFuture requestFuture = new DefaultFuture(future.channel(), 2000);
        future.channel().writeAndFlush(Unpooled.copiedBuffer("123".getBytes(StandardCharsets.UTF_8)));
        Object result = requestFuture.get();
        if (result != null) {
            System.out.println("接收到结果" + result.toString());
        }
        group.shutdownGracefully();
    }
}
