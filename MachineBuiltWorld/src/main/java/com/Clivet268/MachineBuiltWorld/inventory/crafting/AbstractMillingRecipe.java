package com.Clivet268.MachineBuiltWorld.inventory.crafting;

import com.Clivet268.MachineBuiltWorld.util.RegistryHandler;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.item.crafting.IRecipeSerializer;
import net.minecraft.item.crafting.IRecipeType;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.util.NonNullList;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;

public abstract class AbstractMillingRecipe implements IRecipe<IInventory> {
        protected final IRecipeType<?> type;
        protected final ResourceLocation id;
        protected final String group;
        protected final Ingredient ingredient;
        protected final ItemStack result;
        protected final float experience;
        protected final int cookTime;

        public AbstractMillingRecipe(IRecipeType<?> typeIn, ResourceLocation idIn, String groupIn, Ingredient ingredientIn, ItemStack resultIn, float experienceIn, int cookTimeIn) {
            this.type = typeIn;
            this.id = idIn;
            this.group = groupIn;
            this.ingredient = ingredientIn;
            this.result = resultIn;
            this.experience = experienceIn;
            this.cookTime = cookTimeIn;
        }

        /**
         * Used to check if a recipe matches current crafting inventory
         */
        @Override
        public boolean matches(IInventory inv, World worldIn) {
            return this.ingredient.test(inv.getStackInSlot(0));
        }

        /**
         * Returns an Item that is the result of this recipe
         */
        @Override
        public ItemStack getCraftingResult(IInventory inv) {
            return this.result.copy();
        }

        /**
         * Used to determine if this recipe can fit in a grid of the given width/height
         */
        @Override
        public boolean canFit(int width, int height) {
            return true;
        }
         @Override
        public NonNullList<Ingredient> getIngredients() {
            NonNullList<Ingredient> nonnulllist = NonNullList.create();
            nonnulllist.add(this.ingredient);
            return nonnulllist;
        }

        /**
         * Gets the experience of this recipe
         */
        public float getExperience() {
            return this.experience;
        }

        /**
         * Get the result of this recipe, usually for display purposes (e.g. recipe book). If your recipe has more than one
         * possible result (e.g. it's dynamic and depends on its inputs), then return an empty stack.
         */
        @Override
        public ItemStack getRecipeOutput() {
            return this.result;
        }

        /**
         * Recipes with equal group are combined into one button in the recipe book
         */
        @Override
        public String getGroup() {
            return this.group;
        }

        /**
         * Gets the cook time in ticks
         */
        public int getCookTime() {
            return this.cookTime;
        }
        @Override
        public IRecipeSerializer<?> getSerializer() {
           return RegistryHandler.MILLING_RECIPE.get();
          }
        @Override
        public ResourceLocation getId() {
            return this.id;
        }
        @Override
        public IRecipeType<?> getType() {
            return this.type;
        }
    }

