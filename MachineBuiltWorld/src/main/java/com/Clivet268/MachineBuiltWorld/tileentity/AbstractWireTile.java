package com.Clivet268.MachineBuiltWorld.tileentity;

import net.minecraft.tileentity.TileEntityType;

//TODO make anything that uses energy holdable tile more efficient and better fit the actual values in the class
public abstract class AbstractWireTile extends EnergyHoldableTile{

    public AbstractWireTile(TileEntityType<?> tileTypeIn, int i, int ie, int tra) {
        super(tileTypeIn, i, ie, tra);
    }

}