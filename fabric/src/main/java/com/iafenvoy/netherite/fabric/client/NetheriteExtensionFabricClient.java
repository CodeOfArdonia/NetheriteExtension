package com.iafenvoy.netherite.fabric.client;

import com.iafenvoy.netherite.NetheriteExtensionClient;
import com.iafenvoy.netherite.render.NetheriteBuiltinItemRenderer;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.event.client.ClientSpriteRegistryCallback;
import net.minecraft.client.texture.SpriteAtlasTexture;

@Environment(EnvType.CLIENT)
public final class NetheriteExtensionFabricClient implements ClientModInitializer {
    @SuppressWarnings("deprecation")
    @Override
    public void onInitializeClient() {
        NetheriteExtensionClient.init();
        NetheriteExtensionClient.process();
        ClientSpriteRegistryCallback.event(SpriteAtlasTexture.BLOCK_ATLAS_TEXTURE).register((atlasTexture, registry) -> NetheriteBuiltinItemRenderer.registerTextures(registry::register));
    }
}
