package com.iafenvoy.netherite.registry;

import com.iafenvoy.netherite.NetheriteExtension;
import com.iafenvoy.netherite.render.NetheriteShulkerBoxBlockEntityRenderer;
import com.iafenvoy.netherite.render.NetheriteTridentEntityRenderer;
import dev.architectury.registry.client.level.entity.EntityRendererRegistry;
import dev.architectury.registry.client.rendering.BlockEntityRendererRegistry;
import dev.architectury.registry.item.ItemPropertiesRegistry;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.util.ModelIdentifier;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.CrossbowItem;
import net.minecraft.item.ElytraItem;
import net.minecraft.item.FishingRodItem;
import net.minecraft.item.Items;
import net.minecraft.util.Identifier;

import java.util.function.Consumer;

import static com.iafenvoy.netherite.registry.NetheriteItems.*;

@Environment(EnvType.CLIENT)
public final class NetheriteRenderers {
    public static void registerEntityRenderers() {
        EntityRendererRegistry.register(NetheriteEntities.NETHERITE_TRIDENT, NetheriteTridentEntityRenderer::new);
    }

    public static void registerModelPredicates() {
        ItemPropertiesRegistry.register(NETHERITE_ELYTRA.get(), Identifier.of(Identifier.DEFAULT_NAMESPACE, "broken"), (itemStack, clientWorld, livingEntity, i) -> ElytraItem.isUsable(itemStack) ? 0.0F : 1.0F);
        ItemPropertiesRegistry.register(NETHERITE_FISHING_ROD.get(), Identifier.of(Identifier.DEFAULT_NAMESPACE, "cast"), (itemStack, clientWorld, livingEntity, i) -> {
            if (livingEntity == null) return 0.0F;
            boolean bl = livingEntity.getMainHandStack() == itemStack;
            boolean bl2 = livingEntity.getOffHandStack() == itemStack;
            if (livingEntity.getMainHandStack().getItem() instanceof FishingRodItem) bl2 = false;
            return (bl || bl2) && livingEntity instanceof PlayerEntity player && player.fishHook != null ? 1.0F : 0.0F;
        });
        ItemPropertiesRegistry.register(NETHERITE_BOW.get(), Identifier.of(Identifier.DEFAULT_NAMESPACE, "pull"), (itemStack, clientWorld, livingEntity, i) -> livingEntity == null ? 0.0F : livingEntity.getActiveItem() != itemStack ? 0.0F : (itemStack.getMaxUseTime() - livingEntity.getItemUseTimeLeft()) / 20.0F);
        ItemPropertiesRegistry.register(NETHERITE_BOW.get(), Identifier.of(Identifier.DEFAULT_NAMESPACE, "pulling"), (itemStack, clientWorld, livingEntity, i) -> livingEntity == null ? 0.0F : livingEntity.isUsingItem() && livingEntity.getActiveItem() == itemStack ? 1.0F : 0.0F);
        ItemPropertiesRegistry.register(NETHERITE_CROSSBOW.get(), Identifier.of(Identifier.DEFAULT_NAMESPACE, "pull"), (itemStack, clientWorld, livingEntity, i) -> livingEntity == null ? 0.0F : CrossbowItem.isCharged(itemStack) ? 0.0F : (float) (itemStack.getMaxUseTime() - livingEntity.getItemUseTimeLeft()) / (float) CrossbowItem.getPullTime(itemStack));
        ItemPropertiesRegistry.register(NETHERITE_CROSSBOW.get(), Identifier.of(Identifier.DEFAULT_NAMESPACE, "pulling"), (itemStack, clientWorld, livingEntity, i) -> livingEntity == null ? 0.0F : livingEntity.isUsingItem() && livingEntity.getActiveItem() == itemStack && !CrossbowItem.isCharged(itemStack) ? 1.0F : 0.0F);
        ItemPropertiesRegistry.register(NETHERITE_CROSSBOW.get(), Identifier.of(Identifier.DEFAULT_NAMESPACE, "charged"), (itemStack, clientWorld, livingEntity, i) -> livingEntity == null ? 0.0F : CrossbowItem.isCharged(itemStack) ? 1.0F : 0.0F);
        ItemPropertiesRegistry.register(NETHERITE_CROSSBOW.get(), Identifier.of(Identifier.DEFAULT_NAMESPACE, "firework"), (itemStack, clientWorld, livingEntity, i) -> livingEntity == null ? 0.0F : CrossbowItem.isCharged(itemStack) && CrossbowItem.hasProjectile(itemStack, Items.FIREWORK_ROCKET) ? 1.0F : 0.0F);
        ItemPropertiesRegistry.register(NETHERITE_TRIDENT.get(), Identifier.of(Identifier.DEFAULT_NAMESPACE, "throwing"), (itemStack, clientWorld, livingEntity, i) -> livingEntity != null && livingEntity.isUsingItem() && livingEntity.getActiveItem() == itemStack ? 1.0F : 0.0F);
        ItemPropertiesRegistry.register(NETHERITE_SHIELD.get(), Identifier.of(Identifier.DEFAULT_NAMESPACE, "blocking"), (stack, world, entity, seed) -> entity != null && entity.isUsingItem() && entity.getActiveItem() == stack ? 1.0F : 0.0F);
    }

    public static void registerBlockEntityRenderers() {
        BlockEntityRendererRegistry.register(NetheriteBlockEntities.NETHERITE_SHULKER_BOX_ENTITY.get(), NetheriteShulkerBoxBlockEntityRenderer::new);
    }

    public static void registerModel(Consumer<Identifier> consumer) {
        consumer.accept(new ModelIdentifier(NetheriteExtension.MOD_ID, "netherite_trident", "inventory"));
        consumer.accept(new ModelIdentifier(NetheriteExtension.MOD_ID, "netherite_trident_in_hand", "inventory"));
    }
}
