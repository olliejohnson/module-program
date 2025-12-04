package io.oliverj.demo;

import io.oliverj.module.registry.GenericRegistry;
import io.oliverj.module.registry.RegistryKey;
import io.oliverj.module.util.Identifier;

public class Registries {
    public static final RegistryKey<GenericRegistry<Identifier, String>> DEMO = new RegistryKey<>("demo");
}
