package com.iafenvoy.netherite.registry;

import com.iafenvoy.netherite.NetheriteExtension;
import dev.architectury.registry.registries.DeferredRegister;
import dev.architectury.registry.registries.RegistrySupplier;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.potion.Potion;
import net.minecraft.registry.RegistryKeys;

import java.util.function.Supplier;

public final class NetheritePotions {
    public static final DeferredRegister<Potion> REGISTRY = DeferredRegister.create(NetheriteExtension.MOD_ID, RegistryKeys.POTION);

    public static final RegistrySupplier<Potion> NETHERITE = register("netherite", () -> new Potion(new StatusEffectInstance(StatusEffects.FIRE_RESISTANCE, 3600), new StatusEffectInstance(StatusEffects.RESISTANCE, 1200, 2)));

    public static RegistrySupplier<Potion> register(String id, Supplier<Potion> group) {
        return REGISTRY.register(id, group);
    }
}
