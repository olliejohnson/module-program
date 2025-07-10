package io.oliverj.client;

import io.netty.channel.Channel;
import io.netty.channel.ChannelInitializer;
import io.netty.handler.logging.LoggingHandler;
import io.oliverj.client.handler.TestPacketHandler;
import io.oliverj.module.network.packet.handler.PacketDecoder;
import io.oliverj.module.network.packet.handler.PacketEncoder;
import io.oliverj.module.network.packet.registry.PacketRegistry;
import io.oliverj.module.registry.BuiltInRegistries;
import io.oliverj.module.registry.Registry;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class ClientInitializer extends ChannelInitializer<Channel> {

    public static final Logger LOGGER = LogManager.getLogger();

    private final PacketRegistry packetRegistry;

    public ClientInitializer() {
        this.packetRegistry = Registry.getRegistry(BuiltInRegistries.PACKET);
    }

    @Override
    protected void initChannel(Channel ch) throws Exception {
        ch.pipeline().addLast(
                new PacketEncoder(packetRegistry),
                new PacketDecoder(packetRegistry),
                new TestPacketHandler(),
                new LoggingHandler()
        );
    }
}
