package com.Clivet268.MachineBuiltWorld.tileentity;

import com.Clivet268.MachineBuiltWorld.MachineBuiltWorld;
import net.minecraft.tileentity.TileEntityType;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

public class ModTileEntityTypes {

    public static final DeferredRegister<TileEntityType<?>> TILE_ENTITY_TYPES = new DeferredRegister<>(
            ForgeRegistries.TILE_ENTITIES, MachineBuiltWorld.MOD_ID);

   // public static final RegistryObject<TileEntityType<AtomizerTile>> ATOMIZER = TILE_ENTITY_TYPES.register("atomizer",
     //       () -> TileEntityType.Builder.create(AtomizerTile::new, RegistryHandler.ATOMIZER.get()).build(null));

  //  public static final TileEntityType<AtomizerTile> ATOMIZER = TileEntityType.Builder.create(AtomizerTile::new, RegistryHandler.ATOMIZER.get()).build(null);
}