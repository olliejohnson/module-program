package io.oliverj.module.network;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.nio.NioIoHandler;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.oliverj.module.network.packet.event.EventRegistry;
import io.oliverj.module.network.packet.handler.PacketChannelInboundHandler;
import io.oliverj.module.network.packet.handler.PacketDecoder;
import io.oliverj.module.network.packet.handler.PacketEncoder;
import io.oliverj.module.network.packet.registry.PacketRegistry;
import io.oliverj.module.network.packet.response.RespondingPacket;
import io.oliverj.module.registry.BuiltInRegistries;
import io.oliverj.module.registry.Registry;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.net.InetSocketAddress;
import java.util.UUID;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import java.util.concurrent.ThreadFactory;
import java.util.function.Consumer;

public class Server{

    public static final Logger LOGGER = LogManager.getLogger();

    private final ServerBootstrap bootstrap;

    private EventLoopGroup parentGroup = new MultiThreadIoEventLoopGroup(NioIoHandler.newFactory());
    private EventLoopGroup workerGroup = new MultiThreadIoEventLoopGroup(NioIoHandler.newFactory());

    private Channel connectedChannel;

    public Server(EventRegistry eventRegistry, Consumer<Future<? super Void>> doneCallback) {
        this.bootstrap = new ServerBootstrap()
                .option(ChannelOption.AUTO_READ, true)
                .childOption(ChannelOption.SO_KEEPALIVE, true)
                .group(parentGroup, workerGroup)
                .childHandler(new ServerInitializer(eventRegistry))
                .channel(NioServerSocketChannel.class);

        try {
            this.bootstrap.bind(new InetSocketAddress("127.0.0.1", 1234))
                    .awaitUninterruptibly().sync().addListener(doneCallback::accept);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public void shutdown() {
        try {
            parentGroup.shutdownGracefully().get();
            workerGroup.shutdownGracefully().get();
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
        }
    }
}
