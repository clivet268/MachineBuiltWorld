package com.Clivet268.MachineBuiltWorld.Loot;

import com.Clivet268.MachineBuiltWorld.util.RegistryHandler;
import net.minecraft.data.loot.BlockLootTables;

public class MoreBlockLootTables extends BlockLootTables {
   public void addTables() {
   this.registerDropSelfLootTable(RegistryHandler.CRUSHER.get());
   this.registerDropSelfLootTable(RegistryHandler.FIBERGLASS_MOULD.get());
   }
}
