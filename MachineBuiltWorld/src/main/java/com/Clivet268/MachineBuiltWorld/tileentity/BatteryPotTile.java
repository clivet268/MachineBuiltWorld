package com.Clivet268.MachineBuiltWorld.tileentity;

import net.minecraft.item.ItemStack;
import net.minecraft.util.Direction;

import javax.annotation.Nullable;

import static com.Clivet268.MachineBuiltWorld.Config.BATTERY_POT_MAXPOWER;
import static com.Clivet268.MachineBuiltWorld.util.RegistryHandler.BATTERY_POT_TILE;

public class BatteryPotTile extends EnergyHoldableTile {

        public BatteryPotTile()
        {
            super(BATTERY_POT_TILE.get() , BATTERY_POT_MAXPOWER.get(), 10);
        }



}
