package io.oliverj.module.registry;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Supplier;

public class GenericRegistry<K, V> {

    private Map<K, V> registry = new HashMap<>();

    public GenericRegistry() {}

    public Supplier<V> register(K identifier, V object) {
        registry.put(identifier, object);
        return () -> { return registry.get(object); };
    }

    public V get(K identifier) {
        return registry.get(identifier);
    }

    public List<V> entries() {
        return registry.values().stream().toList();
    }

    public List<K> keys() {
        return registry.keySet().stream().toList();
    }

    public boolean containsKey(K key) {
        return registry.containsKey(key);
    }

    public boolean containsValue(V value) {
        return registry.containsValue(value);
    }

    public Map<K, V> getRawRegistry() { return registry; }

    public void deregister(K key) { registry.remove(key); }
}
