package com.iafenvoy.netherite;

import com.iafenvoy.netherite.registry.NetheriteRenderers;
import com.iafenvoy.netherite.registry.NetheriteScreenHandlers;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;

@Environment(EnvType.CLIENT)
public class NetheriteExtensionClient {
    public static void init() {
        NetheriteRenderers.registerEntityRenderers();
    }

    public static void process() {
        NetheriteRenderers.registerModelPredicates();
        NetheriteRenderers.registerBlockEntityRenderers();
        NetheriteScreenHandlers.initializeClient();
    }
}
