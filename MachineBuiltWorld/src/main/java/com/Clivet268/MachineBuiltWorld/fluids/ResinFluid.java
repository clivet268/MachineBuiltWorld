package com.Clivet268.MachineBuiltWorld.fluids;

import com.Clivet268.MachineBuiltWorld.MachineBuiltWorld;
import com.Clivet268.MachineBuiltWorld.util.RegistryHandler;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.FlowingFluidBlock;
import net.minecraft.fluid.FlowingFluid;
import net.minecraft.fluid.Fluid;
import net.minecraft.fluid.IFluidState;
import net.minecraft.item.Item;
import net.minecraft.particles.IParticleData;
import net.minecraft.particles.ParticleTypes;
import net.minecraft.state.StateContainer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.Direction;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.SoundEvents;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.IWorld;
import net.minecraft.world.IWorldReader;
import net.minecraft.world.World;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.fluids.FluidAttributes;

import javax.annotation.Nullable;
import java.util.Random;

public abstract class ResinFluid extends FlowingFluid {
        public Fluid getFlowingFluid() {
            return RegistryHandler.RESIN_FLOWING.get();
        }

        public Fluid getStillFluid() {
            return RegistryHandler.RESIN.get();
        }

        public Item getFilledBucket() {
            return RegistryHandler.RESIN_BUCKET.get();
        }

        @OnlyIn(Dist.CLIENT)
        public void animateTick(World worldIn, BlockPos pos, IFluidState state, Random random) {
            if (!state.isSource() && !state.get(FALLING)) {
                if (random.nextInt(64) == 0) {
                    worldIn.playSound((double)pos.getX() + 0.5D, (double)pos.getY() + 0.5D, (double)pos.getZ() + 0.5D, SoundEvents.BLOCK_WATER_AMBIENT, SoundCategory.BLOCKS, random.nextFloat() * 0.25F + 0.75F, random.nextFloat() + 0.5F, false);
                }
            } else if (random.nextInt(10) == 0) {
                worldIn.addParticle(ParticleTypes.UNDERWATER, (double)pos.getX() + (double)random.nextFloat(), (double)pos.getY() + (double)random.nextFloat(), (double)pos.getZ() + (double)random.nextFloat(), 0.0D, 0.0D, 0.0D);
            }

        }

        @Nullable
        @OnlyIn(Dist.CLIENT)
        public IParticleData getDripParticleData() {
            return ParticleTypes.DRIPPING_WATER;
        }

        protected boolean canSourcesMultiply() {
            return false;
        }

        protected void beforeReplacingBlock(IWorld worldIn, BlockPos pos, BlockState state) {
            TileEntity tileentity = state.getBlock().hasTileEntity() ? worldIn.getTileEntity(pos) : null;
            Block.spawnDrops(state, worldIn.getWorld(), pos, tileentity);
        }

        public int getSlopeFindDistance(IWorldReader worldIn) {
            return 4;
        }

        public BlockState getBlockState(IFluidState state) {
            return RegistryHandler.RESIN_BLOCK.get().getDefaultState().with(FlowingFluidBlock.LEVEL, Integer.valueOf(getLevelFromState(state)));
        }

        public boolean isEquivalentTo(Fluid fluidIn) {

            return fluidIn == RegistryHandler.RESIN.get() || fluidIn == RegistryHandler.RESIN_FLOWING.get();
        }

        public int getLevelDecreasePerBlock(IWorldReader worldIn) {
            return 1;
        }

        public int getTickRate(IWorldReader tickRate) {
            return 5;
        }

        public boolean canDisplace(IFluidState fluidState, IBlockReader reader, BlockPos pos, Fluid fluid, Direction direction) {
            return direction == Direction.DOWN;
        }

        protected float getExplosionResistance() {
            return 100.0F;
        }

        public static class Flowing extends ResinFluid {
            protected void fillStateContainer(StateContainer.Builder<Fluid, IFluidState> builder) {
                super.fillStateContainer(builder);
                builder.add(LEVEL_1_8);
            }

            public int getLevel(IFluidState fluidState) {
                return fluidState.get(LEVEL_1_8);
            }

            public boolean isSource(IFluidState state) {
                return false;
            }
        }

        public static class Source extends ResinFluid {
            public int getLevel(IFluidState fluidState) {
                return 8;
            }

            public boolean isSource(IFluidState state) {
                return true;
            }
        }
        @Override
        protected FluidAttributes createAttributes() {
        return FluidAttributes.builder(new ResourceLocation(MachineBuiltWorld.MOD_ID, "block/resin"), new ResourceLocation(MachineBuiltWorld.MOD_ID, "block/resin_flowing"))
                .translationKey("block.machinebuiltworld.resin")
                .build(this);

    }
}

