package com.Clivet268.MachineBuiltWorld;


import com.Clivet268.MachineBuiltWorld.client.gui.*;
import com.Clivet268.MachineBuiltWorld.util.RegistryHandler;
import com.Clivet268.MachineBuiltWorld.util.Renderer.BulletRenderer;
import com.Clivet268.MachineBuiltWorld.worldgen.MachineBuiltWorldOreGen;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.ScreenManager;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.RenderTypeLookup;
import net.minecraft.item.crafting.IRecipeSerializer;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.registry.Registry;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.client.registry.RenderingRegistry;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLLoadCompleteEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.function.Supplier;

//import static com.Clivet268.MachineBuiltWorld.util.RegistryHandler.CRUSHER_RECIPE;


@Mod("machinebuiltworld")
@Mod.EventBusSubscriber(modid = MachineBuiltWorld.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class MachineBuiltWorld
{

    public static final Logger LOGGER = LogManager.getLogger();
    public static final String MOD_ID = "machinebuiltworld";

    private void registerRecipeSerializers (RegistryEvent.Register<IRecipeSerializer<?>> event) {

        // Vanilla has a registry for recipe types, but it does not actively use this registry.
        // While this makes registering your recipe type an optional step, I recommend
        // registering it anyway to allow other mods to discover your custom recipe types.
        //Registry.register(Registry.RECIPE_TYPE, new ResourceLocation(CRUSHER_RECIPE.toString()), CRUSHER_RECIPE);

        // Register the recipe serializer. This handles from json, from packet, and to packet.
        //event.getRegistry().register(IMoreRecipeSerializer.CRUSHING);
    }
    public MachineBuiltWorld() {
        final IEventBus modEventBus = FMLJavaModLoadingContext.get().getModEventBus();
        modEventBus.addListener(this::setup);
        modEventBus.addListener(this::clientSetup);
        RegistryHandler.init();

        MinecraftForge.EVENT_BUS.register(this);
        FMLJavaModLoadingContext.get().getModEventBus().addGenericListener(IRecipeSerializer.class, this::registerRecipeSerializers);

    }
    private void clientSetup(final FMLClientSetupEvent event) {
            ScreenManager.registerFactory(RegistryHandler.TTTBATTERY_CONTAINER.get(), TTTBatteryScreen::new);
            ScreenManager.registerFactory(RegistryHandler.TTBATTERY_CONTAINER.get(), TTBatteryScreen::new);
            ScreenManager.registerFactory(RegistryHandler.TBATTERY_CONTAINER.get(), TBatteryScreen::new);
            ScreenManager.registerFactory(RegistryHandler.BATTERY_POT_CONTAINER.get(), BatteryPotScreen::new);
            ScreenManager.registerFactory(RegistryHandler.MIXER_CONTAINER.get(), MixerScreen::new);
            ScreenManager.registerFactory(RegistryHandler.ATOMIZER_CONTAINER.get(), AtomizerScreen::new);
            //ScreenManager.registerFactory(RegistryHandler.CRUSHER_CONTAINER.get(), CrusherScreen::new);
            //ScreenManager.registerFactory(RegistryHandler.COKE_OVEN_CONTAINER.get(), CokeOvenScreen::new);

            registerEntityModels(event.getMinecraftSupplier());
        }
    private void registerEntityModels(Supplier<Minecraft> minecraft){
        //ItemRenderer renderer = minecraft.get().getItemRenderer();
        RenderingRegistry.registerEntityRenderingHandler(RegistryHandler.BULLET_ENTITY.get(), (renderManager) -> new BulletRenderer(renderManager));
    }
    private void setup(final FMLCommonSetupEvent event)
    {
        RenderTypeLookup.setRenderLayer(RegistryHandler.BORONATED_GLASS.get(), RenderType.getTranslucent());
        RenderTypeLookup.setRenderLayer(RegistryHandler.FIBERGLASS_MOULD.get(), RenderType.getTranslucent());
        RenderTypeLookup.setRenderLayer(RegistryHandler.ATOMIZER.get(), RenderType.getTranslucent());
    }

    public static ResourceLocation locate(String path) {
        return new ResourceLocation(MachineBuiltWorld.MOD_ID, path);
    }

    @SubscribeEvent
    public static void loadCompleteEvent(final FMLLoadCompleteEvent event)
    {
        MachineBuiltWorldOreGen.generateOre();
    }

}
