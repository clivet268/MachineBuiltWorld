package com.Clivet268.MachineBuiltWorld.client.renderer.tileentity;

import com.Clivet268.MachineBuiltWorld.MachineBuiltWorld;
import com.Clivet268.MachineBuiltWorld.state.CrusherTeethProperty;
import com.Clivet268.MachineBuiltWorld.tileentity.CrusherTile;
import com.Clivet268.MachineBuiltWorld.util.RegistryHandler;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.model.Material;
import net.minecraft.client.renderer.texture.AtlasTexture;
import net.minecraft.util.ResourceLocation;

import java.util.function.Consumer;

//TODO needed?
public class MoreAtlases {
    public static final ResourceLocation CRUSHER_ATLAS = new ResourceLocation(MachineBuiltWorld.MOD_ID,"textures/atlas/crusher.png");

    private static final RenderType CRUSHER_TYPE = RenderType.getEntityCutout(CRUSHER_ATLAS);
    private static final RenderType SOLID_BLOCK_TYPE = RenderType.getEntitySolid(AtlasTexture.LOCATION_BLOCKS_TEXTURE);
    private static final RenderType CUTOUT_BLOCK_TYPE = RenderType.getEntityCutout(AtlasTexture.LOCATION_BLOCKS_TEXTURE);

    public static final Material COPPER = getChestMaterial("copper");
    public static final Material IRON = getChestMaterial("iron");
    public static final Material GOLD = getChestMaterial("gold");
    public static final Material STEEL = getChestMaterial("steel");
    public static final Material DIAMOND = getChestMaterial("diamond");
    public static final Material EMPTY = getChestMaterial("empty");

    public static RenderType getCrusherType() {
        return CRUSHER_TYPE;
    }

    public static RenderType getSolidBlockType() {
        return SOLID_BLOCK_TYPE;
    }

    public static RenderType getCutoutBlockType() {
        return CUTOUT_BLOCK_TYPE;
    }

    public static void collectAllMaterials(Consumer<Material> p_228775_0_) {
        p_228775_0_.accept(COPPER);
        p_228775_0_.accept(IRON);
        p_228775_0_.accept(GOLD);
        p_228775_0_.accept(STEEL);
        p_228775_0_.accept(DIAMOND);
    }

    private static Material getChestMaterial(String p_228774_0_) {
        return new Material(CRUSHER_ATLAS, new ResourceLocation(MachineBuiltWorld.MOD_ID,"entity/crusher/" + p_228774_0_));
    }

    public static Material getChestMaterial(CrusherTile tile, CrusherTeethProperty teethProperty) {
        if (tile.whatsadateethtype().getItem() == RegistryHandler.COPPER_CRUSHER_TEETH.get()) {
            return getChestMaterial(COPPER);
        }
        else if(tile.whatsadateethtype().getItem() == RegistryHandler.IRON_CRUSHER_TEETH.get()){
            return getChestMaterial(IRON);
        }
        else if(tile.whatsadateethtype().getItem() == RegistryHandler.GOLD_CRUSHER_TEETH.get()){
            return getChestMaterial(GOLD);
        }
        else if(tile.whatsadateethtype().getItem() == RegistryHandler.STEEL_CRUSHER_TEETH.get()){
            return getChestMaterial(STEEL);
        }
        else if(tile.whatsadateethtype().getItem() == RegistryHandler.DIAMOND_CRUSHER_TEETH.get()){
            return getChestMaterial(DIAMOND);
        }
        else {return EMPTY;}

    }

    private static Material getChestMaterial(Material p_228772_1_) {

                return p_228772_1_;

    }
}
