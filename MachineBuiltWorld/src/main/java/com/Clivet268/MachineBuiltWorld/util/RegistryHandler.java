package com.Clivet268.MachineBuiltWorld.util;

import com.Clivet268.MachineBuiltWorld.blocks.*;
import com.Clivet268.MachineBuiltWorld.entity.*;
import com.Clivet268.MachineBuiltWorld.fluids.ResinFluid;
import com.Clivet268.MachineBuiltWorld.inventory.Containers.*;
import com.Clivet268.MachineBuiltWorld.inventory.crafting.*;
import com.Clivet268.MachineBuiltWorld.items.*;
import com.Clivet268.MachineBuiltWorld.items.tools.CraftingToolsItem;
import com.Clivet268.MachineBuiltWorld.items.tools.ModItemTier;
import com.Clivet268.MachineBuiltWorld.items.tools.MultimeterItem;
import com.Clivet268.MachineBuiltWorld.tileentity.*;
import com.Clivet268.MachineBuiltWorld.world.TheMachineBuiltWorldModDimension;
import com.Clivet268.MachineBuiltWorld.world.biome.TheMachineBuiltWorldBarrenLands;
import com.Clivet268.MachineBuiltWorld.world.biome.TheMachineBuiltWorldBarrenLandsSurfaceBuilder;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.block.FlowingFluidBlock;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.entity.EntityClassification;
import net.minecraft.entity.EntityType;
import net.minecraft.fluid.FlowingFluid;
import net.minecraft.fluid.Fluid;
import net.minecraft.inventory.container.ContainerType;
import net.minecraft.item.BucketItem;
import net.minecraft.item.Item;
import net.minecraft.item.Items;
import net.minecraft.item.WallOrFloorItem;
import net.minecraft.item.crafting.IRecipeSerializer;
import net.minecraft.particles.BasicParticleType;
import net.minecraft.particles.ParticleType;
import net.minecraft.stats.IStatFormatter;
import net.minecraft.stats.StatType;
import net.minecraft.stats.Stats;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.ItemTags;
import net.minecraft.tags.Tag;
import net.minecraft.tileentity.TileEntityType;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.SoundEvents;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.registry.Registry;
import net.minecraft.world.World;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.dimension.DimensionType;
import net.minecraft.world.gen.surfacebuilders.ConfiguredSurfaceBuilder;
import net.minecraft.world.gen.surfacebuilders.ISurfaceBuilderConfig;
import net.minecraft.world.gen.surfacebuilders.SurfaceBuilder;
import net.minecraft.world.gen.surfacebuilders.SurfaceBuilderConfig;
import net.minecraftforge.common.BiomeDictionary;
import net.minecraftforge.common.ModDimension;
import net.minecraftforge.common.ToolType;
import net.minecraftforge.common.extensions.IForgeContainerType;
import net.minecraftforge.fluids.FluidAttributes;
import net.minecraftforge.fluids.ForgeFlowingFluid;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

import static com.Clivet268.MachineBuiltWorld.MachineBuiltWorld.MOD_ID;

//import com.Clivet268.MachineBuiltWorld.blocks.BatteryPot;
//TODO are the steel and copper tools and armor a goo ide to have, if so we can readded but it can be readded if nececary
public class RegistryHandler {
    //public static final DeferredRegister<LootTable> LOOT_TABLES = new DeferredRegister<>(ForgeRegistries.LOOT_MODIFIER_SERIALIZERS)
    public static final DeferredRegister<IRecipeSerializer<?>> RECIPES_SERIALIZER = new DeferredRegister<>(ForgeRegistries.RECIPE_SERIALIZERS, MOD_ID);
    public static final DeferredRegister<SoundEvent> SOUNDS = new DeferredRegister<>(ForgeRegistries.SOUND_EVENTS, MOD_ID);
    public static final DeferredRegister<Fluid> FLUIDS = new DeferredRegister<>(ForgeRegistries.FLUIDS, MOD_ID);
    public static final DeferredRegister<Item> ITEMS = new DeferredRegister<>(ForgeRegistries.ITEMS, MOD_ID);
    public static final DeferredRegister<Block> BLOCKS = new DeferredRegister<>(ForgeRegistries.BLOCKS, MOD_ID);
    private static final DeferredRegister<TileEntityType<?>> TILES = new DeferredRegister<>(ForgeRegistries.TILE_ENTITIES, MOD_ID);
    private static final DeferredRegister<ContainerType<?>> CONTAINERS = new DeferredRegister<>(ForgeRegistries.CONTAINERS, MOD_ID);
    public static final DeferredRegister<EntityType<?>> ENTITIES = new DeferredRegister<>(ForgeRegistries.ENTITIES, MOD_ID);
    public static final DeferredRegister<StatType<?>> STATS = new DeferredRegister<>(ForgeRegistries.STAT_TYPES , MOD_ID);
    public static final DeferredRegister<ModDimension> DIMENSIONS = new DeferredRegister<>(ForgeRegistries.MOD_DIMENSIONS, MOD_ID);
    public static final  DeferredRegister<Biome> BIOMES = new DeferredRegister<>(ForgeRegistries.BIOMES, MOD_ID);
    public static final  DeferredRegister<ParticleType<?>> PARTICLES = new DeferredRegister<>(ForgeRegistries.PARTICLE_TYPES, MOD_ID);

    public static void init() {
        RECIPES_SERIALIZER.register(FMLJavaModLoadingContext.get().getModEventBus());
        SOUNDS.register(FMLJavaModLoadingContext.get().getModEventBus());
        FLUIDS.register(FMLJavaModLoadingContext.get().getModEventBus());
        ENTITIES.register(FMLJavaModLoadingContext.get().getModEventBus());
        ITEMS.register(FMLJavaModLoadingContext.get().getModEventBus());
        BLOCKS.register(FMLJavaModLoadingContext.get().getModEventBus());
        TILES.register(FMLJavaModLoadingContext.get().getModEventBus());
        CONTAINERS.register(FMLJavaModLoadingContext.get().getModEventBus());
        BIOMES.register(FMLJavaModLoadingContext.get().getModEventBus());
        DIMENSIONS.register(FMLJavaModLoadingContext.get().getModEventBus());
        PARTICLES.register(FMLJavaModLoadingContext.get().getModEventBus());
    }

    //sounds
    public static final RegistryObject<SoundEvent> BULLET_HIT = SOUNDS.register("bullet_hit",() -> new SoundEvent(new ResourceLocation(MOD_ID,"entity.bullet_hit")));
    public static final RegistryObject<SoundEvent> PISTOL_SHOOT = SOUNDS.register("pistol_shoot",() -> new SoundEvent(new ResourceLocation(MOD_ID,"item.pistol_shoot")));
    public static final RegistryObject<SoundEvent> LASER_PISTOL_SHOOT = SOUNDS.register("laser_pistol_shoot",() -> new SoundEvent(new ResourceLocation(MOD_ID,"item.laser_pistol_shoot")));
    public static final RegistryObject<SoundEvent> SMOKE_ALARM = SOUNDS.register("smoke_alarm",() -> new SoundEvent(new ResourceLocation(MOD_ID,"block.smoke_alarm")));
    public static final RegistryObject<SoundEvent> CRUSHER_CRUSHING = SOUNDS.register("crusher_crushing",() -> new SoundEvent(new ResourceLocation(MOD_ID,"block.crusher_crushing")));
    public static final RegistryObject<SoundEvent> SPROCKETEER_STEP = SOUNDS.register("sprocketeer_step",() -> new SoundEvent(new ResourceLocation(MOD_ID,"entity.sprocketeer_step")));
    public static final RegistryObject<SoundEvent> PITTERBOT_SHOOT = SOUNDS.register("pitterbot_shoot",() -> new SoundEvent(new ResourceLocation(MOD_ID,"entity.pitterbot_shoot")));
    public static final RegistryObject<SoundEvent> SHREDDER_SHOOT = SOUNDS.register("shredderbot_shoot",() -> new SoundEvent(new ResourceLocation(MOD_ID,"entity.shredderbot_shoot")));

