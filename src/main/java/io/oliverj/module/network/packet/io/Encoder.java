package io.oliverj.module.network.packet.io;

import io.oliverj.module.network.packet.buffer.PacketBuffer;

public interface Encoder {
    void write(PacketBuffer buffer);
}
