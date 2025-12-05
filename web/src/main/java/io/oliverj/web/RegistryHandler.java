package io.oliverj.web;

import io.oliverj.module.plugin.PluginManager;
import io.oliverj.module.util.Identifier;
import io.undertow.server.HttpHandler;
import io.undertow.server.HttpServerExchange;
import io.undertow.util.Headers;
import io.undertow.util.StatusCodes;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.nio.ByteBuffer;

public class RegistryHandler implements HttpHandler {
    @Override
    public void handleRequest(HttpServerExchange exchange) throws Exception {
        String route = exchange.getRequestPath();

        Identifier page = Registries.ROUTE.get().get(route);

        if (page == null) {
            exchange.setStatusCode(StatusCodes.BAD_REQUEST);
            exchange.getResponseHeaders().put(Headers.CONTENT_TYPE, "text/html");
            exchange.getResponseSender().send(ByteBuffer.wrap(PluginManager.getResource("io.oliverj.web", "error.html").readAllBytes()));
            exchange.endExchange();
        }

        InputStream pageStream = Registries.WEB.get().get(page);

        exchange.getResponseSender().send(ByteBuffer.wrap(pageStream.readAllBytes()));
    }
}