    //entities
    public static final RegistryObject<EntityType<SprocketeerEntity>> SPROCKETEER = ENTITIES.register("sprocketeer",
            () -> EntityType.Builder.<SprocketeerEntity>create(SprocketeerEntity::new, EntityClassification.MISC).size(0.5F, 0.5F).build("sprocketeer"));
    public static final RegistryObject<EntityType<SprocketeerEntity>> PITTERBOT = ENTITIES.register("pitterbot",
            () -> EntityType.Builder.<SprocketeerEntity>create(SprocketeerEntity::new, EntityClassification.MISC).size(0.5F, 0.5F).build("pitterbot"));
    public static final RegistryObject<EntityType<SprocketeerEntity>> SHREDDERBOT = ENTITIES.register("shredderbot",
            () -> EntityType.Builder.<SprocketeerEntity>create(SprocketeerEntity::new, EntityClassification.MISC).size(0.5F, 0.5F).build("shredderbot"));
    /*public static final RegistryObject<EntityType<SprocketeerEntity>> THE_CRUSHER = ENTITIES.register("shredderbot",
            () -> EntityType.Builder.<SprocketeerEntity>create(SprocketeerEntity::new, EntityClassification.MISC).size(0.5F, 0.5F).build("shredderbot"));
    public static final RegistryObject<EntityType<SprocketeerEntity>> THE_ROLLER = ENTITIES.register("shredderbot",
            () -> EntityType.Builder.<SprocketeerEntity>create(SprocketeerEntity::new, EntityClassification.MISC).size(0.5F, 0.5F).build("shredderbot"));
    public static final RegistryObject<EntityType<SprocketeerEntity>> THE_BLASTER = ENTITIES.register("shredderbot",
            () -> EntityType.Builder.<SprocketeerEntity>create(SprocketeerEntity::new, EntityClassification.MISC).size(0.5F, 0.5F).build("shredderbot"));


     */
    //projectiles
    public static final RegistryObject<EntityType<BulletEntity>> BULLET_ENTITY = ENTITIES.register("bullet_projectile",
            () -> EntityType.Builder.<BulletEntity>create(BulletEntity::new, EntityClassification.MISC).size(0.5F, 0.9F).build("bullet_projectile"));
    public static final RegistryObject<EntityType<LaserEntity>> LASER_ENTITY = ENTITIES.register("laser_projectile",
            () -> EntityType.Builder.<LaserEntity>create(LaserEntity::new, EntityClassification.MISC).size(0.5F, 0.9F).build("laser_projectile"));
    public static final RegistryObject<EntityType<GearEntity>> GEAR_ENTITY = ENTITIES.register("gear_projectile",
            () -> EntityType.Builder.<GearEntity>create(GearEntity::new, EntityClassification.MISC).size(0.5F, 0.9F).build("gear_projectile"));
    public static final RegistryObject<EntityType<SickShotEntity>> SICK_SHOT_ENTITY = ENTITIES.register("sick_shot_projectile",
            () -> EntityType.Builder.<SickShotEntity>create(SickShotEntity::new, EntityClassification.MISC).size(0.5F, 0.9F).build("sick_shot_projectile"));

