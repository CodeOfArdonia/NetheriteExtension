package com.iafenvoy.netherite.neoforge.mixin;

import com.iafenvoy.netherite.NetheriteExtension;
import com.iafenvoy.netherite.registry.NetheriteItems;
import net.minecraft.client.render.entity.feature.ElytraFeatureRenderer;
import net.minecraft.client.render.entity.feature.FeatureRenderer;
import net.minecraft.client.render.entity.feature.FeatureRendererContext;
import net.minecraft.client.render.entity.model.EntityModel;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Identifier;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.ModifyVariable;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@OnlyIn(Dist.CLIENT)
@Mixin(ElytraFeatureRenderer.class)
public abstract class ElytraFeatureRendererMixin<T extends LivingEntity, M extends EntityModel<T>> extends FeatureRenderer<T, M> {
    @Shadow
    @Final
    private static Identifier SKIN;
    @Unique
    private static final Identifier NETHERITE_ELYTRA_SKIN = Identifier.of(NetheriteExtension.MOD_ID, "textures/entity/netherite_elytra.png");
    @Unique
    private ItemStack netherite_ext$tempStack = ItemStack.EMPTY;

    public ElytraFeatureRendererMixin(FeatureRendererContext<T, M> context) {
        super(context);
    }

    @Inject(method = "shouldRender", at = @At("HEAD"), cancellable = true)
    private void allowElytra(ItemStack stack, LivingEntity entity, CallbackInfoReturnable<Boolean> cir) {
        this.netherite_ext$tempStack = stack;
        if (stack.isOf(NetheriteItems.NETHERITE_ELYTRA.get())) cir.setReturnValue(true);
    }

    @ModifyVariable(method = "render(Lnet/minecraft/client/util/math/MatrixStack;Lnet/minecraft/client/render/VertexConsumerProvider;ILnet/minecraft/entity/LivingEntity;FFFFFF)V", at = @At("STORE"), ordinal = 0)
    private Identifier handleNetheriteElytraTexture(Identifier identifier) {
        return this.netherite_ext$tempStack.isOf(NetheriteItems.NETHERITE_ELYTRA.get()) && identifier.equals(SKIN) ? NETHERITE_ELYTRA_SKIN : identifier;
    }
}
