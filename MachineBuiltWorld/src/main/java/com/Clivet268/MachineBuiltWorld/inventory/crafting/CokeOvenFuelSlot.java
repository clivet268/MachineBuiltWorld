package com.Clivet268.MachineBuiltWorld.inventory.crafting;

import com.Clivet268.MachineBuiltWorld.inventory.Containers.CokeOvenContainer;
import com.Clivet268.MachineBuiltWorld.inventory.Containers.CokeOvenContainerBase;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.container.AbstractFurnaceContainer;
import net.minecraft.inventory.container.Slot;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;

public class CokeOvenFuelSlot extends Slot {
    private final CokeOvenContainerBase cockContainerBase;

    public CokeOvenFuelSlot(CokeOvenContainerBase cokeOvenContainer, IInventory inventory, int index, int x, int y) {
        super(inventory, index, x, y);
        this.cockContainerBase = cokeOvenContainer;
    }

    /**
     * Check if the stack is allowed to be placed in this slot, used for armor slots as well as furnace fuel.
     */
    public boolean isItemValid(ItemStack stack) {
        return this.cockContainerBase.isFuel(stack) || isBucket(stack);
    }

    public int getItemStackLimit(ItemStack stack) {
        return isBucket(stack) ? 1 : super.getItemStackLimit(stack);
    }

    public static boolean isBucket(ItemStack stack) {
        return stack.getItem() == Items.BUCKET;
    }
}
