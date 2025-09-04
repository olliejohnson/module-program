package io.oliverj.module.plugin;

import com.google.gson.Gson;
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
import java.nio.charset.StandardCharsets;
import java.util.*;

public class PluginLoader {

    public static final Logger LOGGER = LoggerFactory.getLogger(PluginLoader.class);

    private final Map<String, Pair<BasePlugin, PluginMetadata>> plugins = new HashMap<>();

    private final String plugin_dir;

    public PluginLoader(@Nullable String dir) {
        plugin_dir = Objects.requireNonNullElse(dir, "plugins");
    }

    public void loadAll() throws URISyntaxException, IOException {
        LOGGER.info("Starting Plugin Discovery");

        URL loc = getClass().getProtectionDomain().getCodeSource().getLocation();

        File rootFolder = new File(loc.toURI()).getParentFile();

        File pluginsFolder = new File(rootFolder.toPath() + "/" + plugin_dir);

        for (File plugin : Objects.requireNonNull(pluginsFolder.listFiles())) {
            load(plugin);
        }
    }

    public void load(File plugin) throws IOException {
        PluginClassLoader classLoader;
        try {
            classLoader = new PluginClassLoader(plugin);
        } catch (MalformedURLException e) {
            // Something REALLY went wrong
            throw new RuntimeException(e);
        }

        InputStream metaIn = classLoader.getResourceAsStream("plugin.json");

        try {
            if (metaIn == null) {
                throw new FileNotFoundException("Failed to load plugin data from: " + plugin);
            }
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

            Registry.register(BuiltInRegistries.PLUGIN, metadata.identifier, Pair.of(pluginClass, metadata));
        } catch (IOException e) {
            LOGGER.error("Failed to load plugin metadata", e);
        } catch (ClassNotFoundException e) {
            LOGGER.error("Cannot find main entrypoint", e);
        } catch (InstantiationException | InvocationTargetException e) {
            LOGGER.error("failed to instantiate Plugin main entrypoint", e);
        } catch (IllegalAccessException e) {
            LOGGER.error("Cannot access main entrypoint", e);
        } catch (NoSuchMethodException e) {
            LOGGER.error("Method '<init>' does not exist", e);
        }

        classLoader.close();
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
