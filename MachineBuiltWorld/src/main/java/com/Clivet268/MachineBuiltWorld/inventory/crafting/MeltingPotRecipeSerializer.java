package com.Clivet268.MachineBuiltWorld.inventory.crafting;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipeSerializer;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.item.crafting.ShapedRecipe;
import net.minecraft.network.PacketBuffer;
import net.minecraft.util.JSONUtils;
import net.minecraft.util.NonNullList;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.registry.Registry;
import net.minecraftforge.registries.ForgeRegistryEntry;

public class MeltingPotRecipeSerializer extends ForgeRegistryEntry<IRecipeSerializer<?>> implements IRecipeSerializer<MeltingPotRecipe>
{
private final int cookingTime;

    public MeltingPotRecipeSerializer(int p_i50025_2_) {
        this.cookingTime = p_i50025_2_;
    }

    public MeltingPotRecipe read(ResourceLocation recipeId, JsonObject json) {
        String s = JSONUtils.getString(json, "group", "");
        NonNullList<Ingredient> ingredient = NonNullList.withSize(4, Ingredient.EMPTY);
        JsonElement jsonelement = (JsonElement)(JSONUtils.isJsonArray(json, "ingredients") ? JSONUtils.getJsonArray(json, "ingredients") : JSONUtils.getJsonObject(json, "ingredients"));
        JsonElement jsonelement1 = (JsonElement)(JSONUtils.isJsonArray(json, "ingredients1") ? JSONUtils.getJsonArray(json, "ingredients1") : JSONUtils.getJsonObject(json, "ingredients1"));
        JsonElement jsonelement2 = (JsonElement)(JSONUtils.isJsonArray(json, "ingredients2") ? JSONUtils.getJsonArray(json, "ingredients2") : JSONUtils.getJsonObject(json, "ingredients2"));
        JsonElement jsonelement3 = (JsonElement)(JSONUtils.isJsonArray(json, "ingredients3") ? JSONUtils.getJsonArray(json, "ingredients3") : JSONUtils.getJsonObject(json, "ingredients3"));
        ingredient.set(0,Ingredient.deserialize(jsonelement));
        ingredient.set(1,Ingredient.deserialize(jsonelement1));
        ingredient.set(2,Ingredient.deserialize(jsonelement2));
        ingredient.set(3,Ingredient.deserialize(jsonelement3));

        //Forge: Check if primitive string to keep vanilla or a object which can contain a count field.
        if (!json.has("result")) throw new com.google.gson.JsonSyntaxException("Missing result, expected to find a string or object");
        ItemStack itemstack;
        if (json.get("result").isJsonObject()) itemstack = ShapedRecipe.deserializeItem(JSONUtils.getJsonObject(json, "result"));
        else {
            String s1 = JSONUtils.getString(json, "result");
            System.out.println();
            ResourceLocation resourcelocation = new ResourceLocation(s1);
            itemstack = new ItemStack(Registry.ITEM.getValue(resourcelocation).orElseThrow(() -> {
                return new IllegalStateException("Item: " + s1 + " does not exist");
            }));
        }
        float f = JSONUtils.getFloat(json, "experience", 0.0F);
        int i = JSONUtils.getInt(json, "cookingtime", this.cookingTime);
        int c = JSONUtils.getInt(json, "count", 1);
        int c1 = JSONUtils.getInt(json, "count1", 1);
        int c2 = JSONUtils.getInt(json, "count2", 1);
        int c3 = JSONUtils.getInt(json, "count3", 1);
        int c4 = JSONUtils.getInt(json, "count4", 1);

        return new MeltingPotRecipe(recipeId, s, ingredient, itemstack, i, f, c, c1, c2, c3, c4);
    }

    public MeltingPotRecipe read(ResourceLocation recipeId, PacketBuffer buffer) {
        String s = buffer.readString(32767);
        NonNullList<Ingredient> ingredient = NonNullList.withSize(4, Ingredient.EMPTY);
        ingredient.set(0,Ingredient.read(buffer));
        ingredient.set(1,Ingredient.read(buffer));
        ingredient.set(2,Ingredient.read(buffer));
        ingredient.set(3,Ingredient.read(buffer));
        ItemStack itemstack = buffer.readItemStack();
        float f = buffer.readFloat();
        int i = buffer.readVarInt();
        int c = buffer.readVarInt();
        int c1 = buffer.readVarInt();
        int c2 = buffer.readVarInt();
        int c3 = buffer.readVarInt();
        int c4 = buffer.readVarInt();

        return new MeltingPotRecipe(recipeId, s, ingredient, itemstack, i, f, c, c1, c2, c3, c4);
    }

    @Override
    public void write(PacketBuffer buffer, MeltingPotRecipe recipe) {
        buffer.writeString(recipe.group);
        recipe.ingredient.get(0).write(buffer);
        recipe.ingredient.get(1).write(buffer);
        recipe.ingredient.get(2).write(buffer);
        recipe.ingredient.get(3).write(buffer);
        buffer.writeItemStack(recipe.result);
        buffer.writeFloat(recipe.experience);
        buffer.writeVarInt(recipe.cookTime);
        buffer.writeVarInt(recipe.count);
        buffer.writeVarInt(recipe.countOfTheFirstIng);
        buffer.writeVarInt(recipe.countOfTheSecondIng);
        buffer.writeVarInt(recipe.countOfTheThirdIng);
        buffer.writeVarInt(recipe.countOfTheFourthIng);
    }
}