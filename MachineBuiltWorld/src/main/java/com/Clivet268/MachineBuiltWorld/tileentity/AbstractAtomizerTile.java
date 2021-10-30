package com.Clivet268.MachineBuiltWorld.tileentity;

import com.Clivet268.MachineBuiltWorld.blocks.Atomizer;
import com.Clivet268.MachineBuiltWorld.inventory.crafting.AbstractAtomizingRecipe;
import com.Clivet268.MachineBuiltWorld.inventory.crafting.AtomizingRecipe;
import com.Clivet268.MachineBuiltWorld.inventory.crafting.CokeingRecipe;
import com.Clivet268.MachineBuiltWorld.util.CustomEnergyStorage;
import com.Clivet268.MachineBuiltWorld.util.CustomHeatStorage;
import com.google.common.collect.Maps;
import net.minecraft.block.AbstractFurnaceBlock;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.ISidedInventory;
import net.minecraft.inventory.ItemStackHelper;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.tileentity.ITickableTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityType;
import net.minecraft.util.Direction;
import net.minecraft.util.IIntArray;
import net.minecraft.util.NonNullList;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.Explosion;
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
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Add Configs sometime
 *
 */
//TODO make min heat to work
//TODO heating and atomizing process, then once materials are aquired actially make things
    //TODO dump?
    //TODO hot dump shoots out hot , could shoots our unfunclumps
public abstract class AbstractAtomizerTile extends TileEntity implements ISidedInventory, ITickableTileEntity {
    private int burnTime;
    private CustomHeatStorage heatStorage = createHeat();
    private CustomEnergyStorage energyStorage = createEnergy();
    private ItemStackHandler itemHandler = createHandler();
    // Never create lazy optionals in getCapability. Always place them as fields in the tile entity:

    private LazyOptional<IEnergyStorage> energy = LazyOptional.of(() -> energyStorage);
    private LazyOptional<IItemHandler> handler = LazyOptional.of(() -> itemHandler);
    protected NonNullList<ItemStack> items = NonNullList.withSize(4, ItemStack.EMPTY);
    private int recipesUsed;
    private int cookTime;
    private int cookTimeTotal;

    protected final IIntArray furnaceData = new IIntArray() {
        public int get(int index) {
            switch(index) {
                case 0:
                    return AbstractAtomizerTile.this.burnTime;
                case 1:
                    return AbstractAtomizerTile.this.recipesUsed;
                case 2:
                    return AbstractAtomizerTile.this.cookTime;
                case 3:
                    return AbstractAtomizerTile.this.cookTimeTotal;
                default:
                    return 0;
            }
        }

        public void set(int index, int value) {
            switch(index) {
                case 0:
                    AbstractAtomizerTile.this.burnTime = value;
                    break;
                case 1:
                    AbstractAtomizerTile.this.recipesUsed = value;
                    break;
                case 2:
                    AbstractAtomizerTile.this.cookTime = value;
                    break;
                case 3:
                    AbstractAtomizerTile.this.cookTimeTotal = value;
            }

        }

        public int size() {
            return 4;
        }
    };
    public AbstractAtomizerTile(TileEntityType<?> tileTypeIn)
    {
        super(tileTypeIn);
    }
    private final Map<ResourceLocation, Integer> cokeOvenSlotThings = Maps.newHashMap();

    //0 ing 1 1ing 2 solid material in 3 out
    @Override
    public void tick() {
        boolean flag = this.isBurning();
        boolean flag1 = false;
        if (this.isBurning()) {
            --this.burnTime;
        }
     if (!this.world.isRemote) {
        ItemStack itemstack = this.items.get(1);
        if (this.isBurning() || !itemstack.isEmpty() && !this.items.get(0).isEmpty()) {
            IRecipe<?> irecipe = null;//this.world.getRecipeManager().getRecipe((IRecipeType<CokeingRecipe>)this.recipeType, this, this.world).orElse(null);

            if (!this.isBurning() && this.canSmelt(irecipe)) {
                this.burnTime = this.getBurnTime(itemstack);
                this.recipesUsed = this.burnTime;
                if (this.isBurning()) {
                    flag1 = true;
                    if (itemstack.hasContainerItem())
                        this.items.set(1, itemstack.getContainerItem());
                    else
                    if (!itemstack.isEmpty()) {
                        Item item = itemstack.getItem();
                        itemstack.shrink(1);
                        if (itemstack.isEmpty()) {
                            this.items.set(1, itemstack.getContainerItem());
                        }
                    }
                }
            }


            if (this.isBurning() && this.canSmelt(irecipe)) {
                ++this.cookTime;
                if (this.cookTime == this.cookTimeTotal) {
                    this.cookTime = 0;
                    //this.cookTimeTotal = this.getCookTime();
                    this.smelt(irecipe);
                    flag1 = true;
                }
            } else {
                this.cookTime = 0;
            }
        } else if (!this.isBurning() && this.cookTime > 0) {
            this.cookTime = MathHelper.clamp(this.cookTime - 2, 0, this.cookTimeTotal);
        }

        if (flag != this.isBurning()) {
            flag1 = true;
            this.world.setBlockState(this.pos, this.world.getBlockState(this.pos).with(AbstractFurnaceBlock.LIT, this.isBurning()), 3);
        }
    }

        if(flag1) {
        this.markDirty();
    }

}

