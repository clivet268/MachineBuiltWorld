package com.Clivet268.MachineBuiltWorld.inventory.Containers;

import com.Clivet268.MachineBuiltWorld.inventory.crafting.IMoreRecipeType;
import com.Clivet268.MachineBuiltWorld.util.RegistryHandler;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.IInventory;
import net.minecraft.util.IIntArray;
import net.minecraft.util.IWorldPosCallable;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import java.util.Objects;

public class IntensiveHeatingOvenContainer extends AbstractIntensiveHeatingOvenContainer {
    public IntensiveHeatingOvenContainer(int windowId, World world, PlayerInventory playerInventory, BlockPos pos) {
        super(RegistryHandler.INTENSIVE_HEATING_OVEN_CONTAINER.get(), world, IMoreRecipeType.COKEING, windowId, playerInventory, pos);
    }

    public IntensiveHeatingOvenContainer(int windowId, World world, PlayerInventory pplayerInventory, IInventory iInventory, IIntArray iIntArray, BlockPos pos) {
        super(RegistryHandler.INTENSIVE_HEATING_OVEN_CONTAINER.get(), world, IMoreRecipeType.COKEING, windowId, pplayerInventory, iInventory, iIntArray, pos);

    }
    @Override
    public boolean canInteractWith(PlayerEntity playerIn) {
        return isWithinUsableDistance(IWorldPosCallable.of(Objects.requireNonNull(tileEntity.getWorld()), tileEntity.getPos()), playerIn, RegistryHandler.INTENSIVE_HEATING_OVEN.get());

    }
}