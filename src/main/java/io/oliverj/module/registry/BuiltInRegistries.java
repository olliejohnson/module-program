package io.oliverj.module.registry;

import io.oliverj.module.network.packet.registry.PacketRegistry;

public final class BuiltInRegistries {
    public static final RegistryKey<GenericRegistry<String, String>> DEMO = new RegistryKey<>();

    public static final RegistryKey<PacketRegistry> PACKET = new RegistryKey<>();

    private BuiltInRegistries() {}
}