    //items
    public static final RegistryObject<Item> IRON_GEAR = ITEMS.register("iron_gear", ItemBase::new);
    public static final RegistryObject<Item> COPPER_INGOT = ITEMS.register("copper_ingot", ItemBase::new);
    //TODO needed?
    public static final RegistryObject<Item> TIN_INGOT = ITEMS.register("tin_ingot", ItemBase::new);
    public static final RegistryObject<Item> BRONZE_INGOT = ITEMS.register("bronze_ingot", ItemBase::new);
    public static final RegistryObject<Item> SILVER_INGOT = ITEMS.register("silver_ingot", ItemBase::new);
    public static final RegistryObject<Item> CRUDE_ELECTROMAGNET = ITEMS.register("crude_electromagnet", ItemBase::new);
    //TODO fix the texture/improve it
    public static final RegistryObject<Item> NEODYMIUM_MAGNET = ITEMS.register("neodymium_magnet", ItemBase::new);
    public static final RegistryObject<Item> GRAPHENE = ITEMS.register("graphene", ItemBase::new);
    public static final RegistryObject<Item> TRACK_GEAR = ITEMS.register("track_gear", ItemBase::new);
    public static final RegistryObject<Item> IRON_AXLE = ITEMS.register("iron_axle", ItemBase::new);
    public static final RegistryObject<Item> COPPER_AXLE = ITEMS.register("copper_axle", ItemBase::new);
    public static final RegistryObject<Item> GOLD_AXLE = ITEMS.register("gold_axle", ItemBase::new);
    public static final RegistryObject<Item> STEEL_AXLE = ITEMS.register("steel_axle", ItemBase::new);
    public static final RegistryObject<Item> DIAMOND_AXLE = ITEMS.register("diamond_axle", ItemBase::new);
    public static final RegistryObject<Item> BORON_DUST = ITEMS.register("boron_dust", ItemBase::new);
    public static final RegistryObject<Item> BORON_CHUNK = ITEMS.register("boron_chunk", ItemBase::new);
    public static final RegistryObject<Item> COPPER_DUST = ITEMS.register("copper_dust", ItemBase::new);
    public static final RegistryObject<Item> IRON_DUST = ITEMS.register("iron_dust", ItemBase::new);
    public static final RegistryObject<Item> GOLD_DUST = ITEMS.register("gold_dust", ItemBase::new);
    public static final RegistryObject<Item> GARNET_DUST = ITEMS.register("garnet_dust", ItemBase::new);
    public static final RegistryObject<Item> NEODYMIUM_DUST = ITEMS.register("neodymium_dust", ItemBase::new);
    public static final RegistryObject<Item> DIRTY_BORON_DUST = ITEMS.register("dirty_boron_dust", ItemBase::new);
    public static final RegistryObject<Item> CIRCUIT_BOARD = ITEMS.register("circuit_board", ItemBase::new);
    //needed?
    public static final RegistryObject<Item> SILICONE_GLOB = ITEMS.register("silicone_glob", ItemBase::new);
    public static final RegistryObject<Item> WOOD_GEAR = ITEMS.register("wood_gear", ItemBase::new);
    public static final RegistryObject<Item> EPOXY = ITEMS.register("epoxy", ItemBase::new);
    public static final RegistryObject<Item> HIGH_PERFORMANCE_EPOXY = ITEMS.register("high_performance_epoxy", ItemBase::new);
    public static final RegistryObject<Item> SUPER_PERFORMANCE_EPOXY = ITEMS.register("super_performance_epoxy", ItemBase::new);
    public static final RegistryObject<Item> FIBER_GLASS = ITEMS.register("fiber_glass", ItemBase::new);
    public static final RegistryObject<Item> HIGH_PERFORMANCE_CIRCUIT_BOARD = ITEMS.register("high_performance_circuit_board", ItemBase::new);
    public static final RegistryObject<Item> SUPER_PERFORMANCE_CIRCUIT_BOARD = ITEMS.register("super_performance_circuit_board", ItemBase::new);
    public static final RegistryObject<Item> GLASH_SHARDS = ITEMS.register("glass_shards", ItemBase::new);
    public static final RegistryObject<Item> ION_SHELL = ITEMS.register("ion_shell", ItemBase::new);
    public static final RegistryObject<Item> LASER_SHELL = ITEMS.register("laser_shell", ItemBase::new);
    public static final RegistryObject<Item> MICRO_RESONATOR = ITEMS.register("micro_resonator", ItemBase::new);
    public static final RegistryObject<Item> RESONATOR = ITEMS.register("resonator", ItemBase::new);
    public static final RegistryObject<Item> LARGE_RESONATOR = ITEMS.register("large_resonator", ItemBase::new);
    public static final RegistryObject<Item> STEEL_INGOT = ITEMS.register("steel_ingot", ItemBase::new);
    public static final RegistryObject<Item> COKE = ITEMS.register("coke", () -> new FuelItemBase(1600));
    public static final RegistryObject<Item> CUT_GARNET = ITEMS.register("cut_garnet", ItemBase::new);
    public static final RegistryObject<Item> CUT_POLISHED_GARNET = ITEMS.register("cut_polished_garnet", ItemBase::new);
    public static final RegistryObject<Item> LARGE_UNCUT_GARNET = ITEMS.register("large_uncut_garnet", ItemBase::new);
    public static final RegistryObject<Item> UNCUT_GARNET = ITEMS.register("uncut_garnet", ItemBase::new);
    public static final RegistryObject<Item> ERBIUM_CHUNK = ITEMS.register("erbium_chunk", ItemBase::new);
    //TODO attach texture
    public static final RegistryObject<Item> XENOTIME_CHUNK = ITEMS.register("xenotime_chunk", ItemBase::new);
    public static final RegistryObject<Item> ALUMINUM_INGOT = ITEMS.register("aluminum_ingot", ItemBase::new);
    public static final RegistryObject<Item> YTTRIUM = ITEMS.register("yttrium", ItemBase::new);
    //TODO how we gonna make this? maybe atomize or something to make it more epic
    public static final RegistryObject<Item> RESONATING_CRYSTAL = ITEMS.register("resonating_crystal", ItemBase::new);
    public static final RegistryObject<Item> ALUMINUM_NUGGET = ITEMS.register("aluminum_nugget", ItemBase::new);
    public static final RegistryObject<Item> COPPER_NUGGET = ITEMS.register("copper_nugget", ItemBase::new);
    //TODO make texture
    public static final RegistryObject<Item> STEEL_NUGGET = ITEMS.register("steel_nugget", ItemBase::new);
    public static final RegistryObject<Item> ERBIUM_DUST = ITEMS.register("erbium_dust", ItemBase::new);
    public static final RegistryObject<Item> NEODYMIUM_CHUNK = ITEMS.register("neodymium_chunk", ItemBase::new);
    public static final RegistryObject<Item> COPPER_ROLLER = ITEMS.register("copper_roller", ItemBase::new);
    //TODO do we really want to add another step to the crafting? i mean it makes since that you have to add
    // some umph to the axle.
    //TODO make textures
    public static final RegistryObject<Item> IRON_ROLLER = ITEMS.register("iron_roller", ItemBase::new);
    public static final RegistryObject<Item> GOLD_ROLLER = ITEMS.register("gold_roller", ItemBase::new);
    public static final RegistryObject<Item> STEEL_ROLLER = ITEMS.register("steel_roller", ItemBase::new);
    public static final RegistryObject<Item> DIAMOND_ROLLER = ITEMS.register("diamond_roller", ItemBase::new);
    public static final RegistryObject<Item> INDUCTION_PLATE = ITEMS.register("induction_plate", ItemBase::new);
    public static final RegistryObject<Item> PISTOL_BODY = ITEMS.register("pistol_body", ItemBase::new);
    public static final RegistryObject<Item> STEEL_GRIP = ITEMS.register("steel_grip", ItemBase::new);
    public static final RegistryObject<Item> GLASS_DAGGER_BLADE = ITEMS.register("glass_dagger_blade", ItemBase::new);
    //TODO buff and make harder to get somehow
    public static final RegistryObject<Item> STRENGTHENED_GLASS_DAGGER_BLADE = ITEMS.register("strengthened_glass_dagger_blade", ItemBase::new);
    public static final RegistryObject<Item> SICK_SHOT = ITEMS.register("sick_shot", ItemBase::new);
    //TODO do
    public static final RegistryObject<Item> SYNTHETIC_SOUL = ITEMS.register("synthetic_soul", ItemBase::new);
    //TODO do texture
    public static final RegistryObject<Item> UNFUN_LUMP = ITEMS.register("unfun_lump", ItemBase::new);


    //special items
    public static final RegistryObject<CrusherTeethBase> COPPER_CRUSHER_TEETH = ITEMS.register("copper_crusher_teeth", ()->
            new CrusherTeethBase(new Item.Properties().group(MachineBuiltWorldItemGroup.instance).maxDamage(800), 1));
    public static final RegistryObject<CrusherTeethBase> IRON_CRUSHER_TEETH = ITEMS.register("iron_crusher_teeth", ()->
            new CrusherTeethBase(new Item.Properties().group(MachineBuiltWorldItemGroup.instance).maxDamage(1000), 1));
    public static final RegistryObject<CrusherTeethBase> GOLD_CRUSHER_TEETH = ITEMS.register("gold_crusher_teeth", ()->
            new CrusherTeethBase(new Item.Properties().group(MachineBuiltWorldItemGroup.instance).maxDamage(500), 4));
    public static final RegistryObject<CrusherTeethBase> STEEL_CRUSHER_TEETH = ITEMS.register("steel_crusher_teeth", ()->
            new CrusherTeethBase(new Item.Properties().group(MachineBuiltWorldItemGroup.instance).maxDamage(2500), 3));
    public static final RegistryObject<CrusherTeethBase> DIAMOND_CRUSHER_TEETH = ITEMS.register("diamond_crusher_teeth", ()->
            new CrusherTeethBase(new Item.Properties().group(MachineBuiltWorldItemGroup.instance).maxDamage(4500), 4));

    //spawn eggs

