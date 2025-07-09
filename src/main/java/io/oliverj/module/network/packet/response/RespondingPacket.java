package io.oliverj.module.network.response;

import io.netty.channel.ChannelOutboundInvoker;
import io.oliverj.module.network.Packet;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;
import java.util.function.Consumer;

public class RespondingPacket<T extends Packet> {

    private static final Map<Long, PendingResponse<?>> pendingResponses = new HashMap<>();
    private final Packet toSend;
    private final long timeout;
    private final Class<T> responseType;
    private final Consumer<T> callback;

    public RespondingPacket(Packet toSend, long timeout, Class<T> responseType, Consumer<T> callback) {
        this.toSend = toSend;
        this.timeout = timeout;
        this.responseType = responseType;
        this.callback = callback;
    }

    public RespondingPacket(Packet toSend, Class<T> responseType, Consumer<T> callback) {
        this(toSend, TimeUnit.SECONDS.toMillis(10), responseType, callback);
    }

    public void send(ChannelOutboundInvoker invoker) {
        invoker.writeAndFlush(toSend);
        pendingResponses.put(toSend.getSessionId(), new PendingResponse<>(responseType, callback, timeout));
    }

    public static void callReceive(Packet packet) {
        if (!pendingResponses.containsKey(packet.getSessionId())) {
            return;
        }
        pendingResponses.get(packet.getSessionId()).callResponseReceived(packet);
        pendingResponses.remove(packet.getSessionId());

        cleanupPendingResponses();
    }

    private static void cleanupPendingResponses() {
        pendingResponses.forEach((sessionId, pendingResponse) -> {
            if (!pendingResponse.isExpired()) {
                return;
            }
            RespondingPacket.pendingResponses.remove(sessionId.longValue());
        });
    }
}
