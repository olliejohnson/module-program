package io.oliverj.demo;

import io.oliverj.module.api.BasePlugin;
import io.oliverj.module.network.packet.Packet;
import io.oliverj.module.registry.BuiltInRegistries;
import io.oliverj.module.registry.Registry;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

@SuppressWarnings("unused")
public class DemoPlugin extends BasePlugin {

    public static final Logger LOGGER = LogManager.getLogger();

    @Override
    public void init() {
        LOGGER.info("Loading Demo Plugin");

        Registry.register(BuiltInRegistries.DEMO, "demo:page", "demo-plugin");
        Registry.register(BuiltInRegistries.DEMO, "demo:de", "demo-plugin2");
    }
}
