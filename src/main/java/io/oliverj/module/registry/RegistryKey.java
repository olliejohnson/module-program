package io.oliverj.module.registry;

public class RegistryKey<T extends GenericRegistry> {

    public RegistryKey() {
    }

    public T get() {
        return Registry.getRegistry(this);
    }
}