    //blocks
    public static final RegistryObject<Block> COPPER_BLOCK = BLOCKS.register("copper_block", CopperBlock::new);
    public static final RegistryObject<Block> BATTERY_POT = BLOCKS.register("battery_pot", BatteryPot::new);
    public static final RegistryObject<Block> MAGNETS = BLOCKS.register("magnets", Magnets::new);
    public static final RegistryObject<Block> CRUDE_ELECTROMAGNETA = BLOCKS.register("crude_electromagneta", CrudeElectromagnetA::new);
//TODO voxel shapes are apparantly not optional, maybe just a AABB something like that?
    public static final RegistryObject<Block> WIRE = BLOCKS.register("wire", Wire::new);
    public static final RegistryObject<Block> MELTING_POT = BLOCKS.register("melting_pot", MeltingPot::new);
    public static final RegistryObject<Block> ATOMIZER = BLOCKS.register("atomizer", Atomizer::new);
    public static final RegistryObject<Block> BORONATED_GLASS = BLOCKS.register("boronated_glass", BoronatedGlass::new);
    public static final RegistryObject<Block> COPPER_ORE = BLOCKS.register("copper_ore", CopperOre::new);
    public static final RegistryObject<Block> TIN_ORE = BLOCKS.register("tin_ore", TinOre::new);
    public static final RegistryObject<Block> BORON_ORE = BLOCKS.register("boron_ore", BoronOre::new);
    public static final RegistryObject<Block> TTTBATTERY = BLOCKS.register("tttbattery", TTTBattery::new);
    public static final RegistryObject<Block> TTBATTERY = BLOCKS.register("ttbattery", TTBattery::new);
    public static final RegistryObject<Block> TBATTERY = BLOCKS.register("tbattery", TBattery::new);
    public static final RegistryObject<Block> MILL = BLOCKS.register("mill", Mill::new);
    public static final RegistryObject<Block> MIXER = BLOCKS.register("mixer", Mixer::new);
    public static final RegistryObject<Block> SMOKE_DETECTOR = BLOCKS.register("smoke_detector", SmokeDetector::new);
    public static final RegistryObject<Block> FIBERGLASS_MOULD = BLOCKS.register("fiberglass_mould", FiberglassMould::new);
    public static final RegistryObject<Block> CRUSHER = BLOCKS.register("crusher", Crusher::new);
    public static final RegistryObject<Block> INTENSIVE_HEATING_OVEN = BLOCKS.register("intensive_heating_oven", IntensiveHeatingOven::new);
    public static final RegistryObject<Block> REINFORCED_BRICK = BLOCKS.register("reinforced_brick", ReinforcedBrick::new);
    public static final RegistryObject<Block> BAUXITE_ORE = BLOCKS.register("bauxite_ore", BauxiteOre::new);
    public static final RegistryObject<Block> GARNET_ORE = BLOCKS.register("garnet_ore", GarnetOre::new);
    public static final RegistryObject<Block> ERBIUM_ORE = BLOCKS.register("erbium_ore", ErbiumOre::new);
    public static final RegistryObject<Block> CRYSTALLIZATION_CHAMBER_PART = BLOCKS.register("crystallization_chamber_part", CrystallizationChamberPart::new);
    public static final RegistryObject<Block> BROKEN_GLASS_BUNCH = BLOCKS.register("broken_glass_bunch", BrokenGlassBunch::new);
    public static final RegistryObject<Block> MANAGER = BLOCKS.register("manager", Manager::new);
    public static final RegistryObject<Block> STEEL_FRAME = BLOCKS.register("steel_frame", SteelFrame::new);
    public static final RegistryObject<Block> GENERATOR = BLOCKS.register("generator", Generator::new);
    public static final RegistryObject<Block> NEODYMIUM_ORE = BLOCKS.register("neodymium_ore", NeodymiumOre::new);
    public static final RegistryObject<Block> STEEL_BLOCK = BLOCKS.register("steel_block", SteelBlock::new);
    public static final RegistryObject<Block> STEEL_PANEL = BLOCKS.register("steel_panel", SteelPanel::new);
    public static final RegistryObject<Block> INDUCTOR = BLOCKS.register("inductor", SteelPanel::new);
    public static final RegistryObject<Block> SPROCKETEERER = BLOCKS.register("sprocketeerer", Sprocketeerer::new);
    public static final RegistryObject<Block> PRESSURIZED_GAS_CONTAINER = BLOCKS.register("pressurized_gas_container", PressurizedGasContainer::new);
    //TODO do?
    public static final RegistryObject<Block> COMPLEX_FABRICATOR = BLOCKS.register("complex_fabricator", PressurizedGasContainer::new);
    //TODO do
    public static final RegistryObject<Block> MINI_MOULDER = BLOCKS.register("mini-moulder", PressurizedGasContainer::new);
    //TODO do
    public static final RegistryObject<Block> MOULDER = BLOCKS.register("moulder", PressurizedGasContainer::new);
    //TODO do
    public static final RegistryObject<Block> DISASSOCIATED_ATOM_CONTAINER = BLOCKS.register("disassociated_atom_container", DisasossiatedAtomContainer::new);
    //TODO do
    public static final RegistryObject<Block> ATOMIC_MANAGER = BLOCKS.register("atomic_manager", PressurizedGasContainer::new);


    //special blocks
    public static final RegistryObject<Block> IRON_OXIDE = BLOCKS.register("iron_oxide",
            () -> new IronOxide(13190441));
    //TODO needed?
    public static final RegistryObject<Block> MOULDING_SAND = BLOCKS.register("moulding_sand",
            () -> new MouldingSand(10130441));
    //TODO design
    //TODO tweak functionality
    //TODO carry items better
    public static final RegistryObject<Block> CONVEYOR = BLOCKS.register("conveyor",() -> new Conveyor(Block.Properties.create(Material.IRON)
            .hardnessAndResistance(5.0f, 6.0f)
            .sound(SoundType.METAL)
            .harvestLevel(0)));
    public static final RegistryObject<Block> CONVEYOR_UP = BLOCKS.register("conveyor_up",() -> new ConveyorUp(Block.Properties.create(Material.IRON)
            .hardnessAndResistance(5.0f, 6.0f)
            .sound(SoundType.METAL)
            .harvestLevel(1)
            .harvestTool(ToolType.PICKAXE)));

    //weapons
    public static final RegistryObject<GlassDaggerItem> GLASS_DAGGER = ITEMS.register("glass_dagger", () ->
            new GlassDaggerItem(ModItemTier.GLASS_DAGGER, 1, 1.0f, new Item.Properties().group(MachineBuiltWorldItemGroup.instance).maxDamage(150)));
    public static final RegistryObject<GlassDaggerItem> STRENGTHENED_GLASS_DAGGER = ITEMS.register("strengthened_glass_dagger", () ->
            new GlassDaggerItem(ModItemTier.STRENGTHENED_GLASS_DAGGER, 3, 1.1f, new Item.Properties().group(MachineBuiltWorldItemGroup.instance).maxDamage(150)));
    public static final RegistryObject<LaserPistolItem> LASER_PISTOL = ITEMS.register("laser_pistol", ()->
            new LaserPistolItem(new Item.Properties().group(MachineBuiltWorldItemGroup.instance).maxDamage(1800)));
    public static final RegistryObject<SickerItem> SICKER = ITEMS.register("sicker", ()->
            new SickerItem(new Item.Properties().group(MachineBuiltWorldItemGroup.instance).maxDamage(1800)));
    public static final RegistryObject<PistolItem> PISTOL = ITEMS.register("pistol", ()->
            new PistolItem(new Item.Properties().group(MachineBuiltWorldItemGroup.instance).maxDamage(800)));
    public static final RegistryObject<BulletItem> BULLET = ITEMS.register("bullet", ()->
            new BulletItem(new Item.Properties().group(MachineBuiltWorldItemGroup.instance)));
    public static final RegistryObject<IncendiaryBulletItem> INCENDIARY_BULLET = ITEMS.register("incendiary_bullet", ()->
            new IncendiaryBulletItem(new Item.Properties().group(MachineBuiltWorldItemGroup.instance)));
    public static final RegistryObject<LaserPisolMagItem> LASER_PISTOL_MAG = ITEMS.register("laser_pistol_mag", ()->
            new LaserPisolMagItem(new Item.Properties().group(MachineBuiltWorldItemGroup.instance).maxStackSize(8)));
    public static final RegistryObject<PisolMagItem> PISTOL_MAG = ITEMS.register("pistol_mag", ()->
            new PisolMagItem(new Item.Properties().group(MachineBuiltWorldItemGroup.instance).maxStackSize(8)));

