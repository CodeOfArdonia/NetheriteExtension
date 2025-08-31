package com.iafenvoy.netherite.forge;

import com.iafenvoy.netherite.NetheriteExtensionClient;
import com.iafenvoy.netherite.render.NetheriteBuiltinItemRenderer;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.TextureStitchEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;

@Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
public class NetheriteExtensionForgeClient {
    @SubscribeEvent
    public static void init(FMLClientSetupEvent event) {
        event.enqueueWork(NetheriteExtensionClient::process);
    }

    @SubscribeEvent
    public static void registerAtlasTexture(TextureStitchEvent.Pre event) {
        NetheriteBuiltinItemRenderer.registerTextures(event::addSprite);
    }
}
