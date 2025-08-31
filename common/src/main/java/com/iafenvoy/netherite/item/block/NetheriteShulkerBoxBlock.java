package com.iafenvoy.netherite.item.block;

import com.iafenvoy.netherite.item.block.entity.NetheriteShulkerBoxBlockEntity;
import com.iafenvoy.netherite.item.block.entity.NetheriteShulkerBoxBlockEntity.AnimationStage;
import com.iafenvoy.netherite.registry.NetheriteBlockEntities;
import com.iafenvoy.netherite.registry.NetheriteBlocks;
import com.mojang.serialization.MapCodec;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.block.*;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.BlockEntityTicker;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.block.piston.PistonBehavior;
import net.minecraft.component.DataComponentTypes;
import net.minecraft.component.type.ContainerComponent;
import net.minecraft.entity.ItemEntity;
import net.minecraft.entity.mob.PiglinBrain;
import net.minecraft.entity.mob.ShulkerEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.Inventory;
import net.minecraft.item.Item;
import net.minecraft.item.ItemPlacementContext;
import net.minecraft.item.ItemStack;
import net.minecraft.item.tooltip.TooltipType;
import net.minecraft.loot.context.LootContextParameterSet;
import net.minecraft.loot.context.LootContextParameters;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.stat.Stats;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.EnumProperty;
import net.minecraft.text.Text;
import net.minecraft.util.*;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Box;
import net.minecraft.util.math.Direction;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.util.shape.VoxelShapes;
import net.minecraft.world.BlockView;
import net.minecraft.world.World;
import net.minecraft.world.WorldView;
import org.jetbrains.annotations.Nullable;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Stream;

public class NetheriteShulkerBoxBlock extends BlockWithEntity {
    public static final EnumProperty<Direction> FACING = FacingBlock.FACING;
    public static final Identifier CONTENTS = Identifier.of(Identifier.DEFAULT_NAMESPACE, "contents");
    private static final Map<DyeColor, Block> BY_COLOR = new HashMap<>();
    private static final AbstractBlock.ContextPredicate CONTEXT_PREDICATE = (state, world, pos) -> !(world.getBlockEntity(pos) instanceof NetheriteShulkerBoxBlockEntity shulkerBoxBlockEntity) || shulkerBoxBlockEntity.suffocates();
    @Nullable
    private final DyeColor color;

    public NetheriteShulkerBoxBlock(@Nullable DyeColor color) {
        super(Settings.create().mapColor(color == null ? DyeColor.GRAY : color).solid().strength(2.0F).resistance(1200.0F).dynamicBounds().nonOpaque().suffocates(CONTEXT_PREDICATE).blockVision(CONTEXT_PREDICATE).pistonBehavior(PistonBehavior.DESTROY).solidBlock((state, world, pos) -> true));
        this.color = color;
        this.setDefaultState(this.stateManager.getDefaultState().with(FACING, Direction.UP));
        BY_COLOR.put(this.color, this);
    }

    public static Block get(DyeColor dyeColor) {
        return BY_COLOR.getOrDefault(dyeColor, NetheriteBlocks.NETHERITE_SHULKER_BOX.get());
    }

    public static Stream<Block> streamAll() {
        return BY_COLOR.values().stream();
    }

    @Environment(EnvType.CLIENT)
    public static DyeColor getColor(Block block) {
        return block instanceof NetheriteShulkerBoxBlock ? ((NetheriteShulkerBoxBlock) block).getColor() : null;
    }

    public static DyeColor getColor(Item item) {
        return getColor(Block.getBlockFromItem(item));
    }

    public static ItemStack getItemStack(DyeColor color) {
        return new ItemStack(get(color));
    }

    private static boolean canOpen(BlockState state, World world, BlockPos pos, NetheriteShulkerBoxBlockEntity entity) {
        if (entity.getAnimationStage() != AnimationStage.CLOSED)
            return true;
        else {
            Box box = ShulkerEntity.calculateBoundingBox(1.0F, state.get(FACING), 0.0F, 0.5F).offset(pos).contract(1.0E-6);
            return world.isSpaceEmpty(box);
        }
    }

    @Override
    protected void appendProperties(StateManager.Builder<Block, BlockState> builder) {
        builder.add(FACING);
    }

    @Override
    public void appendTooltip(ItemStack stack, Item.TooltipContext context, List<Text> tooltip, TooltipType options) {
        super.appendTooltip(stack, context, tooltip, options);
        if (stack.contains(DataComponentTypes.CONTAINER_LOOT))
            tooltip.add(Text.translatable("container.shulkerBox.unknownContents"));

        int i = 0;
        int j = 0;
        for (ItemStack itemStack : stack.getOrDefault(DataComponentTypes.CONTAINER, ContainerComponent.DEFAULT).iterateNonEmpty()) {
            ++j;
            if (i <= 4) {
                ++i;
                tooltip.add(Text.translatable("container.shulkerBox.itemCount", itemStack.getName(), itemStack.getCount()));
            }
        }
        if (j - i > 0) tooltip.add(Text.translatable("container.shulkerBox.more", j - i).formatted(Formatting.ITALIC));
    }

    @Override
    public BlockEntity createBlockEntity(BlockPos pos, BlockState state) {
        return new NetheriteShulkerBoxBlockEntity(this.color, pos, state);
    }

