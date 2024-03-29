package com.Clivet268.MachineBuiltWorld.world;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.ChunkPos;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import net.minecraft.world.dimension.Dimension;
import net.minecraft.world.dimension.DimensionType;
import net.minecraft.world.gen.ChunkGenerator;


//TODO this HAS to be a thing before 1.0.5 is released that way in 2.0 we can redo it with a framework
// and again in 3.0 and again in 4.0
// version updates are going to be fun
//TODO biomes
//TODO chunk gen with right noise
//TODO structures
//TODO how boss is going to work with world
//TODO the way you get in, the machining something, the machiner, idk something along the lines of that
public class TheMachineBuiltWorldDimension extends Dimension {
   public TheMachineBuiltWorldDimension(World world, DimensionType type) {
      super(world, type, 0.0f);
   }

   @Override
   public ChunkGenerator<?> createChunkGenerator() {
      return new TheMachineBuiltWorldChunkGenerator(world,
              new TheMachineBuiltWorldBiomeProvider(new TheMachineBuiltWorldBiomeProviderSettings(this.world.getWorldInfo())));
   }

   @Override
   public BlockPos findSpawn(ChunkPos chunkPosIn, boolean checkValid) {
      return null;
   }

   @Override
   public BlockPos findSpawn(int posX, int posZ, boolean checkValid) {
      return null;
   }

   @Override
   public float calculateCelestialAngle(long worldTime, float partialTicks) {
      int j = 6000;
      float f1 = (j + partialTicks) / 24000.0f - 0.25f;
      if (f1 < 0.0f) {
         f1 += 1.0f;
      }

      if (f1 > 1.0f) {
         f1 -= 1.0f;
      }

      float f2 = f1;
      f1 = 1.0f - (float) ((Math.cos(f1 * Math.PI) + 1.0d) / 2.0d);
      f1 = f2 + (f1 - f2) / 3.0f;
      return f1;
   }

   @Override
   public boolean isSurfaceWorld() {
      return false;
   }

   @Override
   public Vec3d getFogColor(float celestialAngle, float partialTicks) {
      return Vec3d.ZERO;
   }

   @Override
   public boolean canRespawnHere() {
      return true;
   }

   @Override
   public boolean doesXZShowFog(int x, int z) {
      return false;
   }

   @Override
   public SleepResult canSleepAt(PlayerEntity player, BlockPos pos) {
      return SleepResult.ALLOW;
   }

   @Override
   public boolean hasSkyLight() {
      return true;
   }

   @Override
   public int getActualHeight() {
      return 256;
   }
}