package com.Clivet268.MachineBuiltWorld.tileentity;

import com.Clivet268.MachineBuiltWorld.inventory.crafting.AbstractCrushingRecipe;
import com.Clivet268.MachineBuiltWorld.inventory.crafting.CrushingRecipe;
import com.Clivet268.MachineBuiltWorld.items.CrusherTeethBase;
import com.Clivet268.MachineBuiltWorld.util.CustomEnergyStorage;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import net.minecraft.block.ChestBlock;
import net.minecraft.entity.item.ItemEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.IRecipeHelperPopulator;
import net.minecraft.inventory.IRecipeHolder;
import net.minecraft.inventory.ISidedInventory;
import net.minecraft.inventory.ItemStackHelper;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.item.crafting.IRecipeType;
import net.minecraft.item.crafting.RecipeItemHelper;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.state.properties.ChestType;
import net.minecraft.tileentity.ITickableTileEntity;
import net.minecraft.tileentity.LockableTileEntity;
import net.minecraft.tileentity.TileEntityType;
import net.minecraft.util.*;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.World;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.energy.IEnergyStorage;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.ItemStackHandler;
import net.minecraftforge.items.wrapper.InvWrapper;
import net.minecraftforge.items.wrapper.SidedInvWrapper;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.List;
import java.util.Map;
import java.util.Random;
//TODO no workey, recipies arent going trough or something
public abstract class AbstractCrusherTile extends LockableTileEntity implements ICrusherTeeth, ISidedInventory, IRecipeHolder, IRecipeHelperPopulator, ITickableTileEntity {
    public ItemStackHandler itemHandler = createHandler();
    public CustomEnergyStorage energyStorage = createEnergy();
    // Never create lazy optionals in getCapability. Always place them as fields in the tile entity:
    private LazyOptional<IItemHandler> handler = LazyOptional.of(() -> itemHandler);

    private LazyOptional<IEnergyStorage> energy = LazyOptional.of(() -> energyStorage);
    private static final int[] SLOTS_UP = new int[]{0};
    private static final int[] SLOTS_DOWN = new int[]{1,2};

    protected NonNullList<ItemStack> items = NonNullList.withSize(4, ItemStack.EMPTY);
    private boolean flag = false;
    private boolean crushing = false;
    private int recipesUsed;
    private int cookTime;
    private int cookTimeTotal;
    private int crusherMultiplier;
    /** The current angle of the lid (between 0 and 1) */
    protected float lidAngle;
    /** The angle of the lid last tick */
    protected float prevLidAngle;
    /** The number of players currently using this chest */
    protected int numPlayersUsing;
    private int ticksSinceSync;
    public CustomEnergyStorage getEnergyStorage(){
        return energyStorage;
    }

    protected final IIntArray crusherData = new IIntArray() {
        public int get(int index) {
            switch(index) {
                case 0:
                    return AbstractCrusherTile.this.recipesUsed;
                case 1:
                    return AbstractCrusherTile.this.cookTime;
                case 2:
                    return AbstractCrusherTile.this.cookTimeTotal;
                default:
                    return 0;
            }
        }

        public void set(int index, int value) {
            switch(index) {
                case 0:
                    AbstractCrusherTile.this.recipesUsed = value;
                    break;
                case 1:
                    AbstractCrusherTile.this.cookTime = value;
                    break;
                case 2:
                   AbstractCrusherTile.this.cookTimeTotal = value;
            }

        }

        public int size() {
            return 3;
        }
    };

    private CustomEnergyStorage createEnergy() {
        return new CustomEnergyStorage(10000, 100, 0) {
            @Override
            protected void onEnergyChanged() {
                markDirty();
            }
        };
    }
    @OnlyIn(Dist.CLIENT)
    public float getLidAngle(float partialTicks) {
        return MathHelper.lerp(partialTicks, this.prevLidAngle, this.lidAngle);
    }

    public ItemStack getIItems(int i)
    {
        return this.items.get(i);
    }
    @Nonnull
    @Override
    public <T> LazyOptional<T> getCapability(@Nonnull Capability<T> cap, @Nullable Direction side) {
        if (cap == CapabilityItemHandler.ITEM_HANDLER_CAPABILITY) {
            return handler.cast();
        }
        return super.getCapability(cap, side);
    }

    private ItemStackHandler createHandler() {
        return new ItemStackHandler(3) {

            @Override
            protected void onContentsChanged(int slot) {
                // To make sure the TE persists when the chunk is saved later we need to
                // mark it dirty every time the item handler changes
                markDirty();
            }

            @Override
            public boolean isItemValid(int slot, @Nonnull ItemStack stack) {
                return slot == 0;
            }

            @Nonnull
            @Override
            public ItemStack insertItem(int slot, @Nonnull ItemStack stack, boolean simulate) {
                return stack;
            }
        };
    }
    private final Map<ResourceLocation, Integer> cokeOvenSlotThings = Maps.newHashMap();
    protected final IRecipeType<? extends AbstractCrushingRecipe> recipeType;

