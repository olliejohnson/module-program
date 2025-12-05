package io.oliverj.module.api;

import io.oliverj.module.api.plugin.PluginMetadata;

/**
 * Base Plugin class
 */
public abstract class BasePlugin {

    protected PluginMetadata meta = null;

    public BasePlugin() {}

    /**
     * Init method. Called when plugin is loaded.
     */
    public abstract void init();

    /**
     * Disable method. Called when plugin is disabled.
     */
    public abstract void disable();
}
