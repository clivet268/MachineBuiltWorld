package com.Clivet268.MachineBuiltWorld.util;

import net.minecraft.nbt.INBT;
import net.minecraft.nbt.IntNBT;
import net.minecraft.util.Direction;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.CapabilityInject;
import net.minecraftforge.common.capabilities.CapabilityManager;

public class CapabilityHeat {

        @CapabilityInject(IHeatStorage.class)
        public static Capability<IHeatStorage> HEAT = null;

        public static void register()
        {
            CapabilityManager.INSTANCE.register(IHeatStorage.class, new Capability.IStorage<IHeatStorage>()
                    {
                        @Override
                        public INBT writeNBT(Capability<IHeatStorage> capability, IHeatStorage instance, Direction side)
                        {
                            return IntNBT.valueOf(instance.getHeatStored());
                        }

                        @Override
                        public void readNBT(Capability<IHeatStorage> capability, IHeatStorage instance, Direction side, INBT nbt)
                        {
                            if (!(instance instanceof HeatStorage))
                                throw new IllegalArgumentException("Can not deserialize to an instance that isn't the default implementation");
                            ((HeatStorage)instance).heat = ((IntNBT)nbt).getInt();
                        }
                    },
                    () -> new HeatStorage(1000));
        }
    }