    protected AbstractCrusherTile(TileEntityType<?> tileTypeIn, IRecipeType<? extends AbstractCrushingRecipe> recipeTypeIn) {
        super(tileTypeIn);
        this.recipeType = recipeTypeIn;
    }
    @Override
    protected IItemHandler createUnSidedHandler() {
        return new InvWrapper(this);
    }

    private boolean isCrushing() {
       return crushing;
    }

    private void setCrushing(boolean a) {
        crushing = a;
    }

    @Override
    public void read(CompoundNBT compound) {
        super.read(compound);
        energyStorage.deserializeNBT(compound.getCompound("energy"));
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

    }
    @Override
    public CompoundNBT write(CompoundNBT compound) {
        super.write(compound);
        compound.put("energy", energyStorage.serializeNBT());
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
        System.out.println("wrote");
        return compound;
    }
    protected ItemStack dispenseStack(World world, BlockPos pos, ItemStack stack) {
        Direction direction = Direction.DOWN;
        doDispense(world, stack, 0, direction, pos);
        return stack;
    }

    public static void doDispense(World worldIn, ItemStack stack, int speed, Direction facing, BlockPos position) {
        double d0 = position.getX()+0.5;
        double d1 = position.getY()-0.175D;
        double d2 = position.getZ()+0.5;
        if(stack.getItem() == Items.AIR) {
            ItemEntity itementity = new ItemEntity(worldIn, d0, d1, d2, stack);
            double d3 = worldIn.rand.nextDouble() * 0.1D + 0.2D;
            itementity.setMotion(worldIn.rand.nextGaussian() * (double) 0.0075F * (double) speed - 0.3D + (double) facing.getXOffset() * d3, worldIn.rand.nextGaussian() * (double) 0.0075F * (double) speed + (double) 0.2F, worldIn.rand.nextGaussian() * (double) 0.0075F * (double) speed + (double) facing.getZOffset() * d3);
            worldIn.addEntity(itementity);
        }
    }
    private void playSound(SoundEvent soundIn) {
        ChestType chesttype = this.getBlockState().get(ChestBlock.TYPE);
        if (chesttype != ChestType.LEFT) {
            double d0 = (double)this.pos.getX() + 0.5D;
            double d1 = (double)this.pos.getY() + 0.5D;
            double d2 = (double)this.pos.getZ() + 0.5D;
            if (chesttype == ChestType.RIGHT) {
                Direction direction = ChestBlock.getDirectionToAttached(this.getBlockState());
                d0 += (double)direction.getXOffset() * 0.5D;
                d2 += (double)direction.getZOffset() * 0.5D;
            }

            this.world.playSound((PlayerEntity)null, d0, d1, d2, soundIn, SoundCategory.BLOCKS, 0.5F, this.world.rand.nextFloat() * 0.1F + 0.9F);
        }
    }

    @Override
    public void tick() {
        if(this.getEnergyStorage().getEnergyStored() > 5) {
            World worldIn = this.world;
            if (!this.items.get(1).isEmpty()) {
                ItemStack stack = getStackInSlot(1).getStack().split(1);
                dispenseStack(worldIn, pos, stack);
            }
            if (!this.items.get(2).isEmpty()) {
                ItemStack stack1 = getStackInSlot(2).getStack().split(1);
                dispenseStack(worldIn, pos, stack1);
            }

            ItemStack itemstack333 = this.items.get(3);
            if (itemstack333.getItem() != Items.AIR) {
                this.flag = true;
                this.crusherMultiplier = ((CrusherTeethBase) itemstack333.getItem()).getMultiplier();
            } else {
                this.flag = false;
            }

            if (!this.world.isRemote) {
                if (!this.items.get(0).isEmpty()) {
                    IRecipe<?> irecipe = (this.world.getRecipeManager().getRecipe((IRecipeType<CrushingRecipe>) this.recipeType, this, this.world).orElse(null));
                    //this.canCrush(irecipe);
                    if (this.getEnergyStorage().getEnergyStored() > 5 && this.canCrush(irecipe)) {
                        this.getEnergyStorage().consumeEnergy(5);
                        ++this.cookTime;
                        if (this.cookTime == this.cookTimeTotal) {
                            this.cookTime = 0;
                            this.cookTimeTotal = this.getCookTime();
                            this.crush(irecipe);
                        }
                    } else {
                        this.cookTime = 0;
                    }
                } else if (/*!this.isBurning() &&*/ this.cookTime > 0) {
                    this.cookTime = MathHelper.clamp(this.cookTime - 2 + (crusherMultiplier - 1), 0, this.cookTimeTotal);
                }

            }
            this.markDirty();
        }

    }

