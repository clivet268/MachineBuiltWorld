package com.Clivet268.MachineBuiltWorld.worldgen;

import com.google.common.collect.ImmutableMap;
import com.mojang.datafixers.Dynamic;
import com.mojang.datafixers.types.DynamicOps;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.pattern.BlockMatcher;
import net.minecraft.world.gen.feature.OreFeatureConfig;

import java.util.Arrays;
import java.util.Map;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public class MoreOreFeatureConfig extends OreFeatureConfig{


    public MoreOreFeatureConfig(FillerBlockType target, BlockState state, int size) {
        super(target, state, size);
    }
}
