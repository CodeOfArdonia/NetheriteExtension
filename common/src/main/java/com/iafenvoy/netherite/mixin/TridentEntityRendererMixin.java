package com.iafenvoy.netherite.mixin;

import com.iafenvoy.netherite.NetheriteExtension;
import com.iafenvoy.netherite.registry.NetheriteItems;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.render.entity.TridentEntityRenderer;
import net.minecraft.entity.projectile.TridentEntity;
import net.minecraft.util.Identifier;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Environment(EnvType.CLIENT)
@Mixin(TridentEntityRenderer.class)
public class TridentEntityRendererMixin {
    @Inject(method = "getTexture(Lnet/minecraft/entity/projectile/TridentEntity;)Lnet/minecraft/util/Identifier;", at = @At(value = "HEAD"), cancellable = true)
    public void getTextureMixin(TridentEntity entity, CallbackInfoReturnable<Identifier> cir) {
        if (entity.tridentStack.isOf(NetheriteItems.NETHERITE_TRIDENT.get()))
            cir.setReturnValue(Identifier.of(NetheriteExtension.MOD_ID, "textures/entity/netherite_trident.png"));
    }
}
