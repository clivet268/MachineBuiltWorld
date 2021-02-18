package com.Clivet268.MachineBuiltWorld.fluids;

import net.minecraft.block.BlockState;
import net.minecraft.fluid.FlowingFluid;
import net.minecraft.fluid.Fluid;
import net.minecraft.fluid.IFluidState;
import net.minecraft.item.Item;
import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.IWorld;
import net.minecraft.world.IWorldReader;

import static com.Clivet268.MachineBuiltWorld.util.RegistryHandler.BATTERY_ACID_FLOWING;
import static com.Clivet268.MachineBuiltWorld.util.RegistryHandler.BATTERY_ACID_FLUID;

public abstract class BatteryAcidFluid extends FlowingFluid {

    //maybe change

    @Override
    public Fluid getFlowingFluid() {
        return BATTERY_ACID_FLOWING.get();

    }

    @Override
    public Fluid getStillFluid() {
        return BATTERY_ACID_FLUID.get();
    }

    @Override
    protected boolean canSourcesMultiply() {
        return false;
    }

    @Override
    protected void beforeReplacingBlock(IWorld worldIn, BlockPos pos, BlockState state) {

    }

    @Override
    protected int getSlopeFindDistance(IWorldReader worldIn) {
        return 4;
    }

    @Override
    protected int getLevelDecreasePerBlock(IWorldReader worldIn) {
        return 12;
    }

    @Override
    public Item getFilledBucket() {
        return null;
    }

    @Override
    protected boolean canDisplace(IFluidState p_215665_1_, IBlockReader p_215665_2_, BlockPos p_215665_3_, Fluid p_215665_4_, Direction p_215665_5_) {
        return false;
    }

    @Override
    public int getTickRate(IWorldReader p_205569_1_) {
        return 0;
    }

    @Override
    protected float getExplosionResistance() {
        return 0;
    }

    @Override
    protected BlockState getBlockState(IFluidState state) {
        return null;
    }

}
