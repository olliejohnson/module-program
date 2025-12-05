package io.oliverj.module.api.plugin;

import java.util.Map;

public class PluginMetadata {
    public String name;
    public String identifier;
    public String version;

    public Map<String, String> entrypoints;

    public Map<String, String> depends;

    public PluginMetadata(String name, String identifier, String version, Map<String, String> entrypoints, Map<String, String> depends) {
        this.name = name;
        this.identifier = identifier;
        this.version = version;
        this.entrypoints = entrypoints;
        this.depends = depends;
    }
}
