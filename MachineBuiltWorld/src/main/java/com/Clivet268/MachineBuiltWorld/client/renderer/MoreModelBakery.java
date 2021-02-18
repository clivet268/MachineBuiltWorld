package com.Clivet268.MachineBuiltWorld.client.renderer;

import net.minecraft.client.renderer.color.BlockColors;
import net.minecraft.client.renderer.model.ModelBakery;
import net.minecraft.profiler.IProfiler;
import net.minecraft.resources.IResourceManager;

public class MoreModelBakery extends ModelBakery {
    public MoreModelBakery(IResourceManager resourceManagerIn, BlockColors blockColorsIn, IProfiler profilerIn, int maxMipmapLevel) {
        super(resourceManagerIn, blockColorsIn, profilerIn, maxMipmapLevel);
    }

}
