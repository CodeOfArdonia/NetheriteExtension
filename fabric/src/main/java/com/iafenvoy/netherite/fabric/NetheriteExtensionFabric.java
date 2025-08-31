package com.iafenvoy.netherite.fabric;

import com.iafenvoy.netherite.NetheriteExtension;
import com.iafenvoy.netherite.registry.NetheriteItems;
import com.iafenvoy.netherite.registry.NetheritePotions;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.registry.FabricBrewingRecipeRegistryBuilder;
import net.minecraft.potion.Potions;
import net.minecraft.recipe.Ingredient;

public final class NetheriteExtensionFabric implements ModInitializer {
    @Override
    public void onInitialize() {
        NetheriteExtension.init();
        NetheriteExtension.process();
        FabricBrewingRecipeRegistryBuilder.BUILD.register(builder -> builder.registerPotionRecipe(Potions.AWKWARD, Ingredient.ofItems(NetheriteItems.NETHERITE_NUGGET.get()), NetheritePotions.NETHERITE));
    }
}
