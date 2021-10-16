package com.Clivet268.MachineBuiltWorld.world;

import net.minecraft.world.WorldType;
import net.minecraft.world.biome.provider.IBiomeProviderSettings;
import net.minecraft.world.storage.WorldInfo;

public class TheMachineBuiltWorldBiomeProviderSettings implements IBiomeProviderSettings {

    private final long seed;
    private final WorldType worldType;
    private TheMachineBuiltWorldGenSettings generatorSettings = new TheMachineBuiltWorldGenSettings();

    public TheMachineBuiltWorldBiomeProviderSettings(WorldInfo info) {
        this.seed = info.getSeed();
        this.worldType = info.getGenerator();
    }

    public TheMachineBuiltWorldBiomeProviderSettings setGeneratorSettings(TheMachineBuiltWorldGenSettings settings) {
        this.generatorSettings = settings;
        return this;
    }

    public long getSeed() {
        return this.seed;
    }

    public WorldType getWorldType() {
        return this.worldType;
    }

    public TheMachineBuiltWorldGenSettings getGeneratorSettings() {
        return this.generatorSettings;
    }
}