package com.Clivet268.MachineBuiltWorld.inventory.crafting;

import com.Clivet268.MachineBuiltWorld.util.RegistryHandler;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.util.NonNullList;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;

public abstract class AbstractAtomizingRecipe extends AbstractMBWRecipe {
        protected final ResourceLocation id;
        protected final String group;
        protected final NonNullList<Ingredient> ingredient;
        protected final ItemStack result;
        protected final int cookTime;
        protected final int count;
        protected final int count1;
        protected final int count2;
        protected final int minHeat;
        protected final boolean aod;
        int[] whoiswho = new int[4];


        public AbstractAtomizingRecipe( ResourceLocation idIn, String groupIn,
                                       NonNullList<Ingredient> ingredientsIn, ItemStack resultIn,
                                       int cookTimeIn, int countIn, int countIn1, int countIn2, int minHehe,
                                        boolean asordys) {
            super(IMoreRecipeType.MoreAtomizing.ATOMIZING,  idIn,  groupIn,
                    ingredientsIn,  resultIn, 0, cookTimeIn,
             0.0f,  countIn, RegistryHandler.ATOMIZING_RECIPE.get());
            this.id = idIn;
            this.group = groupIn;
            this.ingredient = ingredientsIn;
            this.result = resultIn;
            this.cookTime = cookTimeIn;
            this.count = countIn;
            this.count1 = countIn1;
            this.count2 = countIn2;
            this.minHeat = minHehe;
            this.aod = asordys;
        }

        /**
         * Used to check if a recipe matches current crafting inventory
         */
        @Override
        public boolean matches(IInventory inv, World worldIn){
            for(int i = 2; i <= 4; i++){
                if(!this.ingredient.get(i).test(inv.getStackInSlot(i))){
                    return false;
                }
            }
            return true;
        }

        public boolean asymordysa(){
            return this.aod;
        }

        public int getHeatRequired() {
        return this.minHeat;
    }
    }