    protected boolean canSmelt(@Nullable IRecipe<?> recipeIn) {
        if (!this.items.get(0).isEmpty() && recipeIn != null) {
            ItemStack itemstack = recipeIn.getRecipeOutput();
            ItemStack itemstack2 = this.items.get(2);
            ItemStack itemstack3 = this.items.get(0);
            ItemStack itemstack4 = ((CokeingRecipe)recipeIn).getInfusie();
            //System.out.println(recipeIn);
            if (itemstack.isEmpty()) {
                return false;
            } else {
                if(itemstack4.getItem() != Items.AIR){
                    return itemstack2.getItem() == itemstack4.getItem();
                }
                ItemStack itemstack1 = this.items.get(3);
                if (itemstack1.isEmpty()) {
                    return true;
                } else if (!itemstack1.isItemEqual(itemstack)) {
                    return false;
                } else if (itemstack1.getCount() + itemstack.getCount() <= this.getInventoryStackLimit() && itemstack1.getCount() + itemstack.getCount() <= itemstack1.getMaxStackSize()) { // Forge fix: make furnace respect stack sizes in furnace recipes
                    return true;
                } else {
                    return itemstack1.getCount() + itemstack.getCount() <= itemstack.getMaxStackSize(); // Forge fix: make furnace respect stack sizes in furnace recipes
                }
            }
        } else {
            return false;
        }
    }

    private void smelt(@Nullable IRecipe<?> recipe) {
        if (recipe != null && this.canSmelt(recipe)) {
            ItemStack itemstack = this.items.get(0);
            ItemStack itemstack1 = recipe.getRecipeOutput();
            ItemStack itemstack2 = this.items.get(3);
            ItemStack itemstack3 = this.items.get(2);
            boolean cookorasymble = ((AtomizingRecipe)recipe).asymordysa();
            boolean flag = false;
            ItemStack itemstack4 = ((CokeingRecipe)recipe).getInfusie();
            //System.out.println(itemstack1);

            if(itemstack4.getItem() != Items.AIR) {
                if (itemstack3.getItem() != itemstack4.getItem()) {
                    return;
                }
                else{
                    flag = true;
                }
            }

            if (itemstack2.isEmpty() || itemstack2.getItem() == Items.AIR) {
                this.items.set(3, itemstack1.copy());
            } else if (itemstack2.getItem() == itemstack1.getItem()) {
                itemstack2.grow(itemstack1.getCount());
            }

            if (!this.world.isRemote) {
                //this.setRecipeUsed(recipe);
            }

            if (itemstack.getItem() == Blocks.WET_SPONGE.asItem() && !this.items.get(1).isEmpty() && this.items.get(1).getItem() == Items.BUCKET) {
                this.items.set(1, new ItemStack(Items.WATER_BUCKET));
            }

            if(flag)
            {
                itemstack3.shrink(1);
            }
            itemstack.shrink(1);
        }
    }


    @Nullable
    public ItemStack getLeftTankIng(){
        BlockState blok = (this.world.getBlockState(this.pos));
        BlockPos ntolaewpf = this.pos.offset(((Atomizer)blok.getBlock()).getLeft(blok),1);
        if(this.world != null){
            return null;
        }
        AbstractDisassociatedAtomContainerTile blokok = (AbstractDisassociatedAtomContainerTile)this.world.getTileEntity(ntolaewpf);
        return blokok.items.get(0);
    }

    @Nullable
    public ItemStack getRightTankIng(){
        BlockState blok = (this.world.getBlockState(this.pos));
        BlockPos ntolaewpf = this.pos.offset(((Atomizer)blok.getBlock()).getRight(blok), 1);
        if(this.world != null){
            return null;
        }
        AbstractDisassociatedAtomContainerTile blokok = (AbstractDisassociatedAtomContainerTile)this.world.getTileEntity(ntolaewpf);
        return blokok.items.get(0);
    }

    public int getEnergy() {
        return energyStorage.getEnergyStored();
    }
    private boolean isBurning() {
        return this.burnTime > 0;
    }

    public void generate(){
        this.heatStorage.addHeat(25);
    }

    protected int getBurnTime(ItemStack fuel) {
        if (fuel.isEmpty()) {
            return 0;
        } else {
            return ForgeHooks.getBurnTime(fuel);
        }
    }

    private CustomEnergyStorage createEnergy() {
        return new CustomEnergyStorage(10000, 100, 100) {
            @Override
            protected void onEnergyChanged() {
                markDirty();
            }
        };
    }

    private CustomHeatStorage createHeat() {
        return new CustomHeatStorage(10000, 100, 100, 100, 10, 10, 200) {
            @Override
            protected void onHeatChanged(boolean over) {
                markDirty();
                int releasingEnergy = this.heat - this.capacity / (this.capacity/100);
                if(over){
                    if(checkMaxHeatAndDetonate(this.forgiveness, this.blasin)){
                        getWorld().createExplosion(null, getPos().getX()- 0.5, getPos().getY() + 1.0, getPos().getZ() - 0.,
                                Math.min(releasingEnergy, 4), releasingEnergy > 2, Explosion.Mode.DESTROY);
                    }
                };
            }
        };
    }

    @Override
    public void read(CompoundNBT compound) {
        super.read(compound);
        this.items = NonNullList.withSize(this.getSizeInventory(), ItemStack.EMPTY);
        ItemStackHelper.loadAllItems(compound, this.items);
        this.burnTime = compound.getInt("BurnTime");
        this.cookTime = compound.getInt("CookTime");
        this.cookTimeTotal = compound.getInt("CookTimeTotal");
        if(this.getBurnTime(this.items.get(1)) > this.getBurnTime(this.items.get(2)))
        {
            this.recipesUsed = this.getBurnTime(this.items.get(2));
        }
        else if(this.getBurnTime(this.items.get(1)) < this.getBurnTime(this.items.get(2)))
        {
            this.recipesUsed = this.getBurnTime(this.items.get(1));
        }
        else
        {
            this.recipesUsed = this.getBurnTime(this.items.get(1));
        }
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
        compound.putInt("BurnTime", this.burnTime);
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
        return compound;
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