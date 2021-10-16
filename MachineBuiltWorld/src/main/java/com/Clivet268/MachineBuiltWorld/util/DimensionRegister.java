package com.Clivet268.MachineBuiltWorld.util;

import com.Clivet268.MachineBuiltWorld.MachineBuiltWorld;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.dimension.DimensionType;
import net.minecraftforge.common.DimensionManager;
import net.minecraftforge.event.world.RegisterDimensionsEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import static com.Clivet268.MachineBuiltWorld.MachineBuiltWorld.LOGGER;
import static com.Clivet268.MachineBuiltWorld.util.RegistryHandler.THE_MACHINE_BUILT_WORLD;

@Mod.EventBusSubscriber(modid = MachineBuiltWorld.MOD_ID, bus = Mod.EventBusSubscriber.Bus.FORGE)
public class DimensionRegister {
    public static final ResourceLocation MOD_DIM_LOC = new ResourceLocation(MachineBuiltWorld.MOD_ID, "the_machine_built_world");


    @SubscribeEvent
    public static void registerDimensions(final RegisterDimensionsEvent event) {
        if (DimensionType.byName(MOD_DIM_LOC) == null) {
            DimensionManager.registerDimension(MOD_DIM_LOC, THE_MACHINE_BUILT_WORLD.get(), null,
                    true);
        }
        LOGGER.info("TheMachineBuiltWorld Dimension Registered");
    }
}
