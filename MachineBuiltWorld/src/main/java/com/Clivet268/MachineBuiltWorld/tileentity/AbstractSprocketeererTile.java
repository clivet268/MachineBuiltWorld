package com.Clivet268.MachineBuiltWorld.tileentity;

import com.Clivet268.MachineBuiltWorld.blocks.Sprocketeerer;
import com.Clivet268.MachineBuiltWorld.entity.SprocketeerEntity;
import com.Clivet268.MachineBuiltWorld.inventory.crafting.AbstractSprocketeerRecipe;
import com.Clivet268.MachineBuiltWorld.inventory.crafting.SprocketeerRecipe;
import com.Clivet268.MachineBuiltWorld.util.RegistryHandler;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import net.minecraft.block.AbstractFurnaceBlock;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.IRecipeHelperPopulator;
import net.minecraft.inventory.IRecipeHolder;
import net.minecraft.inventory.ISidedInventory;
import net.minecraft.inventory.ItemStackHelper;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.item.crafting.IRecipeType;
import net.minecraft.item.crafting.RecipeItemHelper;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.tileentity.ITickableTileEntity;
import net.minecraft.tileentity.LockableTileEntity;
import net.minecraft.tileentity.TileEntityType;
import net.minecraft.util.Direction;
import net.minecraft.util.IIntArray;
import net.minecraft.util.NonNullList;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.MathHelper;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.ItemStackHandler;
import net.minecraftforge.items.wrapper.InvWrapper;
import net.minecraftforge.items.wrapper.SidedInvWrapper;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.List;
import java.util.Map;
import java.util.UUID;
//TODO add energy support and use
public abstract class AbstractSprocketeererTile extends LockableTileEntity implements ISidedInventory, IRecipeHolder, IRecipeHelperPopulator, ITickableTileEntity {
    private ItemStackHandler itemHandler = createHandler();
    // Never create lazy optionals in getCapability. Always place them as fields in the tile entity:
    private LazyOptional<IItemHandler> handler = LazyOptional.of(() -> itemHandler);
    private static final int[] SLOTS_UP = new int[]{0};
    private static final int[] SLOTS_DOWN = new int[]{3};
    private static final int[] SLOTS_HORIZONTAL = new int[]{1,2};
    protected NonNullList<ItemStack> items = NonNullList.withSize(7, ItemStack.EMPTY);
    private int recipesUsed;
    private int cookTime;
    private int cookTimeTotal;
    private IRecipe<?> recipelastused = null;
    private @Nullable UUID owner;


    protected final IIntArray furnaceData = new IIntArray() {
        public int get(int index) {
            switch(index) {
                case 0:
                    return AbstractSprocketeererTile.this.recipesUsed;
                case 1:
                    return AbstractSprocketeererTile.this.cookTime;
                case 2:
                    return AbstractSprocketeererTile.this.cookTimeTotal;
                default:
                    return 0;
            }
        }

        public void set(int index, int value) {
            switch(index) {
                case 0:
                    AbstractSprocketeererTile.this.recipesUsed = value;
                    break;
                case 1:
                    AbstractSprocketeererTile.this.cookTime = value;
                    break;
                case 2:
                    AbstractSprocketeererTile.this.cookTimeTotal = value;
            }

        }

        public int size() {
            return 3;
        }
    };

    @Override
    public <T> LazyOptional<T> getCapability(Capability<T> capability, @Nullable Direction facing) {
        if (!this.removed && facing != null && capability == CapabilityItemHandler.ITEM_HANDLER_CAPABILITY) {
            if (facing == Direction.UP)
                return handlers[0].cast();
            else if (facing == Direction.DOWN)
                return handlers[1].cast();
            else
                return handlers[2].cast();
        }
        return super.getCapability(capability, facing);

    }

