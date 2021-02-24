package com.Clivet268.MachineBuiltWorld.util;

import com.Clivet268.MachineBuiltWorld.MachineBuiltWorld;
import com.Clivet268.MachineBuiltWorld.blocks.*;
import com.Clivet268.MachineBuiltWorld.entity.BulletEntity;
import com.Clivet268.MachineBuiltWorld.fluids.ResinFluid;
import com.Clivet268.MachineBuiltWorld.inventory.Containers.*;
import com.Clivet268.MachineBuiltWorld.inventory.crafting.CokeingRecipe;
import com.Clivet268.MachineBuiltWorld.inventory.crafting.CokeingRecipeSerializer;
import com.Clivet268.MachineBuiltWorld.items.*;
import com.Clivet268.MachineBuiltWorld.items.armor.ModArmorMaterial;
import com.Clivet268.MachineBuiltWorld.tileentity.*;
import com.Clivet268.MachineBuiltWorld.tools.CraftingToolsItem;
import com.Clivet268.MachineBuiltWorld.tools.ModItemTier;
import com.Clivet268.MachineBuiltWorld.tools.MultimeterItem;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.block.FlowingFluidBlock;
import net.minecraft.block.material.Material;
import net.minecraft.data.BlockTagsProvider;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.ItemTagsProvider;
import net.minecraft.entity.EntityClassification;
import net.minecraft.entity.EntityType;
import net.minecraft.fluid.FlowingFluid;
import net.minecraft.fluid.Fluid;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.inventory.container.Container;
import net.minecraft.inventory.container.ContainerType;
import net.minecraft.inventory.container.FurnaceContainer;
import net.minecraft.item.*;
import net.minecraft.item.crafting.IRecipeSerializer;
import net.minecraft.item.crafting.IRecipeType;
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
import net.minecraftforge.common.extensions.IForgeContainerType;
import net.minecraftforge.fluids.FluidAttributes;
import net.minecraftforge.fluids.ForgeFlowingFluid;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.fml.network.NetworkHooks;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

//import com.Clivet268.MachineBuiltWorld.blocks.BatteryPot;

public class RegistryHandler {
    //public static final DeferredRegister<LootTable> LOOT_TABLES = new DeferredRegister<>(ForgeRegistries.LOOT_MODIFIER_SERIALIZERS)
    public static final DeferredRegister<IRecipeSerializer<?>> RECIPES_SERIALIZER = new DeferredRegister<>(ForgeRegistries.RECIPE_SERIALIZERS, MachineBuiltWorld.MOD_ID);
    public static final DeferredRegister<SoundEvent> SOUNDS = new DeferredRegister<>(ForgeRegistries.SOUND_EVENTS, MachineBuiltWorld.MOD_ID);
    public static final DeferredRegister<Fluid> FLUIDS = new DeferredRegister<>(ForgeRegistries.FLUIDS, MachineBuiltWorld.MOD_ID);
    public static final DeferredRegister<Item> ITEMS = new DeferredRegister<>(ForgeRegistries.ITEMS, MachineBuiltWorld.MOD_ID);
    public static final DeferredRegister<Block> BLOCKS = new DeferredRegister<>(ForgeRegistries.BLOCKS, MachineBuiltWorld.MOD_ID);
    private static final DeferredRegister<TileEntityType<?>> TILES = new DeferredRegister<>(ForgeRegistries.TILE_ENTITIES, MachineBuiltWorld.MOD_ID);
    private static final DeferredRegister<ContainerType<?>> CONTAINERS = new DeferredRegister<>(ForgeRegistries.CONTAINERS, MachineBuiltWorld.MOD_ID);
    public static final DeferredRegister<EntityType<?>> ENTITIES = new DeferredRegister<>(ForgeRegistries.ENTITIES, MachineBuiltWorld.MOD_ID);
    public static final DeferredRegister<StatType<?>> STATS = new DeferredRegister<>(ForgeRegistries.STAT_TYPES , MachineBuiltWorld.MOD_ID);

    public static void init() {
        RECIPES_SERIALIZER.register(FMLJavaModLoadingContext.get().getModEventBus());
        SOUNDS.register(FMLJavaModLoadingContext.get().getModEventBus());
        FLUIDS.register(FMLJavaModLoadingContext.get().getModEventBus());
        ITEMS.register(FMLJavaModLoadingContext.get().getModEventBus());
        BLOCKS.register(FMLJavaModLoadingContext.get().getModEventBus());
        TILES.register(FMLJavaModLoadingContext.get().getModEventBus());
        CONTAINERS.register(FMLJavaModLoadingContext.get().getModEventBus());
        ENTITIES.register(FMLJavaModLoadingContext.get().getModEventBus());
    }

