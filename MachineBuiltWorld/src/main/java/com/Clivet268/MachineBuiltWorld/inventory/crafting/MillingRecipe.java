package com.Clivet268.MachineBuiltWorld.inventory.crafting;

import com.Clivet268.MachineBuiltWorld.util.RegistryHandler;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipeSerializer;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.util.ResourceLocation;

public class MillingRecipe extends AbstractMillingRecipe {
    public MillingRecipe(ResourceLocation idIn, String groupIn, Ingredient ingredientIn, ItemStack resultIn, float experienceIn, int cookTimeIn) {
        super(IMoreRecipeType.MoreMillings.MILLING, idIn, groupIn, ingredientIn, resultIn, experienceIn, cookTimeIn);
    }
    @Override
    public ItemStack getIcon() {
        return new ItemStack(RegistryHandler.MILL.get());
    }
    @Override
    public IRecipeSerializer<?> getSerializer() {
        return RegistryHandler.MILLING_RECIPE.get();
    }
}
