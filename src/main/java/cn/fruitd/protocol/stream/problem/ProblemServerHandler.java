package cn.fruitd.protocol.stream.problem;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.util.ReferenceCountUtil;

/**
 * <p>
 * description:
 * </p>
 *
 * @author guopeng
 * @date 10/14/2020
 * @since v1.0.11
 */
public class ProblemServerHandler extends ChannelInboundHandlerAdapter {

  private static final int PACKET_LEN = 1500;

  @Override
  public void channelActive(ChannelHandlerContext ctx) throws Exception {
    System.out.println("链接建立");
  }

  @Override
  public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
    ByteBuf m = (ByteBuf) msg;
    try {
      int len = m.readableBytes();
      if (len > PACKET_LEN) {
        System.out.println("接收到粘包,本次长度"+len);
      } else if (len < PACKET_LEN) {
        System.out.println("接收到半包"+len);
      }
    }finally {
      ReferenceCountUtil.release(m);
    }
  }

  @Override
  public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
    cause.printStackTrace();
    ctx.close();
  }
}
