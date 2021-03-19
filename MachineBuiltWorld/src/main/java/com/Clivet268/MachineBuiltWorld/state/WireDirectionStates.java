package com.Clivet268.MachineBuiltWorld.state;

import net.minecraft.util.IStringSerializable;

public enum WireDirectionStates implements IStringSerializable {
    NS("ns"),
    NW("nw"),
    NE("ne"),
    WE("we"),
    WN("wn"),
    WS("ws"),
    SE("se"),
    SW("sw"),
    SN("sn"),
    EN("en"),
    EW("ew"),
    ES("es"),
    NONE("none");
    private final String name;

    private WireDirectionStates(String name) {
        this.name = name;
    }

    public String toString() {
        return this.getName();
    }

    public String getName() {
        return this.name;
    }
}