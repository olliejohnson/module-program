package io.oliverj.module.network.handler;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.oliverj.module.network.ConnectionManager;
import io.oliverj.module.network.EstablishPacket;
import io.oliverj.module.network.packet.Packet;

public class SessionEstablishListener extends SimpleChannelInboundHandler<Packet> {

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, Packet msg) throws Exception {
        if (msg instanceof EstablishPacket pkt) {
            ConnectionManager.addClient(pkt.getUsername(), ctx.channel());
        }
    }
}
