package io.oliverj.module.registry;

import io.oliverj.module.util.Identifier;

public class RegistryKey<T extends GenericRegistry<?, ?>> {

    private final Identifier id;

    public RegistryKey(Identifier id) {
        this.id = id;
    }

    public RegistryKey(String id) {
        this.id = Identifier.parse(id);
    }

    public T get() {
        return Registry.getRegistry(this);
    }

    @SuppressWarnings({"unchecked", "unused"})
    public static <K extends GenericRegistry<?, ?>> RegistryKey<K> ofIdentifier(Identifier id) {
        return (RegistryKey<K>) Registry.getKey(id);
    }

    @SuppressWarnings("unchecked")
    public static <K extends GenericRegistry<?, ?>> RegistryKey<K> ofIdentifier(String id) {
        return (RegistryKey<K>) Registry.getKey(id);
    }

    @SuppressWarnings({"unchecked", "unused"})
    public static <K, V, T extends GenericRegistry<K, V>> RegistryKey<T> ofIdentifier(Identifier id, Class<K> key, Class<V> value) {
        return (RegistryKey<T>) Registry.getKey(id);
    }

    @SuppressWarnings({"unchecked", "unused"})
    public static <K, V, T extends GenericRegistry<K, V>> RegistryKey<T> ofIdentifier(String id, Class<K> key, Class<V> value) {
        return (RegistryKey<T>) Registry.getKey(id);
    }

    @SuppressWarnings({"unchecked", "unused"})
    public static <V, T extends GenericRegistry<Identifier, V>> RegistryKey<T> ofIdentifier(Identifier id, Class<V> value) {
        return (RegistryKey<T>) Registry.getKey(id);
    }

    @SuppressWarnings({"unchecked", "unused"})
    public static <V, T extends GenericRegistry<Identifier, V>> RegistryKey<T> ofIdentifier(String id, Class<V> value) {
        return (RegistryKey<T>) Registry.getKey(id);
    }

    public Identifier getId() {
        return this.id;
    }
}
