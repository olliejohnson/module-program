package io.oliverj.module.registry;

import io.oliverj.module.util.Identifier;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Supplier;

public class Registry {

    public static final Map<RegistryKey<?>, ? super GenericRegistry<?, ?>> registries = new HashMap<>();
    public static final Map<Identifier, RegistryKey<? extends GenericRegistry<?, ?>>> keys = new HashMap<>();

    public static <T extends GenericRegistry<?, ?>> void addRegister(RegistryKey<T> key, T registry) {
        registries.put(key, registry);
        keys.put(key.getId(), key);
    }

    @SuppressWarnings("UnusedReturnValue")
    public static <K, V, T extends GenericRegistry<K, V>> Supplier<V> register(RegistryKey<T> key, K id, V value) {
        return getRegistry(key).register(id, value);
    }

    @SuppressWarnings("unchecked")
    public static <T extends GenericRegistry<?, ?>> T getRegistry(RegistryKey<T> key) {
        return (T) registries.get(key);
    }

    public static <T> RegistryKey<? extends GenericRegistry<?, T>> getKey(Identifier id) {
        return (RegistryKey<? extends GenericRegistry<?, T>>) keys.get(id);
    }

    public static <T> RegistryKey<? extends GenericRegistry<?, T>> getKey(String id) {
        return (RegistryKey<? extends GenericRegistry<?, T>>) keys.get(Identifier.parse(id));
    }

    public static <T extends GenericRegistry<?, ?>> void removeRegister(RegistryKey<T> key) {
        registries.remove(key);
        keys.remove(key.getId());
    }
}
