package com.Clivet268.MachineBuiltWorld.inventory.crafting;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipeSerializer;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.item.crafting.ShapedRecipe;
import net.minecraft.network.PacketBuffer;
import net.minecraft.util.NonNullList;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.crafting.CraftingHelper;
import net.minecraftforge.registries.ForgeRegistryEntry;

import javax.annotation.Nonnull;

public class UseToolSerializer extends ForgeRegistryEntry<IRecipeSerializer<?>> implements IRecipeSerializer<UseToolRecipe>
{
    @Nonnull
    @Override
    public UseToolRecipe read(@Nonnull ResourceLocation recipeId, @Nonnull JsonObject json)
    {
        NonNullList<Ingredient> defIngredients = readIngredients(json.getAsJsonArray("ingredients"));
        Ingredient tool = Ingredient.deserialize(json.get("tool"));
        String group = json.get("group").getAsString();
        ItemStack result = ShapedRecipe.deserializeItem(json.getAsJsonObject("result"));
        return new UseToolRecipe(recipeId, group, result, tool, defIngredients);
    }

    @Nonnull
    @Override
    public UseToolRecipe read(@Nonnull ResourceLocation recipeId, @Nonnull PacketBuffer buffer)
    {
        int stdCount = buffer.readInt();
        NonNullList<Ingredient> stdIngr = NonNullList.create();
        for(int i = 0; i < stdCount; ++i)
            stdIngr.add(Ingredient.read(buffer));
        Ingredient tool = Ingredient.read(buffer);
        String group = buffer.readString(512);
        ItemStack output = buffer.readItemStack();
        return new UseToolRecipe(recipeId, group, output, tool, stdIngr);
    }

    @Override
    public void write(@Nonnull PacketBuffer buffer, @Nonnull UseToolRecipe recipe)
    {
        int standardCount = recipe.getIngredients().size()-1;
        buffer.writeInt(standardCount);
        for(int i = 0; i < standardCount; ++i)
            CraftingHelper.write(buffer, recipe.getIngredients().get(i));
        CraftingHelper.write(buffer, recipe.getTool());
        buffer.writeString(recipe.getGroup());
        buffer.writeItemStack(recipe.getRecipeOutput());
    }

    private static NonNullList<Ingredient> readIngredients(JsonArray all)
    {
        NonNullList<Ingredient> ret = NonNullList.create();

        for(int i = 0; i < all.size(); ++i)
        {
            Ingredient ingredient = Ingredient.deserialize(all.get(i));
            if(!ingredient.hasNoMatchingItems())
                ret.add(ingredient);
        }

        return ret;
    }
}