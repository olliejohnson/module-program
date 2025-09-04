module io.oliverj.module {
    exports io.oliverj.module.registry;
    exports io.oliverj.module.util;

    requires com.google.gson;
    requires org.jetbrains.annotations;
    requires java.desktop;
    requires io.netty.transport;
    requires io.netty.buffer;
    requires jdk.unsupported;
    requires io.netty.common;
    requires io.netty.codec;
    requires io.netty.handler;
    requires module.program.api.main;
    requires org.slf4j;
    requires java.logging;
}