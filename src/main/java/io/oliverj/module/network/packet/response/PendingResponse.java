package io.oliverj.module.network.response;

import io.oliverj.module.network.Packet;

import java.util.concurrent.TimeUnit;
import java.util.function.Consumer;

public class PendingResponse<T extends Packet> {

    private final Long sent;
    private final Consumer<T> responseCallable;
    private final Long timeout;

    public PendingResponse(Class<T> type, Consumer<T> responseCallable) {
        this(type, responseCallable, TimeUnit.SECONDS.toMillis(10));
    }

    public PendingResponse(Class<T> type, Consumer<T> responseCallable, long timeout) {
        this.timeout = timeout;
        this.sent = System.currentTimeMillis();
        this.responseCallable = responseCallable;
    }

    public void callResponseReceived(Packet packet) {
        responseCallable.accept((T) packet);
    }

    public boolean isExpired() {
        return System.currentTimeMillis() - sent > timeout;
    }
}
