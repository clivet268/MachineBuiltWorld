package com.Clivet268.MachineBuiltWorld.items;

import net.minecraft.item.Item;

public class CrusherTeethBase extends Item {
    private int multi;
    public CrusherTeethBase(Properties builder, int multiplier) {
        super(builder);
        this.multi=multiplier;
    }

    public int getMultiplier()
    {
        return this.multi;
    }
}
