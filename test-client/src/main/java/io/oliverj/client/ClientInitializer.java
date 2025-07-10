package io.oliverj.client;

import io.netty.channel.Channel;
import io.netty.channel.ChannelInitializer;
import io.oliverj.module.network.packet.event.EventRegistry;
import io.oliverj.module.network.packet.handler.PacketChannelInboundHandler;
import io.oliverj.module.network.packet.handler.PacketDecoder;
import io.oliverj.module.network.packet.handler.PacketEncoder;
import io.oliverj.module.network.packet.registry.PacketRegistry;
import io.oliverj.module.registry.BuiltInRegistries;
import io.oliverj.module.registry.Registry;

public class ClientInitializer extends ChannelInitializer<Channel> {

    private final PacketRegistry packetRegistry;
    private final EventRegistry eventRegistry;

    public ClientInitializer(EventRegistry e) {
        this.packetRegistry = Registry.getRegistry(BuiltInRegistries.PACKET);
        this.eventRegistry = e;
    }

    @Override
    protected void initChannel(Channel ch) throws Exception {
        ch.pipeline().addLast(
                new PacketEncoder(packetRegistry),
                new PacketDecoder(packetRegistry),
                new PacketChannelInboundHandler(eventRegistry)
        );
    }
}
