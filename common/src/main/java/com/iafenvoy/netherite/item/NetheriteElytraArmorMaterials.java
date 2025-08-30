package com.iafenvoy.netherite.item;

import com.iafenvoy.netherite.config.NetheriteExtensionConfig;
import com.iafenvoy.netherite.registry.NetheriteItems;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.item.ArmorItem;
import net.minecraft.item.ArmorMaterial;
import net.minecraft.item.Items;
import net.minecraft.recipe.Ingredient;
import net.minecraft.sound.SoundEvent;
import net.minecraft.sound.SoundEvents;

public enum NetheriteElytraArmorMaterials implements ArmorMaterial {
    INSTANCE;

    @Override
    public int getDurability(EquipmentSlot slot) {
        return 592;
    }

    @Override
    public int getEnchantability() {
        return 15;
    }

    @Override
    public SoundEvent getEquipSound() {
        return SoundEvents.ITEM_ARMOR_EQUIP_NETHERITE;
    }

    @Override
    public float getKnockbackResistance() {
        return 0.1F;
    }

    @Override
    public String getName() {
        return "netherite_elytra";
    }

    @Override
    public int getProtectionAmount(EquipmentSlot slot) {
        return NetheriteExtensionConfig.INSTANCE.damage.elytra_armor_points;
    }

    @Override
    public Ingredient getRepairIngredient() {
        return Ingredient.ofItems(Items.NETHERITE_INGOT, NetheriteItems.NETHERITE_NUGGET.get());
    }

    @Override
    public float getToughness() {
        return 3;
    }
}
