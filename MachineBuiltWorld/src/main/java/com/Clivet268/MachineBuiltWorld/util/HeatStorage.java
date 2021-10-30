package com.Clivet268.MachineBuiltWorld.util;


public class HeatStorage implements IHeatStorage {
    public int heat;
    public int capacity;
    public int maxReceive;
    public int maxTransfer;
    public int macsGen;
    public int macpasdis;

        public HeatStorage(int capacity, int maxTransferr,int maxReceivee , int maxGen, int maxPasDis)
        {
            this.capacity = capacity;
            this.maxReceive = maxReceivee;
            this.maxTransfer = maxTransferr;
            this.macpasdis = maxPasDis;
            this.macsGen = maxGen;
            this.heat = 0;
        }

        @Override
        public int receiveHeat(int maxReceive, boolean simulate)
        {
            if (!canReceive())
                return 0;

            int heatReceived = Math.min(capacity - heat, Math.min(this.maxReceive, maxReceive));
            if (!simulate)
                heat += heatReceived;
            return heatReceived;
        }

        @Override
        public int extractHeat(int maxExtract, boolean simulate)
        {
            if (!canExtract())
                return 0;

            int heatExtracted = Math.min(heat, Math.min(this.maxTransfer, maxExtract));
            if (!simulate)
                heat -= heatExtracted;
            return heatExtracted;
        }

        @Override
        public int getHeatStored()
        {
            return heat;
        }

        @Override
        public int getMaxHeatStored()
        {
            return capacity;
        }



    @Override
        public boolean canExtract()
        {
            return this.maxTransfer > 0;
        }

        @Override
        public boolean canReceive()
        {
            return this.maxReceive > 0;
        }
    }

