package io.oliverj.module.network;

import io.oliverj.module.network.packet.Packet;
import io.oliverj.module.network.packet.buffer.PacketBuffer;

public class EstablishPacket extends Packet {

    private String username;

    public EstablishPacket() {}

    @Override
    public void read(PacketBuffer buffer) {
        username = buffer.readUTF8();
    }

    @Override
    public void write(PacketBuffer buffer) {
        buffer.writeUTF8(username);
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
}
