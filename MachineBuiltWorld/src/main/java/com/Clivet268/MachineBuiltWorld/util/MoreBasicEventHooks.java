package com.Clivet268.MachineBuiltWorld.util;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.fml.hooks.BasicEventHooks;

public class MoreBasicEventHooks extends BasicEventHooks {
    public static void firePlayerCokedEvent(PlayerEntity player, ItemStack smelted)
    {
        MinecraftForge.EVENT_BUS.post(new PlayerEvent.ItemSmeltedEvent(player, smelted));
    }
}
