package com.iafenvoy.netherite.registry;

import com.iafenvoy.netherite.NetheriteExtension;
import net.minecraft.loot.LootTable;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.util.Identifier;

public final class NetheriteLoots {
    public static final RegistryKey<LootTable> LAVA_FISHING_LOOT_TABLE = RegistryKey.of(RegistryKeys.LOOT_TABLE, Identifier.of(NetheriteExtension.MOD_ID, "gameplay/fishing"));
}
