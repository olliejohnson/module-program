package io.oliverj.module.network.packet.registry;

import io.oliverj.module.network.packet.Packet;
import io.oliverj.module.network.packet.exception.PacketRegistrationException;
import io.oliverj.module.registry.GenericRegistry;

import java.lang.reflect.InvocationTargetException;
import java.util.Iterator;
import java.util.Map;

public class PacketRegistry extends GenericRegistry<Integer, RegisteredPacket> {

    public void registerPacket(int packetId, Packet packet) throws PacketRegistrationException {
        registerPacket(packetId, packet.getClass());
    }

    public void registerPacket(int packetId, Class<? extends Packet> packet) throws PacketRegistrationException {
        if (containsPacketId(packetId)) {
            throw new PacketRegistrationException("PacketID is already in use");
        }
        try {
            RegisteredPacket registeredPacket = new RegisteredPacket(packet);
            register(packetId, registeredPacket);
        } catch (NoSuchMethodException e) {
            throw new PacketRegistrationException("Failed to register packet", e);
        }
    }

    public int getPacketId(Class<? extends Packet> packetClass) {
        for (Iterator<Map.Entry<Integer, RegisteredPacket>> it = getRawRegistry().entrySet().iterator(); it.hasNext();) {
            Map.Entry<Integer, RegisteredPacket> entry = it.next();
            if (entry.getValue().getPacketClass().equals(packetClass)) {
                return entry.getKey();
            }
        }
        return -1;
    }

    public <T extends Packet> T constructPacket(int packetId) throws InvocationTargetException, InstantiationException, IllegalAccessException {
        return (T) getRawRegistry().get(packetId).getConstructor().newInstance();
    }

    public boolean containsPacketId(int id) {
        return containsKey(id);
    }
}
