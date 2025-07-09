package io.oliverj.module.network.event;

import io.netty.channel.ChannelHandlerContext;
import io.oliverj.module.network.Packet;
import io.oliverj.module.network.io.Responder;

import java.lang.reflect.InvocationTargetException;
import java.util.HashSet;
import java.util.Set;

public class EventRegistry {

    private final Set<RegisteredPacketSubscriber> subscribers = new HashSet<>();

    public void registerEvents(Object holder) {
        subscribers.add(new RegisteredPacketSubscriber(holder));
    }

    public void invoke(Packet packet, ChannelHandlerContext ctx) {
        try {
            for (RegisteredPacketSubscriber subscriber : subscribers) {
                subscriber.invoke(packet, ctx, Responder.forId(packet.getSessionId(), ctx));
            }
        } catch (InvocationTargetException | IllegalAccessException e) {
            e.printStackTrace();
        }
    }
}
