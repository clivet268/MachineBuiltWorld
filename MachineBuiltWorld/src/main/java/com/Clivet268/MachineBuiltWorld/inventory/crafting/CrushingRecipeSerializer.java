package com.Clivet268.MachineBuiltWorld.inventory.crafting;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipeSerializer;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.item.crafting.ShapedRecipe;
import net.minecraft.network.PacketBuffer;
import net.minecraft.util.JSONUtils;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.registry.Registry;
import net.minecraftforge.registries.ForgeRegistryEntry;

public class CrushingRecipeSerializer extends ForgeRegistryEntry<IRecipeSerializer<?>> implements IRecipeSerializer<CrushingRecipe>
{
private final int cookingTime;

    public CrushingRecipeSerializer(int p_i50025_2_) {
        this.cookingTime = p_i50025_2_;
    }

    public CrushingRecipe read(ResourceLocation recipeId, JsonObject json) {
        String s = JSONUtils.getString(json, "group", "");
        JsonElement jsonelement = (JsonElement)(JSONUtils.isJsonArray(json, "ingredient") ? JSONUtils.getJsonArray(json, "ingredient") : JSONUtils.getJsonObject(json, "ingredient"));
        Ingredient ingredient = Ingredient.deserialize(jsonelement);
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
        if (!json.has("result1")) throw new com.google.gson.JsonSyntaxException("Missing result, expected to find a string or object");
        ItemStack itemstack1;
        if (json.get("result1").isJsonObject()) itemstack1 = ShapedRecipe.deserializeItem(JSONUtils.getJsonObject(json, "result1"));
        else {
            String s1 = JSONUtils.getString(json, "result1");
            System.out.println();
            ResourceLocation resourcelocation = new ResourceLocation(s1);
            itemstack1 = new ItemStack(Registry.ITEM.getValue(resourcelocation).orElseThrow(() -> {
                return new IllegalStateException("Item: " + s1 + " does not exist lol");
            }));
        }

        int i = JSONUtils.getInt(json, "cookingtime", this.cookingTime);
        int c = JSONUtils.getInt(json, "count", 1);
        int c1 = JSONUtils.getInt(json, "count1", 1);
        double chance = (double)JSONUtils.getFloat(json, "chance", 1.0F);
        if(chance < Math.random())
        {
            itemstack1 = ItemStack.EMPTY;
        }
        return new CrushingRecipe(recipeId, s, ingredient, itemstack, itemstack1, i, c, c1);
    }

    public CrushingRecipe read(ResourceLocation recipeId, PacketBuffer buffer) {
        String s = buffer.readString(32767);
        Ingredient ingredient = Ingredient.read(buffer);
        ItemStack itemstack = buffer.readItemStack();
        ItemStack itemstack1 = buffer.readItemStack();
        int i = buffer.readVarInt();
        int c = buffer.readVarInt();
        int c1 = buffer.readVarInt();
        return new CrushingRecipe(recipeId, s, ingredient, itemstack, itemstack1, i, c, c1);
    }

    @Override
    public void write(PacketBuffer buffer, CrushingRecipe recipe) {
        buffer.writeString(recipe.group);
        recipe.ingredient.write(buffer);
        buffer.writeItemStack(recipe.result);
        buffer.writeItemStack(recipe.result1);
        buffer.writeVarInt(recipe.cookTime);
        buffer.writeVarInt(recipe.count);
        buffer.writeVarInt(recipe.count1);
    }
}