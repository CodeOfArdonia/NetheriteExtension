package com.iafenvoy.netherite.item.impl;

import com.iafenvoy.netherite.registry.NetheriteArmorMaterials;
import dev.architectury.injectables.annotations.ExpectPlatform;
import net.minecraft.item.ArmorItem;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;

public class NetheriteElytraItem extends ArmorItem {
    protected NetheriteElytraItem(Settings settings) {
        super(NetheriteArmorMaterials.NETHERITE_ELYTRA, Type.CHESTPLATE, settings);
    }

    @ExpectPlatform
    public static NetheriteElytraItem create(Settings settings) {
        throw new AssertionError("This method should be replaced by Architectury.");
    }

    @Override
    public boolean canRepair(ItemStack stack, ItemStack ingredient) {
        return ingredient.getItem() == Items.PHANTOM_MEMBRANE;
    }
}
