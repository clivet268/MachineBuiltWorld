package com.Clivet268.MachineBuiltWorld.inventory.crafting;

import com.Clivet268.MachineBuiltWorld.util.RegistryHandler;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.item.crafting.IRecipeSerializer;
import net.minecraft.item.crafting.IRecipeType;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.util.NonNullList;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;

import javax.annotation.Nonnull;

public abstract class AbstractSprocketeerRecipe implements IRecipe<IInventory> {
        protected final IRecipeType<?> type;
        protected final ResourceLocation id;
        protected final String group;
        protected final NonNullList<Ingredient> ingredient;
        protected final int cookTime;


        public AbstractSprocketeerRecipe(IRecipeType<?> typeIn, ResourceLocation idIn, String groupIn,
                                         NonNullList<Ingredient> ingredientsIn, int cookTimeIn) {
            this.type = typeIn;
            this.id = idIn;
            this.group = groupIn;
            this.ingredient = ingredientsIn;
            this.cookTime = cookTimeIn;
        }

        /**
         * Used to check if a recipe matches current crafting inventory
         */
        @Override
        public boolean matches(IInventory inv, World worldIn) {
            int ii = 0;

            for(Ingredient i : ingredient){
                boolean tech = false;
                for(int ew = 0; ew < inv.getSizeInventory(); ew++){
                    if(i.test(inv.getStackInSlot(ew))){
                        tech = true;
                        break;
                    }
                }
                if(!tech){
                    return false;
                }
            }
            return true;
        }



        /**
         * Returns an Item that is the result of this recipe
         */
        @Override
        public ItemStack getCraftingResult(IInventory inv) {
            return new ItemStack(Items.AIR);
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
            nonnulllist.addAll(this.ingredient);
            return nonnulllist;
        }
        /**
         * Get the result of this recipe, usually for display purposes (e.g. recipe book). If your recipe has more than one
         * possible result (e.g. it's dynamic and depends on its inputs), then return an empty stack.
         */
        @Override
        public ItemStack getRecipeOutput() {
            return new ItemStack(Items.AIR);
        }

        /**
         * Recipes with equal group are combined into one button in the recipe book
         */
        @Override
        public String getGroup() {
            return this.group;
        }

        /**
         * Gets the cook time in tick
         */
        public int getCookTime() {
            return this.cookTime;
        }
        @Override
        public IRecipeSerializer<?> getSerializer() {
           return RegistryHandler.SPROCKETEER_RECIPE.get();
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

