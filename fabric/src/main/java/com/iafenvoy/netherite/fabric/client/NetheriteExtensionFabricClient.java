package com.iafenvoy.netherite.fabric.client;

import com.iafenvoy.netherite.NetheriteExtensionClient;
import com.iafenvoy.netherite.registry.NetheriteRenderers;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.impl.client.model.ModelLoadingRegistryImpl;

@Environment(EnvType.CLIENT)
public final class NetheriteExtensionFabricClient implements ClientModInitializer {
    @Override
    public void onInitializeClient() {
        NetheriteExtensionClient.init();
        NetheriteExtensionClient.process();
        ModelLoadingRegistryImpl.INSTANCE.registerModelProvider((manager, out) -> NetheriteRenderers.registerModel(out));
    }
}
