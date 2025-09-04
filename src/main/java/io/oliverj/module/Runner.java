package io.oliverj.module;

import io.oliverj.module.network.*;
import io.oliverj.module.network.packet.registry.PacketRegistry;
import io.oliverj.module.plugin.PluginLoader;
import io.oliverj.module.plugin.PluginRegistry;
import io.oliverj.module.registry.BuiltInRegistries;
import io.oliverj.module.registry.Registry;
import io.oliverj.module.registry.RegistryKey;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.awt.*;

/**
 * Runner class
 */
public class Runner {

    /**
     * Logger
     */
    public static final Logger LOGGER = LoggerFactory.getLogger(Runner.class);

    /**
     * Network Server
     */
    private static volatile Server server;

    /**
     * Set the network server
     * @param srv The server
     */
    public static void setServer(Server srv) {
        server = srv;
    }

    /**
     * Main method
     * @param args program args
     * @throws Exception error
     */
    public static void main(String[] args) throws Exception {
        LOGGER.info("Starting Application");

        Registry.addRegister(BuiltInRegistries.PACKET, new PacketRegistry());
        Registry.addRegister(BuiltInRegistries.PLUGIN, new PluginRegistry());

        BuiltInRegistries.PACKET.get().registerPacket(PacketIds.TEST, TestPacket.class);
        BuiltInRegistries.PACKET.get().registerPacket(PacketIds.VALID, ValidPacket.class);
        BuiltInRegistries.PACKET.get().registerPacket(PacketIds.ESTABLISH, EstablishPacket.class);

        PluginLoader loader = new PluginLoader(null);

        loader.loadAll();

        loader.initAll();

        Thread network = new Thread(() -> setServer(new Server(future -> LOGGER.info("Server started"))), "network");

        network.start();

        if (Registry.getRegistry(BuiltInRegistries.PLUGIN).containsKey("demo")) {
            for (String name : RegistryKey.ofIdentifier("core:demo", String.class).get().entries()) {
                LOGGER.info(name);
            }
        }
    }

    @SuppressWarnings("unused")
    public static Server getServer() {
        return server;
    }
}
