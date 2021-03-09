package com.Clivet268.MachineBuiltWorld.items;

import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;

public class FuelItemBase extends Item {
    int bt;
    public FuelItemBase(int burnTime) {
        super(new Properties().group(ItemGroup.MATERIALS));
        this.bt = burnTime;
    }
    @Override
    public int getBurnTime(ItemStack itemstack)
    {
        if (itemstack.getItem() instanceof FuelItemBase)
        {
            return this.bt;
        }
        else {
            return 0;
        }
    }
}
