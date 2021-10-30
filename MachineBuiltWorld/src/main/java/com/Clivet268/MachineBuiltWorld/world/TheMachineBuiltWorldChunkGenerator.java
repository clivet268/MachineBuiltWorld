package com.Clivet268.MachineBuiltWorld.world;

import net.minecraft.world.IWorld;
import net.minecraft.world.biome.provider.BiomeProvider;
import net.minecraft.world.gen.NoiseChunkGenerator;

import javax.annotation.Nonnull;

public class TheMachineBuiltWorldChunkGenerator extends NoiseChunkGenerator<TheMachineBuiltWorldGenSettings> {

    private final double[] field_222573_h = this.terrainMaker();

    public TheMachineBuiltWorldChunkGenerator(IWorld world, BiomeProvider provider) {
        super(world, provider, 4, 8, 128,new TheMachineBuiltWorldGenSettings(), false);
    }

    @Override
    protected void fillNoiseColumn(@Nonnull double[] noiseColumn, int noiseX, int noiseZ) {
        double d0 = 684.412D;
        double d1 = 2053.236D;
        double d2 = 8.555150000000001D;
        double d3 = 34.2206D;
        int i = -10;
        int j = 3;
        this.calcNoiseColumn(noiseColumn, noiseX, noiseZ, 684.412D, 2053.236D, 8.555150000000001D, 34.2206D, 3, -10);
    }

    @Override
    @Nonnull
    protected double[] getBiomeNoiseColumn(int noiseX, int noiseZ) {
        return new double[]{0.0D, 0.0D};
    }

    @Override
    protected double func_222545_a(double p_222545_1_, double p_222545_3_, int p_222545_5_) {
        return this.field_222573_h[p_222545_5_];
    }


    private double[] terrainMaker() {
        double[] adouble = new double[this.noiseSizeY()];

        for(int i = 0; i < this.noiseSizeY(); ++i) {
            adouble[i] = Math.cos((double)i * Math.PI * 6.0D / (double)this.noiseSizeY()) * 2.0D;
            double d0 = (double)i;
            if (i > this.noiseSizeY() / 2) {
                d0 = (double)(this.noiseSizeY() - 1 - i);
            }

            if (d0 < 4.0D) {
                d0 = 4.0D - d0;
                adouble[i] -= d0 * d0 * d0 * 10.0D;
            }
        }

        return adouble;
    }

    public int getGroundHeight() {
        return this.world.getSeaLevel() + 1;
    }

    public int getMaxHeight() {
        return 300;
    }

    public int getSeaLevel() {
        return 16;
    }
}