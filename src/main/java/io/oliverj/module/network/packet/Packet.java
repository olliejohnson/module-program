package io.oliverj.module.network;

import io.oliverj.module.network.io.Decoder;
import io.oliverj.module.network.io.Encoder;

import java.util.concurrent.ThreadLocalRandom;

public abstract class Packet implements Encoder, Decoder {
    /**
     * SessionID is used for identification of the packet for use with {@link io.oliverj.module.network.io.Responder}
     */
    private long sessionId = ThreadLocalRandom.current().nextLong();

    public void setSessionId(long sessionId) {
        this.sessionId = sessionId;
    }

    public long getSessionId() {
        return sessionId;
    }
}
