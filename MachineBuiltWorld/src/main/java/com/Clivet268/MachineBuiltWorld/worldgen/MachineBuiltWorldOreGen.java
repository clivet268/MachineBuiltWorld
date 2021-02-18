package com.Clivet268.MachineBuiltWorld.worldgen;

import com.Clivet268.MachineBuiltWorld.util.RegistryHandler;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.biome.Biomes;
import net.minecraft.world.gen.GenerationStage;
import net.minecraft.world.gen.feature.Feature;
import net.minecraft.world.gen.feature.OreFeatureConfig;
import net.minecraft.world.gen.placement.ConfiguredPlacement;
import net.minecraft.world.gen.placement.CountRangeConfig;
import net.minecraft.world.gen.placement.Placement;
import net.minecraftforge.registries.ForgeRegistries;

public class MachineBuiltWorldOreGen {
    public static void generateOre(){

        for(Biome biome : ForgeRegistries.BIOMES)
        {
            if(biome == Biomes.DESERT || biome == Biomes.DESERT_HILLS || biome == Biomes.DESERT_LAKES || biome == Biomes.BADLANDS || biome == Biomes.BADLANDS_PLATEAU
                    || biome == Biomes.ERODED_BADLANDS || biome == Biomes.MODIFIED_BADLANDS_PLATEAU || biome == Biomes.MODIFIED_WOODED_BADLANDS_PLATEAU
                    || biome == Biomes.WOODED_BADLANDS_PLATEAU)
            {
                ConfiguredPlacement customConfig = Placement.COUNT_RANGE.configure(new CountRangeConfig(3, 5, 5,35));
                biome.addFeature(GenerationStage.Decoration.UNDERGROUND_ORES, Feature.ORE.withConfiguration(
                        new OreFeatureConfig(OreFeatureConfig.FillerBlockType.NATURAL_STONE,
                        RegistryHandler.BORON_ORE.get().getDefaultState(), 3)).withPlacement(customConfig));
            }
        }
        for(Biome biome : ForgeRegistries.BIOMES)
        {
            ConfiguredPlacement customConfig = Placement.COUNT_RANGE.configure(new CountRangeConfig(10, 3, 5,55));
                biome.addFeature(GenerationStage.Decoration.UNDERGROUND_ORES, Feature.ORE.withConfiguration(
                        new OreFeatureConfig(OreFeatureConfig.FillerBlockType.NATURAL_STONE,
                                RegistryHandler.COPPER_ORE.get().getDefaultState(), 6)).withPlacement(customConfig));
            }
        }
    }

