package io.oliverj.demo;

import io.oliverj.module.api.BasePlugin;
import io.oliverj.module.registry.GenericRegistry;
import io.oliverj.module.registry.Registry;
import io.oliverj.module.util.Identifier;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

@SuppressWarnings("unused")
public class DemoPlugin extends BasePlugin {

    public static final Logger LOGGER = LogManager.getLogger();

    public static Identifier id(String key) {
        return Identifier.ofNamespaceAndKey("demo", key);
    }

    @Override
    public void init() {
        LOGGER.info("Loading Demo Plugin");

        try {
            ClassLoader cl = this.getClass().getClassLoader();

            LOGGER.info("Using {} classloader", cl);
            LOGGER.info("Registries classloader {}", Registries.class.getClassLoader());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        Registry.addRegister(Registries.DEMO, new GenericRegistry<>());

        Registry.register(Registries.DEMO, id("page"), "demo-plugin");
        Registry.register(Registries.DEMO, id("de"), "demo-plugin2");
    }
}
