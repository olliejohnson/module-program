package io.oliverj.module.network.io;

import io.oliverj.module.network.buffer.PacketBuffer;

public interface CallableDecoder<T> {
    T read(PacketBuffer buffer);
}
