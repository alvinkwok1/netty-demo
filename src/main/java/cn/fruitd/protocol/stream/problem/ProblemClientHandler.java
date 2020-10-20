package cn.fruitd.protocol.stream.problem;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

/**
 * <p>
 * description:
 * </p>
 *
 * @author guopeng
 * @date 10/14/2020
 * @since v1.0.11
 */
public class ProblemClientHandler extends ChannelInboundHandlerAdapter {

  public static final byte[] data = new byte[1500];

  @Override
  public void channelActive(ChannelHandlerContext ctx) throws Exception {
    while (true) {
      final ByteBuf m = ctx.alloc().buffer(1500);
      m.writeBytes(data);
      ctx.writeAndFlush(m);
      Thread.sleep(1);
    }
  }

  @Override
  public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
    cause.printStackTrace();
    ctx.close();
  }
}
