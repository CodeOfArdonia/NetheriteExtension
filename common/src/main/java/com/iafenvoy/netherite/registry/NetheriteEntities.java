package com.iafenvoy.netherite.registry;

import com.iafenvoy.netherite.NetheriteExtension;
import com.iafenvoy.netherite.entity.NetheriteFishingBobberEntity;
import com.iafenvoy.netherite.entity.NetheriteTridentEntity;
import dev.architectury.registry.registries.DeferredRegister;
import dev.architectury.registry.registries.RegistrySupplier;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.SpawnGroup;
import net.minecraft.registry.RegistryKeys;

import java.util.function.Function;

public final class NetheriteEntities {
    public static final DeferredRegister<EntityType<?>> REGISTRY = DeferredRegister.create(NetheriteExtension.MOD_ID, RegistryKeys.ENTITY_TYPE);

    public static final RegistrySupplier<EntityType<NetheriteTridentEntity>> NETHERITE_TRIDENT = register("netherite_trident", EntityType.Builder.<NetheriteTridentEntity>create(NetheriteTridentEntity::new, SpawnGroup.MISC).dimensions(0.5F, 0.5F).maxTrackingRange(4).trackingTickInterval(20).makeFireImmune()::build);
    public static final RegistrySupplier<EntityType<NetheriteFishingBobberEntity>> NETHERITE_FISHING_BOBBER = register("netherite_fishing_bobber", EntityType.Builder.<NetheriteFishingBobberEntity>create(NetheriteFishingBobberEntity::new, SpawnGroup.MISC).dimensions(0.5F, 0.5F).maxTrackingRange(4).trackingTickInterval(20).makeFireImmune()::build);

    public static <T extends Entity> RegistrySupplier<EntityType<T>> register(String id, Function<String, EntityType<T>> group) {
        return REGISTRY.register(id, () -> group.apply(id));
    }
}
