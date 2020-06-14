package cn.stylefeng.guns.sys.netty.service;

import com.google.gson.Gson;
import io.netty.buffer.Unpooled;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.channel.group.ChannelGroup;

import java.util.HashMap;
import java.util.Map;

public class EchoServerHandler extends SimpleChannelInboundHandler<String> {
    private ChannelGroup channelGroup;
    private Gson gson;
    public EchoServerHandler(ChannelGroup channelGroup){
        this.channelGroup=channelGroup;
        gson=new Gson();
    }

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        System.out.println("EchoServer 有APP链接成功:" + ctx.channel().remoteAddress());
        super.channelActive(ctx);
        channelGroup.add(ctx.channel());
    }

    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
        System.out.println("EchoServer 有APP断开连接:" + ctx.channel().remoteAddress());
        super.channelInactive(ctx);
        channelGroup.remove(ctx.channel());
    }

    @Override
    protected void channelRead0(ChannelHandlerContext channelHandlerContext, String s) throws Exception {
        System.out.println("EchoServer 接收到消息:" + s);
        if(!s.contains("\"cmid\"")){
            return;
        }
        receiveMsg(channelHandlerContext.channel(),s);
    }

    @Override
    public void channelReadComplete(ChannelHandlerContext ctx) {
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext context, Throwable couse) {
        couse.printStackTrace();
        context.close();
    }

    public void receiveMsg(Channel channel,String receiveMsg){
        BaseNettyBean baseNettyBean=gson.fromJson(receiveMsg,BaseNettyBean.class);
        if(baseNettyBean.getCmid()==100){
            //回执包不进行转发
            sendMsg(channel,receiveMsg);
        }

    }

    //向单个socket发送消息
    public boolean sendMsg(Channel channel, String msg) {

        System.out.println("EchoServer 发送消息:" + msg);
        try {
            if (channel != null && channel.isActive()) {
                channel.writeAndFlush(Unpooled.copiedBuffer((msg + "\n").getBytes()));
                return true;
            }
        } catch (Exception ex) {
            System.out.println("send msg to client fail:"+ex.toString());
        }
        return false;
    }
}
