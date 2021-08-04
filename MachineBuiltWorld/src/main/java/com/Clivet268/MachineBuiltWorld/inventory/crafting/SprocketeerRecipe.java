package com.Clivet268.MachineBuiltWorld.inventory.crafting;

import com.Clivet268.MachineBuiltWorld.util.RegistryHandler;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipeSerializer;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.util.NonNullList;
import net.minecraft.util.ResourceLocation;

public class SprocketeerRecipe extends AbstractSprocketeerRecipe {
    public SprocketeerRecipe(ResourceLocation idIn, String groupIn, NonNullList<Ingredient> ingredientsIn, int cookTimeIn) {
        super(IMoreRecipeType.MoreSprocketeers.SPROCKETEER, idIn, groupIn, ingredientsIn,  cookTimeIn);
    }
    @Override
    public ItemStack getIcon() {
        return new ItemStack(RegistryHandler.SPROCKETEERER.get());
    }
    @Override
    public IRecipeSerializer<?> getSerializer() {
        return RegistryHandler.SPROCKETEER_RECIPE.get();
    }
}
