package com.iafenvoy.netherite.network;

import io.netty.buffer.Unpooled;
import net.minecraft.network.PacketByteBuf;

public class PacketBufferUtils {
    public static PacketByteBuf create() {
        return new PacketByteBuf(Unpooled.buffer());
    }
}
