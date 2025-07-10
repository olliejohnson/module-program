package io.oliverj.module.network;

import io.netty.channel.Channel;
import io.oliverj.module.network.packet.Packet;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class ConnectionManager {

    private static final Map<String, Channel> channels = new ConcurrentHashMap<>();

    public static void addClient(String username, Channel channel) {
        channels.put(username, channel);
    }

    public static Channel get(String username) {
        return channels.get(username);
    }

    public static void send(String username, Packet packet) {
        channels.get(username).writeAndFlush(packet);
    }
}
