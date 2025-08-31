package com.iafenvoy.netherite.neoforge;

import com.iafenvoy.netherite.NetheriteExtension;
import com.iafenvoy.netherite.NetheriteExtensionClient;
import com.iafenvoy.netherite.registry.NetheriteItems;
import com.iafenvoy.netherite.registry.NetheritePotions;
import dev.architectury.platform.Platform;
import net.minecraft.potion.Potions;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.event.lifecycle.FMLCommonSetupEvent;
import net.neoforged.neoforge.event.brewing.RegisterBrewingRecipesEvent;

@Mod(NetheriteExtension.MOD_ID)
@EventBusSubscriber(bus = EventBusSubscriber.Bus.MOD)
public final class NetheriteExtensionNeoForge {
    public NetheriteExtensionNeoForge() {
        NetheriteExtension.init();
        if (Platform.getEnv() == Dist.CLIENT)
            NetheriteExtensionClient.init();
    }

    @SubscribeEvent
    public static void process(FMLCommonSetupEvent event) {
        event.enqueueWork(NetheriteExtension::process);
    }

    @EventBusSubscriber(bus = EventBusSubscriber.Bus.GAME)
    public static class NeoForgeEvents {
        @SubscribeEvent
        public static void registerBrewingRecipe(RegisterBrewingRecipesEvent event) {
            event.getBuilder().registerPotionRecipe(Potions.AWKWARD, NetheriteItems.NETHERITE_NUGGET.get(), NetheritePotions.NETHERITE);
        }
    }
}
