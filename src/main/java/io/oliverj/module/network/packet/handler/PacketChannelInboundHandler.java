package io.oliverj.module.network.packet.handler;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.oliverj.module.network.packet.Packet;
import io.oliverj.module.network.packet.event.EventRegistry;
import io.oliverj.module.network.packet.response.RespondingPacket;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@SuppressWarnings("unused")
public class PacketChannelInboundHandler extends SimpleChannelInboundHandler<Packet> {

    public static final Logger LOGGER = LoggerFactory.getLogger(PacketChannelInboundHandler.class);

    private final EventRegistry eventRegistry;

    public PacketChannelInboundHandler(EventRegistry eventRegistry) {
        this.eventRegistry = eventRegistry;
    }

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, Packet msg) {
        LOGGER.debug("Received packet");
        RespondingPacket.callReceive(msg);
        eventRegistry.invoke(msg, ctx);
    }
}
