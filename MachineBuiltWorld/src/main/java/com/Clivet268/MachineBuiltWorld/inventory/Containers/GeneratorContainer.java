package com.Clivet268.MachineBuiltWorld.inventory.Containers;

import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.IInventory;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class GeneratorContainer extends AbstractGeneratorContainer{
    public GeneratorContainer(int windowId, World world, BlockPos pos, PlayerInventory playerInventory) {
        super(world, windowId, playerInventory, pos);
    }

    public GeneratorContainer(int windowId, World world, BlockPos pos, PlayerInventory pplayerInventory, IInventory iInventory) {
        super(windowId, world, pos, pplayerInventory, iInventory);
        System.out.println(world);
    }
}
