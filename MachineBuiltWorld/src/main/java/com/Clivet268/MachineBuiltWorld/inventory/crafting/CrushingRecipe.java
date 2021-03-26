package com.Clivet268.MachineBuiltWorld.inventory.crafting;

import com.Clivet268.MachineBuiltWorld.util.RegistryHandler;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipeSerializer;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.util.ResourceLocation;

public class CrushingRecipe extends AbstractCrushingRecipe {
    public CrushingRecipe(ResourceLocation idIn, String groupIn, Ingredient ingredientIn, ItemStack resultIn, ItemStack resultIn1, int cookTimeIn, int c, int c1) {
        super(IMoreRecipeType.CRUSHING, idIn, groupIn, ingredientIn, resultIn, resultIn1, cookTimeIn, c, c1);
    }
    @Override
    public ItemStack getIcon() {
        return new ItemStack(RegistryHandler.CRUSHER_ITEM.get());
    }
    @Override
    public IRecipeSerializer<?> getSerializer() {
        return RegistryHandler.CRUSHING_RECIPE.get();
    }
}
