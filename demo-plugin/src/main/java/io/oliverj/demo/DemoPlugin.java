package io.oliverj.demo;

import io.oliverj.module.api.BasePlugin;
import io.oliverj.module.registry.GenericRegistry;
import io.oliverj.module.registry.Registry;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

@SuppressWarnings("unused")
public class DemoPlugin extends BasePlugin {

    public static final Logger LOGGER = LogManager.getLogger();

    @Override
    public void init() {
        LOGGER.info("Loading Demo Plugin");

        Registry.addRegister(Registries.DEMO, new GenericRegistry<>());

        Registry.register(Registries.DEMO, "demo:page", "demo-plugin");
        Registry.register(Registries.DEMO, "demo:de", "demo-plugin2");
    }
}
