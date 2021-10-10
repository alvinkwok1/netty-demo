package cn.alvinkwok.protocol.discard;

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
public class DiscardServerHandler extends ChannelInboundHandlerAdapter {
  @Override
  public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
    try {
      // 不做任何操作
    } finally {
      // 释放引用计数
      ReferenceCountUtil.release(msg);
    }
  }

  @Override
  public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
    cause.printStackTrace();
    ctx.close();
  }
}
