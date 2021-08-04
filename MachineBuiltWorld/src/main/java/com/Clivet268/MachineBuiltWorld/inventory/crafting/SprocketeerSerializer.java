package com.Clivet268.MachineBuiltWorld.inventory.crafting;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import net.minecraft.item.crafting.IRecipeSerializer;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.network.PacketBuffer;
import net.minecraft.util.JSONUtils;
import net.minecraft.util.NonNullList;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.registries.ForgeRegistryEntry;

public class SprocketeerSerializer extends ForgeRegistryEntry<IRecipeSerializer<?>> implements IRecipeSerializer<SprocketeerRecipe>
{
private final int cookingTime;

    public SprocketeerSerializer(int p_i50025_2_) {
        this.cookingTime = p_i50025_2_;
    }

    public SprocketeerRecipe read(ResourceLocation recipeId, JsonObject json) {
        String s = JSONUtils.getString(json, "group", "");
        NonNullList<Ingredient> ingredient = NonNullList.withSize(7, Ingredient.EMPTY);
        JsonElement jsonelement = (JsonElement)(JSONUtils.isJsonArray(json, "ingredients") ? JSONUtils.getJsonArray(json, "ingredients") : JSONUtils.getJsonObject(json, "ingredients"));
        JsonElement jsonelement1 = (JsonElement)(JSONUtils.isJsonArray(json, "ingredients1") ? JSONUtils.getJsonArray(json, "ingredients1") : JSONUtils.getJsonObject(json, "ingredients1"));
        JsonElement jsonelement2 = (JsonElement)(JSONUtils.isJsonArray(json, "ingredients2") ? JSONUtils.getJsonArray(json, "ingredients2") : JSONUtils.getJsonObject(json, "ingredients"));
        JsonElement jsonelement3 = (JsonElement)(JSONUtils.isJsonArray(json, "ingredients3") ? JSONUtils.getJsonArray(json, "ingredients3") : JSONUtils.getJsonObject(json, "ingredients1"));
        JsonElement jsonelement4 = (JsonElement)(JSONUtils.isJsonArray(json, "ingredients4") ? JSONUtils.getJsonArray(json, "ingredients4") : JSONUtils.getJsonObject(json, "ingredients"));
        JsonElement jsonelement5 = (JsonElement)(JSONUtils.isJsonArray(json, "ingredients5") ? JSONUtils.getJsonArray(json, "ingredients5") : JSONUtils.getJsonObject(json, "ingredients1"));
        JsonElement jsonelement6 = (JsonElement)(JSONUtils.isJsonArray(json, "ingredients6") ? JSONUtils.getJsonArray(json, "ingredients6") : JSONUtils.getJsonObject(json, "ingredients"));
        ingredient.set(0,Ingredient.deserialize(jsonelement));
        ingredient.set(1,Ingredient.deserialize(jsonelement1));
        ingredient.set(2,Ingredient.deserialize(jsonelement2));
        ingredient.set(3,Ingredient.deserialize(jsonelement3));
        ingredient.set(4,Ingredient.deserialize(jsonelement4));
        ingredient.set(5,Ingredient.deserialize(jsonelement5));
        ingredient.set(6,Ingredient.deserialize(jsonelement6));

        int i = JSONUtils.getInt(json, "cookingtime", this.cookingTime);

        return new SprocketeerRecipe(recipeId, s, ingredient, i);
    }

    public SprocketeerRecipe read(ResourceLocation recipeId, PacketBuffer buffer) {
        String s = buffer.readString(32767);
        NonNullList<Ingredient> ingredient = NonNullList.withSize(7, Ingredient.EMPTY);
        ingredient.set(0,Ingredient.read(buffer));
        ingredient.set(1,Ingredient.read(buffer));
        ingredient.set(2,Ingredient.read(buffer));
        ingredient.set(3,Ingredient.read(buffer));
        ingredient.set(4,Ingredient.read(buffer));
        ingredient.set(5,Ingredient.read(buffer));
        ingredient.set(6,Ingredient.read(buffer));
        int i = buffer.readVarInt();

        return new SprocketeerRecipe(recipeId, s, ingredient, i);
    }

    @Override
    public void write(PacketBuffer buffer, SprocketeerRecipe recipe) {
        buffer.writeString(recipe.group);
        recipe.ingredient.get(0).write(buffer);
        recipe.ingredient.get(1).write(buffer);
        recipe.ingredient.get(2).write(buffer);
        recipe.ingredient.get(3).write(buffer);
        recipe.ingredient.get(4).write(buffer);
        recipe.ingredient.get(5).write(buffer);
        recipe.ingredient.get(6).write(buffer);
        buffer.writeVarInt(recipe.cookTime);
    }
}