    /*
    public static final RegistryObject<IncendiaryBulletItem> INCENDIARY_BULLET = ITEMS.register("incendiary_bullet", ()->
            new IncendiaryBulletItem(new Item.Properties().group(MachineBuiltWorldItemGroup.instance)));

     */

    //tools
    public static final RegistryObject<CraftingToolsItem> WIRE_CUTTERS = ITEMS.register("wire_cutters", () ->
            new CraftingToolsItem(ModItemTier.CRAFTING_TOOLS, 2, -2.4f, new Item.Properties().group(MachineBuiltWorldItemGroup.instance)));
    public static final RegistryObject<CraftingToolsItem> PLIERS = ITEMS.register("pliers", () ->
            new CraftingToolsItem(ModItemTier.CRAFTING_TOOLS, 2, -2.4f, new Item.Properties().group(MachineBuiltWorldItemGroup.instance)));
    public static final RegistryObject<MultimeterItem> MULTIMETER = ITEMS.register("multimeter", () ->
            new MultimeterItem(ModItemTier.MULTIMETER, 2, -2.4f, new Item.Properties().group(MachineBuiltWorldItemGroup.instance)));
    public static final RegistryObject<CraftingToolsItem> SANDPAPER = ITEMS.register("sandpaper", () ->
            new CraftingToolsItem(ModItemTier.CRAFTING_TOOLS, 2, -2.4f, new Item.Properties().group(MachineBuiltWorldItemGroup.instance).maxDamage(70)));

    //armor


    //block items
    public static final RegistryObject<Item> COPPER_BLOCK_ITEM = ITEMS.register("copper_block", () -> new BlockItemBase(COPPER_BLOCK.get()));
    public static final RegistryObject<Item> BATTERY_POT_ITEM = ITEMS.register("battery_pot", () -> new BlockItemBase(BATTERY_POT.get()));
    public static final RegistryObject<Item> MAGNETS_ITEM = ITEMS.register("magnets", () -> new BlockItemBase(MAGNETS.get()));
    public static final RegistryObject<Item> CRUDE_ELECTROMAGNETA_ITEM = ITEMS.register("crude_electromagneta", () -> new BlockItemBase(CRUDE_ELECTROMAGNETA.get()));
    public static final RegistryObject<Item> WIRE_ITEM = ITEMS.register("wire", () -> new BlockItemBase(WIRE.get()));
    public static final RegistryObject<Item> MELTING_POT_ITEM = ITEMS.register("melting_pot", () -> new BlockItemBase(MELTING_POT.get()));
    public static final RegistryObject<Item> ATOMIZER_ITEM = ITEMS.register("atomizer", () -> new BlockItemBase(ATOMIZER.get()));
    public static final RegistryObject<Item> MOULDING_SAND_ITEM = ITEMS.register("moulding_sand", () -> new BlockItemBase(MOULDING_SAND.get()));
    //public static final RegistryObject<Item> CONVEYOR_ITEM = ITEMS.register("conveyor", () -> new BlockItemBase(CONVEYOR.get()));
    public static final RegistryObject<Item> BORONATED_GLASS_ITEM = ITEMS.register("boronated_glass", () -> new BlockItemBase(BORONATED_GLASS.get()));
    public static final RegistryObject<Item> COPPER_ORE_ITEM = ITEMS.register("copper_ore", () -> new BlockItemBase(COPPER_ORE.get()));
    public static final RegistryObject<Item> TIN_ORE_ITEM = ITEMS.register("tin_ore", () -> new BlockItemBase(TIN_ORE.get()));
    public static final RegistryObject<Item> BORON_ORE_ITEM = ITEMS.register("boron_ore", () -> new BlockItemBase(BORON_ORE.get()));
    public static final RegistryObject<Item> TTTBATTERY_ITEM = ITEMS.register("tttbattery", () -> new BlockItemBase(TTTBATTERY.get()));
    public static final RegistryObject<Item> TTBATTERY_ITEM = ITEMS.register("ttbattery", () -> new BlockItemBase(TTBATTERY.get()));
    public static final RegistryObject<Item> TBATTERY_ITEM = ITEMS.register("tbattery", () -> new BlockItemBase(TBATTERY.get()));
    public static final RegistryObject<Item> MILL_ITEM = ITEMS.register("mill", () -> new BlockItemBase(MILL.get()));
    public static final RegistryObject<Item> MIXER_ITEM = ITEMS.register("mixer", () -> new BlockItemBase(MIXER.get()));
    public static final RegistryObject<Item> SMOKE_DETECTOR_ITEM = ITEMS.register("smoke_detector", () -> new BlockItemBase(SMOKE_DETECTOR.get()));
    public static final RegistryObject<Item> FIBERGLASS_MOULD_ITEM = ITEMS.register("fiberglass_mould", () -> new BlockItemBase(FIBERGLASS_MOULD.get()));
    public static final RegistryObject<Item> CRUSHER_ITEM = ITEMS.register("crusher", () -> new BlockItemBase(CRUSHER.get()));
    public static final RegistryObject<Item> INTENSIVE_HEATING_OVEN_ITEM = ITEMS.register("intensive_heating_oven", () -> new BlockItemBase(INTENSIVE_HEATING_OVEN.get()));
    public static final RegistryObject<Item> REINFORCED_BRICK_ITEM = ITEMS.register("reinforced_brick", () -> new BlockItemBase(REINFORCED_BRICK.get()));
    public static final RegistryObject<Item> CRYSTALLIZATION_CHAMBER_PART_ITEM = ITEMS.register("crystallization_chamber_part", () -> new BlockItemBase(CRYSTALLIZATION_CHAMBER_PART.get()));
    public static final RegistryObject<Item> BROKEN_GLASS_BUNCH_ITEM = ITEMS.register("broken_glass_bunch", () -> new BlockItemBase(BROKEN_GLASS_BUNCH.get()));
    public static final RegistryObject<Item> STEEL_FRAME_ITEM = ITEMS.register("steel_frame", () -> new BlockItemBase(STEEL_FRAME.get()));
    public static final RegistryObject<Item> MANAGER_ITEM = ITEMS.register("manager", () -> new BlockItemBase(MANAGER.get()));
    public static final RegistryObject<Item> GENERATOR_ITEM = ITEMS.register("generator", () -> new BlockItemBase(GENERATOR.get()));
    public static final RegistryObject<Item> BAUXITE_ORE_ITEM = ITEMS.register("bauxite_ore", () -> new BlockItemBase(BAUXITE_ORE.get()));
    public static final RegistryObject<Item> GARNET_ORE_ITEM = ITEMS.register("garnet_ore", () -> new BlockItemBase(GARNET_ORE.get()));
    public static final RegistryObject<Item> ERBIUM_ORE_ITEM = ITEMS.register("erbium_ore", () -> new BlockItemBase(ERBIUM_ORE.get()));
    public static final RegistryObject<Item> NEODYMIUM_ORE_ITEM = ITEMS.register("neodymium_ore", () -> new BlockItemBase(NEODYMIUM_ORE.get()));
    public static final RegistryObject<Item> STEEL_BLOCK_ITEM = ITEMS.register("steel_block", () -> new BlockItemBase(STEEL_BLOCK.get()));
    public static final RegistryObject<Item> STEEL_PANEL_ITEM = ITEMS.register("steel_panel", () -> new BlockItemBase(STEEL_PANEL.get()));
    public static final RegistryObject<Item> SPROCKETEERER_ITEM = ITEMS.register("sprocketeerer", () -> new BlockItemBase(SPROCKETEERER.get()));
    public static final RegistryObject<Item> IRON_OXIDE_ITEM = ITEMS.register("iron_oxide", () -> new BlockItemBase(IRON_OXIDE.get()));
    public static final RegistryObject<Item> PRESSURIZED_GAS_CONTAINER_ITEM = ITEMS.register("pressurized_gas_container", () -> new BlockItemBase(PRESSURIZED_GAS_CONTAINER.get()));

