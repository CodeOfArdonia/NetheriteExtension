package com.iafenvoy.netherite.item.block;

import com.iafenvoy.netherite.screen.handler.NetheriteAnvilScreenHandler;
import net.minecraft.block.AbstractBlock;
import net.minecraft.block.AnvilBlock;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.screen.NamedScreenHandlerFactory;
import net.minecraft.screen.ScreenHandlerContext;
import net.minecraft.screen.SimpleNamedScreenHandlerFactory;
import net.minecraft.text.Text;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class NetheriteAnvilBlock extends AnvilBlock {
    private static final Text TITLE = Text.translatable("container.repair");

    public NetheriteAnvilBlock() {
        super(AbstractBlock.Settings.copy(Blocks.ANVIL));
    }

    @Override
    public NamedScreenHandlerFactory createScreenHandlerFactory(BlockState state, World world, BlockPos pos) {
        return new SimpleNamedScreenHandlerFactory((i, playerInventory, playerEntity) -> new NetheriteAnvilScreenHandler(i, playerInventory, ScreenHandlerContext.create(world, pos)), TITLE);
    }
}
