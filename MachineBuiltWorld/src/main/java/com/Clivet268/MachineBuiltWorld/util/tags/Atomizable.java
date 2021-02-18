package com.Clivet268.MachineBuiltWorld.util.tags;

import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.ItemTags;
import net.minecraft.tags.Tag;
import net.minecraft.util.ResourceLocation;

import static com.Clivet268.MachineBuiltWorld.MachineBuiltWorld.MOD_ID;

public class Atomizable {
    public static class Items
    {
        public static final Tag<Item> ATOMIZABLE = tag("atomizable");

        public static Tag<Item> tag(String name)
        {
            return new ItemTags.Wrapper(new ResourceLocation(MOD_ID, name));
        }
    }

    public static class Blocks
    {
        public static final Tag<Block> ATOMIZABLE = tag("atomizable");

        public static Tag<Block> tag(String name)
        {
            return new BlockTags.Wrapper(new ResourceLocation(MOD_ID, name));
        }
    }
}
