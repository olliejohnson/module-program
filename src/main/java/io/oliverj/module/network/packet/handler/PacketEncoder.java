package io.oliverj.module.network.handler;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.EncoderException;
import io.netty.handler.codec.MessageToByteEncoder;
import io.oliverj.module.network.Packet;
import io.oliverj.module.network.buffer.PacketBuffer;
import io.oliverj.module.network.registry.PacketRegistry;

public class PacketEncoder extends MessageToByteEncoder<Packet> {

    private final PacketRegistry packetRegistry;

    public PacketEncoder(PacketRegistry packetRegistry) {
        this.packetRegistry = packetRegistry;
    }

    protected void encode(ChannelHandlerContext channelHandlerContext, Packet packet, ByteBuf byteBuf) throws Exception {
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
