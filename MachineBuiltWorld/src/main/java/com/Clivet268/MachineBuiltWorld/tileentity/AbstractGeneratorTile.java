package com.Clivet268.MachineBuiltWorld.tileentity;

import com.Clivet268.MachineBuiltWorld.util.CustomEnergyStorage;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.ISidedInventory;
import net.minecraft.inventory.ItemStackHelper;
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
import java.util.concurrent.atomic.AtomicInteger;

import static com.Clivet268.MachineBuiltWorld.blocks.Generator.FACING;

/**
 * Add Configs sometime
 *
 */
public abstract class AbstractGeneratorTile extends TileEntity implements ISidedInventory, ITickableTileEntity {
    private int burnTime;
    private CustomEnergyStorage energyStorage = createEnergy();
    private ItemStackHandler itemHandler = createHandler();
    // Never create lazy optionals in getCapability. Always place them as fields in the tile entity:

    private LazyOptional<IEnergyStorage> energy = LazyOptional.of(() -> energyStorage);
    private LazyOptional<IItemHandler> handler = LazyOptional.of(() -> itemHandler);
    protected NonNullList<ItemStack> items = NonNullList.withSize(1, ItemStack.EMPTY);
    public AbstractGeneratorTile(TileEntityType<?> tileTypeIn)
    {
        super(tileTypeIn);
    }
    @Override
    public void tick()
    {
        boolean flag = this.isBurning();
        boolean flag1 = false;
        if (this.isBurning()) {
            --this.burnTime;
            /*rip fish the cat on -----'s server*/
            this.generate();
        }
        if (!this.world.isRemote) {
            ItemStack itemstack = this.items.get(0);
            if (!itemstack.isEmpty()) {
                    if (!this.isBurning()) {
                        flag1 = true;
                        if (itemstack.hasContainerItem()) {
                            this.items.set(0, itemstack.getContainerItem());
                            this.burnTime = this.getBurnTime(itemstack);
                        }
                        else if (!itemstack.isEmpty()) {
                            this.burnTime = this.getBurnTime(itemstack);
                            itemstack.shrink(1);
                            if (itemstack.isEmpty()) {
                                this.items.set(0, itemstack.getContainerItem());
                            }
                    }
                }
            }
        }
        if (flag != this.isBurning()) {
            flag1 = true;
        }
            sendEnergy();
            this.markDirty();

    }


    public int getEnergy() {
        return energyStorage.getEnergyStored();
    }
    private boolean isBurning() {
        return this.burnTime > 0;
    }

    public void generate(){
        this.energyStorage.addEnergy(10);
    }

    protected int getBurnTime(ItemStack fuel) {
        if (fuel.isEmpty()) {
            return 0;
        } else {
            return ForgeHooks.getBurnTime(fuel);
        }
    }

    private CustomEnergyStorage createEnergy() {
        return new CustomEnergyStorage(10000, 0, 100) {
            @Override
            protected void onEnergyChanged() {
                markDirty();
            }
        };
    }

    @Override
    public void read(CompoundNBT tag) {
        super.read(tag);
        this.items = NonNullList.withSize(this.getSizeInventory(), ItemStack.EMPTY);
        ItemStackHelper.loadAllItems(tag, this.items);
        this.burnTime = tag.getInt("BurnTime");
        this.energyStorage.deserializeNBT(tag.getCompound("Energy"));
    }

    @Override
    public CompoundNBT write(CompoundNBT tag) {
        super.write(tag);
        ItemStackHelper.saveAllItems(tag, this.items);
        tag.putInt("BurnTime", this.burnTime);
        tag.put("Energy", this.energyStorage.serializeNBT());

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
    public <T> LazyOptional<T> getCapability(Capability<T> capability, @Nullable Direction facing) {
        if (!this.removed && facing != null && capability == CapabilityItemHandler.ITEM_HANDLER_CAPABILITY) {
            if (facing == Direction.UP) {
                return handlers[0].cast();
            }
            else if (facing == Direction.DOWN)
                return handlers[1].cast();
        }
        if(!this.removed && facing != null && capability == CapabilityEnergy.ENERGY)
        {
            return LazyOptional.of(() -> energyStorage).cast();
        }
        return super.getCapability(capability, facing);
    }

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


    private void sendEnergy()
    {
        this.energy.ifPresent(energy ->
        {
            AtomicInteger capacity = new AtomicInteger(energy.getEnergyStored());

            for(int i = 0; (i < Direction.values().length) && (capacity.get() > 0); i++)
            {
                Direction facing = Direction.values()[i];
                if(facing == this.world.getBlockState(this.pos).get(FACING))
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