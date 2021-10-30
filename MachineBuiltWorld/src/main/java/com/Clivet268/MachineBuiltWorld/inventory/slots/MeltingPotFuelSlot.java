package com.Clivet268.MachineBuiltWorld.inventory.slots;

import com.Clivet268.MachineBuiltWorld.tileentity.MeltingPotTile;
import net.minecraft.inventory.container.Slot;
import net.minecraft.item.ItemStack;
import net.minecraftforge.common.ForgeHooks;

import javax.annotation.Nonnull;

public class MeltingPotFuelSlot extends Slot {

    public MeltingPotFuelSlot(MeltingPotTile meltingPotTile, int index, int x, int y) {
        super(meltingPotTile, index, x, y);
    }

    /**
     * Check if the stack is allowed to be placed in this slot, used for armor slots as well as furnace fuel.
     */
    @Override
    public boolean isItemValid(ItemStack stack) {
        return ForgeHooks.getBurnTime(stack) > 0;
    }

    public int getItemStackLimit(@Nonnull ItemStack stack) {
        return super.getItemStackLimit(stack);
    }

}
