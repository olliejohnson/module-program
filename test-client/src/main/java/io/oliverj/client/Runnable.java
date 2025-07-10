package io.oliverj.client;

import io.netty.channel.ChannelHandlerContext;
import io.oliverj.module.network.TestPacket;
import io.oliverj.module.network.packet.event.EventRegistry;
import io.oliverj.module.network.packet.event.PacketSubscriber;
import io.oliverj.module.network.packet.registry.PacketRegistry;
import io.oliverj.module.registry.BuiltInRegistries;
import io.oliverj.module.registry.Registry;

public class Runnable {

    public static void main(String[] args) {
        Registry.addRegister(BuiltInRegistries.PACKET, new PacketRegistry());

        BuiltInRegistries.PACKET.get().registerPacket(0, TestPacket.class);

        TestClient client = null;

        try {
            client = new TestClient(new EventRegistry(), future -> {
                System.out.println("Client running");
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
