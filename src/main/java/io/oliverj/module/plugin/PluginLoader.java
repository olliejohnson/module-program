package io.oliverj.module.plugin;

import io.oliverj.module.api.BasePlugin;
import io.oliverj.module.plugin.struct.Dag;
import io.oliverj.module.plugin.struct.HashDag;
import io.oliverj.module.registry.BuiltInRegistries;
import io.oliverj.module.registry.Registry;
import io.oliverj.module.util.Pair;
import org.jetbrains.annotations.Nullable;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.lang.reflect.InvocationTargetException;
import java.net.MalformedURLException;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.*;

public class PluginLoader {

    public static final Logger LOGGER = LoggerFactory.getLogger(PluginLoader.class);

    private final Map<String, Pair<BasePlugin, PluginMetadata>> plugins = new HashMap<>();

    private final String plugin_dir;

    private final PluginClassLoader loader;

    public PluginLoader() {
        this(null);
    }

    public PluginLoader(@Nullable String dir) {
        plugin_dir = Objects.requireNonNullElse(dir, "plugins");
        loader = new PluginClassLoader();
    }

    public void loadAll() throws URISyntaxException {
        LOGGER.info("Starting Plugin Discovery");

        URL loc = getClass().getProtectionDomain().getCodeSource().getLocation();

        File rootFolder = new File(loc.toURI()).getParentFile();

        File pluginsFolder = new File(rootFolder.toPath() + "/" + plugin_dir);

        for (File plugin : Objects.requireNonNull(pluginsFolder.listFiles())) {
            load(plugin);
        }
    }

    public void load(File plugin) {
        LOGGER.info("Loading plugin at {}", plugin.getPath());
        try {
            loader.addPlugin(plugin);
        } catch (MalformedURLException e) {
            // Something REALLY went wrong
            throw new RuntimeException(e);
        }

        try {
            PluginMetadata metadata = loader.getMeta(plugin);

            LOGGER.info("Loading {} ({}) version {}", metadata.name, metadata.identifier, metadata.version);

            @SuppressWarnings("unchecked")
            Class<BasePlugin> clazz = (Class<BasePlugin>) loader.loadClass(metadata.entrypoints.get("main"));

            BasePlugin pluginClass = clazz.getDeclaredConstructor().newInstance();

            Registry.register(BuiltInRegistries.PLUGIN, metadata.identifier, Pair.of(pluginClass, metadata));
        } catch (ClassNotFoundException e) {
            LOGGER.error("Cannot find main entrypoint", e);
        } catch (InstantiationException | InvocationTargetException e) {
            LOGGER.error("failed to instantiate Plugin main entrypoint", e);
        } catch (IllegalAccessException e) {
            LOGGER.error("Cannot access main entrypoint", e);
        } catch (NoSuchMethodException e) {
            LOGGER.error("Method '<init>' does not exist", e);
        }
        LOGGER.info("Finished loading {}", loader.getMeta(plugin).name);
    }

    public List<String> createLoadOrder() {
        Dag<String> dag = new HashDag<>();
        Registry.getRegistry(BuiltInRegistries.PLUGIN).entries().stream()
                .map(pair -> pair.second)
                .forEach(meta -> meta.depends.keySet().forEach(dep -> dag.put(dep, meta.identifier)));
        return dag.sort();
    }

    public void init(String identifier) {
        List<String> builtin = List.of("core", "loader");
        if (builtin.contains(identifier)) return;

        PluginMetadata meta = Registry.getRegistry(BuiltInRegistries.PLUGIN).get(identifier).second;

        LOGGER.info("Initializing {} ({}) version {}", meta.name, meta.identifier, meta.version);
        Registry.getRegistry(BuiltInRegistries.PLUGIN).get(identifier).first.init();
        LOGGER.info("Finished initialization of {} ({})", meta.name, meta.identifier);
    }

    public void initAll() {
        List<String> order = createLoadOrder();
        order.forEach(this::init);
    }

    @SuppressWarnings("unused")
    public Map<String, Pair<BasePlugin, PluginMetadata>> getPlugins() {
        return plugins;
    }
}
