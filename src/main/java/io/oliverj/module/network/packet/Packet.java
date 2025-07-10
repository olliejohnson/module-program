package io.oliverj.module.network.packet;

import io.oliverj.module.network.packet.io.Decoder;
import io.oliverj.module.network.packet.io.Encoder;

import java.util.concurrent.ThreadLocalRandom;

public abstract class Packet implements Encoder, Decoder {
    /**
     * SessionID is used for identification of the packet for use with {@link io.oliverj.module.network.packet.io.Responder}
     */
    private long sessionId = ThreadLocalRandom.current().nextLong();

    public void setSessionId(long sessionId) {
        this.sessionId = sessionId;
    }

    public long getSessionId() {
        return sessionId;
    }
}
