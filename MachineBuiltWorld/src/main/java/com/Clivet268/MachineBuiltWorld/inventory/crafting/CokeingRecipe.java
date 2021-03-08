package com.Clivet268.MachineBuiltWorld.inventory.crafting;

import com.Clivet268.MachineBuiltWorld.util.RegistryHandler;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.AbstractCookingRecipe;
import net.minecraft.item.crafting.IRecipeSerializer;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.util.ResourceLocation;

public class CokeingRecipe extends AbstractCokeingRecipe {
    public CokeingRecipe(ResourceLocation idIn, String groupIn, Ingredient ingredientIn, ItemStack resultIn, float experienceIn, int cookTimeIn) {
        super(IMoreRecipeType.COKEING, idIn, groupIn, ingredientIn, resultIn, experienceIn, cookTimeIn);
    }
    @Override
    public ItemStack getIcon() {
        return new ItemStack(RegistryHandler.INTENSIVE_HEATING_OVEN.get());
    }
    @Override
    public IRecipeSerializer<?> getSerializer() {
        return RegistryHandler.COKEING_RECIPE.get();
    }
}
