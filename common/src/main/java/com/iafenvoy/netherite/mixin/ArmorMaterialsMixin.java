package com.iafenvoy.netherite.mixin;

import com.iafenvoy.netherite.registry.NetheriteItems;
import net.minecraft.item.ArmorMaterial;
import net.minecraft.item.ArmorMaterials;
import net.minecraft.recipe.Ingredient;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.function.Supplier;

@Mixin(ArmorMaterial.class)
public class ArmorMaterialsMixin {
    @SuppressWarnings("all")
    @Inject(method = "repairIngredient", at = @At("HEAD"), cancellable = true)
    private void replaceNugget(CallbackInfoReturnable<Supplier<Ingredient>> cir) {
        if (this.equals(ArmorMaterials.NETHERITE.value()))
            cir.setReturnValue(() -> Ingredient.ofItems(NetheriteItems.NETHERITE_NUGGET.get()));
    }
}
