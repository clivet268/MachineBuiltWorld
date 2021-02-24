package com.Clivet268.MachineBuiltWorld.inventory.Containers;

import com.Clivet268.MachineBuiltWorld.inventory.crafting.CokeOvenFuelSlot;
import com.Clivet268.MachineBuiltWorld.inventory.crafting.AbstractCokeingRecipe;
import com.Clivet268.MachineBuiltWorld.inventory.crafting.CokeOvenResultSlot;
import com.Clivet268.MachineBuiltWorld.inventory.crafting.CokeingRecipe;
import com.Clivet268.MachineBuiltWorld.tileentity.AbstractCokeOvenTile;
import com.Clivet268.MachineBuiltWorld.tileentity.CokeOvenTile;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.IRecipeHelperPopulator;
import net.minecraft.inventory.Inventory;
import net.minecraft.inventory.container.*;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.*;
import net.minecraft.tileentity.AbstractFurnaceTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.IIntArray;
import net.minecraft.util.IntArray;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.SlotItemHandler;
import net.minecraftforge.items.wrapper.InvWrapper;

import static com.Clivet268.MachineBuiltWorld.util.RegistryHandler.COKE_OVEN_CONTAINER;

public class CokeOvenContainerBase extends RecipeBookContainer<IInventory> {
    private final IInventory furnaceInventory;
    private final IIntArray furnaceData;
    public final World world;
    private IItemHandler playerInventory;
    public CokeOvenTile tileEntity;
    private PlayerEntity playerEntity;

    private final IRecipeType<? extends AbstractCokeingRecipe> recipeType;
    public CokeOvenContainerBase(ContainerType<?> containerTypeIn, World wworld, IRecipeType<? extends AbstractCokeingRecipe> recipeTypeIn, int id, PlayerInventory playerInventoryIn, BlockPos pos) {
        this(containerTypeIn, wworld, recipeTypeIn, id, playerInventoryIn, new Inventory(4), new IntArray(5),pos);
    }

    protected CokeOvenContainerBase(ContainerType<?> containerTypeIn, World wworld, IRecipeType<? extends AbstractCokeingRecipe> recipeTypeIn, int id, PlayerInventory playerInventoryIn, IInventory furnaceInventoryIn, IIntArray furnaceDataIn, BlockPos pos) {
        super(containerTypeIn, id);
        this.world = wworld;
        this.tileEntity = (CokeOvenTile) wworld.getTileEntity(pos);
        this.playerEntity = playerInventoryIn.player;
        this.playerInventory = new InvWrapper(playerInventoryIn);
        this.recipeType = recipeTypeIn;
        assertInventorySize(furnaceInventoryIn, 4);
        System.out.println(furnaceDataIn.size());
        assertIntArraySize(furnaceDataIn, 4);
        this.furnaceInventory = furnaceInventoryIn;
        this.furnaceData = furnaceDataIn;

        if (tileEntity != null) {
            addSlot(new Slot(this.tileEntity, 0, 56, 17));
            addSlot(new CokeOvenFuelSlot(this.tileEntity,  1, 38, 53));
            addSlot(new CokeOvenFuelSlot(this.tileEntity,  2, 74, 53));
            addSlot(new CokeOvenResultSlot(playerInventoryIn.player, this.tileEntity,  3, 116, 35));

        }
        layoutPlayerInventorySlots(8,84);
    }
    @Override
    public void fillStackedContents(RecipeItemHelper itemHelperIn) {
        if (this.furnaceInventory instanceof IRecipeHelperPopulator) {
            ((IRecipeHelperPopulator)this.furnaceInventory).fillStackedContents(itemHelperIn);
        }

    }
    @Override
    public void clear() {
        this.furnaceInventory.clear();
    }
    @Override
    public void func_217056_a(boolean p_217056_1_, IRecipe<?> p_217056_2_, ServerPlayerEntity p_217056_3_) {
        (new ServerRecipePlacerFurnace<>(this)).place(p_217056_3_, (IRecipe<IInventory>)p_217056_2_, p_217056_1_);
    }
    @Override
    public boolean matches(IRecipe<? super IInventory> recipeIn) {
        return recipeIn.matches(this.furnaceInventory, this.world);
    }
    @Override
    public int getOutputSlot() {
        return 3;
    }

    public int getWidth() {
        return 1;
    }

    public int getHeight() {
        return 1;
    }

