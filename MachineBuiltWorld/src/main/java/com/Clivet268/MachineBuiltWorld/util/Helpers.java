package com.Clivet268.MachineBuiltWorld.util;

import com.Clivet268.MachineBuiltWorld.MachineBuiltWorld;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.registries.ForgeRegistries;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.Random;

public class Helpers {
    private static final Random RANDOM = new Random();
    public static ItemStack useItem(ItemStack stack, int amount)
    {
        if (stack.isDamageable())
        {
            if (stack.attemptDamageItem(amount, RANDOM, null))
            {
                stack.shrink(1);
                stack.setDamage(0);
            }
        }
        return stack;
    }

    public enum ResourceType {

        RENDER("render"),
        MODEL("models");

        private String prefix;

        ResourceType(String s) {
            prefix = s;
        }

        public String getPrefix() {
            return prefix + "/";
        }
    }
    public static ResourceLocation getResource(ResourceType type, String name) {
        return MachineBuiltWorld.locate(type.getPrefix() + name);
    }

    /*
    public static CompoundNBT toNBT(Object o) {
        if(o instanceof ItemStack) {
            return writeItemStack((ItemStack)o);
        }

        if(o instanceof AtomizerTileEntity) {
            return writeAtomizer((AtomizerTileEntity)o);
        }

        return null;
    }

    private static CompoundNBT writeAtomizer(AtomizerTileEntity o) {
        CompoundNBT compound = new CompoundNBT();
        compound.putInt("x", o.x);
        compound.putInt("y", o.y);
        compound.putInt("z", o.z);
        return compound;
    }

    private static CompoundNBT writeItemStack(ItemStack i) {
        CompoundNBT nbt = new CompoundNBT();
        nbt.putInt("count", i.getCount());
        nbt.putString("item", i.getItem().getRegistryName().toString());
        nbt.putByte("type", (byte)0);
        return nbt;
    }

 */

    @Nullable
    public static Object fromNBT(@Nonnull CompoundNBT compound) {
        switch (compound.getByte("type")) {
            case 0:
                return readItemStack(compound);
            default:
                return null;
        }
    }

    private static ItemStack readItemStack(CompoundNBT compound) {
        Item item = ForgeRegistries.ITEMS.getValue(new ResourceLocation(compound.getString("item")));
        int count = compound.getInt("count");
        return new ItemStack(item, count);
    }
}
