package io.oliverj.client.handler;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.oliverj.module.network.TestPacket;
import io.oliverj.module.network.packet.Packet;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class TestPacketHandler extends SimpleChannelInboundHandler<Packet> {

    public static final Logger LOGGER = LogManager.getLogger();

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, Packet msg) throws Exception {
        if (msg instanceof TestPacket tst) {
            LOGGER.info("Received Test Packet: {}", tst.getUuid());
        }
    }
}
