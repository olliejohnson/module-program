package io.oliverj.client.config;

import com.google.gson.Gson;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.nio.charset.StandardCharsets;

public class ConfigLoader {

    private ConfigLoader() {}

    public static Config load(String fileName) {
        InputStream configIn = ConfigLoader.class.getResourceAsStream(fileName);

        if (configIn == null)
            return new Config();

        try {
            char[] buffer = new char[configIn.available()];
            StringBuilder out = new StringBuilder();
            Reader in = new InputStreamReader(configIn, StandardCharsets.UTF_8);
            for (int numRead; (numRead = in.read(buffer, 0, buffer.length)) > 0;) {
                out.append(buffer, 0, numRead);
            }

            String configJson = out.toString();

            Gson g = new Gson();

            Config config = g.fromJson(configJson, Config.class);

            return config;
        } catch (IOException e) {
            e.printStackTrace();
        }

        return new Config();
    }
}
