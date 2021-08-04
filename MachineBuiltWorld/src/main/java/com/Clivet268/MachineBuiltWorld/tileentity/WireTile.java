package com.Clivet268.MachineBuiltWorld.tileentity;

import static com.Clivet268.MachineBuiltWorld.util.RegistryHandler.WIRE_TILE;


public class WireTile extends AbstractWireTile {

    public WireTile() {
        super(WIRE_TILE.get(), 1000, 1000, 1000);
    }
}
