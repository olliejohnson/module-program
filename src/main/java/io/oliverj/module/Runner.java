package io.oliverj.module;

import io.netty.channel.ChannelHandlerContext;
import io.oliverj.module.network.Server;
import io.oliverj.module.network.TestPacket;
import io.oliverj.module.network.packet.event.EventRegistry;
import io.oliverj.module.network.packet.event.PacketSubscriber;
import io.oliverj.module.network.packet.registry.PacketRegistry;
import io.oliverj.module.plugin.PluginLoader;
import io.oliverj.module.registry.BuiltInRegistries;
import io.oliverj.module.registry.GenericRegistry;
import io.oliverj.module.registry.Registry;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class Runner {

    public static final Logger LOGGER = LogManager.getLogger();

    public static void main(String[] args) throws Exception {
        LOGGER.info("Starting Application");

        Registry.addRegister(BuiltInRegistries.DEMO, new GenericRegistry<>());
        Registry.addRegister(BuiltInRegistries.PACKET, new PacketRegistry());

        BuiltInRegistries.PACKET.get().registerPacket(0, TestPacket.class);

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

        EventRegistry eventRegistry = new EventRegistry();

        eventRegistry.registerEvents(new Object() {

            public static final Logger LOGGER = LogManager.getLogger();

            @PacketSubscriber
            @SuppressWarnings("unused")
            public void onPacketReceive(TestPacket packet, ChannelHandlerContext ctx) {
                LOGGER.info("Received {} from {}", packet.getUuid().toString(), ctx.channel().remoteAddress().toString());
            }
        });

        Thread network = new Thread(() -> {
            Server server = new Server(eventRegistry, future -> {});
        });

        network.start();

        frame.setSize(300, 300);

        frame.setVisible(true);

        frame.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                System.exit(0);
            }
        });
    }
}