    //special block items
    public static final RegistryObject<Item> CONVEYOR_ITEM = ITEMS.register("conveyor",
            () -> new WallOrFloorItem(CONVEYOR.get(),CONVEYOR_UP.get(), (new Item.Properties()).group(MachineBuiltWorldItemGroup.instance)));

    //fluids resource locations
    public static final ResourceLocation BATTERY_ACID_STILL_RL = new ResourceLocation(MOD_ID, "blocks/battery_acid_still");
    public static final ResourceLocation BATTERY_ACID_FLOWING_RL = new ResourceLocation(MOD_ID, "blocks/battery_acid_flowing");
    public static final ResourceLocation BATTERY_ACID_OVERLAY_RL = new ResourceLocation(MOD_ID, "blocks/battery_acid_overlay");
    public static final ResourceLocation RESIN_STILL_RL = new ResourceLocation(MOD_ID, "blocks/resin_still");
    public static final ResourceLocation RESIN_FLOWING_RL = new ResourceLocation(MOD_ID, "blocks/resin_flowing");
    public static final ResourceLocation RESIN_OVERLAY_RL = new ResourceLocation(MOD_ID, "blocks/resin_overlay");

    //fluids
    //TODO battery acid no worky, brokey somehow, learn how to fix it
    public static final RegistryObject<FlowingFluid> BATTERY_ACID_FLUID = FLUIDS.register("battery_acid_fluid",
            () -> new ForgeFlowingFluid.Source(RegistryHandler.BATTERY_ACID_PROPERTIES));
    public static final RegistryObject<FlowingFluid> BATTERY_ACID_FLOWING = FLUIDS.register("battery_acid_flowing",
            () -> new ForgeFlowingFluid.Flowing(RegistryHandler.BATTERY_ACID_PROPERTIES));
    public static final ForgeFlowingFluid.Properties BATTERY_ACID_PROPERTIES = new ForgeFlowingFluid.Properties(() -> BATTERY_ACID_FLUID.get(),
            () -> BATTERY_ACID_FLOWING.get(), FluidAttributes.builder(BATTERY_ACID_STILL_RL, BATTERY_ACID_FLOWING_RL)
            .sound(SoundEvents.ENTITY_VEX_CHARGE, SoundEvents.ENTITY_VEX_CHARGE).density(5).overlay(BATTERY_ACID_OVERLAY_RL)).block(() -> RegistryHandler.BATTERY_ACID_BLOCK.get());
    //TODO do most visual and physical thigns
    public static final RegistryObject<ResinFluid.Flowing> RESIN_FLOWING = FLUIDS.register("resin_flowing", () -> new ResinFluid.Flowing());
    public static final RegistryObject<ResinFluid.Source> RESIN = FLUIDS.register("resin", () -> new ResinFluid.Source());
    //TODO do most visual and physical thigns
    //TODO work on recipe for it in mixer
    public static final RegistryObject<ResinFluid.Flowing> POLYURETHANE_FLOWING = FLUIDS.register("polyurethane_flowing", () -> new ResinFluid.Flowing());
    public static final RegistryObject<ResinFluid.Source> POLYURETHANE = FLUIDS.register("polyurethane", () -> new ResinFluid.Source());
    //fluid items
    public static final RegistryObject<Item> BATTERY_ACID_BUCKET = ITEMS.register("battery_acid",
            () -> new BucketItem(BATTERY_ACID_FLUID, new Item.Properties().group(MachineBuiltWorldItemGroup.instance)));
    public static final RegistryObject<Item> RESIN_BUCKET = ITEMS.register("resin_bucket",
            () -> new ResinBucketItem(0, RESIN));
    public static final RegistryObject<Item> POLYURETHANE_BUCKET = ITEMS.register("polyurethane_bucket",
            () -> new BucketItem(POLYURETHANE, new Item.Properties().group(MachineBuiltWorldItemGroup.instance).containerItem(Items.BUCKET).maxStackSize(1)));

    //fluid blocks
    public static final RegistryObject<FlowingFluidBlock> BATTERY_ACID_BLOCK = BLOCKS.register("battery_acid",
            () -> new FlowingFluidBlock(RegistryHandler.BATTERY_ACID_FLUID::get, Block.Properties.create(Material.WATER).noDrops()));
    public static final RegistryObject<FlowingFluidBlock> RESIN_BLOCK = BLOCKS.register("resin",
            () -> new FlowingFluidBlock(() -> RegistryHandler.RESIN.get(), Block.Properties.create(Material.WATER).noDrops()));
    public static final RegistryObject<FlowingFluidBlock> POYURETHANE_BLOCK = BLOCKS.register("polyurethane",
            () -> new FlowingFluidBlock(() -> RegistryHandler.POLYURETHANE.get(), Block.Properties.create(Material.WATER).noDrops()));


