package com.Clivet268.MachineBuiltWorld.inventory.Containers;

import com.Clivet268.MachineBuiltWorld.util.RegistryHandler;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.IInventory;
import net.minecraft.util.IWorldPosCallable;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import java.util.Objects;

public class PressurizedGasContainerContainer extends AbstractPressurizedGasContainerContainer {
    public PressurizedGasContainerContainer(int windowId, World world, PlayerInventory playerInventory, BlockPos pos) {
        super(world, windowId, playerInventory, pos);
    }

    public PressurizedGasContainerContainer(int windowId, World world, PlayerInventory pplayerInventory, IInventory iInventory, BlockPos pos) {
        super(windowId, world, pos, pplayerInventory, iInventory);
    }
    @Override
    public boolean canInteractWith(PlayerEntity playerIn) {
        return isWithinUsableDistance(IWorldPosCallable.of(Objects.requireNonNull(tileEntity.getWorld()), tileEntity.getPos()), playerIn, RegistryHandler.PRESSURIZED_GAS_CONTAINER.get());

    }
}