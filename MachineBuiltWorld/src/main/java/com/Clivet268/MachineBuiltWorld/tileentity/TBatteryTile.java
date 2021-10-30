package com.Clivet268.MachineBuiltWorld.tileentity;

import static com.Clivet268.MachineBuiltWorld.util.RegistryHandler.TBATTERY_TILE;

public class TBatteryTile extends EnergyHoldableTile {

    public TBatteryTile()
    {
        super(TBATTERY_TILE.get() , 10000, 100, 10);
    }

}