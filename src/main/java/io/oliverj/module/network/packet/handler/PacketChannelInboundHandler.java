package io.oliverj.module.network.handler;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.oliverj.module.network.Packet;
import io.oliverj.module.network.event.EventRegistry;
import io.oliverj.module.network.response.RespondingPacket;

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
