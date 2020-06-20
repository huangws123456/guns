package cn.stylefeng.guns.sys.netty.service;

import cn.stylefeng.guns.base.redis.RedisUtil;
import cn.stylefeng.guns.base.redis.SpringUtil;
import com.google.gson.Gson;
import io.netty.buffer.Unpooled;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.channel.group.ChannelGroup;
import io.netty.util.AttributeKey;
import org.springframework.beans.factory.annotation.Autowired;

import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.Map;

public class EchoServerHandler extends SimpleChannelInboundHandler<String> {

    private static  RedisUtil redisUtil ;
    static {
        redisUtil = SpringUtil.getBean(RedisUtil.class);
    }
    private ChannelGroup channelGroup;
    private Gson gson;
    private   final static Map <String ,Object>  map = new HashMap<String ,Object>();
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
        AttributeKey<String> key = AttributeKey.valueOf("user");
        BaseNettyBean baseNettyBean =  (BaseNettyBean)map.get(ctx.channel().attr(key).get());
        if (baseNettyBean != null){
            baseNettyBean.setDes("离线");
            SimpleDateFormat dateformat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            baseNettyBean.setDate(dateformat.format(System.currentTimeMillis()));
            map.put(baseNettyBean.getDeveice(),baseNettyBean);
            redisUtil.set("statusInfo",map);
        }

    }

    @Override
    protected void channelRead0(ChannelHandlerContext channelHandlerContext, String s) throws Exception {

        if(!s.contains("\"cmid\"")){
            return;
        }

        if (s.contains("\"cmid\":100")) {
            return;
        }
        System.out.println("服务端接收到app消息:" + s);
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
        if(baseNettyBean.getCmid()==995){
            //更新缓存
            SimpleDateFormat dateformat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            baseNettyBean.setDate(dateformat.format(System.currentTimeMillis()));
            baseNettyBean.setDes("在线");
            map.put(baseNettyBean.getDeveice(),baseNettyBean);
            AttributeKey<String> key = AttributeKey.valueOf("user");
            channel.attr(key).set(baseNettyBean.getDeveice());
            System.out.println("baseNettyBean=======在线=========="+baseNettyBean.getDeveice());
            redisUtil.set("statusInfo",map);
        }

    }

    //向单个socket发送消息
    public boolean sendMsg(Channel channel, String msg) {

        System.out.println("向特定web端发送消息:" + msg);
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