    @OnlyIn(Dist.CLIENT)
    public int getSize() {
        return 5;
    }

    /**
     * Determines whether supplied player can use this container
     */
    public boolean canInteractWith(PlayerEntity playerIn) {
        return this.furnaceInventory.isUsableByPlayer(playerIn);
    }

    /**
     * Handle when the stack in slot {@code index} is shift-clicked. Normally this moves the stack between the player
     * inventory and the other inventory(s).
     */
    public ItemStack transferStackInSlot(PlayerEntity playerIn, int index) {
        ItemStack itemstack = ItemStack.EMPTY;
        Slot slot = this.inventorySlots.get(index);
        if (slot != null && slot.getHasStack()) {
            ItemStack itemstack1 = slot.getStack();
            itemstack = itemstack1.copy();
            if (index == 3) {
                if (!this.mergeItemStack(itemstack1, 4, 40, true)) {
                    return ItemStack.EMPTY;
                }
                slot.onSlotChange(itemstack1, itemstack);
            } else if (index != 1 && index != 0 && index != 2) {
                if (this.hasRecipe(itemstack1)) {
                    if (!this.mergeItemStack(itemstack1, 0, 1, false)) {
                        return ItemStack.EMPTY;
                    }
                } else if (this.isFuel(itemstack1)) {
                    System.out.println(this.isFuel(itemstack1));
                    if (!this.mergeItemStack(itemstack1, 1, 2, false)) {
                        return ItemStack.EMPTY;
                    }
                }
                else if (this.isFuel(itemstack1)) {
                    if (!this.mergeItemStack(itemstack1, 2, 3, false)) {
                        return ItemStack.EMPTY;
                    }
                } else if (index >= 4 && index < 31) {
                    if (!this.mergeItemStack(itemstack1, 30, 39, false)) {
                        return ItemStack.EMPTY;
                    }
                } else if (index >= 31 && index < 40 && !this.mergeItemStack(itemstack1, 4, 31, false)) {
                    return ItemStack.EMPTY;
                }
            } else if (!this.mergeItemStack(itemstack1, 4, 40, false)) {
                return ItemStack.EMPTY;
            }

            if (itemstack1.isEmpty()) {
                slot.putStack(ItemStack.EMPTY);
            } else {
                slot.onSlotChanged();
            }

            if (itemstack1.getCount() == itemstack.getCount()) {
                return ItemStack.EMPTY;
            }

            slot.onTake(playerIn, itemstack1);
        }

        return itemstack;
    }

    protected boolean hasRecipe(ItemStack stack) {
        return this.world.getRecipeManager().getRecipe((IRecipeType<CokeingRecipe>)this.recipeType, new Inventory(stack), this.world).isPresent();
    }

    public boolean isFuel(ItemStack stack) {
        return AbstractCokeOvenTile.isFuel(stack);
    }

    @OnlyIn(Dist.CLIENT)
    public int getCookProgressionScaled() {
        int i = this.furnaceData.get(2);
        int j = this.furnaceData.get(3);
        return j != 0 && i != 0 ? i * 24 / j : 0;
    }

    @OnlyIn(Dist.CLIENT)
    public int getBurnLeftScaled() {
        int i = this.furnaceData.get(1);
        if (i == 0) {
            i = 200;
        }

        return this.furnaceData.get(0) * 13 / i;
    }

    @OnlyIn(Dist.CLIENT)
    public boolean isBurning() {
        return this.furnaceData.get(0) > 0;
    }

    private void layoutPlayerInventorySlots(int leftCol, int topRow) {
        // Player inventory
        addSlotBox(playerInventory, 9, leftCol, topRow, 9, 18, 3, 18);

        // Hotbar
        topRow += 58;
        addSlotRange(playerInventory, 0, leftCol, topRow, 9, 18);
    }

    private int addSlotRange(IItemHandler handler, int index, int x, int y, int amount, int dx) {
        for (int i = 0 ; i < amount ; i++) {
            addSlot(new SlotItemHandler(handler, index, x, y));
            x += dx;
            index++;
        }
        return index;
    }

    private int addSlotBox(IItemHandler handler, int index, int x, int y, int horAmount, int dx, int verAmount, int dy) {
        for (int j = 0 ; j < verAmount ; j++) {
            index = addSlotRange(handler, index, x, y, horAmount, dx);
            y += dy;
        }
        return index;
    }
}