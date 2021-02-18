package com.Clivet268.MachineBuiltWorld.tileentity;

import com.Clivet268.MachineBuiltWorld.blocks.Atomizer;
import net.minecraftforge.registries.ObjectHolder;

import static com.Clivet268.MachineBuiltWorld.MachineBuiltWorld.MOD_ID;

@ObjectHolder(MOD_ID)
public class TileEntityHandler {
    @ObjectHolder("machinebuiltworld:atomizer")
    public static Atomizer ATOMIZER;
   // @ObjectHolder("machinebuiltworld:atomizer")
    //public static TileEntityType<AtomizerTile> ATOMIZERTILE;
}
