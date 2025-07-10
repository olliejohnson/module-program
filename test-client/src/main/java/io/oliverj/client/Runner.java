package io.oliverj.client;

import io.oliverj.client.config.Config;
import io.oliverj.client.config.ConfigLoader;
import io.oliverj.module.network.EstablishPacket;
import io.oliverj.module.network.PacketIds;
import io.oliverj.module.network.TestPacket;
import io.oliverj.module.network.ValidPacket;
import io.oliverj.module.network.packet.registry.PacketRegistry;
import io.oliverj.module.registry.BuiltInRegistries;
import io.oliverj.module.registry.Registry;

public class Runner {

    public static void main(String[] args) {
        Registry.addRegister(BuiltInRegistries.PACKET, new PacketRegistry());

        BuiltInRegistries.PACKET.get().registerPacket(0, TestPacket.class);
        BuiltInRegistries.PACKET.get().registerPacket(1, ValidPacket.class);
        BuiltInRegistries.PACKET.get().registerPacket(PacketIds.ESTABLISH, EstablishPacket.class);

        TestClient client = null;

        Config config = ConfigLoader.load("config.json");

        String host = config.host;
        int port = config.port;
        int maxAttempts = config.max_attempts;
        int initialDelayMillis = config.initial_wait;

        try {
            client = new TestClient(host, port, 5, 1000, future -> {
                System.out.println("Client running");
                EstablishPacket est = new EstablishPacket();
                est.setUsername("test-client");
                TestClient.channel.writeAndFlush(est);
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
