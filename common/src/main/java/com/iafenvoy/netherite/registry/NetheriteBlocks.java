package com.iafenvoy.netherite.registry;

import com.iafenvoy.netherite.NetheriteExtension;
import com.iafenvoy.netherite.item.block.NetheriteAnvilBlock;
import com.iafenvoy.netherite.item.block.NetheriteShulkerBoxBlock;
import dev.architectury.registry.registries.DeferredRegister;
import dev.architectury.registry.registries.RegistrySupplier;
import net.minecraft.block.*;
import net.minecraft.block.cauldron.CauldronBehavior;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.sound.BlockSoundGroup;
import net.minecraft.stat.Stats;
import net.minecraft.util.ActionResult;
import net.minecraft.util.DyeColor;

import java.util.function.Function;
import java.util.function.Supplier;

@SuppressWarnings("unused")
public final class NetheriteBlocks {
    public static final DeferredRegister<Block> REGISTRY = DeferredRegister.create(NetheriteExtension.MOD_ID, RegistryKeys.BLOCK);

    public static final Item.Settings NETHERITE_SHULKER_BOX_ITEM_SETTINGS = new Item.Settings().maxCount(1).fireproof().arch$tab(NetheriteItemGroups.MAIN);
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

    public static final RegistrySupplier<Block> NETHERITE_SHULKER_BOX = register("netherite_shulker_box", () -> new NetheriteShulkerBoxBlock(null), block -> new BlockItem(block, NETHERITE_SHULKER_BOX_ITEM_SETTINGS));
    public static final RegistrySupplier<Block> NETHERITE_WHITE_SHULKER_BOX = register("netherite_white_shulker_box", () -> new NetheriteShulkerBoxBlock(DyeColor.WHITE), block -> new BlockItem(block, NETHERITE_SHULKER_BOX_ITEM_SETTINGS));
    public static final RegistrySupplier<Block> NETHERITE_ORANGE_SHULKER_BOX = register("netherite_orange_shulker_box", () -> new NetheriteShulkerBoxBlock(DyeColor.ORANGE), block -> new BlockItem(block, NETHERITE_SHULKER_BOX_ITEM_SETTINGS));
    public static final RegistrySupplier<Block> NETHERITE_MAGENTA_SHULKER_BOX = register("netherite_magenta_shulker_box", () -> new NetheriteShulkerBoxBlock(DyeColor.MAGENTA), block -> new BlockItem(block, NETHERITE_SHULKER_BOX_ITEM_SETTINGS));
    public static final RegistrySupplier<Block> NETHERITE_LIGHT_BLUE_SHULKER_BOX = register("netherite_light_blue_shulker_box", () -> new NetheriteShulkerBoxBlock(DyeColor.LIGHT_BLUE), block -> new BlockItem(block, NETHERITE_SHULKER_BOX_ITEM_SETTINGS));
    public static final RegistrySupplier<Block> NETHERITE_YELLOW_SHULKER_BOX = register("netherite_yellow_shulker_box", () -> new NetheriteShulkerBoxBlock(DyeColor.YELLOW), block -> new BlockItem(block, NETHERITE_SHULKER_BOX_ITEM_SETTINGS));
    public static final RegistrySupplier<Block> NETHERITE_LIME_SHULKER_BOX = register("netherite_lime_shulker_box", () -> new NetheriteShulkerBoxBlock(DyeColor.LIME), block -> new BlockItem(block, NETHERITE_SHULKER_BOX_ITEM_SETTINGS));
    public static final RegistrySupplier<Block> NETHERITE_PINK_SHULKER_BOX = register("netherite_pink_shulker_box", () -> new NetheriteShulkerBoxBlock(DyeColor.PINK), block -> new BlockItem(block, NETHERITE_SHULKER_BOX_ITEM_SETTINGS));
    public static final RegistrySupplier<Block> NETHERITE_GRAY_SHULKER_BOX = register("netherite_gray_shulker_box", () -> new NetheriteShulkerBoxBlock(DyeColor.GRAY), block -> new BlockItem(block, NETHERITE_SHULKER_BOX_ITEM_SETTINGS));
    public static final RegistrySupplier<Block> NETHERITE_LIGHT_GRAY_SHULKER_BOX = register("netherite_light_gray_shulker_box", () -> new NetheriteShulkerBoxBlock(DyeColor.LIGHT_GRAY), block -> new BlockItem(block, NETHERITE_SHULKER_BOX_ITEM_SETTINGS));
    public static final RegistrySupplier<Block> NETHERITE_CYAN_SHULKER_BOX = register("netherite_cyan_shulker_box", () -> new NetheriteShulkerBoxBlock(DyeColor.CYAN), block -> new BlockItem(block, NETHERITE_SHULKER_BOX_ITEM_SETTINGS));
    public static final RegistrySupplier<Block> NETHERITE_PURPLE_SHULKER_BOX = register("netherite_purple_shulker_box", () -> new NetheriteShulkerBoxBlock(DyeColor.PURPLE), block -> new BlockItem(block, NETHERITE_SHULKER_BOX_ITEM_SETTINGS));
    public static final RegistrySupplier<Block> NETHERITE_BLUE_SHULKER_BOX = register("netherite_blue_shulker_box", () -> new NetheriteShulkerBoxBlock(DyeColor.BLUE), block -> new BlockItem(block, NETHERITE_SHULKER_BOX_ITEM_SETTINGS));
    public static final RegistrySupplier<Block> NETHERITE_BROWN_SHULKER_BOX = register("netherite_brown_shulker_box", () -> new NetheriteShulkerBoxBlock(DyeColor.BROWN), block -> new BlockItem(block, NETHERITE_SHULKER_BOX_ITEM_SETTINGS));
    public static final RegistrySupplier<Block> NETHERITE_GREEN_SHULKER_BOX = register("netherite_green_shulker_box", () -> new NetheriteShulkerBoxBlock(DyeColor.GREEN), block -> new BlockItem(block, NETHERITE_SHULKER_BOX_ITEM_SETTINGS));
    public static final RegistrySupplier<Block> NETHERITE_RED_SHULKER_BOX = register("netherite_red_shulker_box", () -> new NetheriteShulkerBoxBlock(DyeColor.RED), block -> new BlockItem(block, NETHERITE_SHULKER_BOX_ITEM_SETTINGS));
    public static final RegistrySupplier<Block> NETHERITE_BLACK_SHULKER_BOX = register("netherite_black_shulker_box", () -> new NetheriteShulkerBoxBlock(DyeColor.BLACK), block -> new BlockItem(block, NETHERITE_SHULKER_BOX_ITEM_SETTINGS));
    public static final RegistrySupplier<Block> FAKE_NETHERITE_BLOCK = register("fake_netherite_block", () -> new Block(AbstractBlock.Settings.copy(Blocks.IRON_BLOCK).mapColor(MapColor.BLACK).sounds(BlockSoundGroup.NETHERITE)), block -> new BlockItem(block, new Item.Settings().fireproof().arch$tab(NetheriteItemGroups.MAIN)));
    public static final RegistrySupplier<Block> NETHERITE_ANVIL_BLOCK = register("netherite_anvil", NetheriteAnvilBlock::new, block -> new BlockItem(block, new Item.Settings().fireproof().arch$tab(NetheriteItemGroups.MAIN)));

    public static <T extends Block> RegistrySupplier<T> register(String id, Supplier<T> supplier, Function<T, Item> itemConstructor) {
        RegistrySupplier<T> r = REGISTRY.register(id, supplier);
        RegistrySupplier<Item> item = NetheriteItems.register(id, () -> itemConstructor.apply(r.get()));
        item.listen(i -> {
            if (r.get() instanceof NetheriteShulkerBoxBlock)
                CauldronBehavior.WATER_CAULDRON_BEHAVIOR.put(i, CLEAN_NETHERITE_BOX);
        });
        return r;
    }
}
