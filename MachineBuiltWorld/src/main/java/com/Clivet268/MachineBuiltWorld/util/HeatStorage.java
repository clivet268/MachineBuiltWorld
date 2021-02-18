package com.Clivet268.MachineBuiltWorld.util;


public class HeatStorage implements IHeatStorage {
        protected int heat;
        protected int redCapacity;
        protected int capacity;
        protected int maxReceive;
        protected int maxExtract;

        public HeatStorage(int capacity)
        {
            this(capacity, capacity, capacity, 0);
        }

        public HeatStorage(int capacity, int maxTransfer)
        {
            this(capacity, maxTransfer, maxTransfer, 0);
        }

        public HeatStorage(int capacity, int maxReceive, int maxExtract)
        {
            this(capacity, maxReceive, maxExtract, 0);
        }

        public HeatStorage(int capacity, int maxReceive, int maxExtract, int heat)
        {
            this.capacity = capacity;
            this.maxReceive = maxReceive;
            this.maxExtract = maxExtract;
            this.heat = Math.max(0 , Math.min(capacity, heat));
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

            int heatExtracted = Math.min(heat, Math.min(this.maxExtract, maxExtract));
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
    public int getRedHeatStored() {
        return redCapacity;
    }

    @Override
        public boolean canExtract()
        {
            return this.maxExtract > 0;
        }

        @Override
        public boolean canReceive()
        {
            return this.maxReceive > 0;
        }
    }

