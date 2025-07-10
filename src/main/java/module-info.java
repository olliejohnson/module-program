module io.oliverj.module {
    requires org.apache.logging.log4j;
    requires com.google.gson;
    requires org.jetbrains.annotations;
    requires java.desktop;
    requires io.netty.transport;
    requires io.netty.buffer;
    requires jdk.unsupported;
    requires io.netty.common;
    requires io.netty.codec;

    exports io.oliverj.module.api;
}