package com.Clivet268.MachineBuiltWorld.inventory.crafting;

import net.minecraft.item.crafting.FurnaceRecipe;
import net.minecraft.item.crafting.IRecipeType;

public interface IMoreRecipeType extends IRecipeType<CokeingRecipe> {
    IRecipeType<CokeingRecipe> COKEING = IRecipeType.register("cokeing");

}