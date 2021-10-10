package cn.alvinkwok.protocol.echo;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.util.ReferenceCountUtil;

/**
 * <p>
 * description:
 * </p>
 *
 * @author guopeng
 * @date 10/12/2020
 * @since v1.0.11
 */
public class EchoServerHandler extends ChannelInboundHandlerAdapter {
  @Override
  public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
    ctx.write(msg); // (1)
    ctx.flush(); // (2)
  }

  @Override
  public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
    cause.printStackTrace();
    ctx.close();
  }
}
