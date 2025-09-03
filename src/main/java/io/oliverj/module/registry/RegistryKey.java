package io.oliverj.module.registry;

public class RegistryKey<T extends GenericRegistry<?, ?>> {

    private final String id;

    public RegistryKey(String id) {
        this.id = id;
    }

    public T get() {
        return Registry.getRegistry(this);
    }

    @SuppressWarnings("unchecked")
    public static <K extends GenericRegistry<?, ?>> RegistryKey<K> ofIdentifier(String id) {
        return (RegistryKey<K>) Registry.getKey(id);
    }

    public String getId() {
        return this.id;
    }
}
