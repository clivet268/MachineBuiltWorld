package com.Clivet268.MachineBuiltWorld.inventory.slots;

import com.Clivet268.MachineBuiltWorld.tileentity.CrusherTile;
import com.Clivet268.MachineBuiltWorld.util.RegistryHandler;
import net.minecraft.inventory.container.Slot;
import net.minecraft.item.ItemStack;

import javax.annotation.Nonnull;

public class CrusherTeethSlot extends Slot {

    public CrusherTeethSlot(CrusherTile crusherTile, int index, int x, int y) {
        super(crusherTile, index, x, y);
    }

    /**
     * Check if the stack is allowed to be placed in this slot, used for armor slots as well as furnace fuel.
     */
    @Override
    public boolean isItemValid(ItemStack stack) {
        return stack.getItem().isIn(RegistryHandler.Tags.CRUSHER_TEETH);
    }

    public int getItemStackLimit(@Nonnull ItemStack stack) {
        return 1;
    }

}
