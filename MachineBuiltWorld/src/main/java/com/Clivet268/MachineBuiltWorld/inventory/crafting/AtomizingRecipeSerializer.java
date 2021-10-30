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

public class AtomizingRecipeSerializer extends ForgeRegistryEntry<IRecipeSerializer<?>> implements IRecipeSerializer<AtomizingRecipe>
{
private final int cookingTime;

    public AtomizingRecipeSerializer(int p_i50025_2_) {
        this.cookingTime = p_i50025_2_;
    }

    public AtomizingRecipe read(ResourceLocation recipeId, JsonObject json) {
        String s = JSONUtils.getString(json, "group", "");
        NonNullList<Ingredient> ingredient = NonNullList.withSize(2, Ingredient.EMPTY);
        JsonElement jsonelement = (JsonElement)(JSONUtils.isJsonArray(json, "ingredients") ? JSONUtils.getJsonArray(json, "ingredients") : JSONUtils.getJsonObject(json, "ingredients"));
        JsonElement jsonelement1 = (JsonElement)(JSONUtils.isJsonArray(json, "ingredients1") ? JSONUtils.getJsonArray(json, "ingredients1") : JSONUtils.getJsonObject(json, "ingredients1"));
        JsonElement jsonelement2 = (JsonElement)(JSONUtils.isJsonArray(json, "ingredients1") ? JSONUtils.getJsonArray(json, "ingredients1") : JSONUtils.getJsonObject(json, "ingredients1"));
        ingredient.set(0,Ingredient.deserialize(jsonelement));
        ingredient.set(1,Ingredient.deserialize(jsonelement1));

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
        int i = JSONUtils.getInt(json, "cookingtime", this.cookingTime);
        int c = JSONUtils.getInt(json, "count", 1);
        int c1 = JSONUtils.getInt(json, "count1", 1);
        int c2 = JSONUtils.getInt(json, "count2", 1);
        int heatreq = JSONUtils.getInt(json, "heatreq", 1);
        boolean clookorblook = JSONUtils.getBoolean(json, "clookorblook",true);

        return new AtomizingRecipe(recipeId, s, ingredient, itemstack, i, c, c1, c2, heatreq, clookorblook);
    }

    public AtomizingRecipe read(ResourceLocation recipeId, PacketBuffer buffer) {
        String s = buffer.readString(32767);
        NonNullList<Ingredient> ingredient = NonNullList.withSize(2, Ingredient.EMPTY);
        ingredient.set(0,Ingredient.read(buffer));
        ingredient.set(1,Ingredient.read(buffer));
        ItemStack itemstack = buffer.readItemStack();
        int i = buffer.readVarInt();
        int c = buffer.readVarInt();
        int c1 = buffer.readVarInt();
        int c2 = buffer.readVarInt();
        int heatreq = buffer.readVarInt();
        boolean clookorblook = buffer.readBoolean();

        return new AtomizingRecipe(recipeId, s, ingredient, itemstack, i, c, c1, c2, heatreq, clookorblook);
    }

    @Override
    public void write(PacketBuffer buffer, AtomizingRecipe recipe) {
        buffer.writeString(recipe.group);
        recipe.ingredient.get(0).write(buffer);
        recipe.ingredient.get(1).write(buffer);
        buffer.writeItemStack(recipe.result);
        buffer.writeVarInt(recipe.cookTime);
        buffer.writeVarInt(recipe.count);
        buffer.writeVarInt(recipe.count1);
        buffer.writeVarInt(recipe.count2);
        buffer.writeVarInt(recipe.minHeat);
        buffer.writeBoolean(recipe.aod);
    }
}