    //sounds
    public static final RegistryObject<SoundEvent> BULLET_HIT = SOUNDS.register("bullet_hit",() -> new SoundEvent(new ResourceLocation(MachineBuiltWorld.MOD_ID,"entity.bullet_hit")));
    public static final RegistryObject<SoundEvent> PISTOL_SHOOT = SOUNDS.register("pistol_shoot",() -> new SoundEvent(new ResourceLocation(MachineBuiltWorld.MOD_ID,"item.pistol_shoot")));
    //entities

    //projectiles
    public static final RegistryObject<EntityType<BulletEntity>> BULLET_ENTITY = ENTITIES.register("bullet_projectile",
            () -> EntityType.Builder.<BulletEntity>create(BulletEntity::new, EntityClassification.MISC).size(0.5F, 0.9F).build("bullet_projectile"));


    //items
    public static final RegistryObject<Item> GEAR = ITEMS.register("gear", ItemBase::new);
    public static final RegistryObject<Item> COPPER_INGOT = ITEMS.register("copper_ingot", ItemBase::new);
    public static final RegistryObject<Item> SILVER_INGOT = ITEMS.register("silver_ingot", ItemBase::new);
    public static final RegistryObject<Item> CRUDE_ELECTROMAGNET = ITEMS.register("crude_electromagnet", ItemBase::new);
    public static final RegistryObject<Item> MAGNET = ITEMS.register("magnet", ItemBase::new);
    public static final RegistryObject<Item> GRAPHENE = ITEMS.register("graphene", ItemBase::new);
    public static final RegistryObject<Item> TRACK_GEAR = ITEMS.register("track_gear", ItemBase::new);
    public static final RegistryObject<Item> IRON_ROD = ITEMS.register("iron_rod", ItemBase::new);
    public static final RegistryObject<Item> IRON_AXLE = ITEMS.register("iron_axle", ItemBase::new);
    public static final RegistryObject<Item> BORON_DUST = ITEMS.register("boron_dust", ItemBase::new);
    public static final RegistryObject<Item> BORON_CHUNK = ITEMS.register("boron_chunk", ItemBase::new);
    public static final RegistryObject<Item> DIRTY_BORON_DUST = ITEMS.register("dirty_boron_dust", ItemBase::new);
    public static final RegistryObject<Item> CIRCUT_BOARD = ITEMS.register("circut_board", ItemBase::new);
    public static final RegistryObject<Item> SILICONE_GLOB = ITEMS.register("silicone_glob", ItemBase::new);
    public static final RegistryObject<Item> WOOD_GEAR = ITEMS.register("wood_gear", ItemBase::new);
    public static final RegistryObject<Item> EPOXY = ITEMS.register("epoxy", ItemBase::new);
    public static final RegistryObject<Item> HIGH_PERFORMANCE_EPOXY = ITEMS.register("high_performance_epoxy", ItemBase::new);
    public static final RegistryObject<Item> SUPER_PERFORMANCE_EPOXY = ITEMS.register("super_performance_epoxy", ItemBase::new);
    public static final RegistryObject<Item> FIBER_GLASS = ITEMS.register("fiber_glass", ItemBase::new);
    public static final RegistryObject<Item> HIGH_PERFORMANCE_CIRCUT_BOARD = ITEMS.register("high_performance_circut_board", ItemBase::new);
    public static final RegistryObject<Item> SUPER_PERFORMANCE_CIRCUT_BOARD = ITEMS.register("super_performance_circut_board", ItemBase::new);
    public static final RegistryObject<Item> GLASH_SHARDS = ITEMS.register("glass_shards", ItemBase::new);
    public static final RegistryObject<Item> ION_SHELL = ITEMS.register("ion_shell", ItemBase::new);
    public static final RegistryObject<Item> LASER_SHELL = ITEMS.register("laser_shell", ItemBase::new);
    public static final RegistryObject<Item> MICRO_RESONATOR = ITEMS.register("micro_resonator", ItemBase::new);
    public static final RegistryObject<Item> RESONATOR = ITEMS.register("resonator", ItemBase::new);
    public static final RegistryObject<Item> LARGE_RESONATOR = ITEMS.register("large_resonator", ItemBase::new);
    public static final RegistryObject<Item> STEEL_INGOT = ITEMS.register("steel_ingot", ItemBase::new);
    public static final RegistryObject<Item> PIG_IRON_INGOT = ITEMS.register("pig_iron_ingot", ItemBase::new);
    public static final RegistryObject<Item> COKE = ITEMS.register("coke", ItemBase::new);

