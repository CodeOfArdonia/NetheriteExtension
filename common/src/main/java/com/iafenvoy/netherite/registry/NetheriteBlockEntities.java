package com.iafenvoy.netherite.registry;

import com.iafenvoy.netherite.NetheriteExtension;
import com.iafenvoy.netherite.item.block.NetheriteShulkerBoxBlock;
import com.iafenvoy.netherite.item.block.entity.NetheriteShulkerBoxBlockEntity;
import dev.architectury.registry.registries.DeferredRegister;
import dev.architectury.registry.registries.RegistrySupplier;
import net.minecraft.block.Block;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.util.registry.Registry;

import java.util.function.Supplier;

public final class NetheriteBlockEntities {
    public static final DeferredRegister<BlockEntityType<?>> REGISTRY = DeferredRegister.create(NetheriteExtension.MOD_ID, Registry.BLOCK_ENTITY_TYPE_KEY);

    public static final RegistrySupplier<BlockEntityType<NetheriteShulkerBoxBlockEntity>> NETHERITE_SHULKER_BOX_ENTITY = register("netherite_shulker_box", () -> BlockEntityType.Builder.create(NetheriteShulkerBoxBlockEntity::new, NetheriteShulkerBoxBlock.streamAll().toArray(Block[]::new)).build(null));

    public static <T extends BlockEntity> RegistrySupplier<BlockEntityType<T>> register(String id, Supplier<BlockEntityType<T>> group) {
        return REGISTRY.register(id, group);
    }
}
