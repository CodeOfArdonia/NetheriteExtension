package com.iafenvoy.netherite.render;

import com.iafenvoy.netherite.NetheriteExtension;
import com.iafenvoy.netherite.item.block.NetheriteShulkerBoxBlock;
import com.iafenvoy.netherite.item.block.entity.NetheriteShulkerBoxBlockEntity;
import com.iafenvoy.netherite.registry.NetheriteBlocks;
import com.iafenvoy.netherite.registry.NetheriteItems;
import com.mojang.datafixers.util.Pair;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.block.entity.BannerBlockEntity;
import net.minecraft.block.entity.BannerPattern;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.block.entity.BannerBlockEntityRenderer;
import net.minecraft.client.render.block.entity.BlockEntityRenderDispatcher;
import net.minecraft.client.render.entity.model.ShieldEntityModel;
import net.minecraft.client.render.entity.model.TridentEntityModel;
import net.minecraft.client.render.item.ItemRenderer;
import net.minecraft.client.render.model.BakedModelManager;
import net.minecraft.client.render.model.json.ModelTransformation;
import net.minecraft.client.texture.SpriteAtlasTexture;
import net.minecraft.client.util.ModelIdentifier;
import net.minecraft.client.util.SpriteIdentifier;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.item.BlockItem;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ShieldItem;
import net.minecraft.util.DyeColor;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.BlockPos;

import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.function.Consumer;

@SuppressWarnings("deprecation")
@Environment(EnvType.CLIENT)
public class NetheriteBuiltinItemRenderer {
    private static final NetheriteShulkerBoxBlockEntity RENDER_NETHERITE_SHULKER_BOX = new NetheriteShulkerBoxBlockEntity(BlockPos.ORIGIN, NetheriteBlocks.NETHERITE_SHULKER_BOX.get().getDefaultState());
    private static final NetheriteShulkerBoxBlockEntity[] RENDER_NETHERITE_SHULKER_BOX_DYED = Arrays.stream(DyeColor.values()).sorted(Comparator.comparingInt(DyeColor::getId)).map(dyeColor -> new NetheriteShulkerBoxBlockEntity(dyeColor, BlockPos.ORIGIN, NetheriteBlocks.NETHERITE_SHULKER_BOX.get().getDefaultState())).toArray(NetheriteShulkerBoxBlockEntity[]::new);
    private static final SpriteIdentifier NETHERITE_SHIELD_BASE = new SpriteIdentifier(SpriteAtlasTexture.BLOCK_ATLAS_TEXTURE, new Identifier(NetheriteExtension.MOD_ID, "entity/netherite_shield_base"));
    private static final SpriteIdentifier NETHERITE_SHIELD_BASE_NO_PATTERN = new SpriteIdentifier(SpriteAtlasTexture.BLOCK_ATLAS_TEXTURE, new Identifier(NetheriteExtension.MOD_ID, "entity/netherite_shield_base_nopattern"));

    public static void registerTextures(Consumer<Identifier> consumer) {
        consumer.accept(NETHERITE_SHIELD_BASE.getTextureId());
        consumer.accept(NETHERITE_SHIELD_BASE_NO_PATTERN.getTextureId());
    }

    public static boolean render(ItemStack stack, ModelTransformation.Mode mode, MatrixStack matrices, VertexConsumerProvider vertexConsumers, int light, int overlay, ShieldEntityModel shieldModel, TridentEntityModel tridentModel, BlockEntityRenderDispatcher dispatcher) {
        if (stack.isOf(NetheriteItems.NETHERITE_TRIDENT.get())) {
            if (mode.ordinal() >= 5) {
                ItemRenderer itemRenderer = MinecraftClient.getInstance().getItemRenderer();
                BakedModelManager bakedModelManager = MinecraftClient.getInstance().getBakedModelManager();
                matrices.pop();
                itemRenderer.renderItem(stack, mode, false, matrices, vertexConsumers, light, overlay, bakedModelManager.getModel(new ModelIdentifier(NetheriteExtension.MOD_ID, "netherite_trident", "inventory")));
                matrices.push();
            } else {
                matrices.push();
                matrices.scale(1, -1, -1);
                VertexConsumer consumer = ItemRenderer.getDirectItemGlintConsumer(vertexConsumers, tridentModel.getLayer(new Identifier(NetheriteExtension.MOD_ID, "textures/entity/netherite_trident.png")), false, stack.hasGlint());
                tridentModel.render(matrices, consumer, light, overlay, 1, 1, 1, 1);
                matrices.pop();
            }
        } else if (stack.isOf(NetheriteItems.NETHERITE_SHIELD.get())) {
            boolean bl = BlockItem.getBlockEntityNbt(stack) != null;
            matrices.push();
            matrices.scale(1, -1, -1);
            SpriteIdentifier spriteIdentifier = bl ? NETHERITE_SHIELD_BASE : NETHERITE_SHIELD_BASE_NO_PATTERN;
            VertexConsumer vertexConsumer = spriteIdentifier.getSprite().getTextureSpecificVertexConsumer(ItemRenderer.getDirectItemGlintConsumer(vertexConsumers, shieldModel.getLayer(spriteIdentifier.getAtlasId()), true, stack.hasGlint()));
            shieldModel.getHandle().render(matrices, vertexConsumer, light, overlay, 1, 1, 1, 1);
            if (bl) {
                List<Pair<BannerPattern, DyeColor>> list = BannerBlockEntity.getPatternsFromNbt(ShieldItem.getColor(stack), BannerBlockEntity.getPatternListNbt(stack));
                BannerBlockEntityRenderer.renderCanvas(matrices, vertexConsumers, light, overlay, shieldModel.getPlate(), spriteIdentifier, false, list, stack.hasGlint());
            } else {
                shieldModel.getPlate().render(matrices, vertexConsumer, light, overlay, 1, 1, 1, 1);
            }

            matrices.pop();
        } else if (stack.getItem() instanceof BlockItem blockItem && blockItem.getBlock() instanceof NetheriteShulkerBoxBlock block) {
            DyeColor dyecolor = block.getColor();
            NetheriteShulkerBoxBlockEntity entity = dyecolor == null ? RENDER_NETHERITE_SHULKER_BOX : RENDER_NETHERITE_SHULKER_BOX_DYED[dyecolor.getId()];
            dispatcher.renderEntity(entity, matrices, vertexConsumers, light, overlay);
        } else return false;
        return true;
    }
}