    //blocks
    public static final RegistryObject<Block> COPPER_BLOCK = BLOCKS.register("copper_block", CopperBlock::new);
    public static final RegistryObject<Block> BATTERY_POT = BLOCKS.register("battery_pot", BatteryPot::new);
    public static final RegistryObject<Block> MAGNETS = BLOCKS.register("magnets", Magnets::new);
    public static final RegistryObject<Block> CRUDE_ELECTROMAGNETA = BLOCKS.register("crude_electromagneta", CrudeElectromagnetA::new);
    public static final RegistryObject<Block> WIRE = BLOCKS.register("wire", Wire::new);
    public static final RegistryObject<Block> MELTING_POT = BLOCKS.register("melting_pot", MeltingPot::new);
    public static final RegistryObject<Block> ATOMIZER = BLOCKS.register("atomizer", Atomizer::new);
    public static final RegistryObject<Block> MOULDING_SAND = BLOCKS.register("moulding_sand", MouldingSand::new);
    public static final RegistryObject<Block> TRACK = BLOCKS.register("track", Track::new);
    public static final RegistryObject<Block> BORONATED_GLASS = BLOCKS.register("boronated_glass", BoronatedGlass::new);
    public static final RegistryObject<Block> COPPER_ORE = BLOCKS.register("copper_ore", CopperOre::new);
    public static final RegistryObject<Block> BORON_ORE = BLOCKS.register("boron_ore", BoronOre::new);
    public static final RegistryObject<Block> TTTBATTERY = BLOCKS.register("tttbattery", TTTBattery::new);
    public static final RegistryObject<Block> TTBATTERY = BLOCKS.register("ttbattery", TTBattery::new);
    public static final RegistryObject<Block> TBATTERY = BLOCKS.register("tbattery", TBattery::new);
    public static final RegistryObject<Block> HEAVY_DUTY_POT = BLOCKS.register("heavy_duty_pot", HeavyDutyPot::new);
    public static final RegistryObject<Block> MILL = BLOCKS.register("mill", Mill::new);
    public static final RegistryObject<Block> MIXER = BLOCKS.register("mixer", Mixer::new);
    public static final RegistryObject<Block> SMOKE_DETECTOR = BLOCKS.register("smoke_detector", SmokeDetector::new);
    public static final RegistryObject<Block> FIBERGLASS_MOULD = BLOCKS.register("fiberglass_mould", FiberglassMould::new);
    public static final RegistryObject<Block> CRUSHER = BLOCKS.register("crusher", Crusher::new);
    public static final RegistryObject<Block> OXYGEN_FURNACE = BLOCKS.register("oxygen_furnace", Crusher::new);
    public static final RegistryObject<Block> COKE_OVEN = BLOCKS.register("coke_oven", CokeOven::new);
    public static final RegistryObject<Block> REINFORCED_BRICK = BLOCKS.register("reinforced_brick", ReinforcedBrick::new);

    //weapons
    public static final RegistryObject<SwordItem> COPPER_SWORD = ITEMS.register("copper_sword", () ->
            new SwordItem(ModItemTier.COPPER, 2, -2.4f, new Item.Properties().group(ItemGroup.COMBAT)));
    public static final RegistryObject<LaserPistolItem> LASER_PISTOL = ITEMS.register("laser_pistol", ()->
            new LaserPistolItem(new Item.Properties().group(ItemGroup.COMBAT).maxDamage(500)));
    public static final RegistryObject<BulletItem> BULLET = ITEMS.register("bullet", ()->
            new BulletItem(new Item.Properties().group(ItemGroup.COMBAT)));
    public static final RegistryObject<IncendiaryBulletItem> INCENDIARY_BULLET = ITEMS.register("incendiary_bullet", ()->
            new IncendiaryBulletItem(new Item.Properties().group(ItemGroup.COMBAT)));
    public static final RegistryObject<LaserPisolMagItem> LASER_PISTOL_MAG = ITEMS.register("laser_pistol_mag", ()->
            new LaserPisolMagItem(new Item.Properties().group(ItemGroup.COMBAT).maxStackSize(8)));


