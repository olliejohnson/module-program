package io.oliverj.module.network;

import io.oliverj.module.network.packet.Packet;
import io.oliverj.module.network.packet.buffer.PacketBuffer;

import java.util.UUID;

public class TestPacket extends Packet {

    private UUID uuid;

    public TestPacket() {}

    @Override
    public void read(PacketBuffer buffer) {
        uuid = buffer.readUUID();
    }

    @Override
    public void write(PacketBuffer buffer) {
        buffer.writeUUID(uuid);
    }

    public UUID getUuid() {
        return uuid;
    }

    public TestPacket setUuid(UUID uuid) {
        this.uuid = uuid;
        return this;
    }
}