    @Nullable
    @Override
    public <T extends BlockEntity> BlockEntityTicker<T> getTicker(World world, BlockState state, BlockEntityType<T> type) {
        return validateTicker(type, NetheriteBlockEntities.NETHERITE_SHULKER_BOX_ENTITY.get(), NetheriteShulkerBoxBlockEntity::tick);
    }

    public @Nullable DyeColor getColor() {
        return this.color;
    }

    @Override
    public int getComparatorOutput(BlockState state, World world, BlockPos pos) {
        return ScreenHandler.calculateComparatorOutput((Inventory) world.getBlockEntity(pos));
    }

    @Override
    public List<ItemStack> getDroppedStacks(BlockState state, LootContextParameterSet.Builder builder) {
        BlockEntity blockEntity = builder.getOptional(LootContextParameters.BLOCK_ENTITY);
        if (blockEntity instanceof NetheriteShulkerBoxBlockEntity shulkerBoxBlockEntity)
            builder = builder.addDynamicDrop(CONTENTS, (consumer) -> {
                for (int i = 0; i < shulkerBoxBlockEntity.size(); ++i)
                    consumer.accept(shulkerBoxBlockEntity.getStack(i));
            });
        return super.getDroppedStacks(state, builder);
    }

    @Override
    public VoxelShape getOutlineShape(BlockState state, BlockView world, BlockPos pos, ShapeContext context) {
        BlockEntity blockEntity = world.getBlockEntity(pos);
        return blockEntity instanceof NetheriteShulkerBoxBlockEntity ? VoxelShapes.cuboid(((NetheriteShulkerBoxBlockEntity) blockEntity).getBoundingBox(state)) : VoxelShapes.fullCube();
    }

    @Override
    public ItemStack getPickStack(WorldView world, BlockPos pos, BlockState state) {
        ItemStack itemStack = super.getPickStack(world, pos, state);
        world.getBlockEntity(pos, BlockEntityType.SHULKER_BOX).ifPresent((blockEntity) -> blockEntity.setStackNbt(itemStack, world.getRegistryManager()));
        return itemStack;
    }

    @Override
    public BlockState getPlacementState(ItemPlacementContext ctx) {
        return this.getDefaultState().with(FACING, ctx.getSide());
    }

    @Override
    protected MapCodec<? extends BlockWithEntity> getCodec() {
        return MapCodec.unit(this);
    }

    @Override
    public BlockRenderType getRenderType(BlockState state) {
        return BlockRenderType.ENTITYBLOCK_ANIMATED;
    }

    @Override
    public boolean hasComparatorOutput(BlockState state) {
        return true;
    }

    @Override
    public BlockState mirror(BlockState state, BlockMirror mirror) {
        return state.rotate(mirror.getRotation(state.get(FACING)));
    }

    @Override
    public BlockState onBreak(World world, BlockPos pos, BlockState state, PlayerEntity player) {
        BlockEntity blockEntity = world.getBlockEntity(pos);
        if (blockEntity instanceof NetheriteShulkerBoxBlockEntity shulkerBoxBlockEntity) {
            if (!world.isClient && player.isCreative() && !shulkerBoxBlockEntity.isEmpty()) {
                ItemStack itemStack = getItemStack(this.getColor());
                itemStack.applyComponentsFrom(blockEntity.createComponentMap());
                ItemEntity itemEntity = new ItemEntity(world, (double) pos.getX() + (double) 0.5F, (double) pos.getY() + (double) 0.5F, (double) pos.getZ() + (double) 0.5F, itemStack);
                itemEntity.setToDefaultPickupDelay();
                world.spawnEntity(itemEntity);
            } else shulkerBoxBlockEntity.generateLoot(player);
        }
        return super.onBreak(world, pos, state, player);
    }

    @Override
    public void onStateReplaced(BlockState state, World world, BlockPos pos, BlockState newState, boolean moved) {
        if (!state.isOf(newState.getBlock())) {
            BlockEntity blockEntity = world.getBlockEntity(pos);
            if (blockEntity instanceof NetheriteShulkerBoxBlockEntity)
                world.updateComparators(pos, state.getBlock());
            super.onStateReplaced(state, world, pos, newState, moved);
        }
    }

    @Override
    protected ActionResult onUse(BlockState state, World world, BlockPos pos, PlayerEntity player, BlockHitResult hit) {
        if (world.isClient)
            return ActionResult.SUCCESS;
        else if (player.isSpectator())
            return ActionResult.CONSUME;
        else {
            BlockEntity blockEntity = world.getBlockEntity(pos);
            if (blockEntity instanceof NetheriteShulkerBoxBlockEntity shulkerBoxBlockEntity) {
                if (canOpen(state, world, pos, shulkerBoxBlockEntity)) {
                    player.openHandledScreen(shulkerBoxBlockEntity);
                    player.incrementStat(Stats.OPEN_SHULKER_BOX);
                    PiglinBrain.onGuardedBlockInteracted(player, true);
                }
                return ActionResult.CONSUME;
            } else
                return ActionResult.PASS;
        }
    }

    @Override
    public BlockState rotate(BlockState state, BlockRotation rotation) {
        return state.with(FACING, rotation.rotate(state.get(FACING)));
    }
}
