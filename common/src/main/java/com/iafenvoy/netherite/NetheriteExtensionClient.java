package com.iafenvoy.netherite;

import com.iafenvoy.netherite.registry.NetheriteRenderers;
import com.iafenvoy.netherite.registry.NetheriteScreenHandlers;
import it.unimi.dsi.fastutil.ints.IntArraySet;
import it.unimi.dsi.fastutil.ints.IntSet;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;

@Environment(EnvType.CLIENT)
public class NetheriteExtensionClient {
    public static final IntSet NETHERITE_TRIDENT_LIST = new IntArraySet();

    public static void init() {
        NetheriteRenderers.registerEntityRenderers();
    }

    public static void process() {
        NetheriteRenderers.registerModelPredicates();
        NetheriteRenderers.registerBlockEntityRenderers();
        NetheriteScreenHandlers.initializeClient();
    }
}
