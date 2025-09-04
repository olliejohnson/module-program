package io.oliverj.module.network.packet.handler;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageDecoder;
import io.netty.handler.codec.DecoderException;
import io.oliverj.module.network.packet.Packet;
import io.oliverj.module.network.packet.buffer.PacketBuffer;
import io.oliverj.module.network.packet.registry.PacketRegistry;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

public class PacketDecoder extends ByteToMessageDecoder {

    public static final Logger LOGGER = LoggerFactory.getLogger(PacketDecoder.class);

    private final PacketRegistry packetRegistry;

    public PacketDecoder(PacketRegistry packetRegistry) {
        this.packetRegistry = packetRegistry;
    }

    protected void decode(ChannelHandlerContext channelHandlerContext, ByteBuf byteBuf, List<Object> list) throws Exception {
        LOGGER.debug("Decoding packet");
        int packetId = byteBuf.readInt();
        if (!packetRegistry.containsPacketId(packetId)) {
            throw new DecoderException("Received invalid packet id");
        }
        long sessionId = byteBuf.readLong();
        PacketBuffer buffer = new PacketBuffer(byteBuf.readBytes(byteBuf.readableBytes()));
        Packet packet = packetRegistry.constructPacket(packetId);
        packet.setSessionId(sessionId);
        packet.read(buffer);

        list.add(packet);
    }
}
