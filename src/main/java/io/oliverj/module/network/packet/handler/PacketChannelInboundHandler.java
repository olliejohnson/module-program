package io.oliverj.module.network.packet.handler;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.oliverj.module.network.packet.Packet;
import io.oliverj.module.network.packet.event.EventRegistry;
import io.oliverj.module.network.packet.response.RespondingPacket;

public class PacketChannelInboundHandler extends SimpleChannelInboundHandler<Packet> {

    private final EventRegistry eventRegistry;

    public PacketChannelInboundHandler(EventRegistry eventRegistry) {
        this.eventRegistry = eventRegistry;
    }

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, Packet msg) throws Exception {
        RespondingPacket.callReceive(msg);
        eventRegistry.invoke(msg, ctx);
    }
}
