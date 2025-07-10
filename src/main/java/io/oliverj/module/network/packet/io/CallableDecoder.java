package io.oliverj.module.network.packet.io;

import io.oliverj.module.network.packet.buffer.PacketBuffer;

public interface CallableDecoder<T> {
    T read(PacketBuffer buffer);
}
