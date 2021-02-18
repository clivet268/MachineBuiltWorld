package com.Clivet268.MachineBuiltWorld.util;

import net.minecraft.nbt.INBT;
import net.minecraft.nbt.IntNBT;
import net.minecraft.util.Direction;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.CapabilityInject;
import net.minecraftforge.common.capabilities.CapabilityManager;

public class CapabilityLaserLight {

        @CapabilityInject(ILaserLightStorage.class)
        public static Capability<ILaserLightStorage> HEAT = null;

        public static void register()
        {
            CapabilityManager.INSTANCE.register(ILaserLightStorage.class, new Capability.IStorage<ILaserLightStorage>()
                    {
                        @Override
                        public INBT writeNBT(Capability<ILaserLightStorage> capability, ILaserLightStorage instance, Direction side)
                        {
                            return IntNBT.valueOf(instance.getLaserLightStored());
                        }

                        @Override
                        public void readNBT(Capability<ILaserLightStorage> capability, ILaserLightStorage instance, Direction side, INBT nbt)
                        {
                            if (!(instance instanceof HeatStorage))
                                throw new IllegalArgumentException("Can not deserialize to an instance that isn't the default implementation");
                            ((HeatStorage)instance).heat = ((IntNBT)nbt).getInt();
                        }
                    },
                    () -> new LaserLightStorage(1000));
        }
    }

