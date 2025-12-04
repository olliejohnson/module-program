package io.oliverj.module.plugin;


import com.google.gson.Gson;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLClassLoader;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;

public class PluginClassLoader extends URLClassLoader {

    Logger LOGGER = LoggerFactory.getLogger(PluginClassLoader.class);

    private final Map<File, PluginMetadata> metadataMap = new HashMap<>();

    public PluginClassLoader() {
        super(new URL[0], getSystemClassLoader());
    }

    @Override
    protected Class<?> loadClass(String name, boolean resolve) throws ClassNotFoundException {
        if (name.startsWith("java.")) {
            return super.loadClass(name, resolve);
        }

        LOGGER.debug("Loading class {}", name);

        synchronized (getClassLoadingLock(name)) {
            Class<?> loadedClass = findLoadedClass(name);
            if (loadedClass != null) {
                LOGGER.trace("Class {} already loaded", name);
                if (resolve) resolveClass(loadedClass);
                return loadedClass;
            }

            try {
                Class<?> clazz = findClass(name);
                if (resolve) resolveClass(clazz);
                return clazz;
            } catch (ClassNotFoundException e) {
                LOGGER.trace("Loading class {} from super", name);
                return super.loadClass(name, resolve);
            }
        }
    }

    @Override
    public Class<?> loadClass(String name) throws ClassNotFoundException {
        return loadClass(name, false);
    }

    public void addPlugin(File plugin) throws MalformedURLException {
        addURL(plugin.toURI().toURL());
        String plugin_file = plugin.getName().substring(0, plugin.getName().length() - 4);
        LOGGER.info("Creating PluginClassLoader for {}", plugin_file);

        InputStream metaIn = this.getResourceAsStream("plugin.json");

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

            metadataMap.put(plugin, metadata);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public PluginMetadata getMeta(File plugin) {
        return metadataMap.get(plugin);
    }
}
