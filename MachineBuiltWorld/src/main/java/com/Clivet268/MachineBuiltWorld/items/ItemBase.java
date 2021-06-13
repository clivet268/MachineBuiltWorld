package com.Clivet268.MachineBuiltWorld.items;

import com.Clivet268.MachineBuiltWorld.util.MachineBuiltWorldItemGroup;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;

public class ItemBase extends Item {
    public ItemBase() {
        super(new Item.Properties().group(MachineBuiltWorldItemGroup.instance));
    }
}
