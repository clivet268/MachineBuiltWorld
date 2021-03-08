package com.Clivet268.MachineBuiltWorld.inventory.crafting;

import com.Clivet268.MachineBuiltWorld.items.IHeatInfuseable;
import com.Clivet268.MachineBuiltWorld.tileentity.IntensiveHeatingOvenTile;
import net.minecraft.inventory.container.Slot;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;

import javax.annotation.Nonnull;

public class CokeOvenInfuseSlot extends Slot implements IHeatInfuseable {

    public CokeOvenInfuseSlot(IntensiveHeatingOvenTile intensiveHeatingOvenTile1, int index, int x, int y) {
        super(intensiveHeatingOvenTile1, index, x, y);
    }

    /**
     * Check if the stack is allowed to be placed in this slot, used for armor slots as well as furnace fuel.
     */
    @Override
    public boolean isItemValid(ItemStack stack) {
        return canDoSomeInfusin(stack.getItem());
    }

    public int getItemStackLimit(@Nonnull ItemStack stack) {
        return isBucket(stack) ? 1 : super.getItemStackLimit(stack);
    }

    public static boolean isBucket(ItemStack stack) {
        return stack.getItem() == Items.BUCKET;
    }
}
