package com.iafenvoy.netherite.render;

import com.iafenvoy.netherite.NetheriteExtension;
import com.iafenvoy.netherite.item.block.entity.NetheriteShulkerBoxBlockEntity;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.block.BlockState;
import net.minecraft.block.ShulkerBoxBlock;
import net.minecraft.client.model.ModelPart;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.block.entity.BlockEntityRenderer;
import net.minecraft.client.render.block.entity.BlockEntityRendererFactory;
import net.minecraft.client.render.entity.model.EntityModelLayers;
import net.minecraft.client.render.entity.model.ShulkerEntityModel;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.DyeColor;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.Direction;

@Environment(EnvType.CLIENT)
public class NetheriteShulkerBoxBlockEntityRenderer implements BlockEntityRenderer<NetheriteShulkerBoxBlockEntity> {
    private final ShulkerEntityModel<?> model;

    public NetheriteShulkerBoxBlockEntityRenderer(BlockEntityRendererFactory.Context context) {
        this.model = new ShulkerEntityModel<>(context.getLayerModelPart(EntityModelLayers.SHULKER));
    }

    @Override
    public void render(NetheriteShulkerBoxBlockEntity shulkerBoxBlockEntity, float f, MatrixStack matrixStack, VertexConsumerProvider vertexConsumerProvider, int i, int j) {
        Direction direction = Direction.UP;
        BlockState blockState = shulkerBoxBlockEntity.getCachedState();
        if (blockState.getBlock() instanceof ShulkerBoxBlock)
            direction = blockState.get(ShulkerBoxBlock.FACING);
        DyeColor dyeColor = shulkerBoxBlockEntity.getColor();
        matrixStack.push();
        matrixStack.translate(0.5, 0.5, 0.5);
        float scale = 0.9995F;
        matrixStack.scale(scale, scale, scale);
        matrixStack.multiply(direction.getRotationQuaternion());
        matrixStack.scale(1.0F, -1.0F, -1.0F);
        matrixStack.translate(0.0, -1.0, 0.0);
        ModelPart modelPart = this.model.getLid();
        modelPart.setPivot(0.0F, 24.0F - shulkerBoxBlockEntity.getAnimationProgress(f) * 0.5F * 16.0F, 0.0F);
        modelPart.yaw = 270.0F * shulkerBoxBlockEntity.getAnimationProgress(f) * (float) (Math.PI / 180.0);
        VertexConsumer vertexConsumer = vertexConsumerProvider.getBuffer(RenderLayer.getEntityCutoutNoCull(new Identifier(NetheriteExtension.MOD_ID, "textures/entity/netherite_shulker/netherite_shulker" + (dyeColor == null ? "" : "_" + dyeColor.getName()) + ".png")));
        this.model.render(matrixStack, vertexConsumer, i, j, 1.0F, 1.0F, 1.0F, 1.0F);
        matrixStack.pop();
    }
}
