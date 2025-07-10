package io.oliverj.client;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioIoHandler;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.oliverj.module.network.TestPacket;
import io.oliverj.module.network.ValidPacket;
import io.oliverj.module.network.packet.event.EventRegistry;
import io.oliverj.module.network.packet.event.PacketSubscriber;
import io.oliverj.module.network.packet.io.Responder;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.net.InetSocketAddress;
import java.util.UUID;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.function.Consumer;

public class TestClient {

    public static final Logger LOGGER = LogManager.getLogger();

    public final Bootstrap bootstrap;
    public final EventRegistry eventRegistry;

    public final EventLoopGroup workerGroup = new MultiThreadIoEventLoopGroup(NioIoHandler.newFactory());

    public TestClient(String host, int port, int maxAttempts, int initialDelayMillis, EventRegistry eventRegistry, Consumer<Future<? super Void>> doneCallback) {
        this.eventRegistry = eventRegistry;
        this.bootstrap = new Bootstrap()
                .option(ChannelOption.AUTO_READ, true)
                .group(workerGroup)
                .handler(new ClientInitializer(eventRegistry))
                .channel(NioSocketChannel.class);

        this.eventRegistry.registerEvents(this);
        InetSocketAddress address = new InetSocketAddress(host, port);

        this.connectWithRetry(address, maxAttempts, initialDelayMillis, doneCallback);
    }

    private void connectWithRetry(InetSocketAddress address, int maxAttempts, long initialDelayMillis, Consumer<Future<? super Void>> doneCallback) {
        int attempt = 0;
        long delay = initialDelayMillis;

        while (attempt < maxAttempts) {
            attempt++;
            LOGGER.info("Attempting to connect (attempt {} of {})...", attempt, maxAttempts);
            ChannelFuture future = bootstrap.connect(address).awaitUninterruptibly();

            if (future.isSuccess()) {
                LOGGER.info("Connected successfully on attempt {}", attempt);
                future.addListener(doneCallback::accept);
                return;
            } else {
                LOGGER.warn("Connection attempt {} failed: {}", attempt, future.cause().getMessage());
            }

            if (attempt < maxAttempts) {
                try {
                    LOGGER.info("Retrying in {} ms...", delay);
                    Thread.sleep(delay);
                } catch (InterruptedException e) {
                    LOGGER.error("Retry interrupted", e);
                    Thread.currentThread().interrupt();
                    break;
                }
                delay = Math.min(delay * 2, TimeUnit.SECONDS.toMillis(30)); // Cap at 30 seconds
            }
        }

        LOGGER.error("Failed to connect to {} after {} attempts.", address, maxAttempts);

        this.shutdown();
    }


    @PacketSubscriber
    public void onPacket(ValidPacket packet, Responder responder) {
        LOGGER.debug("Received ValidPacket");
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
