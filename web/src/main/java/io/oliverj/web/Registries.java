package io.oliverj.web;

import io.oliverj.module.registry.GenericRegistry;
import io.oliverj.module.registry.RegistryKey;
import io.oliverj.module.util.Identifier;

import java.io.File;
import java.io.InputStream;

public class Registries {
    public static final RegistryKey<GenericRegistry<Identifier, InputStream>> WEB = new RegistryKey<>("web");
    public static final RegistryKey<GenericRegistry<String, Identifier>> ROUTE = new RegistryKey<>("route");
}
