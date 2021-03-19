package com.Clivet268.MachineBuiltWorld.tileentity;

import static com.Clivet268.MachineBuiltWorld.Config.TTTBATTERY_MAXPOWER;
import static com.Clivet268.MachineBuiltWorld.util.RegistryHandler.TTTBATTERY_TILE;

public class TTTBatteryTile extends EnergyHoldableTile {

       public TTTBatteryTile()
       {
           super(TTTBATTERY_TILE.get() , TTTBATTERY_MAXPOWER.get(), 0 ,0);
       }

}
