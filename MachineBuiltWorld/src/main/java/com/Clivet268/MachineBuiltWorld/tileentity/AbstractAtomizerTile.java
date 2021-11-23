package com.Clivet268.MachineBuiltWorld.tileentity;

import com.Clivet268.MachineBuiltWorld.blocks.Atomizer;
import com.Clivet268.MachineBuiltWorld.blocks.DisasossiatedAtomContainer;
import com.Clivet268.MachineBuiltWorld.inventory.crafting.AtomizingRecipe;
import com.Clivet268.MachineBuiltWorld.inventory.crafting.CokeingRecipe;
import com.Clivet268.MachineBuiltWorld.inventory.crafting.IMoreRecipeType;
import com.Clivet268.MachineBuiltWorld.util.CustomEnergyStorage;
import com.Clivet268.MachineBuiltWorld.util.CustomHeatStorage;
import com.Clivet268.MachineBuiltWorld.util.IHeatStorage;
import com.Clivet268.MachineBuiltWorld.util.RegistryHandler;
import com.google.common.collect.Maps;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.ISidedInventory;
import net.minecraft.inventory.ItemStackHelper;
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
import net.minecraft.world.Explosion;
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
import java.util.ArrayList;
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
    private CustomHeatStorage heatStorage = createHeat();
    private CustomEnergyStorage energyStorage = createEnergy();
    private ItemStackHandler itemHandler = createHandler();
    // Never create lazy optionals in getCapability. Always place them as fields in the tile entity:
    private LazyOptional<IHeatStorage> heat = LazyOptional.of(() -> heatStorage);
    private LazyOptional<IEnergyStorage> energy = LazyOptional.of(() -> energyStorage);
    private LazyOptional<IItemHandler> handler = LazyOptional.of(() -> itemHandler);
    private ArrayList<DisassociatedAtomContainerTile> containersl = new ArrayList<>(0);
    private ArrayList<DisassociatedAtomContainerTile> containersr = new ArrayList<>(0);
    protected NonNullList<ItemStack> items = NonNullList.withSize(6, ItemStack.EMPTY);
    private int recipesUsed;
    private int cookTime;
    private int cookTimeTotal;
    private int s1selectedtank;
    private int s2selectedtank;

    public int getS1selectedtank() {
        return s1selectedtank;
    }

    public void setS1selectedtank(int s1selectedtank) {
        this.s1selectedtank = s1selectedtank;
    }

    public int getS2selectedtank() {
        return s2selectedtank;
    }

    public void setS2selectedtank(int s2selectedtank) {
        this.s2selectedtank = s2selectedtank;
    }

    protected final IIntArray furnaceData = new IIntArray() {
        public int get(int index) {
            switch(index) {
                case 0:
                    return AbstractAtomizerTile.this.recipesUsed;
                case 1:
                    return AbstractAtomizerTile.this.cookTime;
                case 2:
                    return AbstractAtomizerTile.this.cookTimeTotal;
                default:
                    return 0;
            }
        }

        public void set(int index, int value) {
            switch(index) {
                case 0:
                    AbstractAtomizerTile.this.recipesUsed = value;
                    break;
                case 1:
                    AbstractAtomizerTile.this.cookTime = value;
                    break;
                case 2:
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

    //0 ingin 1ingin2 2 ingou1 3 ingou2  4solid material in 5 out
    @Override
    public void tick() {
        if(this.heatStorage.heat < this.heatStorage.capacity){
            this.energyStorage.consumeEnergy(100);
            this.heatStorage.addHeat(100);
        }
        if(this.heatStorage.heat >0){
            this.heatStorage.pasiDis(0);
        }
        //atomize
        melt();


        if(s1selectedtank != -1){
            this.items.set(0, containersl.get(s1selectedtank).items.get(0));
            }
        if(s2selectedtank != -1){
            this.items.set(0, containersr.get(s2selectedtank).items.get(0));
        }
        //deatomize
        IRecipe<?> irecipe = this.world.getRecipeManager().getRecipe(IMoreRecipeType.MoreAtomizing.ATOMIZING, this, this.world).orElse(null);

    }

    public boolean getTanks(){
        BlockState bs = this.world.getWorld().getBlockState(this.pos);
        switch(((Atomizer)bs.getBlock()).getFacing(bs).getHorizontalIndex()){
            case 0:
                if(this.world.getWorld().getBlockState(this.pos.add(-1,0,-1)).getBlock().equals(RegistryHandler.DISASSOCIATED_ATOM_CONTAINER.get())){
                    this.containersr.set(0,(DisassociatedAtomContainerTile) this.world.getWorld().getTileEntity(this.pos.add(-1,0,-1)));
                }
                if(this.world.getWorld().getBlockState(this.pos.add(1,0,-1)).getBlock().equals(RegistryHandler.DISASSOCIATED_ATOM_CONTAINER.get())){
                    this.containersl.set(0,(DisassociatedAtomContainerTile) this.world.getWorld().getTileEntity(this.pos.add(1,0,-1)));
                }
            case 1:
                this.world.getWorld().getBlockState(this.pos.add(-1,0,0));
            case 2:
                this.world.getWorld().getBlockState(this.pos.add(0,0,1));
            case 3:
                this.world.getWorld().getBlockState(this.pos.add(1,0,0));
            default:
                return false;
        }
    }


    protected boolean melt(){
        if(this.heatStorage.heat > 1000){
            this.cookTime ++;
            this.heatStorage.consumeHeat(100);
        }

        if(this.cookTime>=200) {
            for(DisassociatedAtomContainerTile e : containersl){
                if(e.items.contains(ItemStack.EMPTY)) {
                    this.items.get(0).setCount(this.items.get(0).getCount() - 1);
                    e.items.set(0, this.items.get(0));
                } else if (e.items.contains(items.get(0))) {
                    items.get(0).setCount(items.get(0).getCount() - 1);
                    e.items.set(0, this.items.get(0));
                }
            }
            for(DisassociatedAtomContainerTile e : containersr){
                if(e.items.contains(ItemStack.EMPTY)) {
                    this.items.get(1).setCount(this.items.get(1).getCount() - 1);
                    e.items.set(0, this.items.get(1));
                } else if (e.items.contains(this.items.get(1))) {
                    this.items.get(1).setCount(this.items.get(1).getCount() - 1);
                    e.items.set(0, this.items.get(1));
                }
            }
            cookTime=0;
            return true;
        }
    return false;
    }

    protected boolean canSmelt(@Nullable IRecipe<?> recipeIn) {
        if (recipeIn != null) {
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


    public int getEnergy() {
        return energyStorage.getEnergyStored();
    }

    public void generate(){
        this.heatStorage.addHeat(25);
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
        return new CustomHeatStorage(180000, 100, 100, 100, 10, 10, 200) {
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