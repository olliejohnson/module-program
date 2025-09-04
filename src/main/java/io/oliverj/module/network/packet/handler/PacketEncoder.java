package io.oliverj.module.network.packet.handler;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.EncoderException;
import io.netty.handler.codec.MessageToByteEncoder;
import io.oliverj.module.network.packet.Packet;
import io.oliverj.module.network.packet.buffer.PacketBuffer;
import io.oliverj.module.network.packet.registry.PacketRegistry;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class PacketEncoder extends MessageToByteEncoder<Packet> {

    public static final Logger LOGGER = LoggerFactory.getLogger(PacketEncoder.class);

    private final PacketRegistry packetRegistry;

    public PacketEncoder(PacketRegistry packetRegistry) {
        this.packetRegistry = packetRegistry;
    }

    protected void encode(ChannelHandlerContext channelHandlerContext, Packet packet, ByteBuf byteBuf) {
        LOGGER.debug("Encoding packet");
        int packetId = packetRegistry.getPacketId(packet.getClass());
        if (packetId < 0) {
            throw new EncoderException("Returned PacketId by registry is < 0");
        }
        byteBuf.writeInt(packetId);
        byteBuf.writeLong(packet.getSessionId());

        PacketBuffer buffer = new PacketBuffer();
        packet.write(buffer);
        byteBuf.writeBytes(buffer);
    }
}
