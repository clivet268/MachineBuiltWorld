package com.Clivet268.MachineBuiltWorld.tileentity;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.ISidedInventory;
import net.minecraft.inventory.ItemStackHelper;
import net.minecraft.item.BucketItem;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.tileentity.ITickableTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityType;
import net.minecraft.util.Direction;
import net.minecraft.util.NonNullList;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.capability.CapabilityFluidHandler;
import net.minecraftforge.fluids.capability.IFluidHandler;
import net.minecraftforge.fluids.capability.templates.FluidTank;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.ItemStackHandler;
import net.minecraftforge.items.wrapper.SidedInvWrapper;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Add Configs sometime
 *
 */
public abstract class AbstractPressurizedGasContainerTile extends TileEntity implements ISidedInventory, ITickableTileEntity {
    private int burnTime;

    private ItemStackHandler itemHandler = createHandler();
    int maxDrain = 1000;
    // Never create lazy optionals in getCapability. Always place them as fields in the tile entity:


    protected FluidTank tank = new FluidTank(200000);

    private final LazyOptional<IFluidHandler> holder = LazyOptional.of(() -> tank);
    private LazyOptional<IItemHandler> handler = LazyOptional.of(() -> itemHandler);
    protected NonNullList<ItemStack> items = NonNullList.withSize(1, ItemStack.EMPTY);
    public AbstractPressurizedGasContainerTile(TileEntityType<?> tileTypeIn)
    {
        super(tileTypeIn);
    }
    @Override
    public void tick() {
        boolean flag1 = false;
            /*rip fish the cat on -----'s server*/
        if (!this.world.isRemote) {
            ItemStack itemstack = this.items.get(0);
            FluidStack fluid = new FluidStack(((BucketItem)itemstack.getItem()).getFluid(), 1000);
            if (!itemstack.isEmpty()) {
                if (itemstack.getItem() instanceof BucketItem)
                    if(this.tank.getFluid().containsFluid(fluid) || this.tank.isEmpty()) {
                        this.tank.fill(fluid, IFluidHandler.FluidAction.EXECUTE);
                    }
                }
            }

            sendFluid();
            this.markDirty();

        }



    @Override
    public void read(CompoundNBT tag) {
        super.read(tag);
        tank.readFromNBT(tag);
        this.items = NonNullList.withSize(this.getSizeInventory(), ItemStack.EMPTY);
        ItemStackHelper.loadAllItems(tag, this.items);
    }

    @Override
    public CompoundNBT write(CompoundNBT tag) {
        super.write(tag);
        ItemStackHelper.saveAllItems(tag, this.items);

        tank.writeToNBT(tag);
        System.out.println("wrote");
        return tag;
    }


    @Override
    public int getSizeInventory() {
        return 1;
    }

    @Override
    public boolean isEmpty() {
        return this.items.isEmpty();
    }

    @Override
    public ItemStack getStackInSlot(int index) {
        return this.items.get(index);
    }

    @Override
    public ItemStack decrStackSize(int index, int count) {
        return ItemStackHelper.getAndSplit(this.items, index, count);
    }

    @Override
    public ItemStack removeStackFromSlot(int index) {
        return ItemStackHelper.getAndRemove(this.items, index);
    }

    @Override
    public void setInventorySlotContents(int index, ItemStack stack) {
        this.items.set(index, stack);
        if (stack.getCount() > this.getInventoryStackLimit()) {
            stack.setCount(this.getInventoryStackLimit());
        }
        System.out.println(":(");
        this.markDirty();
    }

    private ItemStackHandler createHandler() {
        return new ItemStackHandler(1) {

            @Override
            protected void onContentsChanged(int slot) {
                // To make sure the TE persists when the chunk is saved later we need to
                // mark it dirty every time the items handler changes
                markDirty();
            }

            @Override
            public boolean isItemValid(int slot, @Nonnull ItemStack stack) {
                return true;
            }

            @Nonnull
            @Override
            public ItemStack insertItem(int slot, @Nonnull ItemStack stack, boolean simulate) {
                return stack;
            }
        };
    }

    @Override
    public boolean isUsableByPlayer(PlayerEntity player) {
        if (this.world == null) {
            this.world = player.world;
        }
        if (this.world.getTileEntity(this.pos) != this) {
            return false;
        } else {

            return player.getDistanceSq((double) this.pos.getX() + 0.5D, (double) this.pos.getY() + 0.5D, (double) this.pos.getZ() + 0.5D) <= 64.0D;

        }
    }

    @Override
    public void clear() {
        this.items.clear();
    }

    LazyOptional<? extends IItemHandler>[] handlers =
            SidedInvWrapper.create(this, Direction.UP, Direction.DOWN);



    @Override
    public int[] getSlotsForFace(Direction side) {
        return new int[0];
    }

    @Override
    public boolean canInsertItem(int index, ItemStack itemStackIn, @Nullable Direction direction) {
        return direction== Direction.UP;
    }

    @Override
    public boolean canExtractItem(int index, ItemStack stack, Direction direction) {
        return direction == Direction.DOWN;
    }


    private void sendFluid()
    {
        this.holder.ifPresent(fluid ->
        {
            AtomicInteger capacity = new AtomicInteger(fluid.getFluidInTank(0).getAmount());

            for(int i = 0; (i < Direction.values().length) && (capacity.get() > 0); i++)
            {
                Direction facing = Direction.values()[i];
                if(facing != Direction.UP)
                {
                    TileEntity tileEntity = world.getTileEntity(pos.offset(facing));
                    if(tileEntity != null)
                    {
                        tileEntity.getCapability(CapabilityFluidHandler.FLUID_HANDLER_CAPABILITY, facing.getOpposite()).ifPresent(handler ->
                        {
                            if(handler.isFluidValid(0,tank.getFluid()))
                            {
                                int received = handler.fill(tank.getFluid(), IFluidHandler.FluidAction.EXECUTE);
                                capacity.addAndGet(-received);
                                this.tank.drain(maxDrain, IFluidHandler.FluidAction.EXECUTE);
                            }
                        });
                    }
                }
            }
        });
    }

    @Override
    @Nonnull
    public <T> LazyOptional<T> getCapability(@Nonnull Capability<T> capability, @Nullable Direction facing)
    {
        if (!this.removed && facing != null && capability == CapabilityItemHandler.ITEM_HANDLER_CAPABILITY) {
            if (facing == Direction.UP) {
                return handlers[0].cast();
            }
            else if (facing == Direction.DOWN)
                return handlers[1].cast();
        }
        if (capability == CapabilityFluidHandler.FLUID_HANDLER_CAPABILITY)
            return holder.cast();
        return super.getCapability(capability, facing);
    }


}