    //tile entities
    public static final RegistryObject<TileEntityType<AtomizerTile>> ATOMIZER_TILE =
            TILES.register("atomizer", () -> TileEntityType.Builder.create(AtomizerTile::new, ATOMIZER.get()).build(null));
    public static final RegistryObject<TileEntityType<TTTBatteryTile>> TTTBATTERY_TILE =
            TILES.register("tttbattery", () -> TileEntityType.Builder.create(TTTBatteryTile::new, TTTBATTERY.get()).build(null));
    public static final RegistryObject<TileEntityType<TTBatteryTile>> TTBATTERY_TILE =
            TILES.register("ttbattery", () -> TileEntityType.Builder.create(TTBatteryTile::new, TTBATTERY.get()).build(null));
    public static final RegistryObject<TileEntityType<TBatteryTile>> TBATTERY_TILE =
            TILES.register("tbattery", () -> TileEntityType.Builder.create(TBatteryTile::new, TBATTERY.get()).build(null));
    public static final RegistryObject<TileEntityType<BatteryPotTile>> BATTERY_POT_TILE =
            TILES.register("battery_pot", () -> TileEntityType.Builder.create(BatteryPotTile::new, BATTERY_POT.get()).build(null));
    public static final RegistryObject<TileEntityType<MixerTile>> MIXER_TILE =
            TILES.register("mixer", () -> TileEntityType.Builder.create(MixerTile::new, MIXER.get()).build(null));
    public static final RegistryObject<TileEntityType<FiberglassMouldTile>> FIBERGLASS_MOULD_TILE =
            TILES.register("fiberglass_mould", () -> TileEntityType.Builder.create(FiberglassMouldTile::new, FIBERGLASS_MOULD.get()).build(null));
    public static final RegistryObject<TileEntityType<CrusherTile>> CRUSHER_TILE =
            TILES.register("crusher", () -> TileEntityType.Builder.create(CrusherTile::new, CRUSHER.get()).build(null));
    public static final RegistryObject<TileEntityType<SmokeDetectorTile>> SMOKE_DETECTOR_TILE =
            TILES.register("smoke_detector", () -> TileEntityType.Builder.create(SmokeDetectorTile::new, SMOKE_DETECTOR.get()).build(null));
    public static final RegistryObject<TileEntityType<IntensiveHeatingOvenTile>> INTENSIVE_HEATING_OVEN_TILE =
            TILES.register("intensive_heating_oven", () -> TileEntityType.Builder.create(IntensiveHeatingOvenTile::new, INTENSIVE_HEATING_OVEN.get()).build(null));
    public static final RegistryObject<TileEntityType<CrystallizationChamberPartTile>> CRYSTALLIZATION_CHAMBER_PART_TILE =
            TILES.register("crystallization_chamber_part", () -> TileEntityType.Builder.create(CrystallizationChamberPartTile::new, CRYSTALLIZATION_CHAMBER_PART.get()).build(null));
    public static final RegistryObject<TileEntityType<WireTile>> WIRE_TILE =
            TILES.register("wire", () -> TileEntityType.Builder.create(WireTile::new, WIRE.get()).build(null));
    public static final RegistryObject<TileEntityType<GeneratorTile>> GENERATOR_TILE =
            TILES.register("generator", () -> TileEntityType.Builder.create(GeneratorTile::new, GENERATOR.get()).build(null));
    public static final RegistryObject<TileEntityType<MeltingPotTile>> MELTING_POT_TILE =
            TILES.register("melting_pot", () -> TileEntityType.Builder.create(MeltingPotTile::new, MELTING_POT.get()).build(null));
    public static final RegistryObject<TileEntityType<SprocketeererTile>> SPROCKETEER_TILE =
            TILES.register("sprocketeerer", () -> TileEntityType.Builder.create(SprocketeererTile::new, SPROCKETEERER.get()).build(null));
    public static final RegistryObject<TileEntityType<MillTile>> MILL_TILE =
            TILES.register("mill", () -> TileEntityType.Builder.create(MillTile::new, MILL.get()).build(null));
    public static final RegistryObject<TileEntityType<PressurizedGasContainerTile>> PRESSURIZED_GAS_CONTAINER_TILE =
            TILES.register("pressurized_gas_container", () -> TileEntityType.Builder.create(PressurizedGasContainerTile::new, PRESSURIZED_GAS_CONTAINER.get()).build(null));
    public static final RegistryObject<TileEntityType<DisassociatedAtomContainerTile>> DISASSOCIATED_ATOM_CONTAINER_TILE =
            TILES.register("disassaciated_atom_container", () -> TileEntityType.Builder.create(DisassociatedAtomContainerTile::new, PRESSURIZED_GAS_CONTAINER.get()).build(null));


    //Containers
    public static final RegistryObject<ContainerType<AtomizerContainer>> ATOMIZER_CONTAINER = CONTAINERS.register("atomizer", () -> IForgeContainerType.create((windowId, inv, data) -> {
        BlockPos pos = data.readBlockPos();
        World world = inv.player.getEntityWorld();
        return new AtomizerContainer(windowId, world, pos, inv, inv.player);
    }));
    public static final RegistryObject<ContainerType<TTTBatteryContainer>> TTTBATTERY_CONTAINER = CONTAINERS.register("tttbattery", () -> IForgeContainerType.create((windowId, inv, data) -> {
        BlockPos pos = data.readBlockPos();
        World world = inv.player.getEntityWorld();
        return new TTTBatteryContainer(windowId, world, pos, inv, inv.player);
    }));
    public static final RegistryObject<ContainerType<TTBatteryContainer>> TTBATTERY_CONTAINER = CONTAINERS.register("ttbattery", () -> IForgeContainerType.create((windowId, inv, data) -> {
        BlockPos pos = data.readBlockPos();
        World world = inv.player.getEntityWorld();
        return new TTBatteryContainer(windowId, world, pos, inv, inv.player);
    }));
    public static final RegistryObject<ContainerType<TBatteryContainer>> TBATTERY_CONTAINER = CONTAINERS.register("tbattery", () -> IForgeContainerType.create((windowId, inv, data) -> {
        BlockPos pos = data.readBlockPos();
        World world = inv.player.getEntityWorld();
        return new TBatteryContainer(windowId, world, pos, inv, inv.player);
    }));
    public static final RegistryObject<ContainerType<BatteryPotContainer>> BATTERY_POT_CONTAINER = CONTAINERS.register("battery_pot", () -> IForgeContainerType.create((windowId, inv, data) -> {
        BlockPos pos = data.readBlockPos();
        World world = inv.player.getEntityWorld();
        return new BatteryPotContainer(windowId, world, pos, inv, inv.player);
    }));
    public static final RegistryObject<ContainerType<MixerContainer>> MIXER_CONTAINER = CONTAINERS.register("mixer", () -> IForgeContainerType.create((windowId, inv, data) -> {
        BlockPos pos = data.readBlockPos();
        World world = inv.player.getEntityWorld();
        return new MixerContainer(windowId, world, inv, pos);
    }));

    public static final RegistryObject<ContainerType<CrusherContainer>> CRUSHER_CONTAINER = CONTAINERS.register("crusher", () -> IForgeContainerType.create((windowId, inv, data) -> {
        BlockPos pos = data.readBlockPos();
        World world = inv.player.getEntityWorld();
        return new CrusherContainer(windowId, world, inv, pos);
    }));

    public static final RegistryObject<ContainerType<IntensiveHeatingOvenContainer>> INTENSIVE_HEATING_OVEN_CONTAINER = CONTAINERS.register("intensive_heating_oven", () -> IForgeContainerType.create((windowId, inv, data) -> {
        BlockPos pos = data.readBlockPos();
        World world = inv.player.getEntityWorld();
        return new IntensiveHeatingOvenContainer(windowId, world, inv, pos);
    }));

    public static final RegistryObject<ContainerType<GeneratorContainer>> GENERATOR_CONTAINER = CONTAINERS.register("generator", () -> IForgeContainerType.create((windowId, inv, data) -> {
        BlockPos pos = data.readBlockPos();
        World world = inv.player.getEntityWorld();
        return new GeneratorContainer(windowId, world, pos, inv);
    }));
    public static final RegistryObject<ContainerType<MeltingPotContainer>> MELTING_POT_CONTAINER = CONTAINERS.register("melting_pot", () -> IForgeContainerType.create((windowId, inv, data) -> {
        BlockPos pos = data.readBlockPos();
        World world = inv.player.getEntityWorld();
        return new MeltingPotContainer(windowId, world, inv, pos);
    }));
    public static final RegistryObject<ContainerType<SprocketeererContainer>> SPROCKETEERER_CONTAINER = CONTAINERS.register("sprocketeer", () -> IForgeContainerType.create((windowId, inv, data) -> {
        BlockPos pos = data.readBlockPos();
        World world = inv.player.getEntityWorld();
        return new SprocketeererContainer(windowId, world, inv, pos);
    }));
    public static final RegistryObject<ContainerType<MillContainer>> MILL_CONTAINER = CONTAINERS.register("mill", () -> IForgeContainerType.create((windowId, inv, data) -> {
        BlockPos pos = data.readBlockPos();
        World world = inv.player.getEntityWorld();
        return new MillContainer(windowId, world, inv, pos);
    }));

