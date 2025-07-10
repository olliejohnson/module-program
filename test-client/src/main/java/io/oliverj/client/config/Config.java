package io.oliverj.client.config;

public class Config {
    public int port;
    public String host;

    public int max_attempts;
    public int initial_wait;

    public Config(int port, String host, int max_attempts, int initial_wait) {
        this.port = port;
        this.host = host;
        this.max_attempts = max_attempts;
        this.initial_wait = initial_wait;
    }

    public Config() {
        this.port = 1234;
        this.host = "127.0.0.1";
        this.max_attempts = 5;
        this.initial_wait = 1000;
    }
}
