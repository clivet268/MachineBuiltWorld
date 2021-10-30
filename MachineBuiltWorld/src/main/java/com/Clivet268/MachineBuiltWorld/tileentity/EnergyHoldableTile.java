package com.Clivet268.MachineBuiltWorld.tileentity;

import com.Clivet268.MachineBuiltWorld.util.CustomEnergyStorage;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityType;
import net.minecraft.util.Direction;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.energy.CapabilityEnergy;
import net.minecraftforge.energy.IEnergyStorage;

import javax.annotation.Nullable;
import java.util.concurrent.atomic.AtomicInteger;

public abstract class EnergyHoldableTile extends TileEntity {
    private CustomEnergyStorage energyStorage;
    // Never create lazy optionals in getCapability. Always place them as fields in the tile entity:

    private LazyOptional<IEnergyStorage> energy = LazyOptional.of(() -> energyStorage);
    public EnergyHoldableTile(TileEntityType<?> tileTypeIn, int i, int reciv, int tra)
    {
        super(tileTypeIn);
        this.energyStorage = createEnergy(i, reciv, tra);

    }

    public void tick()
    {
        /*rip fish the cat on adina's server*/
        sendEnergy();
        this.markDirty();

    }


    private CustomEnergyStorage createEnergy(int i , int e, int tra) {
        return new CustomEnergyStorage(i, e, tra) {
            @Override
            protected void onEnergyChanged() {
                markDirty();
            }
        };
    }

    @Override
    public void read(CompoundNBT tag) {
        super.read(tag);
        this.energyStorage.deserializeNBT(tag.getCompound("energy"));
    }

    @Override
    public CompoundNBT write(CompoundNBT tag) {
        super.write(tag);
        tag.put("energy", this.energyStorage.serializeNBT());
        return tag;
    }

    @Override
    public <T> LazyOptional<T> getCapability(Capability<T> capability, @Nullable Direction facing) {

        if(!this.removed && facing != null && capability == CapabilityEnergy.ENERGY)
        {
            return LazyOptional.of(() -> energyStorage).cast();
        }
        return super.getCapability(capability, facing);
    }

    private void sendEnergy()
    {
        this.energy.ifPresent(energy ->
        {
            AtomicInteger capacity = new AtomicInteger(energy.getEnergyStored());

            for(int i = 0; (i < Direction.values().length) && (capacity.get() > 0); i++)
            {
                Direction facing = Direction.values()[i];
                if(facing != Direction.UP)
                {
                    TileEntity tileEntity = world.getTileEntity(pos.offset(facing));
                    if(tileEntity != null)
                    {
                        tileEntity.getCapability(CapabilityEnergy.ENERGY, facing.getOpposite()).ifPresent(handler ->
                        {
                            if(handler.canReceive())
                            {
                                int received = handler.receiveEnergy(Math.min(capacity.get(), this.energyStorage.maxTransfer), false);
                                capacity.addAndGet(-received);
                                (this.energyStorage).consumeEnergy(received);
                            }
                        });
                    }
                }
            }
        });
    }
}
