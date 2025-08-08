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
import net.minecraft.client.render.entity.model.EntityModelLayers;
import net.minecraft.client.render.entity.model.ShieldEntityModel;
import net.minecraft.client.render.entity.model.TridentEntityModel;
import net.minecraft.client.render.item.ItemRenderer;
import net.minecraft.client.render.model.json.ModelTransformationMode;
import net.minecraft.client.texture.SpriteAtlasTexture;
import net.minecraft.client.util.ModelIdentifier;
import net.minecraft.client.util.SpriteIdentifier;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.item.BlockItem;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ShieldItem;
import net.minecraft.registry.entry.RegistryEntry;
import net.minecraft.util.DyeColor;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.BlockPos;

import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.function.Supplier;

@SuppressWarnings("deprecation")
@Environment(EnvType.CLIENT)
public class NetheritePlusBuiltinItemModelRenderer {
    private static final Supplier<MinecraftClient> CLIENT = MinecraftClient::getInstance;
    private static final SpriteIdentifier NETHERITE_SHIELD_BASE = new SpriteIdentifier(SpriteAtlasTexture.BLOCK_ATLAS_TEXTURE, Identifier.of(NetheriteExtension.MOD_ID, "entity/netherite_shield_base"));
    private static final SpriteIdentifier NETHERITE_SHIELD_BASE_NO_PATTERN = new SpriteIdentifier(SpriteAtlasTexture.BLOCK_ATLAS_TEXTURE, Identifier.of(NetheriteExtension.MOD_ID, "entity/netherite_shield_base_nopattern"));
    private static final NetheriteShulkerBoxBlockEntity RENDER_NETHERITE_SHULKER_BOX = new NetheriteShulkerBoxBlockEntity(BlockPos.ORIGIN, NetheriteBlocks.NETHERITE_SHULKER_BOX.get().getDefaultState());
    private static final NetheriteShulkerBoxBlockEntity[] RENDER_NETHERITE_SHULKER_BOX_DYED = Arrays.stream(DyeColor.values()).sorted(Comparator.comparingInt(DyeColor::getId)).map(dyeColor -> new NetheriteShulkerBoxBlockEntity(dyeColor, BlockPos.ORIGIN, NetheriteBlocks.NETHERITE_SHULKER_BOX.get().getDefaultState())).toArray(NetheriteShulkerBoxBlockEntity[]::new);
    private static ShieldEntityModel modelNetheriteShield;

    public static void renderTrident(TridentEntityModel model, ItemStack stack, ModelTransformationMode mode, MatrixStack matrices, VertexConsumerProvider vertexConsumers, int light, int overlay) {
        if (mode.getIndex() >= 5) {
            matrices.pop();
            CLIENT.get().getItemRenderer().renderItem(stack, mode, false, matrices, vertexConsumers, light, overlay, CLIENT.get().getBakedModelManager().getModel(new ModelIdentifier(NetheriteExtension.MOD_ID, "netherite_trident", "inventory")));
            matrices.push();
        } else {
            matrices.push();
            matrices.scale(1.0F, -1.0F, -1.0F);
            VertexConsumer consumer = ItemRenderer.getDirectItemGlintConsumer(vertexConsumers, model.getLayer(Identifier.of(NetheriteExtension.MOD_ID, "textures/entity/netherite_trident.png")), false, stack.hasGlint());
            model.render(matrices, consumer, light, overlay, 1.0F, 1.0F, 1.0F, 1.0F);
            matrices.pop();
        }
    }

    public void render(ItemStack itemStack, ModelTransformationMode transformType, MatrixStack matrices, VertexConsumerProvider vertices, int light, int overlay) {
        if (itemStack.isOf(NetheriteItems.NETHERITE_TRIDENT.get()))
            renderTrident(new TridentEntityModel(CLIENT.get().getEntityModelLoader().getModelPart(EntityModelLayers.TRIDENT)), itemStack, transformType, matrices, vertices, light, overlay);
        else if (itemStack.isOf(NetheriteItems.NETHERITE_SHIELD.get())) {
            if (modelNetheriteShield == null)
                modelNetheriteShield = new ShieldEntityModel(CLIENT.get().getEntityModelLoader().getModelPart(EntityModelLayers.SHIELD));
            boolean bl = BlockItem.getBlockEntityNbt(itemStack) != null;
            matrices.push();
            matrices.scale(1.0F, -1.0F, -1.0F);
            SpriteIdentifier spriteIdentifier = bl ? NETHERITE_SHIELD_BASE : NETHERITE_SHIELD_BASE_NO_PATTERN;
            VertexConsumer vertexConsumer = spriteIdentifier.getSprite().getTextureSpecificVertexConsumer(ItemRenderer.getDirectItemGlintConsumer(vertices, modelNetheriteShield.getLayer(spriteIdentifier.getAtlasId()), true, itemStack.hasGlint()));
            modelNetheriteShield.getHandle().render(matrices, vertexConsumer, light, overlay, 1.0F, 1.0F, 1.0F, 1.0F);
            if (bl) {
                List<Pair<RegistryEntry<BannerPattern>, DyeColor>> list = BannerBlockEntity.getPatternsFromNbt(ShieldItem.getColor(itemStack), BannerBlockEntity.getPatternListNbt(itemStack));
                BannerBlockEntityRenderer.renderCanvas(matrices, vertices, light, overlay, modelNetheriteShield.getPlate(), spriteIdentifier, false, list, itemStack.hasGlint());
            } else {
                modelNetheriteShield.getPlate().render(matrices, vertexConsumer, light, overlay, 1.0F, 1.0F, 1.0F, 1.0F);
            }

            matrices.pop();
        } else if (itemStack.getItem() instanceof BlockItem blockItem && blockItem.getBlock() instanceof NetheriteShulkerBoxBlock block) {
            DyeColor dyecolor = block.getColor();
            NetheriteShulkerBoxBlockEntity entity = dyecolor == null ? RENDER_NETHERITE_SHULKER_BOX : RENDER_NETHERITE_SHULKER_BOX_DYED[dyecolor.getId()];
            CLIENT.get().getBlockEntityRenderDispatcher().renderEntity(entity, matrices, vertices, light, overlay);
        }
    }
}
