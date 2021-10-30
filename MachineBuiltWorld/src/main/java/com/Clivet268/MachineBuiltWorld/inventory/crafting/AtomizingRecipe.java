package com.Clivet268.MachineBuiltWorld.inventory.crafting;

import com.Clivet268.MachineBuiltWorld.util.RegistryHandler;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipeSerializer;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.util.NonNullList;
import net.minecraft.util.ResourceLocation;

public class AtomizingRecipe extends AbstractAtomizingRecipe {
    public AtomizingRecipe(ResourceLocation idIn, String groupIn, NonNullList<Ingredient> ingredientsIn, ItemStack resultIn,
                           int cookTimeIn, int count, int counttwo, int countthree, int heatreq, boolean cookorbook) {
        super(idIn, groupIn, ingredientsIn, resultIn, cookTimeIn, count, counttwo, countthree,
                heatreq, cookorbook);
    }
    @Override
    public ItemStack getIcon() {
        return new ItemStack(RegistryHandler.ATOMIZER.get());
    }
    @Override
    public IRecipeSerializer<?> getSerializer() {
        return RegistryHandler.ATOMIZING_RECIPE.get();
    }
}