    /*
    public static final RegistryObject<IncendiaryBulletItem> INCENDIARY_BULLET = ITEMS.register("incendiary_bullet", ()->
            new IncendiaryBulletItem(new Item.Properties().group(ItemGroup.COMBAT)));

     */

    //tools
    public static final RegistryObject<CraftingToolsItem> WIRE_CUTTERS = ITEMS.register("wire_cutters", () ->
            new CraftingToolsItem(ModItemTier.CRAFTING_TOOLS, 2, -2.4f, new Item.Properties().group(ItemGroup.TOOLS)));
    public static final RegistryObject<CraftingToolsItem> PLIERS = ITEMS.register("pliers", () ->
            new CraftingToolsItem(ModItemTier.CRAFTING_TOOLS, 2, -2.4f, new Item.Properties().group(ItemGroup.TOOLS)));
    public static final RegistryObject<MultimeterItem> MULTIMETER = ITEMS.register("multimeter", () ->
            new MultimeterItem(ModItemTier.MULTIMETER, 2, -2.4f, new Item.Properties().group(ItemGroup.TOOLS)));


    //armor
    public static final RegistryObject<ArmorItem> COPPER_HELMET = ITEMS.register("copper_helmet", () ->
            new ArmorItem(ModArmorMaterial.COPPER, EquipmentSlotType.HEAD, new Item.Properties().group(ItemGroup.MATERIALS)));
    public static final RegistryObject<ArmorItem> COPPER_CHESTPLATE = ITEMS.register("copper_chestplate", () ->
            new ArmorItem(ModArmorMaterial.COPPER, EquipmentSlotType.CHEST, new Item.Properties().group(ItemGroup.MATERIALS)));
    public static final RegistryObject<ArmorItem> COPPER_LEGGINGS = ITEMS.register("copper_leggings", () ->
            new ArmorItem(ModArmorMaterial.COPPER, EquipmentSlotType.LEGS, new Item.Properties().group(ItemGroup.MATERIALS)));
    public static final RegistryObject<ArmorItem> COPPER_BOOTS = ITEMS.register("copper_boots", () ->
            new ArmorItem(ModArmorMaterial.COPPER, EquipmentSlotType.FEET, new Item.Properties().group(ItemGroup.MATERIALS)));
    public static final RegistryObject<ArmorItem> POWER_PUNCHERS = ITEMS.register("power_punchers", () ->
            new ArmorItem(ModArmorMaterial.COPPER, EquipmentSlotType.HEAD, new Item.Properties().group(ItemGroup.MATERIALS)));


