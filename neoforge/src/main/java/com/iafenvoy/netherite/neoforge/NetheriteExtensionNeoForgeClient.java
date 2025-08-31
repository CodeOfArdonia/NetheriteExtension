package com.iafenvoy.netherite.neoforge;

import com.iafenvoy.netherite.NetheriteExtensionClient;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.fml.event.lifecycle.FMLClientSetupEvent;

@EventBusSubscriber(value = Dist.CLIENT)
public class NetheriteExtensionNeoForgeClient {
    @SubscribeEvent
    public static void init(FMLClientSetupEvent event) {
        event.enqueueWork(NetheriteExtensionClient::process);
    }
}
