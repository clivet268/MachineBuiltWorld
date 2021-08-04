package com.Clivet268.MachineBuiltWorld.tileentity;

import com.Clivet268.MachineBuiltWorld.blocks.Wire;
import net.minecraft.tileentity.ITickableTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityType;
import net.minecraft.util.Direction;

//TODO make anything that uses energy holdable tile more efficient and better fit the actual values in the class
public abstract class AbstractWireTile extends EnergyHoldableTile{

    public AbstractWireTile(TileEntityType<?> tileTypeIn, int i, int ie, int tra) {
        super(tileTypeIn, i, ie, tra);
    }
}