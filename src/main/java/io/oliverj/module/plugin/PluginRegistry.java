package io.oliverj.module.plugin;

import io.oliverj.module.PluginMetadata;
import io.oliverj.module.api.BasePlugin;
import io.oliverj.module.registry.GenericRegistry;
import io.oliverj.module.util.Pair;

public class PluginRegistry extends GenericRegistry<String, Pair<BasePlugin, PluginMetadata>> {
}
