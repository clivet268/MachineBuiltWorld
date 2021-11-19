package com.Clivet268.MachineBuiltWorld.tileentity;

import com.Clivet268.MachineBuiltWorld.util.RegistryHandler;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityType;
import net.minecraft.util.Direction;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

import static com.Clivet268.MachineBuiltWorld.util.RegistryHandler.ATOMIZER_TILE;

public class ResonatingFrameTileEntity extends TileEntity {
    public ResonatingFrameTileEntity() {
        super(RegistryHandler.RESONATING_FRAME_TILE.get());
    }
}



