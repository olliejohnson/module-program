package io.oliverj.web;

import io.oliverj.module.api.BasePlugin;
import io.oliverj.module.registry.GenericRegistry;
import io.oliverj.module.registry.Registry;
import io.undertow.Undertow;
import io.undertow.server.handlers.error.FileErrorPageHandler;
import io.undertow.server.handlers.error.SimpleErrorPageHandler;
import io.undertow.servlet.handlers.SendErrorPageHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@SuppressWarnings("unused")
public class WebPlugin extends BasePlugin {

    public static final Logger LOGGER = LoggerFactory.getLogger(WebPlugin.class);

    private Undertow server;

    @Override
    public void init() {
        LOGGER.info("Initializing Web Services");

        LOGGER.info("Creating Registries");
        Registry.addRegister(Registries.ROUTE, new GenericRegistry<>());
        Registry.addRegister(Registries.WEB, new GenericRegistry<>());

        server = Undertow.builder()
                .addHttpListener(8080, "localhost")
                .setHandler(new RegistryHandler())
                .build();
        server.start();
    }

    @Override
    public void disable() {
        server.stop();
        Registry.removeRegister(Registries.ROUTE);
        Registry.removeRegister(Registries.WEB);
    }
}