    public ItemStack whatsadateethtype(){
        return this.items.get(3);
    }

    protected boolean canCrush(@Nullable IRecipe<?> recipe) {
        CrushingRecipe recipeIn = (CrushingRecipe)recipe;
        if (!this.items.get(0).isEmpty() && recipeIn != null) {
            ItemStack itemstack = recipeIn.getRecipeOutput();
            itemstack.setCount(recipeIn.getCount1());
            ItemStack itemstack1 = recipeIn.getRecipeOutput1();
            itemstack1.setCount(recipeIn.getCount11());
            ItemStack itemstack2 = this.items.get(2);
            ItemStack itemstack3 = this.items.get(0);
            ItemStack itemstack33 = this.items.get(1);
            ItemStack itemstack333 = this.items.get(3);

            if (itemstack333.isEmpty()) {
                setCrushing(false);
                return false;
            }

            if (itemstack.isEmpty()) {
                setCrushing(false);
                return false;
            } else {
                if (itemstack33.isEmpty()) {
                    setCrushing(true);
                    return true;
                } else if (!itemstack33.isItemEqual(itemstack)) {
                    return false;
                } else if (itemstack33.getCount() + itemstack.getCount() <= this.getInventoryStackLimit() && itemstack33.getCount() + itemstack.getCount() <= itemstack33.getMaxStackSize()) { // Forge fix: make furnace respect stack sizes in furnace recipes
                    setCrushing(true);
                    return true;
                } else {
                    setCrushing(itemstack33.getCount() + itemstack.getCount() <= itemstack.getMaxStackSize());
                    return itemstack33.getCount() + itemstack.getCount() <= itemstack.getMaxStackSize(); // Forge fix: make furnace respect stack sizes in furnace recipes
                }
            }
        } else {
            setCrushing(false);
            return false;
        }
    }

    private void crush(@Nullable IRecipe<?> recipep) {
        CrushingRecipe recipe = (CrushingRecipe)recipep;

        if (recipe != null && this.canCrush(recipe)) {

            boolean flag =false;
            ItemStack itemstack = this.items.get(0);
            ItemStack itemstack1 = recipe.getRecipeOutput();
            itemstack1.setCount(recipe.getCount1());
            ItemStack itemstack11 = recipe.getRecipeOutput1();
            itemstack11.setCount(recipe.getCount11());
            ItemStack itemstack2 = this.items.get(1);
            ItemStack itemstack3 = this.items.get(2);
            ItemStack itemstack33 = this.items.get(3);

            if (itemstack2.isEmpty() || itemstack2.getItem() == Items.AIR) {
                this.items.set(1, itemstack1.copy());
            } else if (itemstack2.getItem() == itemstack1.getItem()) {
                itemstack2.grow(itemstack1.getCount());
            }
            else{
                flag = true;
            }
            if(itemstack11.getItem() != Items.AIR) {
                if ((itemstack3.isEmpty() || itemstack3.getItem() == Items.AIR) && !flag) {
                    this.items.set(2, itemstack11.copy());
                } else if (itemstack2.getItem() == itemstack11.getItem()) {
                    itemstack2.grow(itemstack1.getCount());
                }
            }
            if (!this.world.isRemote) {
                this.setRecipeUsed(recipe);
            }
            itemstack33.attemptDamageItem(1, new Random(), null);
            itemstack.shrink(1);
        }
    }


    protected int getCookTime() {
        return this.world.getRecipeManager().getRecipe(this.recipeType, this, this.world)
                .map(AbstractCrushingRecipe::getCookTime).orElse(200);
    }


    @Override
    public int[] getSlotsForFace(Direction side) {
        return SLOTS_DOWN;
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
    public boolean canExtractItem(int index, ItemStack stack, Direction direction) {
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
        if (index == 0) {
            return true;
        } else {
         return false;
        }
    }

    @Override
    public void clear() {
        this.items.clear();
    }

    @Override
    public void setRecipeUsed(@Nullable IRecipe<?> recipe) {
        if (recipe != null) {
            this.cokeOvenSlotThings.compute(recipe.getId(), (p_214004_0_, p_214004_1_) ->
                    1 + (p_214004_1_ == null ? 0 : p_214004_1_));
        }

    }

    @Nullable
    public IRecipe<?> getRecipeUsed() {
        return null;
    }

    public void onCrafting(PlayerEntity player) {
    }

    public void recipeOutput(PlayerEntity player) {
        List<IRecipe<?>> list = Lists.newArrayList();

        for(Map.Entry<ResourceLocation, Integer> entry : this.cokeOvenSlotThings.entrySet()) {
            player.world.getRecipeManager().getRecipe(entry.getKey()).ifPresent(list::add);
        }

        player.unlockRecipes(list);
        this.cokeOvenSlotThings.clear();
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

    public enum Mode{
        OPEN,
        CLOSE
    }
}