    public static final RegistryObject<ContainerType<PressurizedGasContainerContainer>> PRESSURIZED_GAS_CONTAINER_CONTAINER = CONTAINERS.register("pressurized_gas_container", () -> IForgeContainerType.create((windowId, inv, data) -> {
        BlockPos pos = data.readBlockPos();
        World world = inv.player.getEntityWorld();
        return new PressurizedGasContainerContainer(windowId, world, inv, pos);
    }));

    //particles
    public static final RegistryObject<BasicParticleType> SPROCKETEER_CREATION_PARTICLE = PARTICLES.register("sprocketeer_creation_particle", () -> new BasicParticleType(false));

    //recipes
    //public static final IRecipeType<CrushingRecipe> CRUSHER_RECIPE = new CrusherRecipeType();
    public static final RegistryObject<IRecipeSerializer<CokeingRecipe>> COKEING_RECIPE = RECIPES_SERIALIZER.register("cokeing", () -> new CokeingRecipeSerializer(300));
    public static final RegistryObject<IRecipeSerializer<CrushingRecipe>> CRUSHING_RECIPE = RECIPES_SERIALIZER.register("crushing", () -> new CrushingRecipeSerializer(200));
    public static final RegistryObject<IRecipeSerializer<MeltingPotRecipe>> MELTING_POT_RECIPE = RECIPES_SERIALIZER.register("melting_pot", () -> new MeltingPotRecipeSerializer(1000));
    public static final RegistryObject<IRecipeSerializer<MixingRecipe>> MIXING_RECIPE = RECIPES_SERIALIZER.register("mixing", () -> new MixingRecipeSerializer(200));
    //TODO needed?
    public static final RegistryObject<IRecipeSerializer<SprocketeerRecipe>> SPROCKETEER_RECIPE = RECIPES_SERIALIZER.register("sprocketeer", () -> new SprocketeerSerializer(200));
    public static final RegistryObject<IRecipeSerializer<MillingRecipe>> MILLING_RECIPE = RECIPES_SERIALIZER.register("milling", () -> new MillingRecipeSerializer(200));
    //TODO  no workey
    public static final RegistryObject<IRecipeSerializer<UseToolRecipe>> USE_TOOL_RECIPE = RECIPES_SERIALIZER.register("use_tool", UseToolSerializer::new);
    public static final RegistryObject<IRecipeSerializer<AtomizingRecipe>> ATOMIZING_RECIPE = RECIPES_SERIALIZER.register("atomizing", () -> new AtomizingRecipeSerializer(200));



    public static class Tags {
        //public static final Tag<Fluid> RESIN = new FluidTags.Wrapper(new ResourceLocation(MachineBuiltWorld.MOD_ID, "resin"));
        public static final Tag<Block> SMOKY_SENSITIVE = new BlockTags.Wrapper(new ResourceLocation(MOD_ID, "smoky_sensitive"));
        public static final Tag<Item> CRUSHER_TEETH = new ItemTags.Wrapper(new ResourceLocation(MOD_ID, "crusher_teeth"));
        public static final Tag<Item> BULLETS = new ItemTags.Wrapper(new ResourceLocation("machinebuiltworld", "bullets"));
        public static final Tag<Item> ITEM_HEAT_INFUSEABLE = new ItemTags.Wrapper(new ResourceLocation(MOD_ID, "item_heat_infuseable"));

    }
    public static class ForgeTags {
        public static final Tag<Block> COPPER_ORE = new BlockTags.Wrapper(new ResourceLocation("forge", "ores/copper"));
        //public static final Tag<Item> ITEM_HEAT_INFUSEABLE = new ItemTags.Wrapper(new ResourceLocation("forge", "item_heat_infuseable"));

    }

    //Dimension
    //TODO energy core underneath ground
    //TODO make redstone no workey (make it do that on purpose)
    public static final RegistryObject<ModDimension> THE_MACHINE_BUILT_WORLD = DIMENSIONS.register("the_machine_built_world", () -> new TheMachineBuiltWorldModDimension());
    public static DimensionType machinebuiltworld;

    //Biomes
    //TODO not generating
    //TODO Potentially danderous alternative prefix as an error, idk why
    public static final RegistryObject<Biome> BARREN_LANDS = BIOMES
            .register("barren_lands",
                    () -> new TheMachineBuiltWorldBarrenLands(
                            new Biome.Builder().precipitation(Biome.RainType.SNOW).scale(1.5f).temperature(1.5f)
                                    .waterColor(16724639).waterFogColor(16762304)
                                    .surfaceBuilder(
                                            new ConfiguredSurfaceBuilder<SurfaceBuilderConfig>(
                                                    register("barren_lands_surface",
                                                            new TheMachineBuiltWorldBarrenLandsSurfaceBuilder(
                                                                    SurfaceBuilderConfig::deserialize)),
                                                    new SurfaceBuilderConfig(Blocks.COARSE_DIRT.getDefaultState(),
                                                            Blocks.DIRT.getDefaultState(),
                                                            Blocks.GRASS_BLOCK.getDefaultState())))
                                    .category(Biome.Category.PLAINS).downfall(0.5f).depth(0.12f).parent(null)));
    @SuppressWarnings("deprecation")
    private static <C extends ISurfaceBuilderConfig, F extends SurfaceBuilder<C>> F register(String key, F builderIn) {
        return (F) (Registry.<SurfaceBuilder<?>>register(Registry.SURFACE_BUILDER, key, builderIn));
    }


    public static void registerBiomes() {
        registerBiome(BARREN_LANDS.get(), BiomeDictionary.Type.PLAINS, BiomeDictionary.Type.SPARSE);
    }

    private static void registerBiome(Biome biome, BiomeDictionary.Type... types) {
        // the line below will make it spawn in the overworld
        //BiomeManager.addBiome(BiomeManager.BiomeType.COOL, new BiomeManager.BiomeEntry(biome, 100));
        BiomeDictionary.addTypes(biome, types);
        //BiomeManager.addSpawnBiome(biome);
    }



    //Stats
    //public static final RegistryObject<StatType<Block>> INTERACT_WITH_COKE_OVEN = STATS.register("interact_with_coke_oven", () -> new StatType<Block>(BLOCKS.).getRegistry());

    public static class MoreStats extends Stats{
        public static final ResourceLocation INTERACT_WITH_COKE_OVEN = registerCustom("interact_with_coke_oven", IStatFormatter.DEFAULT);
        private static ResourceLocation registerCustom(String key, IStatFormatter formatter) {
            ResourceLocation resourcelocation = new ResourceLocation(key);
            Registry.register(Registry.CUSTOM_STAT, key, resourcelocation);
            CUSTOM.get(resourcelocation, formatter);
            return resourcelocation;
        }

        private static <T> StatType<T> registerType(String key, Registry<T> registry) {
            return Registry.register(Registry.STATS, key, new StatType<>(registry));
        }
    }

    //NBT Keys
    public static final String NBT_AMMO = "ammo";
}
