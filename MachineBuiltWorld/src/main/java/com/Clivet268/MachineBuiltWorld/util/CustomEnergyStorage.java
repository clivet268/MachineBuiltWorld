package com.Clivet268.MachineBuiltWorld.util;
import net.minecraft.nbt.CompoundNBT;
import net.minecraftforge.common.util.INBTSerializable;
import net.minecraftforge.energy.EnergyStorage;

public class CustomEnergyStorage extends EnergyStorage implements INBTSerializable<CompoundNBT> {

    public int capacity;
    public int maxTransfer;
    public int macsRecive;

    public CustomEnergyStorage(int carpacity,int maxRecive, int maxTranrsfer) {
        super(carpacity, maxRecive, maxTranrsfer);
        this.capacity=carpacity;
        this.maxTransfer = maxTranrsfer;
        this.macsRecive = maxRecive;
    }

    protected void onEnergyChanged() {

    }

    public void setEnergy(int eenergy) {
        this.energy = eenergy;
        onEnergyChanged();
    }

    public void addEnergy(int energy) {
        this.energy += energy;
        System.out.println(getMaxEnergyStored());
        if (this.energy > getMaxEnergyStored()) {
            this.energy = getEnergyStored();
        }
        onEnergyChanged();
    }

    public void consumeEnergy(int energy) {
        this.energy -= energy;
        if (this.energy < 0) {
            this.energy = 0;
        }
        onEnergyChanged();
    }

    @Override
    public CompoundNBT serializeNBT() {
        CompoundNBT tag = new CompoundNBT();
        tag.putInt("energy", getEnergyStored());
        return tag;
    }

    @Override
    public void deserializeNBT(CompoundNBT nbt) {
        setEnergy(nbt.getInt("energy"));
    }
}
