package io.oliverj.module.network.packet.registry;

import io.oliverj.module.network.packet.Packet;

import java.lang.reflect.Constructor;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class RegisteredPacket {

    private final Class<? extends Packet> packetClass;
    private final Constructor<? extends Packet> constructor;

    public RegisteredPacket(Class<? extends Packet> packetClass) throws NoSuchMethodException {
        this.packetClass = packetClass;

        List<Constructor<?>> emptyConstructorList = Arrays.stream(packetClass.getConstructors())
                .filter(constructor -> constructor.getParameterCount() == 0).collect(Collectors.toList());
        if (emptyConstructorList.size() == 0) {
            throw new NoSuchMethodException("Packet is missing no-args-constructor");
        }
        this.constructor = (Constructor<? extends Packet>) emptyConstructorList.get(0);
    }

    public Class<? extends Packet> getPacketClass() {
        return packetClass;
    }

    public Constructor<? extends Packet> getConstructor() {
        return constructor;
    }
}
