package com.iafenvoy.netherite;

import com.iafenvoy.netherite.registry.*;
import com.mojang.logging.LogUtils;
import org.slf4j.Logger;

public class NetheriteExtension {
    public static final String MOD_ID = "netherite_ext";
    public static final Logger LOGGER = LogUtils.getLogger();

    public static void init() {
        NetheriteBlocks.REGISTRY.register();
        NetheriteBlockEntities.REGISTRY.register();
        NetheriteEntities.REGISTRY.register();
        NetheriteItemGroups.REGISTRY.register();
        NetheriteItems.REGISTRY.register();
        NetheritePotions.REGISTRY.register();
        NetheriteRecipeSerializers.REGISTRY.register();
        NetheriteScreenHandlers.REGISTRY.register();
    }

    public static void process() {
        NetheriteItems.init();
    }
}
