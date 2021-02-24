package com.Clivet268.MachineBuiltWorld.inventory.crafting;

import com.Clivet268.MachineBuiltWorld.inventory.Containers.CokeOvenContainer;
import com.Clivet268.MachineBuiltWorld.inventory.Containers.CokeOvenContainerBase;
import com.Clivet268.MachineBuiltWorld.tileentity.CokeOvenTile;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.container.AbstractFurnaceContainer;
import net.minecraft.inventory.container.Slot;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.SlotItemHandler;

import javax.annotation.Nonnull;

public class CokeOvenFuelSlot extends Slot {
    private final CokeOvenTile cokeOvenTile;

    public CokeOvenFuelSlot(CokeOvenTile cokeOvenTile1, int index, int x, int y) {
        super(cokeOvenTile1, index, x, y);
        this.cokeOvenTile = cokeOvenTile1;
    }

    /**
     * Check if the stack is allowed to be placed in this slot, used for armor slots as well as furnace fuel.
     */
    @Override
    public boolean isItemValid(ItemStack stack) {
        return this.cokeOvenTile.isFuel(stack) || isBucket(stack);
    }

    public int getItemStackLimit(@Nonnull ItemStack stack) {
        return isBucket(stack) ? 1 : super.getItemStackLimit(stack);
    }

    public static boolean isBucket(ItemStack stack) {
        return stack.getItem() == Items.BUCKET;
    }
}
