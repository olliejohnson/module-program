package io.oliverj.module.plugin;

import com.google.gson.Gson;
import io.oliverj.module.PluginMetadata;
import io.oliverj.module.api.BasePlugin;
import io.oliverj.module.util.Pair;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.jetbrains.annotations.Nullable;

import java.io.*;
import java.lang.reflect.InvocationTargetException;
import java.net.MalformedURLException;
import java.net.URISyntaxException;
import java.net.URL;
import java.net.URLClassLoader;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;

public class PluginLoader {

    public static final Logger LOGGER = LogManager.getLogger();

    private Map<String, Pair<BasePlugin, PluginMetadata>> plugins = new HashMap<>();

    private final String plugin_dir;

    public PluginLoader(@Nullable String dir) {
        if (dir == null) {
            plugin_dir = "plugins";
        } else {
            plugin_dir = dir;
        }
    }

    public void loadAll() throws URISyntaxException {
        LOGGER.info("Starting Plugin Discovery");

        URL loc = getClass().getProtectionDomain().getCodeSource().getLocation();

        File rootFolder = new File(loc.toURI()).getParentFile();

        File pluginsFolder = new File(rootFolder.toPath() + "/" + plugin_dir);

        for (File plugin : pluginsFolder.listFiles()) {
            load(plugin);
        }
    }

    public void load(File plugin) {
        URLClassLoader classLoader = null;
        try {
            classLoader = new URLClassLoader(new URL[]{plugin.toURI().toURL()});
        } catch (MalformedURLException e) {
            // Something REALLY went wrong
            throw new RuntimeException(e);
        }

        InputStream metaIn = classLoader.getResourceAsStream("plugin.json");

        try {
            char[] buffer = new char[metaIn.available()];
            StringBuilder out = new StringBuilder();
            Reader in = new InputStreamReader(metaIn, StandardCharsets.UTF_8);
            for (int numRead; (numRead = in.read(buffer, 0, buffer.length)) > 0; ) {
                out.append(buffer, 0, numRead);
            }

            String meta = out.toString();

            Gson g = new Gson();

            PluginMetadata metadata = g.fromJson(meta, PluginMetadata.class);

            LOGGER.info("Loading {} ({}) version {}", metadata.name, metadata.identifier, metadata.version);

            @SuppressWarnings("unchecked")
            Class<BasePlugin> clazz = (Class<BasePlugin>) classLoader.loadClass(metadata.entrypoints.get("main"));

            BasePlugin pluginClass = clazz.getDeclaredConstructor().newInstance();

            plugins.put(metadata.identifier, Pair.of(pluginClass, metadata));
        } catch (IOException e) {
            LOGGER.error("Failed to load plugin metadata", e);
        } catch (ClassNotFoundException e) {
            LOGGER.error("Cannot find main entrypoint in", e);
        } catch (InstantiationException | InvocationTargetException e) {
            LOGGER.error("failed to instantiate Plugin main entrypoint", e);
        } catch (IllegalAccessException e) {
            LOGGER.error("Cannot access main entrypoint", e);
        } catch (NoSuchMethodException e) {
            LOGGER.error("Method '<init>' does not exist", e);
        }
    }

    public void init(String identifier) {
        PluginMetadata meta = plugins.get(identifier).second;
        LOGGER.info("Initializing {} ({}) version {}", meta.name, meta.identifier, meta.version);
        plugins.get(identifier).first.init();
    }

    public void initAll() {
        plugins.forEach((id, pair) -> init(id));
    }
}
