package com.iafenvoy.netherite.registry;

import net.minecraft.component.type.FoodComponent;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;

public final class NetheriteFoodComponents {
    public static final FoodComponent NETHERITE_APPLE = new FoodComponent.Builder().nutrition(8).saturationModifier(2.4F)
            .statusEffect(new StatusEffectInstance(StatusEffects.REGENERATION, 800, 2), 1.0F)
            .statusEffect(new StatusEffectInstance(StatusEffects.RESISTANCE, 12000, 1), 1.0F)
            .statusEffect(new StatusEffectInstance(StatusEffects.FIRE_RESISTANCE, 12000, 1), 1.0F)
            .statusEffect(new StatusEffectInstance(StatusEffects.ABSORPTION, 4800, 4), 1.0F)
            .statusEffect(new StatusEffectInstance(StatusEffects.SATURATION, 800, 0), 1.0F)
            .alwaysEdible().build();
    public static final FoodComponent NETHERITE_CARROT = new FoodComponent.Builder().nutrition(4).saturationModifier(1.2F)
            .statusEffect(new StatusEffectInstance(StatusEffects.REGENERATION, 400, 1), 1.0F)
            .statusEffect(new StatusEffectInstance(StatusEffects.RESISTANCE, 6000, 0), 1.0F)
            .statusEffect(new StatusEffectInstance(StatusEffects.FIRE_RESISTANCE, 6000, 0), 1.0F)
            .statusEffect(new StatusEffectInstance(StatusEffects.ABSORPTION, 2400, 3), 1.0F)
            .alwaysEdible().build();
}
