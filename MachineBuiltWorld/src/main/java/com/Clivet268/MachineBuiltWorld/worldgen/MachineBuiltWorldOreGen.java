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
                ConfiguredPlacement ccb = Placement.COUNT_RANGE.configure(new CountRangeConfig(3, 5, 5,35));
                biome.addFeature(GenerationStage.Decoration.UNDERGROUND_ORES, Feature.ORE.withConfiguration(
                        new OreFeatureConfig(OreFeatureConfig.FillerBlockType.NATURAL_STONE,
                        RegistryHandler.BORON_ORE.get().getDefaultState(), 3)).withPlacement(ccb));
            }
        }
        for(Biome biome : ForgeRegistries.BIOMES)
        {
            ConfiguredPlacement ccc = Placement.COUNT_RANGE.configure(new CountRangeConfig(10, 3, 5,55));
                biome.addFeature(GenerationStage.Decoration.UNDERGROUND_ORES, Feature.ORE.withConfiguration(
                        new OreFeatureConfig(OreFeatureConfig.FillerBlockType.NATURAL_STONE,
                                RegistryHandler.COPPER_ORE.get().getDefaultState(), 6)).withPlacement(ccc));
            ConfiguredPlacement ccb1 = Placement.COUNT_RANGE.configure(new CountRangeConfig(10, 3, 5,55));
            biome.addFeature(GenerationStage.Decoration.UNDERGROUND_ORES, Feature.ORE.withConfiguration(
                    new OreFeatureConfig(OreFeatureConfig.FillerBlockType.NATURAL_STONE,
                            RegistryHandler.BAUXITE_ORE.get().getDefaultState(), 6)).withPlacement(ccb1));
            ConfiguredPlacement ccg = Placement.COUNT_RANGE.configure(new CountRangeConfig(1, 3, 1,10));
            biome.addFeature(GenerationStage.Decoration.UNDERGROUND_ORES, Feature.ORE.withConfiguration(
                    new OreFeatureConfig(OreFeatureConfig.FillerBlockType.NATURAL_STONE,
                            RegistryHandler.GARNET_ORE.get().getDefaultState(), 1)).withPlacement(ccg));
            ConfiguredPlacement cce = Placement.COUNT_RANGE.configure(new CountRangeConfig(1, 3, 1,255));
            biome.addFeature(GenerationStage.Decoration.UNDERGROUND_ORES, Feature.ORE.withConfiguration(
                    new OreFeatureConfig(OreFeatureConfig.FillerBlockType.NATURAL_STONE,
                            RegistryHandler.ERBIUM_ORE.get().getDefaultState(), 1)).withPlacement(cce));
        }
        }
    }

