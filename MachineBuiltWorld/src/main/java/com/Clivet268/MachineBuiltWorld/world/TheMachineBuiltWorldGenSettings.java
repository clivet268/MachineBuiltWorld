package com.Clivet268.MachineBuiltWorld.world;

import com.Clivet268.MachineBuiltWorld.world.TheMachineBuiltWorldChunkGenerator;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.world.World;
import net.minecraft.world.gen.ChunkGenerator;
import net.minecraft.world.gen.GenerationSettings;
import net.minecraft.world.server.ServerWorld;

import javax.annotation.Nullable;

public class TheMachineBuiltWorldGenSettings extends GenerationSettings {

    public int getBedrockFloorHeight() {
        return 0;
    }
}
