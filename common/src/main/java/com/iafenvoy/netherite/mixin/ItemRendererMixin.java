package com.iafenvoy.netherite.mixin;

import com.iafenvoy.netherite.registry.NetheriteItems;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.render.item.ItemModels;
import net.minecraft.client.render.item.ItemRenderer;
import net.minecraft.client.render.model.BakedModel;
import net.minecraft.client.util.ModelIdentifier;
import net.minecraft.item.ItemStack;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyVariable;

import static com.iafenvoy.netherite.NetheriteExtension.MOD_ID;

@Environment(EnvType.CLIENT)
@Mixin(ItemRenderer.class)
public abstract class ItemRendererMixin {
    @Shadow
    @Final
    private ItemModels models;

    @ModifyVariable(method = "getModel", at = @At(value = "STORE", target = "Lnet/minecraft/client/render/item/ItemModels;getModel(Lnet/minecraft/item/ItemStack;)Lnet/minecraft/client/render/model/BakedModel;", ordinal = 2))
    public BakedModel getHeldNetheriteTridentModel(BakedModel value, ItemStack stack) {
        if (stack.isOf(NetheriteItems.NETHERITE_TRIDENT.get()))
            return this.models.getModelManager().getModel(new ModelIdentifier(MOD_ID, "netherite_trident_in_hand", "inventory"));
        return value;
    }
}
