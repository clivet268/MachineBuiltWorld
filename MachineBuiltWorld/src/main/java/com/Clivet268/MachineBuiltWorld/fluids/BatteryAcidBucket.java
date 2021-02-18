package com.Clivet268.MachineBuiltWorld.fluids;

import net.minecraft.fluid.Fluid;
import net.minecraft.item.BucketItem;

import java.util.function.Supplier;

public class BatteryAcidBucket extends BucketItem {

    public BatteryAcidBucket(Supplier<? extends Fluid> supplier, Properties builder) {
        super(supplier, builder);
    }
}
