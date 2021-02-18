package com.Clivet268.MachineBuiltWorld.blocks;

import net.minecraft.block.BlockState;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.server.ServerWorld;

import java.util.Random;

public interface IWorkable {

    boolean canWork(IBlockReader worldIn, BlockPos pos, BlockState state, boolean isClient);

    //boolean canUseBonemeal(World worldIn, Random rand, BlockPos pos, BlockState state);

    void work(ServerWorld worldIn, Random rand, BlockPos pos, BlockState state);
}
