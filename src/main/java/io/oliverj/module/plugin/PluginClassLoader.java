package io.oliverj.module.plugin;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLClassLoader;

public class PluginClassLoader extends URLClassLoader {

    Logger LOGGER = LoggerFactory.getLogger(PluginClassLoader.class);

    String plugin_file;

    public PluginClassLoader(File plugin) throws MalformedURLException {
        super(new URL[]{plugin.toURI().toURL()}, getSystemClassLoader());
        plugin_file = plugin.getName().substring(0, plugin.getName().length() - 4);
        LOGGER.info("Creating PluginClassLoader for {}", plugin_file);
    }

    @Override
    protected Class<?> loadClass(String name, boolean resolve) throws ClassNotFoundException {
        if (name.startsWith("java.")) {
            return super.loadClass(name, resolve);
        }

        LOGGER.info("Loading class {}", name);

        synchronized (getClassLoadingLock(name)) {
            Class<?> loadedClass = findLoadedClass(name);
            if (loadedClass != null) {
                LOGGER.info("Class {} already loaded", name);
                if (resolve) resolveClass(loadedClass);
                return loadedClass;
            }

            try {
                Class<?> clazz = findClass(name);
                if (resolve) resolveClass(clazz);
                return clazz;
            } catch (ClassNotFoundException e) {
                LOGGER.info("Loading class {} from super", name);
                return super.loadClass(name, resolve);
            }
        }
    }

    @Override
    public Class<?> loadClass(String name) throws ClassNotFoundException {
        return loadClass(name, false);
    }
}
