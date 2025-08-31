package com.iafenvoy.netherite.registry;

import com.iafenvoy.netherite.NetheriteExtension;
import dev.architectury.registry.registries.DeferredRegister;
import dev.architectury.registry.registries.RegistrySupplier;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.item.ArmorItem;
import net.minecraft.item.ArmorMaterial;
import net.minecraft.item.Items;
import net.minecraft.recipe.Ingredient;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.Util;

import java.util.EnumMap;
import java.util.List;
import java.util.function.Supplier;

public final class NetheriteArmorMaterials {
    public static final DeferredRegister<ArmorMaterial> REGISTRY = DeferredRegister.create(NetheriteExtension.MOD_ID, RegistryKeys.ARMOR_MATERIAL);

    public static final RegistrySupplier<ArmorMaterial> NETHERITE_ELYTRA = register("netherite_elytra", () -> new ArmorMaterial(Util.make(new EnumMap<>(ArmorItem.Type.class), map -> {
        map.put(ArmorItem.Type.BOOTS, 1);
        map.put(ArmorItem.Type.LEGGINGS, 4);
        map.put(ArmorItem.Type.CHESTPLATE, 5);
        map.put(ArmorItem.Type.HELMET, 2);
        map.put(ArmorItem.Type.BODY, 4);
    }), 15, SoundEvents.ITEM_ARMOR_EQUIP_NETHERITE, () -> Ingredient.ofItems(Items.NETHERITE_INGOT, NetheriteItems.NETHERITE_NUGGET.get()), List.of(), 3, 0.1f));

    public static <T extends BlockEntity> RegistrySupplier<ArmorMaterial> register(String id, Supplier<ArmorMaterial> group) {
        return REGISTRY.register(id, group);
    }
}
