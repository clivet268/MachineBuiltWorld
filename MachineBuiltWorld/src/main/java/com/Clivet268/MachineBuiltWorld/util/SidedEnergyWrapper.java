package com.Clivet268.MachineBuiltWorld.util;

public class SidedEnergyWrapper {}/*extends CustomEnergyStorage {

    protected final CustomEnergyStorage inv;
    @Nullable
    protected final Direction side;

    @SuppressWarnings("unchecked")
    public static LazyOptional<CustomEnergyStorage>[] create(CustomEnergyStorage inv, Direction... sides) {
        LazyOptional<CustomEnergyStorage>[] ret = new LazyOptional[sides.length];
        for (int x = 0; x < sides.length; x++) {
            final Direction side = sides[x];
            ret[x] = LazyOptional.of(() -> new SidedEnergyWrapper(inv, side));
        }
        return ret;
    }

    public SidedEnergyWrapper(@Nonnull CustomEnergyStorage inv, @Nullable Direction side)
    {
        super(inv.capacity, inv.maxTransfer, inv.macsRecive);
        this.inv = inv;
        this.side = side;
    }
    @Override
    public int receiveEnergy(int maxReceive, boolean simulate) {
        return super.receiveEnergy(maxReceive,simulate);
    }

    @Override
    public int extractEnergy(int maxExtract, boolean simulate) {
        return super.extractEnergy(maxExtract, simulate);
    }

    @Override
    public int getEnergyStored() {
        return super.getEnergyStored();
    }

    @Override
    public int getMaxEnergyStored() {
        return super.getMaxEnergyStored();
    }

    @Override
    public boolean canExtract() {
        return true;
    }

    @Override
    public boolean canReceive() {
        return super.canReceive();
    }
}

*/
