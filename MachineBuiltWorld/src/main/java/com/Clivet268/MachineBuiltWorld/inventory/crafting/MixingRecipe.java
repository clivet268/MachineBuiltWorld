package com.Clivet268.MachineBuiltWorld.inventory.crafting;

import com.Clivet268.MachineBuiltWorld.util.RegistryHandler;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipeSerializer;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.util.NonNullList;
import net.minecraft.util.ResourceLocation;

public class MixingRecipe extends AbstractMixingRecipe {
    public MixingRecipe(ResourceLocation idIn, String groupIn, NonNullList<Ingredient> ingredientsIn, ItemStack resultIn,
                        int cookTimeIn, int count, int firstIn, int secondIn, boolean improvement) {
        super(IMoreRecipeType.MoreMixings.MIXING, idIn, groupIn, ingredientsIn, resultIn, cookTimeIn, count, firstIn,
                secondIn, improvement);
    }
    @Override
    public ItemStack getIcon() {
        return new ItemStack(RegistryHandler.MIXER.get());
    }
    @Override
    public IRecipeSerializer<?> getSerializer() {
        return RegistryHandler.MIXING_RECIPE.get();
    }
}
