package io.oliverj.module.network.packet.io;

import io.oliverj.module.network.packet.buffer.PacketBuffer;

public interface Decoder {
    void read(PacketBuffer buffer);
}
