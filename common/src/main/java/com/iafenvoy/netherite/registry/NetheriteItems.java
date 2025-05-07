package com.iafenvoy.netherite.registry;

import com.iafenvoy.netherite.NetheriteExtension;
import com.iafenvoy.netherite.config.NetheriteExtensionConfig;
import com.iafenvoy.netherite.item.NetheriteBowItem;
import com.iafenvoy.netherite.item.NetheriteFishingRodItem;
import com.iafenvoy.netherite.item.NetheriteHorseArmorItem;
import com.iafenvoy.netherite.item.NetheriteTridentItem;
import com.iafenvoy.netherite.item.block.NetheriteShulkerBoxBlock;
import com.iafenvoy.netherite.item.impl.NetheriteElytraItem;
import com.iafenvoy.netherite.item.impl.NetheriteShieldItem;
import dev.architectury.registry.registries.DeferredRegister;
import dev.architectury.registry.registries.RegistrySupplier;
import net.minecraft.block.Block;
import net.minecraft.block.DispenserBlock;
import net.minecraft.block.LeveledCauldronBlock;
import net.minecraft.block.cauldron.CauldronBehavior;
import net.minecraft.block.dispenser.ShearsDispenserBehavior;
import net.minecraft.item.*;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.stat.Stats;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Rarity;

import java.util.function.Supplier;

@SuppressWarnings("unused")
public final class NetheriteItems {
    public static final DeferredRegister<Item> REGISTRY = DeferredRegister.create(NetheriteExtension.MOD_ID, RegistryKeys.ITEM);

    public static final CauldronBehavior CLEAN_NETHERITE_BOX = (state, world, pos, player, hand, stack) -> {
        Block block = Block.getBlockFromItem(stack.getItem());
        if (!(block instanceof NetheriteShulkerBoxBlock)) return ActionResult.PASS;
        else if (!world.isClient) {
            ItemStack itemStack = new ItemStack(NetheriteBlocks.NETHERITE_SHULKER_BOX.get());
            if (stack.hasNbt()) itemStack.setNbt(stack.getOrCreateNbt().copy());
            player.setStackInHand(hand, itemStack);
            player.incrementStat(Stats.CLEAN_SHULKER_BOX);
            LeveledCauldronBlock.decrementFluidLevel(state, world, pos);
        }
        return ActionResult.success(world.isClient);
    };
    public static final RegistrySupplier<Item> NETHERITE_ELYTRA = register("netherite_elytra", () -> NetheriteElytraItem.create(new Item.Settings().maxDamage(NetheriteExtensionConfig.INSTANCE.durability.elytra).rarity(Rarity.UNCOMMON).fireproof().arch$tab(NetheriteItemGroups.MAIN)));
    public static final RegistrySupplier<Item> NETHERITE_FISHING_ROD = register("netherite_fishing_rod", () -> new NetheriteFishingRodItem(new Item.Settings().maxDamage(NetheriteExtensionConfig.INSTANCE.durability.fishing_rod).fireproof().arch$tab(NetheriteItemGroups.MAIN)));
    public static final RegistrySupplier<Item> NETHERITE_BOW = register("netherite_bow", () -> new NetheriteBowItem(new Item.Settings().maxDamage(NetheriteExtensionConfig.INSTANCE.durability.bow).fireproof().arch$tab(NetheriteItemGroups.MAIN)));
    public static final RegistrySupplier<Item> NETHERITE_CROSSBOW = register("netherite_crossbow", () -> new CrossbowItem(new Item.Settings().maxDamage(NetheriteExtensionConfig.INSTANCE.durability.crossbow).fireproof().arch$tab(NetheriteItemGroups.MAIN)));
    public static final RegistrySupplier<Item> NETHERITE_TRIDENT = register("netherite_trident", () -> new NetheriteTridentItem(new Item.Settings().maxDamage(NetheriteExtensionConfig.INSTANCE.durability.trident).fireproof().arch$tab(NetheriteItemGroups.MAIN)));
    public static final RegistrySupplier<Item> NETHERITE_HORSE_ARMOR = register("netherite_horse_armor", () -> new NetheriteHorseArmorItem(15, new Item.Settings().maxCount(1).fireproof().arch$tab(NetheriteItemGroups.MAIN)));
    public static final RegistrySupplier<Item> NETHERITE_SHEARS = register("netherite_shears", () -> new ShearsItem(new Item.Settings().fireproof().maxDamage(NetheriteExtensionConfig.INSTANCE.durability.shears).arch$tab(NetheriteItemGroups.MAIN)));
    public static final RegistrySupplier<Item> NETHERITE_SHIELD = register("netherite_shield", () -> NetheriteShieldItem.create(new Item.Settings().fireproof().maxDamage(NetheriteExtensionConfig.INSTANCE.durability.shield).arch$tab(NetheriteItemGroups.MAIN)));

    public static RegistrySupplier<Item> register(String id, Supplier<Item> item) {
        return REGISTRY.register(id, () -> {
            Item i = item.get();
            if (i instanceof BlockItem blockItem) {
                blockItem.appendBlocks(Item.BLOCK_ITEMS, i);
                if (blockItem.getBlock() instanceof NetheriteShulkerBoxBlock)
                    CauldronBehavior.WATER_CAULDRON_BEHAVIOR.put(i, CLEAN_NETHERITE_BOX);
            }
            return i;
        });
    }

    public static void init() {
        DispenserBlock.registerBehavior(NETHERITE_SHEARS.get(), new ShearsDispenserBehavior());
    }
}
