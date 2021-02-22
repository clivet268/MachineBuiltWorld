package com.Clivet268.MachineBuiltWorld.inventory.Containers;

import com.Clivet268.MachineBuiltWorld.inventory.crafting.IMoreRecipeType;
import com.Clivet268.MachineBuiltWorld.util.RegistryHandler;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.container.AbstractFurnaceContainer;
import net.minecraft.inventory.container.ContainerType;
import net.minecraft.item.crafting.IRecipeType;
import net.minecraft.util.IIntArray;
import net.minecraft.util.IWorldPosCallable;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import java.util.Objects;

public class CokeOvenContainer extends AbstractCokeOvenContainer {
    public CokeOvenContainer(int windowId, World world, PlayerInventory playerInventoryIn, BlockPos pos) {
        super(RegistryHandler.COKE_OVEN_CONTAINER.get(), IMoreRecipeType.COKEING, world, windowId, playerInventoryIn, pos);
    }

    public CokeOvenContainer(World world, int windowId, PlayerInventory playerInventoryIn, IIntArray furnaceDataIn, BlockPos pos) {
        super(RegistryHandler.COKE_OVEN_CONTAINER.get(), IMoreRecipeType.COKEING, world, windowId, playerInventoryIn, furnaceDataIn, pos);
    }

    @Override
    public boolean canInteractWith(PlayerEntity playerIn) {
        return isWithinUsableDistance(IWorldPosCallable.of(Objects.requireNonNull(tileEntity.getWorld()), tileEntity.getPos()), playerEntity, RegistryHandler.COKE_OVEN.get());

    }


}