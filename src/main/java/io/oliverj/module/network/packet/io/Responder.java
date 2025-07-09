package io.oliverj.module.network.io;

import io.netty.channel.ChannelOutboundInvoker;
import io.oliverj.module.network.Packet;

/**
 * Used to directly respond to send packets
 */
public interface Responder {
    /**
     * Respond to the received packet using another packet.
     *
     * @param packet The packet which contains the answer.
     */
    void respond(Packet packet);

    static Responder forId(Long sessionId, ChannelOutboundInvoker channelOutboundInvoker) {
        return packet -> {
            packet.setSessionId(sessionId);
            channelOutboundInvoker.writeAndFlush(packet);
        };
    }
}
