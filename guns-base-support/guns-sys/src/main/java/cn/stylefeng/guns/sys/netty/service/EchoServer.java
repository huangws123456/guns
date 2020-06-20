package cn.stylefeng.guns.sys.netty.service;

import com.google.gson.Gson;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.group.ChannelGroup;
import io.netty.channel.group.DefaultChannelGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.DelimiterBasedFrameDecoder;
import io.netty.handler.codec.http.HttpObjectAggregator;
import io.netty.handler.codec.http.HttpServerCodec;
import io.netty.handler.codec.http.websocketx.WebSocketServerProtocolHandler;
import io.netty.handler.codec.string.StringDecoder;
import io.netty.handler.codec.string.StringEncoder;
import io.netty.handler.stream.ChunkedWriteHandler;
import io.netty.util.concurrent.ImmediateEventExecutor;

import java.util.HashMap;
import java.util.Map;

public class EchoServer {
    public final static Gson gson=new Gson();
    private final static ChannelGroup channelGroup =
            new DefaultChannelGroup(ImmediateEventExecutor.INSTANCE);
    private final static ChannelGroup channelGroup2 =
            new DefaultChannelGroup(ImmediateEventExecutor.INSTANCE);
    private final int tcpport;
    private final int webport;

    public EchoServer(int tcpport,int webport) {
        this.tcpport = tcpport;
        this.webport = webport;
    }

    public void start() throws Exception{
        EventLoopGroup bossGroup = new NioEventLoopGroup();
        EventLoopGroup workerGroup = new NioEventLoopGroup();
        try {
            ServerBootstrap tcpb = new ServerBootstrap();
            ServerBootstrap webb = new ServerBootstrap();
            tcpb.group(bossGroup,workerGroup).channel(NioServerSocketChannel.class)
                    .childHandler(new ChannelInitializer<SocketChannel>() {
                        @Override
                        protected void initChannel(SocketChannel socketChannel) throws Exception {
                            ChannelPipeline p = socketChannel.pipeline();
                            ByteBuf delimiter = Unpooled.buffer();
                            delimiter.writeBytes("\n".getBytes());
                            p.addLast(new DelimiterBasedFrameDecoder(2 * 1024 * 1024, delimiter));
                            p.addLast(new StringDecoder());
                            p.addLast(new StringEncoder());
                            p.addLast(new EchoServerHandler(channelGroup));
                        }
                    });

            webb.group(bossGroup,workerGroup).channel(NioServerSocketChannel.class)
                    .childHandler(new ChannelInitializer<SocketChannel>() {
                        @Override
                        protected void initChannel(SocketChannel socketChannel) throws Exception {
                            ChannelPipeline pipeline = socketChannel.pipeline();
                            pipeline.addLast(new HttpServerCodec());
                            pipeline.addLast(new ChunkedWriteHandler());
                            pipeline.addLast(new HttpObjectAggregator(64 * 1024));
                            pipeline.addLast(new HttpRequestHandler("/websocket"));
                            pipeline.addLast(new WebSocketServerProtocolHandler("/websocket"));
                            pipeline.addLast(new TextWebSocketFrameHandler(channelGroup2));
                        }
                    });
            ChannelFuture tcpfuture = tcpb.bind(tcpport).sync();
            ChannelFuture webfuture = webb.bind(webport).sync();
            tcpfuture.channel().closeFuture().sync();
            webfuture.channel().closeFuture().sync();
        } finally {
            bossGroup.shutdownGracefully().sync();
            workerGroup.shutdownGracefully();
        }
    }

    //向所有tcp连接发送消息
    public static boolean sendAllMsg(String msg){
        System.out.println("向app群发送消息:" + msg);
        try {
            if (channelGroup != null) {
                channelGroup.writeAndFlush(Unpooled.copiedBuffer((msg + "\n").getBytes()));
                return true;
            }
        } catch (Exception ex) {
            System.out.println("send msg to all client fail:"+ex.toString());
        }
        return false;
    }

}
