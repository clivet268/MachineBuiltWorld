package com.Clivet268.MachineBuiltWorld.tileentity;

import net.minecraft.tileentity.ITickableTileEntity;

import static com.Clivet268.MachineBuiltWorld.util.RegistryHandler.ATOMIZER_TILE;

//import static com.Clivet268.MachineBuiltWorld.tileentity.TileEntityHandler.ATOMIZERTILE;

public class AtomizerTile extends AbstractAtomizerTile implements ITickableTileEntity {

    public AtomizerTile() {
        super(ATOMIZER_TILE.get());//, IMoreRecipeType.MoreCrushes.CRUSHING);


    }


}