package io.oliverj.client;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioIoHandler;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.oliverj.module.network.TestPacket;
import io.oliverj.module.network.ValidPacket;
import io.oliverj.module.network.packet.event.EventRegistry;
import io.oliverj.module.network.packet.event.PacketSubscriber;
import io.oliverj.module.network.packet.handler.PacketChannelInboundHandler;
import io.oliverj.module.network.packet.handler.PacketEncoder;
import io.oliverj.module.network.packet.io.Responder;
import io.oliverj.module.network.packet.registry.PacketRegistry;
import io.oliverj.module.registry.BuiltInRegistries;
import io.oliverj.module.registry.Registry;

import java.net.InetSocketAddress;
import java.util.UUID;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import java.util.function.Consumer;

public class TestClient extends ChannelInitializer<Channel> {

    public final Bootstrap bootstrap;
    public final PacketRegistry packetRegistry;
    public final EventRegistry eventRegistry;

    public final EventLoopGroup workerGroup = new MultiThreadIoEventLoopGroup(NioIoHandler.newFactory());

    public TestClient(EventRegistry eventRegistry, Consumer<Future<? super Void>> doneCallback) {
        this.packetRegistry = Registry.getRegistry(BuiltInRegistries.PACKET);
        this.eventRegistry = eventRegistry;
        this.bootstrap = new Bootstrap()
                .option(ChannelOption.AUTO_READ, true)
                .group(workerGroup)
                .handler(this)
                .channel(NioSocketChannel.class);

        this.eventRegistry.registerEvents(this);

        try {
            this.bootstrap.connect(new InetSocketAddress("127.0.0.1", 1234))
                    .awaitUninterruptibly().sync().addListener(doneCallback::accept);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void initChannel(Channel ch) throws Exception {
        ch.pipeline()
                .addLast(new PacketEncoder(packetRegistry), new PacketEncoder(packetRegistry), new PacketChannelInboundHandler(eventRegistry));
    }

    @PacketSubscriber
    public void onPacket(ValidPacket packet, Responder responder) {
        responder.respond(new TestPacket().setUuid(UUID.randomUUID()));
    }

    public void shutdown() {
        try {
            workerGroup.shutdownGracefully().get();
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
        }
    }
}
