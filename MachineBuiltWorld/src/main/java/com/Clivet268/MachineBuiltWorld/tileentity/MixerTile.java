package com.Clivet268.MachineBuiltWorld.tileentity;

import com.Clivet268.MachineBuiltWorld.util.CapabilityHeat;
import com.Clivet268.MachineBuiltWorld.util.CustomEnergyStorage;
import com.Clivet268.MachineBuiltWorld.util.CustomHeatStorage;
import com.Clivet268.MachineBuiltWorld.util.IHeatStorage;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.tileentity.ITickableTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.Direction;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.energy.CapabilityEnergy;
import net.minecraftforge.energy.IEnergyStorage;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.ItemStackHandler;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.concurrent.atomic.AtomicInteger;

import static com.Clivet268.MachineBuiltWorld.Config.MIXER_MAXPOWER;
import static com.Clivet268.MachineBuiltWorld.Config.MIXER_RECEIVE;
import static com.Clivet268.MachineBuiltWorld.util.RegistryHandler.MIXER_TILE;
import static net.minecraft.particles.ParticleTypes.FLAME;
import static net.minecraft.world.Explosion.Mode.BREAK;

//import static com.Clivet268.MachineBuiltWorld.util.RegistryHandler.BATTERY_TILE;

public class MixerTile extends TileEntity implements ITickableTileEntity {

        private ItemStackHandler itemHandler = createHandler();
        private CustomEnergyStorage energyStorage = createEnergy();
        private CustomHeatStorage heatStorage = createHeat();

        // Never create lazy optionals in getCapability. Always place them as fields in the tile entity:
        private LazyOptional<IItemHandler> handler = LazyOptional.of(() -> itemHandler);
        private LazyOptional<IEnergyStorage> energy = LazyOptional.of(() -> energyStorage);
    private LazyOptional<IHeatStorage> heat = LazyOptional.of(() -> heatStorage);

        private int counter;

        public MixerTile() {
            super(MIXER_TILE.get());


        }

        @Override
        public void remove() {
            super.remove();
            handler.invalidate();
            energy.invalidate();
            heat.invalidate();
        }

        @Override
        public void tick() {
            if (world.isRemote) {
                return;
            }
            AtomicInteger capacity = new AtomicInteger(heatStorage.getHeatStored());

                                capacity.addAndGet(1);

            takeInPower();
            generateHeatOnUse();
            doBadHeatStuff();
        }
    public void doBadHeatStuff(){
            if(this.heatStorage.redLining())
            {
                this.world.addParticle(FLAME,pos.getX(),pos.getY()+1, pos.getZ(),0, 3,0);

            }
            if(this.heatStorage.critical())
            {
                this.world.createExplosion(null, pos.getX(),pos.getY(), pos.getZ(), 3,true, BREAK);
                this.heatStorage.consumeHeat(1000);
            }
        }

    private void takeInPower() {
        if (world.isDaytime()) {
            AtomicInteger capacity = new AtomicInteger(energyStorage.getEnergyStored());
            if (capacity.get() > 0) {
                for (Direction direction : Direction.values()) {
                    TileEntity te = world.getTileEntity(pos.offset(direction));
                    if (te != null) {
                        boolean doContinue = te.getCapability(CapabilityEnergy.ENERGY, direction).map(handler -> {
                                    if (handler.canReceive()) {
                                        int received = handler.receiveEnergy(Math.min(capacity.get(), MIXER_RECEIVE.get()), false);
                                        capacity.addAndGet(received);
                                        energyStorage.consumeEnergy(-received);
                                        markDirty();
                                        return capacity.get() > 0;
                                    } else {
                                        return true;
                                    }
                                }
                        ).orElse(true);
                        if (!doContinue) {
                            return;
                        }
                    }
                }
            }
        }
    }

    private void generateHeatOnUse() {
            AtomicInteger capacity = new AtomicInteger(heatStorage.getHeatStored());
            if(world.isDaytime()) {
                    capacity.addAndGet(1);
            }
            if (capacity.get() > 0) {
                for (Direction direction : Direction.values()) {
                    TileEntity te = world.getTileEntity(pos.offset(direction));
                    if (te != null) {
                        boolean doContinue = te.getCapability(CapabilityHeat.HEAT, direction).map(handler -> {
                                    if (handler.canReceive()) {
                                        int received = handler.receiveHeat(Math.min(capacity.get(), MIXER_RECEIVE.get()), false);
                                        capacity.addAndGet(received);
                                        heatStorage.consumeHeat(-received);
                                        markDirty();
                                        return capacity.get() > 0;
                                    } else {
                                        return true;
                                    }
                                }
                        ).orElse(true);
                        if (!doContinue) {
                            return;
                        }
                    }
                }
            }
        }

        @Override
        public void read(CompoundNBT tag) {
            itemHandler.deserializeNBT(tag.getCompound("inv"));
            energyStorage.deserializeNBT(tag.getCompound("energy"));
            heatStorage.deserializeNBT(tag.getCompound("heat"));

            counter = tag.getInt("counter");
            super.read(tag);
        }

        @Override
        public CompoundNBT write(CompoundNBT tag) {
            tag.put("inv", itemHandler.serializeNBT());
            tag.put("energy", energyStorage.serializeNBT());

            tag.putInt("counter", counter);
            return super.write(tag);
        }

        private ItemStackHandler createHandler() {
            return new ItemStackHandler(1) {

                @Override
                protected void onContentsChanged(int slot) {
                    // To make sure the TE persists when the chunk is saved later we need to
                    // mark it dirty every time the item handler changes
                    markDirty();
                }
                @Nonnull
                @Override
                public ItemStack insertItem(int slot, @Nonnull ItemStack stack, boolean simulate) {
                        return stack;
                }
            };
        }

        private CustomEnergyStorage createEnergy() {
            return new CustomEnergyStorage(MIXER_MAXPOWER.get(), 0) {
                @Override
                protected void onEnergyChanged() {
                    markDirty();
                }
            };
        }
         private CustomHeatStorage createHeat() {
        return new CustomHeatStorage(1000, 0) {
            @Override
            protected void onHeatChanged() {
                markDirty();
            }
              };
        }

        @Nonnull
        @Override
        public <T> LazyOptional<T> getCapability(@Nonnull Capability<T> cap, @Nullable Direction side) {
            if (cap == CapabilityItemHandler.ITEM_HANDLER_CAPABILITY) {
                return handler.cast();
            }
            if (cap == CapabilityEnergy.ENERGY) {
                return energy.cast();
            }
            if (cap == CapabilityHeat.HEAT) {
                return heat.cast();
            }
            return super.getCapability(cap, side);
        }
}
