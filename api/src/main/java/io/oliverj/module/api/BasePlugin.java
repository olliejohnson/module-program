package io.oliverj.module.api;

/**
 * Base Plugin class
 */
public abstract class BasePlugin {

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
