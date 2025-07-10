package io.oliverj.module.network;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioIoHandler;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.oliverj.module.network.packet.Packet;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.net.InetSocketAddress;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import java.util.function.Consumer;

public class Server {

    public static final Logger LOGGER = LogManager.getLogger();

    private final Channel channel;

    private final EventLoopGroup parentGroup = new MultiThreadIoEventLoopGroup(NioIoHandler.newFactory());
    private final EventLoopGroup workerGroup = new MultiThreadIoEventLoopGroup(NioIoHandler.newFactory());

    private Channel connectedChannel;

    public Server(Consumer<Future<? super Void>> doneCallback) {
        ServerBootstrap bootstrap = new ServerBootstrap()
                .option(ChannelOption.AUTO_READ, true)
                .childOption(ChannelOption.SO_KEEPALIVE, true)
                .group(parentGroup, workerGroup)
                .childHandler(new ServerInitializer())
                .channel(NioServerSocketChannel.class);

        this.channel = bootstrap.bind(new InetSocketAddress("127.0.0.1", 1234)).channel();
    }

    public void shutdown() {
        LOGGER.info("Stopping thread groups.");
        try {
            parentGroup.shutdownGracefully().get();
            workerGroup.shutdownGracefully().get();
        } catch (InterruptedException | ExecutionException e) {
            LOGGER.error("Error shutting down networking thread", e);
        }
    }

    public void send(Packet packet) {
        channel.writeAndFlush(packet);
    }
}
