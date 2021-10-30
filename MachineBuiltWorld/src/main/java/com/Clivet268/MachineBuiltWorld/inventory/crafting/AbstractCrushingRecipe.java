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

import javax.annotation.Nonnull;

public abstract class AbstractCrushingRecipe implements IRecipe<IInventory> {
        protected final IRecipeType<?> type;
        protected final ResourceLocation id;
        protected final String group;
        protected final Ingredient ingredient;
        protected final ItemStack result;
        protected final ItemStack result1;
        protected final int cookTime;
        protected final int count;
        protected final int count1;

        public AbstractCrushingRecipe(IRecipeType<?> typeIn, ResourceLocation idIn, String groupIn, Ingredient ingredientIn, ItemStack resultIn, ItemStack resultIn1, int cookTimeIn, int countIn, int countIn1) {
            this.type = typeIn;
            this.id = idIn;
            this.group = groupIn;
            this.ingredient = ingredientIn;
            this.result = resultIn;
            this.result1 = resultIn1;
            this.cookTime = cookTimeIn;
            this.count = countIn;
            this.count1 = countIn1;
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
         @Nonnull
         @Override
        public NonNullList<Ingredient> getIngredients() {
            NonNullList<Ingredient> nonnulllist = NonNullList.create();
            nonnulllist.add(this.ingredient);
            return nonnulllist;
        }
        /**
         * Get the result of this recipe, usually for display purposes (e.g. recipe book). If your recipe has more than one
         * possible result (e.g. it's dynamic and depends on its inputs), then return an empty stack.
         */
        @Override
        public ItemStack getRecipeOutput() {
            return this.result;
        }


        public ItemStack getRecipeOutput1() {
        return this.result1;
    }

        /**
         * Recipes with equal group are combined into one button in the recipe book
         */
        @Override
        public String getGroup() {
            return this.group;
        }

        public int getCount11() {
        return this.count1;
    }

        public int getCount1() {
        return this.count;
    }

        /**
         * Gets the cook time in ticks
         */
        public int getCookTime() {
            return this.cookTime;
        }
        @Override
        public IRecipeSerializer<?> getSerializer() {
           return RegistryHandler.CRUSHING_RECIPE.get();
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

