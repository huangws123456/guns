package cn.stylefeng.guns.sys.netty.service;

import io.netty.buffer.Unpooled;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.channel.group.ChannelGroup;
import io.netty.handler.codec.http.websocketx.*;

import java.util.HashMap;
import java.util.Map;

public class TextWebSocketFrameHandler extends SimpleChannelInboundHandler<TextWebSocketFrame> {
    private ChannelGroup channelGroup;
    public TextWebSocketFrameHandler(ChannelGroup channelGroup){
        this.channelGroup=channelGroup;
    }

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        System.out.println("EchoServer 有WEB端链接成功:" + ctx.channel().remoteAddress());
        super.channelActive(ctx);
        channelGroup.add(ctx.channel());
    }

    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
        System.out.println("EchoServer 有WEB断开连接:" + ctx.channel().remoteAddress());
        super.channelInactive(ctx);
        channelGroup.remove(ctx.channel());
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        super.exceptionCaught(ctx, cause);
        cause.printStackTrace();
        ctx.close();
    }

    @Override
    public void userEventTriggered(ChannelHandlerContext ctx, Object evt) throws Exception {
        if (evt == WebSocketServerProtocolHandler
                .ServerHandshakeStateEvent.HANDSHAKE_COMPLETE) {
            //websocket 连接成功 移除当前通道的
            //如果该事件 表示握手成 功，则从该 Channelipeline 中移除 Http- RequestHandler， 因为将不会 接收到任何 HTTP 消息了
            ctx.pipeline().remove(HttpRequestHandler.class);
        } else {
            super.userEventTriggered(ctx, evt);
        }
    }

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, TextWebSocketFrame msg) throws Exception {
        String s=msg.text();
        System.out.println("服务端接收到web消息:" + s);
        if(!s.contains("cmid")){
            return;
        }
        sendToAllApp(ctx,s);
    }

    public void sendToAllApp(ChannelHandlerContext ctx,String receiveMsg){
        //向所有app发送消息

       EchoServer.sendAllMsg(receiveMsg);
       sendMsg(ctx.channel(),100,null);


    }

    //向单个websocket发送消息
    public boolean sendMsg(Channel channel, int code, Map<String,Object> params) {
        if(params==null){
            params=new HashMap<>();
        }
        params.put("cmid",code);

        String msg=EchoServer.gson.toJson(params);

        System.out.println("向web端回送消息:" + msg);

        try {
            if (channel != null && channel.isActive()) {
                channel.writeAndFlush(new TextWebSocketFrame(msg + "\n"));
                return true;
            }
        } catch (Exception ex) {
            System.out.println("send msg to client fail:"+ex.toString());
        }
        return false;
    }
    public  boolean sendAllMsg(String msg){
        System.out.println("EchoServer 群发送消息:" + msg);
        try {
            if (channelGroup != null&& channelGroup.size()>0) {
                channelGroup.writeAndFlush(Unpooled.copiedBuffer((msg + "\n").getBytes()));
                return true;
            }
        } catch (Exception ex) {
            System.out.println("send msg to all client fail:"+ex.toString());
        }
        return false;
    }
}
