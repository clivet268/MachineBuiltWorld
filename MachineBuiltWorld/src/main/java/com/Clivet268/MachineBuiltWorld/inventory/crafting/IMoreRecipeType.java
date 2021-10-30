package com.Clivet268.MachineBuiltWorld.inventory.crafting;

import net.minecraft.item.crafting.IRecipeType;

public interface IMoreRecipeType extends IRecipeType {
    interface MoreCokes extends  IRecipeType<CokeingRecipe>{
        IRecipeType<CokeingRecipe> COKEING = IRecipeType.register("cokeing");
    }
    interface MoreCrushes extends  IRecipeType<CrushingRecipe>{
        IRecipeType<CrushingRecipe> CRUSHING = IRecipeType.register("crushing");
    }
    interface MoreMeltings extends  IRecipeType<MeltingPotRecipe>{
        IRecipeType<MeltingPotRecipe> MELTING_POT = IRecipeType.register("melting_pot");
    }
    interface MoreMixings extends  IRecipeType<MixingRecipe>{
        IRecipeType<MixingRecipe> MIXING = IRecipeType.register("mixing");
    }
    interface MoreSprocketeers extends  IRecipeType<SprocketeerRecipe>{
        IRecipeType<SprocketeerRecipe> SPROCKETEER = IRecipeType.register("sprocketeer");
    }
    interface MoreMillings extends  IRecipeType<MillingRecipe>{
        IRecipeType<MillingRecipe> MILLING = IRecipeType.register("milling");
    }
    interface MoreAtomizing extends  IRecipeType<AtomizingRecipe>{
        IRecipeType<AtomizingRecipe> ATOMIZING = IRecipeType.register("atomizing");
    }


}