package com.Clivet268.MachineBuiltWorld.util;


public class LaserLightStorage implements ILaserLightStorage {
        protected int laserLight;
        protected int redCapacity;
        protected int capacity;
        protected int maxReceive;
        protected int maxExtract;

        public LaserLightStorage(int capacity)
        {
            this(capacity, capacity, capacity, 0);
        }

        public LaserLightStorage(int capacity, int maxTransfer)
        {
            this(capacity, maxTransfer, maxTransfer, 0);
        }

        public LaserLightStorage(int capacity, int maxReceive, int maxExtract)
        {
            this(capacity, maxReceive, maxExtract, 0);
        }

        public LaserLightStorage(int capacity, int maxReceive, int maxExtract, int laserLight)
        {
            this.capacity = capacity;
            this.maxReceive = maxReceive;
            this.maxExtract = maxExtract;
            this.laserLight = Math.max(0 , Math.min(capacity, laserLight));
        }

        @Override
        public int receiveLaserLight(int maxReceive, boolean simulate)
        {
            if (!canReceive())
                return 0;

            int laserLightReceived = Math.min(capacity - laserLight, Math.min(this.maxReceive, maxReceive));
            if (!simulate)
                laserLight += laserLightReceived;
            return laserLightReceived;
        }

        @Override
        public int extractLaserLight(int maxExtract, boolean simulate)
        {
            if (!canExtract())
                return 0;

            int laserLightExtracted = Math.min(laserLight, Math.min(this.maxExtract, maxExtract));
            if (!simulate)
                laserLight -= laserLightExtracted;
            return laserLightExtracted;
        }

        @Override
        public int getLaserLightStored()
        {
            return laserLight;
        }

        @Override
        public int getMaxLaserLightStored()
        {
            return capacity;
        }

    @Override
    public int getRedLaserLightStored() {
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

