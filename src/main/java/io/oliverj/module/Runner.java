package io.oliverj.module;

import io.oliverj.module.network.*;
import io.oliverj.module.network.packet.registry.PacketRegistry;
import io.oliverj.module.plugin.PluginLoader;
import io.oliverj.module.plugin.PluginRegistry;
import io.oliverj.module.registry.BuiltInRegistries;
import io.oliverj.module.registry.GenericRegistry;
import io.oliverj.module.registry.Registry;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.UUID;

/**
 * Runner class
 */
public class Runner {

    /**
     * Logger
     */
    public static final Logger LOGGER = LogManager.getLogger();

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

        Registry.addRegister(BuiltInRegistries.DEMO, new GenericRegistry<>());
        Registry.addRegister(BuiltInRegistries.PACKET, new PacketRegistry());
        Registry.addRegister(BuiltInRegistries.PLUGIN, new PluginRegistry());

        BuiltInRegistries.PACKET.get().registerPacket(PacketIds.TEST, TestPacket.class);
        BuiltInRegistries.PACKET.get().registerPacket(PacketIds.VALID, ValidPacket.class);
        BuiltInRegistries.PACKET.get().registerPacket(PacketIds.ESTABLISH, EstablishPacket.class);

        PluginLoader loader = new PluginLoader(null);

        loader.loadAll();

        loader.initAll();

        Frame frame = new Frame("Module Runner");

        Panel panel = new Panel();

        frame.add(panel);

        for (String name : BuiltInRegistries.DEMO.get().entries()) {
            Label label = new Label(name);

            panel.add(label);
        }

        Thread network = new Thread(() -> {
             setServer(new Server(future -> {
                 LOGGER.info("Server started");
             }));
        }, "network");

        network.start();

        Button button = new Button("Send packet");
        button.addActionListener(e -> {
            TestPacket packet = new TestPacket().setUuid(UUID.randomUUID());
            ConnectionManager.get("test-client").writeAndFlush(packet);
        });

        panel.add(button);

        frame.setSize(300, 300);

        frame.setVisible(true);

        frame.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                LOGGER.info("Stopping application");
                server.shutdown();
                LOGGER.info("Exiting");
                System.exit(0);
            }
        });
    }
}
