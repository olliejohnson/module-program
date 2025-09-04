package io.oliverj.module.util;

import java.util.Objects;

public class Identifier {
    private final String namespace;
    private final String key;

    public static final String DEFAULT_NAMESPACE = "core";

    private Identifier(String namespace, String key) {
        this.namespace = namespace;
        this.key = key;
    }

    public static Identifier ofDefault(String key) {
        return new Identifier(DEFAULT_NAMESPACE, key);
    }

    public static Identifier ofNamespaceAndKey(String namespace, String key) {
        return new Identifier(namespace, key);
    }

    public static Identifier parse(String identifier) {
        String[] parts = identifier.split(":");
        if (parts.length == 1) {
            return Identifier.ofDefault(parts[0]);
        }
        return new Identifier(parts[0], parts[1]);
    }

    public String getKey() {
        return key;
    }

    public String getNamespace() {
        return namespace;
    }

    @Override
    public String toString() {
        return namespace + ":" + key;
    }

    @Override
    public int hashCode() {
        return Objects.hash(namespace, key);
    }
}
