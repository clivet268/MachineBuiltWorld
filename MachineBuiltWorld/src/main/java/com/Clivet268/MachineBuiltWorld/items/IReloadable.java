package com.Clivet268.MachineBuiltWorld.items;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;

public interface IReloadable {

    boolean isReloadable(PlayerEntity player);

    ItemStack findAmmo(PlayerEntity player);

    void reload(PlayerEntity player);

}
