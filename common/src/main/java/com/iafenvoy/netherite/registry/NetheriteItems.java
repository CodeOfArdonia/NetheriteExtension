package com.iafenvoy.netherite.registry;

import com.iafenvoy.netherite.NetheriteExtension;
import com.iafenvoy.netherite.config.NetheriteExtensionConfig;
import com.iafenvoy.netherite.item.NetheriteBowItem;
import com.iafenvoy.netherite.item.NetheriteFishingRodItem;
import com.iafenvoy.netherite.item.NetheriteShearsItem;
import com.iafenvoy.netherite.item.NetheriteTridentItem;
import com.iafenvoy.netherite.item.impl.NetheriteElytraItem;
import com.iafenvoy.netherite.item.impl.NetheriteShieldItem;
import dev.architectury.registry.CreativeTabRegistry;
import dev.architectury.registry.registries.DeferredRegister;
import dev.architectury.registry.registries.RegistrySupplier;
import net.minecraft.block.DispenserBlock;
import net.minecraft.block.dispenser.ShearsDispenserBehavior;
import net.minecraft.component.DataComponentTypes;
import net.minecraft.component.type.PotionContentsComponent;
import net.minecraft.item.*;
import net.minecraft.potion.Potion;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.registry.entry.RegistryEntry;
import net.minecraft.util.Rarity;

import java.util.function.Supplier;

@SuppressWarnings({"unused", "UnstableApiUsage"})
public final class NetheriteItems {
    public static final DeferredRegister<Item> REGISTRY = DeferredRegister.create(NetheriteExtension.MOD_ID, RegistryKeys.ITEM);

    public static final RegistrySupplier<Item> NETHERITE_NUGGET = register("netherite_nugget", () -> new Item(new Item.Settings().fireproof().arch$tab(NetheriteItemGroups.MAIN)));
    public static final RegistrySupplier<Item> NETHERITE_ELYTRA = register("netherite_elytra", () -> NetheriteElytraItem.create(new Item.Settings().maxDamage(NetheriteExtensionConfig.INSTANCE.durability.elytra).rarity(Rarity.UNCOMMON).fireproof().arch$tab(NetheriteItemGroups.MAIN)));
    public static final RegistrySupplier<Item> NETHERITE_FISHING_ROD = register("netherite_fishing_rod", () -> new NetheriteFishingRodItem(new Item.Settings().maxDamage(NetheriteExtensionConfig.INSTANCE.durability.fishing_rod).fireproof().arch$tab(NetheriteItemGroups.MAIN)));
    public static final RegistrySupplier<Item> NETHERITE_BOW = register("netherite_bow", () -> new NetheriteBowItem(new Item.Settings().maxDamage(NetheriteExtensionConfig.INSTANCE.durability.bow).fireproof().arch$tab(NetheriteItemGroups.MAIN)));
    public static final RegistrySupplier<Item> NETHERITE_CROSSBOW = register("netherite_crossbow", () -> new CrossbowItem(new Item.Settings().maxDamage(NetheriteExtensionConfig.INSTANCE.durability.crossbow).fireproof().arch$tab(NetheriteItemGroups.MAIN)));
    public static final RegistrySupplier<Item> NETHERITE_TRIDENT = register("netherite_trident", () -> new NetheriteTridentItem(new Item.Settings().maxDamage(NetheriteExtensionConfig.INSTANCE.durability.trident).fireproof().arch$tab(NetheriteItemGroups.MAIN)));
    public static final RegistrySupplier<Item> NETHERITE_HORSE_ARMOR = register("netherite_horse_armor", () -> new AnimalArmorItem(ArmorMaterials.NETHERITE, AnimalArmorItem.Type.EQUESTRIAN, false, new Item.Settings().maxCount(1).fireproof().arch$tab(NetheriteItemGroups.MAIN)));
    public static final RegistrySupplier<Item> NETHERITE_SHEARS = register("netherite_shears", () -> new NetheriteShearsItem(new Item.Settings().fireproof().maxDamage(NetheriteExtensionConfig.INSTANCE.durability.shears).arch$tab(NetheriteItemGroups.MAIN)));
    public static final RegistrySupplier<Item> NETHERITE_SHIELD = register("netherite_shield", () -> NetheriteShieldItem.create(new Item.Settings().fireproof().maxDamage(NetheriteExtensionConfig.INSTANCE.durability.shield).arch$tab(NetheriteItemGroups.MAIN)));
    public static final RegistrySupplier<Item> NETHERITE_APPLE = register("netherite_apple", () -> new Item(new Item.Settings().fireproof().food(NetheriteFoodComponents.NETHERITE_APPLE).arch$tab(NetheriteItemGroups.MAIN)));
    public static final RegistrySupplier<Item> NETHERITE_CARROT = register("netherite_carrot", () -> new Item(new Item.Settings().fireproof().food(NetheriteFoodComponents.NETHERITE_CARROT).arch$tab(NetheriteItemGroups.MAIN)));

    public static RegistrySupplier<Item> register(String id, Supplier<Item> item) {
        return REGISTRY.register(id, item);
    }

    public static void init() {
        DispenserBlock.registerBehavior(NETHERITE_SHEARS.get(), new ShearsDispenserBehavior());

        CreativeTabRegistry.appendStack(NetheriteItemGroups.MAIN, buildPotion(Items.POTION, NetheritePotions.NETHERITE));
        CreativeTabRegistry.appendStack(NetheriteItemGroups.MAIN, buildPotion(Items.SPLASH_POTION, NetheritePotions.NETHERITE));
        CreativeTabRegistry.appendStack(NetheriteItemGroups.MAIN, buildPotion(Items.LINGERING_POTION, NetheritePotions.NETHERITE));
    }

    public static ItemStack buildPotion(Item base, RegistryEntry<Potion> potion) {
        ItemStack stack = base.getDefaultStack();
        stack.set(DataComponentTypes.POTION_CONTENTS, new PotionContentsComponent(potion));
        return stack;
    }
}
