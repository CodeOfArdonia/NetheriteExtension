package com.iafenvoy.netherite.registry;

import com.iafenvoy.netherite.NetheriteExtension;
import com.iafenvoy.netherite.entity.NetheriteTridentEntity;
import com.iafenvoy.netherite.item.block.NetheriteShulkerBoxBlock;
import com.iafenvoy.netherite.item.block.entity.NetheriteShulkerBoxBlockEntity;
import dev.architectury.registry.registries.DeferredRegister;
import dev.architectury.registry.registries.RegistrySupplier;
import net.minecraft.block.Block;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.SpawnGroup;
import net.minecraft.entity.projectile.TridentEntity;
import net.minecraft.registry.RegistryKeys;

public final class NetheriteEntities {
    public static final DeferredRegister<EntityType<?>> REGISTRY = DeferredRegister.create(NetheriteExtension.MOD_ID, RegistryKeys.ENTITY_TYPE);

    public static final RegistrySupplier<EntityType<NetheriteTridentEntity>> NETHERITE_TRIDENT = REGISTRY.register("netherite_trident", () -> EntityType.Builder.<NetheriteTridentEntity>create(NetheriteTridentEntity::new, SpawnGroup.MISC).setDimensions(0.5F, 0.5F).maxTrackingRange(4).trackingTickInterval(20).makeFireImmune().build("netherite_trident"));
}
