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

public class MixerContainer extends AbstractMixerContainer {
    public MixerContainer(int windowId, World world, PlayerInventory playerInventory, BlockPos pos) {
        super(RegistryHandler.MIXER_CONTAINER.get(), world, IMoreRecipeType.MoreMixings.MIXING, windowId, playerInventory, pos);
    }

    public MixerContainer(int windowId, World world, PlayerInventory pplayerInventory, IInventory iInventory, IIntArray iIntArray, BlockPos pos) {
        super(RegistryHandler.MIXER_CONTAINER.get(), world, IMoreRecipeType.MoreMixings.MIXING, windowId, pplayerInventory, iInventory, iIntArray, pos);
    }
    @Override
    public boolean canInteractWith(PlayerEntity playerIn) {
        return isWithinUsableDistance(IWorldPosCallable.of(Objects.requireNonNull(tileEntity.getWorld()), tileEntity.getPos()), playerIn, RegistryHandler.MIXER.get());

    }
}