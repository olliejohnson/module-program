package io.oliverj.demo;

import io.oliverj.module.registry.GenericRegistry;
import io.oliverj.module.registry.RegistryKey;

public class Registries {
    public static final RegistryKey<GenericRegistry<String, String>> DEMO = new RegistryKey<>("demo");
}
