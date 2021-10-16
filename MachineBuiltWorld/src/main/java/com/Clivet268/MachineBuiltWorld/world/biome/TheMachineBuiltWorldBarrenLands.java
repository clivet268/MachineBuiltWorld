package com.Clivet268.MachineBuiltWorld.world.biome;

import com.Clivet268.MachineBuiltWorld.world.StrippedWorldCarver;
import net.minecraft.entity.EntityClassification;
import net.minecraft.entity.EntityType;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.biome.DefaultBiomeFeatures;
import net.minecraft.world.gen.GenerationStage;
import net.minecraft.world.gen.carver.WorldCarver;
import net.minecraft.world.gen.feature.Feature;
import net.minecraft.world.gen.feature.ProbabilityConfig;
import net.minecraft.world.gen.feature.TwoFeatureChoiceConfig;
import net.minecraft.world.gen.placement.FrequencyConfig;
import net.minecraft.world.gen.placement.HeightWithChanceConfig;
import net.minecraft.world.gen.placement.Placement;

//TODO make sure does not gen in overworld
public class TheMachineBuiltWorldBarrenLands extends Biome {

        public TheMachineBuiltWorldBarrenLands(Builder biomeBuilder) {
            super(biomeBuilder);/*
            this.addSpawn(EntityClassification.MONSTER, new Biome.SpawnListEntry(EntityType.ZOMBIE, 10, 2, 5));
            this.addSpawn(EntityClassification.CREATURE, new SpawnListEntry(EntityType.BEE, 20, 2, 10));
            */
            /*this.addCarver(GenerationStage.Carving.AIR,
                    Biome.createCarver(WorldCarver.CAVE, new ProbabilityConfig(0.14285715F)));

             */
            this.addCarver(GenerationStage.Carving.AIR,
                    Biome.createCarver(StrippedWorldCarver.STRIPS, new ProbabilityConfig(0.4F)));


            // this.addFeature(GenerationStage.Decoration.VEGETAL_DECORATION,
            // Feature.NORMAL_TREE.withConfiguration(JazzTree.JAZZ_TREE_CONFIG).withPlacement(
            // Placement.COUNT_EXTRA_HEIGHTMAP.configure(new AtSurfaceWithExtraConfig(7,
            // 0.1f, 1))));

            MoreBiomeFeatures.addOresSparse(this);
        }

        @Override
        public int getGrassColor(double posX, double posZ) {
            return 0x664C17;
        }

        @Override
        public int getFoliageColor() {
            return 0x66AC17;
        }
    }


