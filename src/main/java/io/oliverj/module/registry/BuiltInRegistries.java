package io.oliverj.module.registry;

import io.oliverj.module.network.packet.registry.PacketRegistry;
import io.oliverj.module.plugin.PluginRegistry;

public final class BuiltInRegistries {
    public static final RegistryKey<GenericRegistry<String, String>> DEMO = new RegistryKey<>();

    public static final RegistryKey<PacketRegistry> PACKET = new RegistryKey<>();
    public static final RegistryKey<PluginRegistry> PLUGIN = new RegistryKey<>();

    private BuiltInRegistries() {}
}