    private ItemStackHandler createHandler() {
        return new ItemStackHandler(7) {

            @Override
            protected void onContentsChanged(int slot) {
                // To make sure the TE persists when the chunk is saved later we need to
                // mark it dirty every time the item handler changes
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
    private final Map<ResourceLocation, Integer> cokeOvenSlotThings = Maps.newHashMap();
    protected final IRecipeType<? extends AbstractSprocketeerRecipe> recipeType;

    protected AbstractSprocketeererTile(TileEntityType<?> tileTypeIn, IRecipeType<? extends AbstractSprocketeerRecipe> recipeTypeIn, @Nullable PlayerEntity pin) {
        super(tileTypeIn);
        this.recipeType = recipeTypeIn;

    }

    public void setOwner(PlayerEntity pp){
        this.owner = pp.getUniqueID();
    }
    @Override
    protected IItemHandler createUnSidedHandler() {
        return new InvWrapper(this);
    }

    @Override
    public void read(CompoundNBT compound) {
        super.read(compound);
        this.items = NonNullList.withSize(this.getSizeInventory(), ItemStack.EMPTY);
        ItemStackHelper.loadAllItems(compound, this.items);
        this.cookTime = compound.getInt("CookTime");
        this.cookTimeTotal = compound.getInt("CookTimeTotal");
        int i = compound.getShort("RecipesUsedSize");

        for(int j = 0; j < i; ++j) {
            ResourceLocation resourcelocation = new ResourceLocation(compound.getString("RecipeLocation" + j));
            int k = compound.getInt("RecipeAmount" + j);
            this.cokeOvenSlotThings.put(resourcelocation, k);
        }


            this.owner = compound.getUniqueId("Owner");

    }
    @Override
    public CompoundNBT write(CompoundNBT compound) {
        super.write(compound);
        compound.putInt("CookTime", this.cookTime);
        compound.putInt("CookTimeTotal", this.cookTimeTotal);
        ItemStackHelper.saveAllItems(compound, this.items);
        compound.putShort("RecipesUsedSize", (short)this.cokeOvenSlotThings.size());
        int i = 0;

        for(Map.Entry<ResourceLocation, Integer> entry : this.cokeOvenSlotThings.entrySet()) {
            compound.putString("RecipeLocation" + i, entry.getKey().toString());
            compound.putInt("RecipeAmount" + i, entry.getValue());
            ++i;
        }
        compound.putUniqueId("Owner", owner);
        System.out.println("wrote");
        return compound;
    }

//TODO no workey
    @Override
    public void tick() {
        boolean flag1 = false;
        if (!this.world.isRemote) {
            if (!this.items.get(0).isEmpty()) {
                IRecipe<?> irecipe = this.world.getRecipeManager().getRecipe((IRecipeType<SprocketeerRecipe>)this.recipeType, this, this.world).orElse(null);
                if (this.canSmelt(irecipe)) {
                        flag1 = true;
                        this.recipelastused = irecipe;
                    }
                if (this.canSmelt(irecipe)) {
                    ++this.cookTime;
                    if (this.cookTime == this.cookTimeTotal) {
                        this.cookTime = 0;
                        this.cookTimeTotal = this.getCookTime();
                        this.smelt(irecipe);
                        flag1 = true;
                    }
                } else {
                    this.cookTime = 0;
                }
            } else if (this.cookTime > 0) {
                this.cookTime = MathHelper.clamp(this.cookTime - 2, 0, this.cookTimeTotal);
            }

            if (this.cookTime > 0) {
                flag1 = true;
                this.world.setBlockState(this.pos, this.world.getBlockState(this.pos).with(AbstractFurnaceBlock.LIT, true), 3);
            }
            else{
                this.world.setBlockState(this.pos, this.world.getBlockState(this.pos).with(AbstractFurnaceBlock.LIT, false), 3);
            }
        }

        if(flag1) {
            this.markDirty();
        }

    }

    protected boolean canSmelt(@Nullable IRecipe<?> recipeIn) {
        return !this.items.get(0).isEmpty() && recipeIn != null;
    }

    private void smelt(@Nullable IRecipe<?> recipe) {
        if (recipe != null && this.canSmelt(recipe)) {
            for(int i = 0; i <7; i++)
            {
                this.items.get(i).shrink(1);
            }
            this.world.setBlockState(this.pos, this.world.getBlockState(this.pos).with(Sprocketeerer.POSTSPAWN, true), 3);
            spawnSprocketeers(this.world.getPlayerByUuid(owner));
        }
    }

    protected int getCookTime() {
        return this.world.getRecipeManager().getRecipe(this.recipeType, this, this.world).map(AbstractSprocketeerRecipe::getCookTime).orElse(200);
    }

    @Override
    public int[] getSlotsForFace(Direction side) {
        if (side == Direction.DOWN) {
            return SLOTS_DOWN;
        } else {
            return side == Direction.UP ? SLOTS_UP : SLOTS_HORIZONTAL;
        }
    }

    /**
     * Returns true if automation can insert the given item in the given slot from the given side.
     */
    @Override
    public boolean canInsertItem(int index, @Nonnull ItemStack itemStackIn, @Nullable Direction direction) {
        return this.isItemValidForSlot(index, itemStackIn);
    }

    /**
     * Returns true if automation can extract the given item in the given slot from the given side.
     */
    @Override
    public boolean canExtractItem(int index, @Nonnull ItemStack stack, Direction direction) {
        return true;
    }

    /**
     * Returns the number of slots in the inventory.
     */
    @Override
    public int getSizeInventory() {
        return this.items.size();
    }

    @Override
    public boolean isEmpty() {
        for(ItemStack itemstack : this.items) {
            if (!itemstack.isEmpty()) {
                return false;
            }
        }

        return true;
    }

    /**
     * Returns the stack in the given slot.
     */
    @Override
    public ItemStack getStackInSlot(int index) {
        return this.items.get(index);
    }

    /**
     * Removes up to a specified number of items from an inventory slot and returns them in a new stack.
     */
    @Override
    public ItemStack decrStackSize(int index, int count) {
        return ItemStackHelper.getAndSplit(this.items, index, count);
    }

    /**
     * Removes a stack from the given slot and returns it.
     */
    @Override
    public ItemStack removeStackFromSlot(int index) {
        return ItemStackHelper.getAndRemove(this.items, index);
    }

    /**
     * Sets the given item stack to the specified slot in the inventory (can be crafting or armor sections).
     */
    @Override
    public void setInventorySlotContents(int index, ItemStack stack) {
        ItemStack itemstack = this.items.get(index);
        boolean flag = !stack.isEmpty() && stack.isItemEqual(itemstack) && ItemStack.areItemStackTagsEqual(stack, itemstack);
        this.items.set(index, stack);
        if (stack.getCount() > this.getInventoryStackLimit()) {
            stack.setCount(this.getInventoryStackLimit());
        }

        if (index == 0 && !flag) {
            this.cookTimeTotal = this.getCookTime();
            this.cookTime = 0;
            this.markDirty();
        }

    }

    /**
     * Don't rename this method to canInteractWith due to conflicts with Container
     */
    @Override
    public boolean isUsableByPlayer(PlayerEntity player) {
        //assert this.world != null;
        if(this.world == null)
        {
            this.world = player.world;
        }
        if (this.world.getTileEntity(this.pos) != this) {
            return false;
        } else {

            return player.getDistanceSq((double) this.pos.getX() + 0.5D, (double) this.pos.getY() + 0.5D, (double) this.pos.getZ() + 0.5D) <= 64.0D;

        }
    }


    /**
     * Returns true if automation is allowed to insert the given stack (ignoring stack size) into the given slot. For
     * guis use Slot.isItemValid
     */
    @Override
    public boolean isItemValidForSlot(int index, ItemStack stack) {
        return true;
    }
    @Override
    public void clear() {
        this.items.clear();
    }
    @Override
    public void setRecipeUsed(@Nullable IRecipe<?> recipe) {
        if (recipe != null) {
            this.cokeOvenSlotThings.compute(recipe.getId(), (p_214004_0_, p_214004_1_) -> {
                return 1 + (p_214004_1_ == null ? 0 : p_214004_1_);
            });
        }

    }


    @Nullable
    public IRecipe<?> getRecipeUsed() {
        return null;
    }

    @Override
    public void onCrafting(PlayerEntity player) {
        recipeOutput(player);
        //spawnExpOrbs(player);
    }

    public void recipeOutput(PlayerEntity player) {
        List<IRecipe<?>> list = Lists.newArrayList();

        for(Map.Entry<ResourceLocation, Integer> entry : this.cokeOvenSlotThings.entrySet()) {
            player.world.getRecipeManager().getRecipe(entry.getKey()).ifPresent((p_213993_3_) -> {
                list.add(p_213993_3_);
            });
        }

        player.unlockRecipes(list);
        this.cokeOvenSlotThings.clear();
    }

    private void spawnSprocketeers(@Nullable PlayerEntity player) {
            SprocketeerEntity toplace = new SprocketeerEntity(RegistryHandler.SPROCKETEER.get(), player.world);
            toplace.setTamed(true);
            if (player != null) {
                toplace.setAlignedId(player.getUniqueID());
            }
            toplace.setPosition(this.pos.getX(), this.pos.getY() + 1.0, this.pos.getZ());
            toplace.setAllegiance((byte)1);
            toplace.setVelocity(0,0.3,0);
            player.world.addEntity(toplace);

    }
    @Override
    public void fillStackedContents(RecipeItemHelper helper) {
        for(ItemStack itemstack : this.items) {
            helper.accountStack(itemstack);
        }

    }

    LazyOptional<? extends IItemHandler>[] handlers =
            SidedInvWrapper.create(this, Direction.UP, Direction.DOWN, Direction.NORTH);

    /**
     * invalidates a tile entity
     */
    @Override
    public void remove() {
        super.remove();
        for (int x = 0; x < handlers.length; x++)
            handlers[x].invalidate();
    }
}