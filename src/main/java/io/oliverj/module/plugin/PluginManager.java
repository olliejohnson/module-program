package io.oliverj.module.plugin;

import io.oliverj.module.api.plugin.PluginMetadata;
import io.oliverj.module.registry.BuiltInRegistries;

import java.io.InputStream;
import java.util.List;

public class PluginManager {
    private PluginManager() {}

    public static PluginMetadata getPluginMetadata(String id) {
        return BuiltInRegistries.PLUGIN.get().get(id).second;
    }

    public static boolean exists(String id) {
        return BuiltInRegistries.PLUGIN.get().containsKey(id);
    }

    public static void disable(String id) {
        BuiltInRegistries.PLUGIN.get().get(id).first.disable();
        BuiltInRegistries.PLUGIN.get().deregister(id);
    }

    public static List<String> getPlugins() {
        return BuiltInRegistries.PLUGIN.get().keys();
    }

    public static InputStream getResource(String identifier, String path) {
        return PluginLoader.getLoader().getResource(identifier, path);
    }
}
