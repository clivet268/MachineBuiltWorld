package com.Clivet268.MachineBuiltWorld;


import com.Clivet268.MachineBuiltWorld.client.gui.*;
import com.Clivet268.MachineBuiltWorld.client.particle.SprocketeerCreationParticle;
import com.Clivet268.MachineBuiltWorld.client.renderer.entity.GearFactory;
import com.Clivet268.MachineBuiltWorld.client.renderer.entity.LaserRenderFactory;
import com.Clivet268.MachineBuiltWorld.client.renderer.entity.SprocketeerRenderFactory;
import com.Clivet268.MachineBuiltWorld.client.renderer.tileentity.CrusherTileEntityRenderer;
import com.Clivet268.MachineBuiltWorld.client.renderer.tileentity.ResonatingFrameTileRenderer;
import com.Clivet268.MachineBuiltWorld.util.KeyHandler;
import com.Clivet268.MachineBuiltWorld.util.LootHandler;
import com.Clivet268.MachineBuiltWorld.util.RegistryHandler;
import com.Clivet268.MachineBuiltWorld.util.packets.PacketHandler;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.ScreenManager;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.RenderTypeLookup;
import net.minecraft.client.renderer.texture.AtlasTexture;
import net.minecraft.item.crafting.IRecipeSerializer;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.biome.Biome;
import net.minecraftforge.client.event.ParticleFactoryRegisterEvent;
import net.minecraftforge.client.event.TextureStitchEvent;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.client.registry.ClientRegistry;
import net.minecraftforge.fml.client.registry.RenderingRegistry;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.fml.network.NetworkRegistry;
import net.minecraftforge.fml.network.simple.SimpleChannel;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

//import static com.Clivet268.MachineBuiltWorld.util.RegistryHandler.CRUSHER_RECIPE;

//TODO all tile entities are a bit inefficient so yea
@Mod("machinebuiltworld")
@Mod.EventBusSubscriber(modid = MachineBuiltWorld.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class MachineBuiltWorld
{
    //public static IProxy proxy = DistExecutor.runForDist(() -> () -> new ClientProxy(), () -> () -> new ServerProxy());
    public static PacketHandler PACKETHANDLER = new PacketHandler();
    public static final Logger LOGGER = LogManager.getLogger();
    public static final String MOD_ID = "machinebuiltworld";
    private static final String NETWORK_PROTOCOL_VERSION = "1";
    public static final SimpleChannel CHANNEL = NetworkRegistry.newSimpleChannel(
            new ResourceLocation(MOD_ID, "main"),
            () -> NETWORK_PROTOCOL_VERSION,
            NETWORK_PROTOCOL_VERSION::equals,
            NETWORK_PROTOCOL_VERSION::equals
    );


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
        modEventBus.addListener(this::clientSetup);
        RegistryHandler.init();
        PACKETHANDLER.initialize();
        modEventBus.addListener(this::commonSetup);

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
        ScreenManager.registerFactory(RegistryHandler.GENERATOR_CONTAINER.get(),  GeneratorScreen::new);
        ScreenManager.registerFactory(RegistryHandler.CRUSHER_CONTAINER.get(), CrusherScreen::new);
        ScreenManager.registerFactory(RegistryHandler.INTENSIVE_HEATING_OVEN_CONTAINER.get(), IntensiveHeatingOvenScreen::new);
        ScreenManager.registerFactory(RegistryHandler.MELTING_POT_CONTAINER.get(), MeltingPotScreen::new);
        ScreenManager.registerFactory(RegistryHandler.SPROCKETEERER_CONTAINER.get(), SprocketeererScreen::new);
        ScreenManager.registerFactory(RegistryHandler.MILL_CONTAINER.get(), MillScreen::new);
        RenderingRegistry.registerEntityRenderingHandler(RegistryHandler.LASER_ENTITY.get(), LaserRenderFactory.instance);
        RenderingRegistry.registerEntityRenderingHandler(RegistryHandler.SPROCKETEER.get(), SprocketeerRenderFactory.instance);
        RenderingRegistry.registerEntityRenderingHandler(RegistryHandler.GEAR_ENTITY.get(), GearFactory.instance);
        ClientRegistry.bindTileEntityRenderer(RegistryHandler.CRUSHER_TILE.get(), CrusherTileEntityRenderer::new);
        ClientRegistry.bindTileEntityRenderer(RegistryHandler.RESONATING_FRAME_TILE.get(), ResonatingFrameTileRenderer::new);
    }

    private void commonSetup(final FMLCommonSetupEvent event)
    {
        RenderTypeLookup.setRenderLayer(RegistryHandler.BORONATED_GLASS.get(), RenderType.getTranslucent());
        RenderTypeLookup.setRenderLayer(RegistryHandler.FIBERGLASS_MOULD.get(), RenderType.getTranslucent());
        RenderTypeLookup.setRenderLayer(RegistryHandler.ATOMIZER.get(), RenderType.getTranslucent());
        MinecraftForge.EVENT_BUS.register(new KeyHandler());
        MinecraftForge.EVENT_BUS.register(new LootHandler());
         //DeferredWorkQueue.runLater(MachineBuiltWorld::onDimensionRegistry(e));
    }

    public static ResourceLocation locate(String path) {
        return new ResourceLocation(MachineBuiltWorld.MOD_ID, path);
    }


    @SubscribeEvent
    public static void onRegisterBiomes(final RegistryEvent.Register<Biome> event) {
        RegistryHandler.registerBiomes();
    }


    @SubscribeEvent
    public static void registerFactories(ParticleFactoryRegisterEvent evt) {
        Minecraft.getInstance().particles.registerFactory(
                RegistryHandler.SPROCKETEER_CREATION_PARTICLE.get(), SprocketeerCreationParticle.Factory::new);
    }
}

