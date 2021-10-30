package com.Clivet268.MachineBuiltWorld.util;

import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;

public class MachineBuiltWorldItemGroup extends ItemGroup {

    public static final MachineBuiltWorldItemGroup instance = new MachineBuiltWorldItemGroup(ItemGroup.GROUPS.length, "machinebuiltworld");

    public MachineBuiltWorldItemGroup(int index, String label) {
        super(index, label);
    }

    @Override
    public ItemStack createIcon() {
        return new ItemStack(RegistryHandler.INTENSIVE_HEATING_OVEN_ITEM.get());
    }
}
