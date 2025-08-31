package com.iafenvoy.netherite.fabric;

import com.iafenvoy.netherite.NetheriteExtension;
import com.iafenvoy.netherite.registry.NetheriteItems;
import com.iafenvoy.netherite.registry.NetheritePotions;
import net.fabricmc.api.ModInitializer;
import net.minecraft.potion.Potions;
import net.minecraft.recipe.BrewingRecipeRegistry;

public final class NetheriteExtensionFabric implements ModInitializer {
    @Override
    public void onInitialize() {
        NetheriteExtension.init();
        NetheriteExtension.process();
        BrewingRecipeRegistry.registerPotionRecipe(Potions.AWKWARD, NetheriteItems.NETHERITE_NUGGET.get(), NetheritePotions.NETHERITE.get());
    }
}
