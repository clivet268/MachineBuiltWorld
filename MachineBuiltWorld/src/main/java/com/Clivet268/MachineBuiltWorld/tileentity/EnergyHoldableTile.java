package com.Clivet268.MachineBuiltWorld.tileentity;

import com.Clivet268.MachineBuiltWorld.util.CustomEnergyStorage;
import com.Clivet268.MachineBuiltWorld.util.SidedEnergyWrapper;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.ISidedInventory;
import net.minecraft.inventory.ItemStackHelper;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.tileentity.ITickableTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityType;
import net.minecraft.util.Direction;
import net.minecraft.util.NonNullList;
import net.minecraftforge.common.ForgeHooks;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.energy.CapabilityEnergy;
import net.minecraftforge.energy.IEnergyStorage;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.ItemStackHandler;
import net.minecraftforge.items.wrapper.SidedInvWrapper;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

public abstract class EnergyHoldableTile extends TileEntity {
    private CustomEnergyStorage energyStorage;
    // Never create lazy optionals in getCapability. Always place them as fields in the tile entity:

    private LazyOptional<IEnergyStorage> energy = LazyOptional.of(() -> energyStorage);
    public EnergyHoldableTile(TileEntityType<?> tileTypeIn, int i, int ie)
    {
        super(tileTypeIn);
        energyStorage = createEnergy(i, ie);

    }


    private CustomEnergyStorage createEnergy(int i , int e) {
        return new CustomEnergyStorage(i, e) {
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

    LazyOptional<? extends IEnergyStorage>[] handlerss =
            SidedEnergyWrapper.create(this.energyStorage, Direction.NORTH, Direction.EAST, Direction.WEST,
                    Direction.SOUTH, Direction.UP, Direction.DOWN);


    @Override
    public <T> LazyOptional<T> getCapability(Capability<T> capability, @Nullable Direction facing) {
        if(!this.removed && facing != null && capability == CapabilityEnergy.ENERGY)
        {
            if(facing == Direction.NORTH){
                return handlerss[0].cast();
            }
            else if(facing == Direction.EAST){
                return handlerss[1].cast();
            }
            else if(facing == Direction.WEST){
                return handlerss[2].cast();
            }
            else if(facing == Direction.SOUTH){
                return handlerss[3].cast();
            }
            else if(facing == Direction.UP){
                return handlerss[4].cast();
            }
            else if(facing == Direction.DOWN){
                return handlerss[5].cast();
            }
        }
        return super.getCapability(capability, facing);
    }
}
