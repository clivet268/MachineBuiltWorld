package com.Clivet268.MachineBuiltWorld.inventory.crafting;


import com.Clivet268.MachineBuiltWorld.tileentity.AbstractDisassociatedAtomContainerTile;
import net.minecraft.inventory.container.Slot;
import net.minecraft.item.ItemStack;

import javax.annotation.Nonnull;

public class DACslot extends Slot {

    public DACslot(AbstractDisassociatedAtomContainerTile intensiveHeatingOvenTile1, int index, int x, int y) {
        super(intensiveHeatingOvenTile1, index, x, y);
    }

    /**
     * Check if the stack is allowed to be placed in this slot, used for armor slots as well as furnace fuel.
     */
    @Override
    public boolean isItemValid(ItemStack stack) {
        return true;
    }

    public int getItemStackLimit(@Nonnull ItemStack stack) {
        return 1000;
    }

}
