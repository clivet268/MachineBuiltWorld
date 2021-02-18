package com.Clivet268.MachineBuiltWorld.util;

import net.minecraft.nbt.CompoundNBT;
import net.minecraftforge.common.util.INBTSerializable;

public class CustomLaserLightStorage extends LaserLightStorage implements INBTSerializable<CompoundNBT> {

        public CustomLaserLightStorage(int capacity, int maxTransfer) {
            super(capacity, maxTransfer);
        }
    private boolean redLining = false;
        public boolean redLining(){return redLining;}

    private boolean critical = false;
    public boolean critical(){return critical;}

        protected void onLaserLightChanged() {
            if( this.laserLight >= getRedLaserLightStored())
            {
                redLining = true;
            }
            if( this.laserLight >= getMaxLaserLightStored())
            {
                critical = true;
            }
        }

        public void setLaserLight(int laserLight) {
            this.laserLight = laserLight;
            onLaserLightChanged();
        }

        public void addLaserLight(int laserLight) {
            this.laserLight += laserLight;
            if (this.laserLight > getMaxLaserLightStored()) {
                this.laserLight = getLaserLightStored();
            }
            onLaserLightChanged();
        }

        public void consumeLaserLight(int laserLight) {
            this.laserLight -= laserLight;
            if (this.laserLight < 0) {
                this.laserLight = 0;
            }
            onLaserLightChanged();
        }

        @Override
        public CompoundNBT serializeNBT() {
            CompoundNBT tag = new CompoundNBT();
            tag.putInt("laserLight", getLaserLightStored());
            return tag;
        }

        @Override
        public void deserializeNBT(CompoundNBT nbt) {
            setLaserLight(nbt.getInt("laserLight"));
        }
    }


