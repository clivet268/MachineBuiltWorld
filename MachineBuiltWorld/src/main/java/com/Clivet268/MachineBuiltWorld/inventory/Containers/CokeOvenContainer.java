package com.Clivet268.MachineBuiltWorld.inventory.Containers;

import com.Clivet268.MachineBuiltWorld.inventory.crafting.IMoreRecipeType;
import com.Clivet268.MachineBuiltWorld.util.RegistryHandler;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.container.AbstractFurnaceContainer;
import net.minecraft.inventory.container.ContainerType;
import net.minecraft.item.crafting.IRecipeType;
import net.minecraft.util.IIntArray;
import net.minecraft.util.math.BlockPos;

public class CokeOvenContainer extends CokeOvenContainerBase {
    public CokeOvenContainer(int p_i50097_1_, PlayerInventory playerInventory, BlockPos pos) {
        super(RegistryHandler.COKE_OVEN_CONTAINER.get(), IMoreRecipeType.COKEING, p_i50097_1_, playerInventory, pos);
    }

    public CokeOvenContainer(int p_i50098_1_, PlayerInventory playerInventory, IInventory iInventory, IIntArray iIntArray, BlockPos pos) {
        super(RegistryHandler.COKE_OVEN_CONTAINER.get(), IMoreRecipeType.COKEING, p_i50098_1_, playerInventory, iInventory, iIntArray, pos);
    }
}