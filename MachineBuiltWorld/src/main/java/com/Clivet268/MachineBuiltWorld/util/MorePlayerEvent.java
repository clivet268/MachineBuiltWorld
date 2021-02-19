package com.Clivet268.MachineBuiltWorld.util;


import java.io.File;

import net.minecraft.entity.item.ItemEntity;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.world.dimension.DimensionType;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.ForgeEventFactory;
import net.minecraftforge.eventbus.api.Cancelable;
import net.minecraft.block.BlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.event.entity.living.LivingEvent;
import net.minecraftforge.eventbus.api.Event;

import javax.annotation.Nonnull;

/**
 * PlayerEvent is fired whenever an event involving Living entities occurs. <br>
 * If a method utilizes this {@link net.minecraftforge.eventbus.api.Event} as its parameter, the method will
 * receive every child event of this class.<br>
 * <br>
 * All children of this event are fired on the {@link MinecraftForge#EVENT_BUS}.
 **/
public class MorePlayerEvent extends LivingEvent
{
    private final PlayerEntity entityPlayer;
    public MorePlayerEvent(PlayerEntity player)
    {
        super(player);
        entityPlayer = player;
    }
    public static class ItemCraftedEvent extends net.minecraftforge.event.entity.player.PlayerEvent {
        @Nonnull
        private final ItemStack crafting;
        private final IInventory craftMatrix;
        public ItemCraftedEvent(PlayerEntity player, @Nonnull ItemStack crafting, IInventory craftMatrix)
        {
            super(player);
            this.crafting = crafting;
            this.craftMatrix = craftMatrix;
        }

        @Nonnull
        public ItemStack getCrafting()
        {
            return this.crafting;
        }

        public IInventory getInventory()
        {
            return this.craftMatrix;
        }
    }
/*
    public static class ItemCokedvent extends net.minecraftforge.event.entity.player.PlayerEvent {
        @Nonnull
        private final ItemStack smelting;
        public ItemSmeltedEvent(PlayerEntity player, @Nonnull ItemStack crafting)
        {
            super(player);
            this.smelting = crafting;
        }

        @Nonnull
        public ItemStack getSmelting()
        {
            return this.smelting;
        }
    }

 */

}
