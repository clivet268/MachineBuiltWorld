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

public abstract class AbstractMixingRecipe implements IRecipe<IInventory> {
        protected final IRecipeType<?> type;
        protected final ResourceLocation id;
        protected final String group;
        protected final NonNullList<Ingredient> ingredient;
        protected final ItemStack result;
        protected final int cookTime;
        protected final int count;
        protected final int countOfTheFirstIng;
        protected final int countOfTheSecondIng;
        protected boolean improvement;
        int[] whoiswho = new int[2];


        public AbstractMixingRecipe(IRecipeType<?> typeIn, ResourceLocation idIn, String groupIn,
                                    NonNullList<Ingredient> ingredientsIn, ItemStack resultIn, int cookTimeIn,
                                     int countIn, int firstIm, int secondIn, boolean im) {
            this.type = typeIn;
            this.id = idIn;
            this.group = groupIn;
            this.ingredient = ingredientsIn;
            this.result = resultIn;
            this.cookTime = cookTimeIn;
            this.count = countIn;
            this.countOfTheFirstIng = firstIm;
            this.countOfTheSecondIng = secondIn;
            this.improvement = im;
        }

        /**
         * Used to check if a recipe matches current crafting inventory
         */
        @Override
        public boolean matches(IInventory inv, World worldIn) {
            ItemStack[] ea =  {inv.getStackInSlot(0),inv.getStackInSlot(1)};
            int checksum=0;
            int i = 0;
            for(Ingredient o : this.getIngredients()) {
                for (ItemStack e : ea) {
                    if (o.test(e)) {
                        checksum++;
                        this.whoiswho[i] = getadacounta(i);
                        break;
                    }
                }
                i++;
            }
            //System.out.println(checksum);
            return checksum == 2;
        }
    public int actuallygetadacounta(int e)
    {
        return this.whoiswho[e];
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
            nonnulllist.addAll(this.ingredient);
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

        /**
         * Recipes with equal group are combined into one button in the recipe book
         */
        @Override
        public String getGroup() {
            return this.group;
        }
    public boolean getImprovement() {
        return this.improvement;
    }
        public int getCount1() {
        return this.count;
    }
        public int getadacounta(int e)
        {
            switch(e){
                case(0):
                    return this.countOfTheFirstIng;
                case(1):
                    return this.countOfTheSecondIng;
                default:
                    return 999;
            }
        }

        /**
         * Gets the cook time in ticks
         */
        public int getCookTime() {
            return this.cookTime;
        }
        @Override
        public IRecipeSerializer<?> getSerializer() {
           return RegistryHandler.MIXING_RECIPE.get();
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