    //block items
    public static final RegistryObject<Item> COPPER_BLOCK_ITEM = ITEMS.register("copper_block", () -> new BlockItemBase(COPPER_BLOCK.get()));
    public static final RegistryObject<Item> BATTERY_POT_ITEM = ITEMS.register("battery_pot", () -> new BlockItemBase(BATTERY_POT.get()));
    public static final RegistryObject<Item> MAGNETS_ITEM = ITEMS.register("magnets", () -> new BlockItemBase(MAGNETS.get()));
    public static final RegistryObject<Item> CRUDE_ELECTROMAGNETA_ITEM = ITEMS.register("crude_electromagneta", () -> new BlockItemBase(CRUDE_ELECTROMAGNETA.get()));
    public static final RegistryObject<Item> WIRE_ITEM = ITEMS.register("wire", () -> new BlockItemBase(WIRE.get()));
    public static final RegistryObject<Item> MELTING_POT_ITEM = ITEMS.register("melting_pot", () -> new BlockItemBase(MELTING_POT.get()));
    public static final RegistryObject<Item> ATOMIZER_ITEM = ITEMS.register("atomizer", () -> new BlockItemBase(ATOMIZER.get()));
    public static final RegistryObject<Item> MOULDING_SAND_ITEM = ITEMS.register("moulding_sand", () -> new BlockItemBase(MOULDING_SAND.get()));
    public static final RegistryObject<Item> TRACK_ITEM = ITEMS.register("track", () -> new BlockItemBase(TRACK.get()));
    public static final RegistryObject<Item> BORONATED_GLASS_ITEM = ITEMS.register("boronated_glass", () -> new BlockItemBase(BORONATED_GLASS.get()));
    public static final RegistryObject<Item> COPPER_ORE_ITEM = ITEMS.register("copper_ore", () -> new BlockItemBase(COPPER_ORE.get()));
    public static final RegistryObject<Item> BORON_ORE_ITEM = ITEMS.register("boron_ore", () -> new BlockItemBase(BORON_ORE.get()));
    public static final RegistryObject<Item> TTTBATTERY_ITEM = ITEMS.register("tttbattery", () -> new BlockItemBase(TTTBATTERY.get()));
    public static final RegistryObject<Item> TTBATTERY_ITEM = ITEMS.register("ttbattery", () -> new BlockItemBase(TTBATTERY.get()));
    public static final RegistryObject<Item> TBATTERY_ITEM = ITEMS.register("tbattery", () -> new BlockItemBase(TBATTERY.get()));
    public static final RegistryObject<Item> HEAVY_DUTY_POT_ITEM = ITEMS.register("heavy_duty_pot", () -> new BlockItemBase(HEAVY_DUTY_POT.get()));
    public static final RegistryObject<Item> MACHINING_BENCH_ITEM = ITEMS.register("mill", () -> new BlockItemBase(MILL.get()));
    public static final RegistryObject<Item> MIXER_ITEM = ITEMS.register("mixer", () -> new BlockItemBase(MIXER.get()));
    public static final RegistryObject<Item> SMOKE_DETECTOR_ITEM = ITEMS.register("smoke_detector", () -> new BlockItemBase(SMOKE_DETECTOR.get()));
    public static final RegistryObject<Item> FIBERGLASS_MOULD_ITEM = ITEMS.register("fiberglass_mould", () -> new BlockItemBase(FIBERGLASS_MOULD.get()));
    public static final RegistryObject<Item> CRUSHER_ITEM = ITEMS.register("crusher", () -> new BlockItemBase(CRUSHER.get()));
    public static final RegistryObject<Item> COKE_OVEN_ITEM = ITEMS.register("coke_oven", () -> new BlockItemBase(COKE_OVEN.get()));
    public static final RegistryObject<Item> REINFORCED_BRICK_ITEM = ITEMS.register("reinforced_brick", () -> new BlockItemBase(REINFORCED_BRICK.get()));

    //fluids resource locations
    public static final ResourceLocation BATTERY_ACID_STILL_RL = new ResourceLocation(MachineBuiltWorld.MOD_ID, "blocks/battery_acid_still");
    public static final ResourceLocation BATTERY_ACID_FLOWING_RL = new ResourceLocation(MachineBuiltWorld.MOD_ID, "blocks/battery_acid_flowing");
    public static final ResourceLocation BATTERY_ACID_OVERLAY_RL = new ResourceLocation(MachineBuiltWorld.MOD_ID, "blocks/battery_acid_overlay");
    public static final ResourceLocation RESIN_STILL_RL = new ResourceLocation(MachineBuiltWorld.MOD_ID, "blocks/resin_still");
    public static final ResourceLocation RESIN_FLOWING_RL = new ResourceLocation(MachineBuiltWorld.MOD_ID, "blocks/resin_flowing");
    public static final ResourceLocation RESIN_OVERLAY_RL = new ResourceLocation(MachineBuiltWorld.MOD_ID, "blocks/resin_overlay");

    //fluids
    public static final RegistryObject<FlowingFluid> BATTERY_ACID_FLUID = FLUIDS.register("battery_acid_fluid",
            () -> new ForgeFlowingFluid.Source(RegistryHandler.BATTERY_ACID_PROPERTIES));
    public static final RegistryObject<FlowingFluid> BATTERY_ACID_FLOWING = FLUIDS.register("battery_acid_flowing",
            () -> new ForgeFlowingFluid.Flowing(RegistryHandler.BATTERY_ACID_PROPERTIES));
    public static final ForgeFlowingFluid.Properties BATTERY_ACID_PROPERTIES = new ForgeFlowingFluid.Properties(() -> BATTERY_ACID_FLUID.get(),
            () -> BATTERY_ACID_FLOWING.get(), FluidAttributes.builder(BATTERY_ACID_STILL_RL, BATTERY_ACID_FLOWING_RL)
            .sound(SoundEvents.ENTITY_VEX_CHARGE, SoundEvents.ENTITY_VEX_CHARGE).density(5).overlay(BATTERY_ACID_OVERLAY_RL)).block(() -> RegistryHandler.BATTERY_ACID_BLOCK.get());
    public static final RegistryObject<ResinFluid.Flowing> RESIN_FLOWING = FLUIDS.register("resin_flowing", () -> new ResinFluid.Flowing());
    public static final RegistryObject<ResinFluid.Source> RESIN = FLUIDS.register("resin", () -> new ResinFluid.Source());

