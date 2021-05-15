package com.Clivet268.MachineBuiltWorld.inventory.crafting;

import com.Clivet268.MachineBuiltWorld.util.RegistryHandler;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipeSerializer;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.util.NonNullList;
import net.minecraft.util.ResourceLocation;

public class MeltingPotRecipe extends AbstractMeltingPotRecipe {
    public MeltingPotRecipe(ResourceLocation idIn, String groupIn, NonNullList<Ingredient> ingredientsIn, ItemStack resultIn,
                            int cookTimeIn, float experienceIn, int count, int firstIn, int secondIn, int thirdIn, int fourthIn) {
        super(IMoreRecipeType.MoreMeltings.MELTING_POT, idIn, groupIn, ingredientsIn, resultIn, cookTimeIn, experienceIn, count, firstIn,
                secondIn, thirdIn, fourthIn);
    }
    @Override
    public ItemStack getIcon() {
        return new ItemStack(RegistryHandler.MELTING_POT.get());
    }
    @Override
    public IRecipeSerializer<?> getSerializer() {
        return RegistryHandler.MELTING_POT_RECIPE.get();
    }
}
