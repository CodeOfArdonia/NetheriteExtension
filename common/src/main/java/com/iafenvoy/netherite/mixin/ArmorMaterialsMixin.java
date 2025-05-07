package com.iafenvoy.netherite.mixin;

import com.iafenvoy.netherite.registry.NetheriteItems;
import net.minecraft.item.ArmorMaterials;
import net.minecraft.recipe.Ingredient;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(ArmorMaterials.class)
public class ArmorMaterialsMixin {
    @SuppressWarnings("all")
    @Inject(method = "getRepairIngredient", at = @At("HEAD"), cancellable = true)
    private void replaceNugget(CallbackInfoReturnable<Ingredient> cir) {
        if (this.equals(ArmorMaterials.NETHERITE))
            cir.setReturnValue(Ingredient.ofItems(NetheriteItems.NETHERITE_NUGGET.get()));
    }
}
