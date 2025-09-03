package io.oliverj.module.registry;

import io.oliverj.module.network.packet.registry.PacketRegistry;
import io.oliverj.module.plugin.PluginRegistry;

public final class BuiltInRegistries {
    public static final RegistryKey<PacketRegistry> PACKET = new RegistryKey<>("packet");
    public static final RegistryKey<PluginRegistry> PLUGIN = new RegistryKey<>("plugin");

    private BuiltInRegistries() {}
}
