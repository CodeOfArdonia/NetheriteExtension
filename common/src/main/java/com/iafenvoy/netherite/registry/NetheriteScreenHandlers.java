package com.iafenvoy.netherite.registry;

import com.iafenvoy.netherite.NetheriteExtension;
import com.iafenvoy.netherite.screen.gui.NetheriteAnvilScreen;
import com.iafenvoy.netherite.screen.handler.NetheriteAnvilScreenHandler;
import dev.architectury.registry.menu.MenuRegistry;
import dev.architectury.registry.registries.DeferredRegister;
import dev.architectury.registry.registries.RegistrySupplier;
import net.minecraft.client.gui.screen.ingame.HandledScreens;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.resource.featuretoggle.FeatureSet;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.screen.ScreenHandlerType;

public final class NetheriteScreenHandlers {
    public static final DeferredRegister<ScreenHandlerType<?>> REGISTRY = DeferredRegister.create(NetheriteExtension.MOD_ID, RegistryKeys.SCREEN_HANDLER);

    public static final RegistrySupplier<ScreenHandlerType<NetheriteAnvilScreenHandler>> NETHERITE_ANVIL = register("netherite_anvil", NetheriteAnvilScreenHandler::new);

    public static <T extends ScreenHandler> RegistrySupplier<ScreenHandlerType<T>> register(String id, ScreenHandlerType.Factory<T> factory) {
        return REGISTRY.register(id, () -> new ScreenHandlerType<>(factory, FeatureSet.empty()));
    }

    public static void initializeClient() {
        HandledScreens.register(NetheriteScreenHandlers.NETHERITE_ANVIL.get(), NetheriteAnvilScreen::new);
    }
}
