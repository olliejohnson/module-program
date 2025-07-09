package io.oliverj.module.network.io;

import io.oliverj.module.network.buffer.PacketBuffer;

public interface CallableEncoder<T> {
    void write(T data, PacketBuffer buffer);
}
