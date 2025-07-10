package io.oliverj.module.network.packet.handler;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.oliverj.module.network.packet.Packet;
import io.oliverj.module.network.packet.event.EventRegistry;
import io.oliverj.module.network.packet.response.RespondingPacket;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class PacketChannelInboundHandler extends SimpleChannelInboundHandler<Packet> {

    public static final Logger LOGGER = LogManager.getLogger();

    private final EventRegistry eventRegistry;

    public PacketChannelInboundHandler(EventRegistry eventRegistry) {
        this.eventRegistry = eventRegistry;
    }

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, Packet msg) throws Exception {
        LOGGER.debug("Received packet");
        RespondingPacket.callReceive(msg);
        eventRegistry.invoke(msg, ctx);
    }
}
