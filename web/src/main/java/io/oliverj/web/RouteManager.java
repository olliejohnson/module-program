package io.oliverj.web;

import io.oliverj.module.util.Identifier;

import java.io.InputStream;

public class RouteManager {
    public static void addRoute(String path, Identifier route) {
        Registries.ROUTE.get().register(path, route);
    }

    public static void createPage(Identifier id, InputStream page) {
        Registries.WEB.get().register(id, page);
    }
}
