package com.Clivet268.MachineBuiltWorld.world.biome;

import com.Clivet268.MachineBuiltWorld.util.RegistryHandler;
import com.mojang.datafixers.Dynamic;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.chunk.IChunk;
import net.minecraft.world.gen.surfacebuilders.SurfaceBuilder;
import net.minecraft.world.gen.surfacebuilders.SurfaceBuilderConfig;

import java.util.Random;
import java.util.function.Function;

public class TheMachineBuiltWorldBarrenLandsSurfaceBuilder extends SurfaceBuilder<SurfaceBuilderConfig> {

    public TheMachineBuiltWorldBarrenLandsSurfaceBuilder(Function<Dynamic<?>, ? extends SurfaceBuilderConfig> function) {
        super(function);
    }

    @Override
    public void buildSurface(Random random, IChunk chunkIn, Biome biomeIn, int x, int z, int startHeight, double noise,
                             BlockState defaultBlock, BlockState defaultFluid, int seaLevel, long seed, SurfaceBuilderConfig config) {
        Random rd = new Random();
        int i = rd.nextInt(30);
        int ii = rd.nextInt(300);
        if (i == 0) {
            SurfaceBuilder.DEFAULT.buildSurface(random, chunkIn, biomeIn, x, z, startHeight, noise, defaultBlock,
                    defaultFluid, seaLevel, seed,
                    new SurfaceBuilderConfig(RegistryHandler.IRON_OXIDE.get().getDefaultState(),
                            Blocks.DIRT.getDefaultState(), Blocks.ACACIA_PLANKS.getDefaultState()));
        } else {
            SurfaceBuilder.DEFAULT.buildSurface(random, chunkIn, biomeIn, x, z, startHeight, noise, defaultBlock,
                    defaultFluid, seaLevel, seed,
                    new SurfaceBuilderConfig(
                            i == 1 ? Blocks.GRASS_BLOCK.getDefaultState()
                                    : Blocks.COARSE_DIRT.getDefaultState(),
                            Blocks.DIRT.getDefaultState(), Blocks.ACACIA_PLANKS.getDefaultState()));
        }
    }
}