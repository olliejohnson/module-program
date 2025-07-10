package io.oliverj.module.registry;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Supplier;

public class Registry {

    @SuppressWarnings("rawtypes")
    public static final Map<RegistryKey, ? super GenericRegistry<?, ?>> registries = new HashMap<>();

    public static <T extends GenericRegistry<?, ?>> void addRegister(RegistryKey<T> key, T registry) {
        registries.put(key, registry);
    }

    public static <K, V, T extends GenericRegistry<K, V>> Supplier<V> register(RegistryKey<T> key, K id, V value) {
        return getRegistry(key).register(id, value);
    }

    @SuppressWarnings("unchecked")
    public static <T extends GenericRegistry<?, ?>> T getRegistry(RegistryKey<T> key) {
        return (T) registries.get(key);
    }
}