    //fluid items
    public static final RegistryObject<Item> BATTERY_ACID_BUCKET = ITEMS.register("battery_acid",
            () -> new BucketItem(BATTERY_ACID_FLUID, new Item.Properties().group(ItemGroup.MATERIALS)));
    public static final RegistryObject<Item> RESIN_BUCKET = ITEMS.register("resin_bucket",
            () -> new BucketItem(RESIN, new Item.Properties().group(ItemGroup.MATERIALS).containerItem(Items.BUCKET).maxStackSize(1)));

    //fluid blocks
    public static final RegistryObject<FlowingFluidBlock> BATTERY_ACID_BLOCK = BLOCKS.register("battery_acid",
            () -> new FlowingFluidBlock(() -> RegistryHandler.BATTERY_ACID_FLUID.get(), Block.Properties.create(Material.WATER).noDrops()));
    public static final RegistryObject<FlowingFluidBlock> RESIN_BLOCK = BLOCKS.register("resin",
            () -> new FlowingFluidBlock(() -> RegistryHandler.RESIN.get(), Block.Properties.create(Material.WATER).noDrops()));


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
    public static final RegistryObject<TileEntityType<CokeOvenTile>> COKE_OVEN_TILE =
            TILES.register("coke_oven", () -> TileEntityType.Builder.create(CokeOvenTile::new, COKE_OVEN.get()).build(null));

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
        return new MixerContainer(windowId, world, pos, inv, inv.player);
    }));
    /*
    public static final RegistryObject<ContainerType<CrusherContainer>> CRUSHER_CONTAINER = CONTAINERS.register("crusher", () -> IForgeContainerType.create((windowId, inv, data) -> {
        BlockPos pos = data.readBlockPos();
        World world = inv.player.getEntityWorld();
        return new CrusherContainer(windowId, world, pos, inv, inv.player);
    }));

     */
    public static final RegistryObject<ContainerType<CokeOvenContainer>> COKE_OVEN_CONTAINER = CONTAINERS.register("coke_oven", () -> IForgeContainerType.create((windowId, inv, data) -> {
        BlockPos pos = data.readBlockPos();
        System.out.println(pos);
        World world = inv.player.getEntityWorld();
        return new CokeOvenContainer(windowId, world, inv, pos);
    }));


    //translucent layers

    //recipes
    //public static final IRecipeType<CrushingRecipe> CRUSHER_RECIPE = new CrusherRecipeType();
    public static final RegistryObject<IRecipeSerializer<?>> COKEING_RECIPE = RECIPES_SERIALIZER.register("cokeing", () -> new CokeingRecipeSerializer(300));

    //tags
    public static class MoreBlockTagsProvider extends BlockTagsProvider
    {

        public MoreBlockTagsProvider(DataGenerator generatorIn) {
            super(generatorIn);
        }

        @Override
        protected void registerTags() {
            this.getBuilder(Tags.SMOKY).add(Blocks.CAMPFIRE);
            this.getBuilder(Tags.SMOKY).add(Blocks.FIRE);
        }

    }
    public static class MoreItemTagsProvider extends ItemTagsProvider
    {

        public MoreItemTagsProvider(DataGenerator generatorIn) {
            super(generatorIn);
        }

        @Override
        protected void registerTags() {
            this.getBuilder(Tags.ION_SHELLS).add(RegistryHandler.ION_SHELL.get());
            this.getBuilder(Tags.BULLETS).add(RegistryHandler.BULLET.get());
            this.getBuilder(Tags.BULLETS).add(RegistryHandler.INCENDIARY_BULLET.get());
        }

    }
    public static class Tags {
        //public static final Tag<Fluid> RESIN = new FluidTags.Wrapper(new ResourceLocation(MachineBuiltWorld.MOD_ID, "resin"));
        public static final Tag<Block> SMOKY = new BlockTags.Wrapper(new ResourceLocation(MachineBuiltWorld.MOD_ID, "smoky"));
        public static final Tag<Item> BULLETS = new ItemTags.Wrapper(new ResourceLocation(MachineBuiltWorld.MOD_ID, "bullets"));
        public static final Tag<Item> ION_SHELLS = new ItemTags.Wrapper(new ResourceLocation(MachineBuiltWorld.MOD_ID, "ion_shells"));

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
