package com.Clivet268.MachineBuiltWorld.tileentity;

import static com.Clivet268.MachineBuiltWorld.Config.TTBATTERY_MAXPOWER;
import static com.Clivet268.MachineBuiltWorld.util.RegistryHandler.TTBATTERY_TILE;

public class TTBatteryTile extends EnergyHoldableTile {

    public TTBatteryTile()
    {
        super(TTBATTERY_TILE.get() , TTBATTERY_MAXPOWER.get(), 1000);
    }

}