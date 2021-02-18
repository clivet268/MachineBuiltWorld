package com.Clivet268.MachineBuiltWorld.util;

import net.minecraft.nbt.CompoundNBT;
import net.minecraftforge.common.util.INBTSerializable;

public class CustomHeatStorage extends HeatStorage implements INBTSerializable<CompoundNBT> {

        public CustomHeatStorage(int capacity, int maxTransfer) {
            super(capacity, maxTransfer);
        }
    private boolean redLining = false;
        public boolean redLining(){return redLining;}

    private boolean critical = false;
    public boolean critical(){return critical;}

        protected void onHeatChanged() {
            if( this.heat >= getRedHeatStored())
            {
                redLining = true;
            }
            if( this.heat >= getMaxHeatStored())
            {
                critical = true;
            }
        }

        public void setHeat(int heat) {
            this.heat = heat;
            onHeatChanged();
        }

        public void addHeat(int heat) {
            this.heat += heat;
            if (this.heat > getMaxHeatStored()) {
                this.heat = getHeatStored();
            }
            onHeatChanged();
        }

        public void consumeHeat(int heat) {
            this.heat -= heat;
            if (this.heat < 0) {
                this.heat = 0;
            }
            onHeatChanged();
        }

        @Override
        public CompoundNBT serializeNBT() {
            CompoundNBT tag = new CompoundNBT();
            tag.putInt("heat", getHeatStored());
            return tag;
        }

        @Override
        public void deserializeNBT(CompoundNBT nbt) {
            setHeat(nbt.getInt("heat"));
        }
    }


