package com.iafenvoy.netherite.block;

import com.iafenvoy.netherite.block.entity.NetheriteBeaconBlockEntity;
import net.minecraft.block.BeaconBlock;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.BlockEntityTicker;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.component.DataComponentTypes;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.stat.Stats;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.Properties;
import net.minecraft.util.ActionResult;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

public class NetheriteBeaconBlock extends BeaconBlock {
    public NetheriteBeaconBlock(Settings settings) {
        super(settings);
        this.setDefaultState(this.getStateManager().getDefaultState().with(Properties.POWERED, false));
    }

    @Override
    protected void appendProperties(StateManager.Builder<Block, BlockState> builder) {
        super.appendProperties(builder);
        builder.add(Properties.POWERED);
    }

    @Override
    public BlockEntity createBlockEntity(BlockPos pos, BlockState state) {
        return new NetheriteBeaconBlockEntity(pos, state);
    }

    @Nullable
    @Override
    public <T extends BlockEntity> BlockEntityTicker<T> getTicker(World world, BlockState state, BlockEntityType<T> type) {
        return (world1, pos, state1, blockEntity) -> NetheriteBeaconBlockEntity.tick(world1, pos, state1, (NetheriteBeaconBlockEntity) blockEntity);
    }

    @Override
    public void onPlaced(World world, BlockPos pos, BlockState state, LivingEntity placer, ItemStack itemStack) {
        if (itemStack.contains(DataComponentTypes.CUSTOM_NAME)) {
            BlockEntity blockEntity = world.getBlockEntity(pos);
            if (blockEntity instanceof NetheriteBeaconBlockEntity beaconBlock)
                beaconBlock.setCustomName(itemStack.getName());
        }
    }

    @Override
    protected ActionResult onUse(BlockState state, World world, BlockPos pos, PlayerEntity player, BlockHitResult hit) {
        if (world.isClient)
            return ActionResult.SUCCESS;
        else {
            BlockEntity blockEntity = world.getBlockEntity(pos);
            if (blockEntity instanceof NetheriteBeaconBlockEntity beaconBlock) {
                player.openHandledScreen(beaconBlock);
                player.incrementStat(Stats.INTERACT_WITH_BEACON);
            }
            return ActionResult.CONSUME;
        }
    }
}
