package com.Clivet268.MachineBuiltWorld.util;

import net.minecraft.nbt.CompoundNBT;
import net.minecraftforge.common.util.INBTSerializable;
//TODO its a bit weird
public class CustomHeatStorage extends HeatStorage implements INBTSerializable<CompoundNBT> {

    public int forgiveness = 0;
    public int blasin = 0;
    public CustomHeatStorage(int capacity, int maxTransfer,int maxRecive , int maxGen, int maxPasDis, int forgivenesse,
                             int blastingn) {
        super(capacity, maxTransfer, maxRecive, maxGen,maxPasDis);
        this.forgiveness =forgivenesse;
        this.blasin =blastingn;
    }
    protected void onHeatChanged(boolean over) { }

    public void setHeat(int heat) {
        this.heat = heat;
        onHeatChanged(this.heat > getMaxHeatStored());
    }

    public boolean checkMaxHeatAndDetonate(int forgiveness, int redlinerblessrelease) {
        int e = (int) (Math.random() * 100);
        if(e <= forgiveness){
            this.heat -= redlinerblessrelease;
            return false;
        }
        else{
            return true;
        }
    }

    public void addHeat(int heat) {
        this.heat += heat;
        onHeatChanged(this.heat > getMaxHeatStored());
    }

    public void consumeHeat(int heat) {
        this.heat -= heat;
        if (this.heat < 0) {
            this.heat = 0;
        }
        onHeatChanged(this.heat > getMaxHeatStored());
    }
    public void pasiDis(int heat) {
        this.consumeHeat(heat + this.macpasdis);
        onHeatChanged(this.heat > getMaxHeatStored());
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


