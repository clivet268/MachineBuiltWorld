package com.Clivet268.MachineBuiltWorld.tileentity;

import static com.Clivet268.MachineBuiltWorld.Config.TBATTERY_MAXPOWER;
import static com.Clivet268.MachineBuiltWorld.util.RegistryHandler.TBATTERY_TILE;

public class TBatteryTile extends EnergyHoldableTile {

    public TBatteryTile()
    {
        super(TBATTERY_TILE.get() , TBATTERY_MAXPOWER.get(), 0 ,0);
    }

}