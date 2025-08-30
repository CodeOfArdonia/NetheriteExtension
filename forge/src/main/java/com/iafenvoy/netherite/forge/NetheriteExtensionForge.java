package com.iafenvoy.netherite.forge;

import com.iafenvoy.netherite.NetheriteExtension;
import com.iafenvoy.netherite.NetheriteExtensionClient;
import com.iafenvoy.netherite.registry.NetheriteItems;
import com.iafenvoy.netherite.registry.NetheritePotions;
import dev.architectury.platform.Platform;
import dev.architectury.platform.forge.EventBuses;
import net.minecraft.item.Items;
import net.minecraft.potion.PotionUtil;
import net.minecraft.potion.Potions;
import net.minecraft.recipe.Ingredient;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.common.brewing.BrewingRecipeRegistry;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;

@Mod(NetheriteExtension.MOD_ID)
@Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.MOD)
public final class NetheriteExtensionForge {
    @SuppressWarnings("removal")
    public NetheriteExtensionForge() {
        EventBuses.registerModEventBus(NetheriteExtension.MOD_ID, FMLJavaModLoadingContext.get().getModEventBus());
        NetheriteExtension.init();
        if (Platform.getEnv() == Dist.CLIENT)
            NetheriteExtensionClient.init();
    }

    @SubscribeEvent
    public static void process(FMLCommonSetupEvent event) {
        event.enqueueWork(NetheriteExtension::process);
        BrewingRecipeRegistry.addRecipe(Ingredient.ofStacks(PotionUtil.setPotion(Items.POTION.getDefaultStack(), Potions.AWKWARD)),
                Ingredient.ofItems(NetheriteItems.NETHERITE_NUGGET.get()),
                PotionUtil.setPotion(Items.POTION.getDefaultStack(), NetheritePotions.NETHERITE.get()));
        BrewingRecipeRegistry.addRecipe(Ingredient.ofStacks(PotionUtil.setPotion(Items.SPLASH_POTION.getDefaultStack(), Potions.AWKWARD)),
                Ingredient.ofItems(NetheriteItems.NETHERITE_NUGGET.get()),
                PotionUtil.setPotion(Items.SPLASH_POTION.getDefaultStack(), NetheritePotions.NETHERITE.get()));
        BrewingRecipeRegistry.addRecipe(Ingredient.ofStacks(PotionUtil.setPotion(Items.LINGERING_POTION.getDefaultStack(), Potions.AWKWARD)),
                Ingredient.ofItems(NetheriteItems.NETHERITE_NUGGET.get()),
                PotionUtil.setPotion(Items.LINGERING_POTION.getDefaultStack(), NetheritePotions.NETHERITE.get()));

        BrewingRecipeRegistry.addRecipe(Ingredient.ofStacks(PotionUtil.setPotion(Items.POTION.getDefaultStack(), NetheritePotions.NETHERITE.get())),
                Ingredient.ofItems(Items.GUNPOWDER),
                PotionUtil.setPotion(Items.SPLASH_POTION.getDefaultStack(), NetheritePotions.NETHERITE.get()));
        BrewingRecipeRegistry.addRecipe(Ingredient.ofStacks(PotionUtil.setPotion(Items.SPLASH_POTION.getDefaultStack(), NetheritePotions.NETHERITE.get())),
                Ingredient.ofItems(Items.DRAGON_BREATH),
                PotionUtil.setPotion(Items.LINGERING_POTION.getDefaultStack(), NetheritePotions.NETHERITE.get()));
    }
}
