package com.iafenvoy.netherite.registry;

import com.iafenvoy.netherite.NetheriteExtension;
import com.iafenvoy.netherite.item.block.NetheriteShulkerBoxBlock;
import com.iafenvoy.netherite.item.block.entity.NetheriteShulkerBoxBlockEntity;
import dev.architectury.registry.registries.DeferredRegister;
import dev.architectury.registry.registries.RegistrySupplier;
import net.minecraft.block.Block;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.registry.RegistryKeys;

public final class NetheriteBlockEntities {
    public static final DeferredRegister<BlockEntityType<?>> REGISTRY = DeferredRegister.create(NetheriteExtension.MOD_ID, RegistryKeys.BLOCK_ENTITY_TYPE);

    public static final RegistrySupplier<BlockEntityType<NetheriteShulkerBoxBlockEntity>> NETHERITE_SHULKER_BOX_ENTITY = REGISTRY.register("netherite_shulker_box", () -> BlockEntityType.Builder.create(NetheriteShulkerBoxBlockEntity::new, NetheriteShulkerBoxBlock.streamAll().toArray(Block[]::new)).build(null));
}
