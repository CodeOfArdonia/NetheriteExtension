package com.iafenvoy.netherite.neoforge;

import com.iafenvoy.netherite.NetheriteExtension;
import com.iafenvoy.netherite.NetheriteExtensionClient;
import com.iafenvoy.netherite.registry.NetheriteItems;
import com.iafenvoy.netherite.registry.NetheritePotions;
import dev.architectury.platform.Platform;
import net.minecraft.item.Items;
import net.minecraft.potion.PotionUtil;
import net.minecraft.potion.Potions;
import net.minecraft.recipe.Ingredient;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.event.lifecycle.FMLCommonSetupEvent;
import net.neoforged.neoforge.common.brewing.BrewingRecipeRegistry;

@Mod(NetheriteExtension.MOD_ID)
@Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.MOD)
public final class NetheriteExtensionNeoForge {
    public NetheriteExtensionNeoForge() {
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
