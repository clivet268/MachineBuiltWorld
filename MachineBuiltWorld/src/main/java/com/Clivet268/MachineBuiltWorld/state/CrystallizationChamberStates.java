package com.Clivet268.MachineBuiltWorld.state;

import net.minecraft.util.IStringSerializable;

public enum CrystallizationChamberStates implements IStringSerializable {
    UP ("up"),
    DOWN("down"),
    COMBINED("combined"),
    COMBINEDSTICK("combinedstick"),
    COMBINEDSTICKSTAGE1("combinedstickstage1"),
    COMBINEDTICKSTAGE2("combinedstickstage2"),
    COMBINEDTICKSTAGE3("combinedstickstage3");

    private final String name;

    private CrystallizationChamberStates(String name) {
        this.name = name;
    }

    public String toString() {
        return this.name;
    }

    public String getName() {
        return this.name;
    }
}