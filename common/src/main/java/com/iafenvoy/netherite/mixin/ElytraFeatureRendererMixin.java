package com.iafenvoy.netherite.mixin;

import com.iafenvoy.netherite.NetheriteExtension;
import com.iafenvoy.netherite.registry.NetheriteItems;
import com.llamalad7.mixinextras.sugar.Local;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.network.AbstractClientPlayerEntity;
import net.minecraft.client.render.OverlayTexture;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.PlayerModelPart;
import net.minecraft.client.render.entity.feature.ElytraFeatureRenderer;
import net.minecraft.client.render.entity.feature.FeatureRenderer;
import net.minecraft.client.render.entity.feature.FeatureRendererContext;
import net.minecraft.client.render.entity.model.ElytraEntityModel;
import net.minecraft.client.render.entity.model.EntityModel;
import net.minecraft.client.render.item.ItemRenderer;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Identifier;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Environment(EnvType.CLIENT)
@Mixin(ElytraFeatureRenderer.class)
public abstract class ElytraFeatureRendererMixin<T extends LivingEntity, M extends EntityModel<T>> extends FeatureRenderer<T, M> {
    @Unique
    private static final Identifier NETHERITE_ELYTRA_SKIN = Identifier.of(NetheriteExtension.MOD_ID, "textures/entity/netherite_elytra.png");
    @Shadow
    @Final
    private ElytraEntityModel<T> elytra;

    public ElytraFeatureRendererMixin(FeatureRendererContext<T, M> context) {
        super(context);
    }

    @Inject(method = "render(Lnet/minecraft/client/util/math/MatrixStack;Lnet/minecraft/client/render/VertexConsumerProvider;ILnet/minecraft/entity/LivingEntity;FFFFFF)V", at = @At("RETURN"))
    private void appendNetheriteElytraRender(MatrixStack matrixStack, VertexConsumerProvider vertexConsumerProvider, int i, T livingEntity, float f, float g, float h, float j, float k, float l, CallbackInfo ci, @Local ItemStack itemStack) {
        if (itemStack.isOf(NetheriteItems.NETHERITE_ELYTRA.get())) {
            Identifier identifier4;
            if (livingEntity instanceof AbstractClientPlayerEntity abstractClientPlayerEntity) {
                if (abstractClientPlayerEntity.canRenderElytraTexture() && abstractClientPlayerEntity.getElytraTexture() != null)
                    identifier4 = abstractClientPlayerEntity.getElytraTexture();
                else if (abstractClientPlayerEntity.canRenderCapeTexture() && abstractClientPlayerEntity.getCapeTexture() != null && abstractClientPlayerEntity.isPartVisible(PlayerModelPart.CAPE))
                    identifier4 = abstractClientPlayerEntity.getCapeTexture();
                else identifier4 = NETHERITE_ELYTRA_SKIN;
            } else identifier4 = NETHERITE_ELYTRA_SKIN;

            matrixStack.push();
            matrixStack.translate(0.0D, 0.0D, 0.125D);
            this.getContextModel().copyStateTo(this.elytra);
            this.elytra.setAngles(livingEntity, f, g, j, k, l);
            VertexConsumer vertexConsumer = ItemRenderer.getDirectItemGlintConsumer(vertexConsumerProvider, this.elytra.getLayer(identifier4), false, itemStack.hasGlint());
            this.elytra.render(matrixStack, vertexConsumer, i, OverlayTexture.DEFAULT_UV, 1.0F, 1.0F, 1.0F, 1.0F);
            matrixStack.pop();
        }
    }
}
