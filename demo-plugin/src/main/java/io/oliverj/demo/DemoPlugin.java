package io.oliverj.demo;

import io.oliverj.module.api.BasePlugin;
import io.oliverj.module.api.plugin.PluginMetadata;
import io.oliverj.module.plugin.PluginManager;
import io.oliverj.module.registry.GenericRegistry;
import io.oliverj.module.registry.Registry;
import io.oliverj.module.util.Identifier;
import io.oliverj.web.RouteManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.InputStream;

@SuppressWarnings("unused")
public class DemoPlugin extends BasePlugin {

    public static final Logger LOGGER = LoggerFactory.getLogger(DemoPlugin.class);

    public static Identifier id(String key) {
        return Identifier.ofNamespaceAndKey("demo", key);
    }

    public InputStream getResource(String path) {
        return PluginManager.getResource(meta.identifier, path);
    }

    @Override
    public void init() {
        LOGGER.info("Loading Demo Plugin");

        Registry.addRegister(Registries.DEMO, new GenericRegistry<>());

        Registry.register(Registries.DEMO, id("page"), "demo-plugin");
        Registry.register(Registries.DEMO, id("de"), "demo-plugin2");

        LOGGER.info("Finished init of version {}", meta.version);

        RouteManager.createPage(id("home"), getResource("home.html"));
        RouteManager.addRoute("/", id("home"));
    }

    @Override
    public void disable() {
        LOGGER.info("Disabling Demo Plugin");
        Registry.removeRegister(Registries.DEMO);
    }
}
