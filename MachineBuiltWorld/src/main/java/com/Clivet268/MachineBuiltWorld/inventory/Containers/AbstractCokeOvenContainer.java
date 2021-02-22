package com.Clivet268.MachineBuiltWorld.inventory.Containers;

import com.Clivet268.MachineBuiltWorld.inventory.crafting.CokeOvenFuelSlot;
import com.Clivet268.MachineBuiltWorld.inventory.crafting.CokeOvenResultSlot;
import com.Clivet268.MachineBuiltWorld.tileentity.AbstractCokeOvenTile;
import com.Clivet268.MachineBuiltWorld.tileentity.CokeOvenTile;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.Inventory;
import net.minecraft.inventory.container.*;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.*;
import net.minecraft.util.IIntArray;
import net.minecraft.util.IntArray;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.SlotItemHandler;
import net.minecraftforge.items.wrapper.InvWrapper;

import javax.annotation.Nonnull;

//import static com.Clivet268.MachineBuiltWorld.util.RegistryHandler.COKE_OVEN_CONTAINER;

public abstract class AbstractCokeOvenContainer extends Container {
    public final IIntArray furnaceData;
    public final World world;
    public IItemHandler playerInventory;
    public AbstractCokeOvenTile tileEntity;
    public PlayerEntity playerEntity;

    private final IRecipeType<? extends AbstractCookingRecipe> recipeType;

    public AbstractCokeOvenContainer(ContainerType<?> containerTypeIn, IRecipeType<? extends AbstractCookingRecipe> recipeTypeIn, World world, int windowId, PlayerInventory playerInventoryIn, BlockPos pos) {
        this(containerTypeIn, recipeTypeIn, world, windowId, playerInventoryIn, new IntArray(5),pos);
    }

    protected AbstractCokeOvenContainer(ContainerType<?> containerTypeIn, IRecipeType<? extends AbstractCookingRecipe> recipeTypeIn, World world, int windowId, PlayerInventory playerInventoryIn, IIntArray furnaceDataIn, BlockPos pos) {
        super(containerTypeIn, windowId);
        this.world = playerInventoryIn.player.world.getWorld();
        this.tileEntity = (AbstractCokeOvenTile)world.getTileEntity(pos);
        this.playerEntity = playerInventoryIn.player;
        this.playerInventory = new InvWrapper(playerInventoryIn);
        this.recipeType = recipeTypeIn;
        System.out.println(furnaceDataIn.size());
        assertIntArraySize(furnaceDataIn, 4);
        this.furnaceData = furnaceDataIn;

        if (tileEntity != null) {
            addSlot(new Slot(tileEntity, 0, 56, 17));
            addSlot(new CokeOvenFuelSlot(tileEntity, 1, 38, 53));
            addSlot(new CokeOvenFuelSlot(tileEntity, 2, 74, 53));
            addSlot(new CokeOvenResultSlot(playerInventoryIn.player, tileEntity, 3, 116, 35));
            layoutPlayerInventorySlots(8, 84);
        }
        this.trackIntArray(furnaceDataIn);
    }

    /**
     * Handle when the stack in slot {@code index} is shift-clicked. Normally this moves the stack between the player
     * inventory and the other inventory(s).
     */
    public ItemStack transferStackInSlot(@Nonnull PlayerEntity playerIn, int index) {
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
                } else if (isFuel(itemstack1)) {
                    if (this.mergeItemStack(itemstack1, 1, 2, false)) {
                        return itemstack1;
                    }
                    else{
                        return ItemStack.EMPTY;
                    }
                }
                else if (isFuel(itemstack1)) {
                    if (this.mergeItemStack(itemstack1, 2, 3, false)) {
                        return itemstack1;
                    }
                    else{
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

    public static boolean isFuel(ItemStack stack) {
        return CokeOvenTile.isFuel(stack);
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

    protected boolean hasRecipe(ItemStack item) {
        return this.world.getRecipeManager().getRecipe(IRecipeType.SMELTING, new Inventory(item), this.world).isPresent();
    }
}