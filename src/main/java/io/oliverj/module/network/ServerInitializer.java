package io.oliverj.module.network;

import io.netty.channel.Channel;
import io.netty.channel.ChannelInitializer;
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

public class ServerInitializer extends ChannelInitializer<Channel> {

    public static final Logger LOGGER = LogManager.getLogger();

    private final PacketRegistry registry;
    private final EventRegistry eventRegistry;

    public ServerInitializer(EventRegistry eventRegistry) {
        this.registry = Registry.getRegistry(BuiltInRegistries.PACKET);
        this.eventRegistry = eventRegistry;
    }

    @Override
    protected void initChannel(Channel ch) throws Exception {
        ch.pipeline()
                .addLast(new PacketEncoder(registry), new PacketDecoder(registry), new PacketChannelInboundHandler(eventRegistry));
        new RespondingPacket<>(new ValidPacket(), TestPacket.class, packet -> {
            LOGGER.info("Received: {}", packet.getUuid());
        }).send(ch);
    }
}
