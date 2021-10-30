package com.Clivet268.MachineBuiltWorld.inventory.crafting;

import com.Clivet268.MachineBuiltWorld.util.RegistryHandler;
import net.minecraft.inventory.CraftingInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipeSerializer;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.item.crafting.ShapelessRecipe;
import net.minecraft.util.NonNullList;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;

import javax.annotation.Nonnull;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

public class UseToolRecipe extends ShapelessRecipe
    {
        public UseToolRecipe(ResourceLocation id, String group, ItemStack result, Ingredient tool, NonNullList<Ingredient> input)
        {
            super(id, group, result, addTo(tool, input));
        }

        private static NonNullList<Ingredient> addTo(Ingredient additional, NonNullList<Ingredient> old)
        {
            old.add(additional);
            return old;
        }

        @Nonnull
        @Override
        public NonNullList<ItemStack> getRemainingItems(CraftingInventory inv)
        {
            NonNullList<ItemStack> remains = super.getRemainingItems(inv);
            for(int i = 0; i < remains.size(); i++)
            {
                ItemStack s = inv.getStackInSlot(i);
                ItemStack remain = remains.get(i);
                ItemStack tool = ItemStack.EMPTY;
                int toolDamageSlot = getIngredients().size()-1;
                if(remain.isEmpty()&&!s.isEmpty()&&getIngredients().get(toolDamageSlot).test(s))
                    tool = s.copy();
                else if(!remain.isEmpty()&&getIngredients().get(toolDamageSlot).test(remain))
                    tool = remain;
                if(!tool.isEmpty()&&tool.isDamageable())
                {
                    tool.setDamage(tool.getDamage()+1);
                    if(tool.getDamage() > tool.getMaxDamage())
                        tool = ItemStack.EMPTY;
                    remains.set(i, tool);
                }
            }
            return remains;
        }

        @Override
        public boolean matches(CraftingInventory matrix, World world)
        {
            List<Ingredient> required = new LinkedList<>(getIngredients());

            for(int i = 0; i < matrix.getSizeInventory(); i++)
            {
                ItemStack slot = matrix.getStackInSlot(i);
                if(!slot.isEmpty())
                {
                    boolean inRecipe = false;
                    Iterator<Ingredient> iterator = required.iterator();
                    while(iterator.hasNext())
                    {
                        Ingredient next = iterator.next();
                        if(next.test(slot))
                        {
                            inRecipe = true;
                            iterator.remove();
                            break;
                        }
                    }
                    if(!inRecipe)
                        return false;
                }
            }
            return required.isEmpty();
        }

        @Nonnull
        @Override
        public IRecipeSerializer<?> getSerializer()
        {
            return RegistryHandler.USE_TOOL_RECIPE.get();
        }

        public Ingredient getTool()
        {
            return getIngredients().get(getIngredients().size()-1);
        }
    }