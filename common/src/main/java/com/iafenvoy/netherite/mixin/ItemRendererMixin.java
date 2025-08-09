package com.iafenvoy.netherite.mixin;

import com.iafenvoy.netherite.registry.NetheriteItems;
import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
import com.llamalad7.mixinextras.sugar.Local;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.render.item.ItemRenderer;
import net.minecraft.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

@Environment(EnvType.CLIENT)
@Mixin(ItemRenderer.class)
public abstract class ItemRendererMixin {
    @ModifyExpressionValue(method = "getModel", at = @At(value = "INVOKE", target = "Lnet/minecraft/item/ItemStack;isOf(Lnet/minecraft/item/Item;)Z", ordinal = 0))
    private boolean getHeldNetheriteTridentModel(boolean original, @Local(argsOnly = true) ItemStack stack) {
        return original || stack.isOf(NetheriteItems.NETHERITE_TRIDENT.get());
    }
}
