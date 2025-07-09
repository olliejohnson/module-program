package io.oliverj.module.network.io;

import io.oliverj.module.network.buffer.PacketBuffer;

public interface Encoder {
    void write(PacketBuffer buffer);
}
