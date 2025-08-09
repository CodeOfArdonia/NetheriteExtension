package com.iafenvoy.netherite.network;

import com.iafenvoy.netherite.NetheriteExtension;
import io.netty.buffer.Unpooled;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.util.Identifier;

public class NetworkHelper {
    public static final Identifier NETHERITE_TRIDENT = Identifier.of(NetheriteExtension.MOD_ID, "netherite_trident");

    public static PacketByteBuf create() {
        return new PacketByteBuf(Unpooled.buffer());
    }
}
