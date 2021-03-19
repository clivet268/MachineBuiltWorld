package com.Clivet268.MachineBuiltWorld.tileentity;

import net.minecraft.block.AbstractFurnaceBlock;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.ItemStackHelper;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.item.crafting.RecipeItemHelper;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.nbt.INBT;
import net.minecraft.nbt.ListNBT;
import net.minecraft.tileentity.TileEntityType;
import net.minecraft.util.Direction;
import net.minecraft.util.NonNullList;
import net.minecraftforge.common.ForgeHooks;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.ItemStackHandler;
import net.minecraftforge.items.wrapper.InvWrapper;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.Collections;

import static com.Clivet268.MachineBuiltWorld.Config.TBATTERY_MAXPOWER;
import static com.Clivet268.MachineBuiltWorld.util.RegistryHandler.GENERATOR_TILE;

/**
 * Add Configs sometime
 */
public abstract class AbstractGeneratorTile extends EnergyHoldableTile implements IInventory {
    private int burnTime;
    private ItemStackHandler itemHandler = createHandler();
    // Never create lazy optionals in getCapability. Always place them as fields in the tile entity:
    private LazyOptional<IItemHandler> handler = LazyOptional.of(() -> itemHandler);
    protected NonNullList<ItemStack> items = NonNullList.withSize(4, ItemStack.EMPTY);
    public AbstractGeneratorTile(TileEntityType<?> tileTypeIn)
    {
        super(tileTypeIn, TBATTERY_MAXPOWER.get(), 100 ,10);
    }
    @Override
    public void tick()
    {
        boolean flag = this.isBurning();
        boolean flag1 = false;
        if (this.isBurning()) {
            --this.burnTime;
            this.generate();
        }
        if (!this.world.isRemote) {
            ItemStack itemstack = this.items.get(0);
            if (this.isBurning() || !itemstack.isEmpty()) {
                    this.burnTime = this.getBurnTime(itemstack);
                    if (this.isBurning()) {
                        flag1 = true;
                        if (itemstack.hasContainerItem())
                            this.items.set(0, itemstack.getContainerItem());
                        else if (!itemstack.isEmpty()) {
                            Item items = itemstack.getItem();
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
        if(flag1) {
            this.markDirty();
        }
    }
    private boolean isBurning() {
        return this.burnTime > 0;
    }

    public void generate(){
        this.getEnergyS().addEnergy(10);
    }

    protected int getBurnTime(ItemStack fuel) {
        if (fuel.isEmpty()) {
            return 0;
        } else {
            return ForgeHooks.getBurnTime(fuel)/3;
        }
    }
    @Override
    public void read(CompoundNBT tag) {
        super.read(tag);
        this.items = NonNullList.withSize(this.getSizeInventory(), ItemStack.EMPTY);
        ItemStackHelper.loadAllItems(tag, this.items);
        this.burnTime = tag.getInt("BurnTime");
        this.getEnergyS().deserializeNBT(tag.getCompound("energy"));
    }

    @Override
    public CompoundNBT write(CompoundNBT tag) {
        super.write(tag);
        ItemStackHelper.saveAllItems(tag, this.items);
        tag.putInt("BurnTime", this.burnTime);
        tag.put("energy", this.getEnergyS().serializeNBT());

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

    @Nonnull
    @Override
    public <T> LazyOptional<T> getCapability(@Nonnull Capability<T> cap, @Nullable Direction side) {
        if (cap == CapabilityItemHandler.ITEM_HANDLER_CAPABILITY) {
            return handler.cast();
        }
        return super.getCapability(cap, side);
    }
}