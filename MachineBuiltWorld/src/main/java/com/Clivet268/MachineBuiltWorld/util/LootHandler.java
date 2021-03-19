package com.Clivet268.MachineBuiltWorld.util;

import com.Clivet268.MachineBuiltWorld.MachineBuiltWorld;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.storage.loot.LootEntry;
import net.minecraft.world.storage.loot.LootPool;
import net.minecraft.world.storage.loot.TableLootEntry;
import net.minecraftforge.event.LootTableLoadEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = MachineBuiltWorld.MOD_ID)
public class LootHandler {
    @SubscribeEvent
    public static void lootLoad(LootTableLoadEvent evt) {
        String prefix = "minecraft:blocks/";
        String name = evt.getName().toString();
        if (name.startsWith(prefix)) {
            String file = name.substring(name.indexOf(prefix) + prefix.length());
            switch(file)
            {
                case"glass":
                case"glass_pane":
                case"red_stained_glass":
                case"blue_stained_glass":
                case"cyan_stained_glass":
                case"black_stained_glass":
                case"white_stained_glass":
                case"magenta_stained_glass":
                case"lime_stained_glass":
                case"brown_stained_glass":
                case"light_blue_stained_glass":
                case"gray_stained_glass":
                case"light_gray_stained_glass":
                case"green_stained_glass":
                case"orange_stained_glass":
                case"pink_stained_glass":
                case"yellow_stained_glass":
                case"red_stained_glass_pane":
                case"blue_stained_glass_pane":
                case"cyan_stained_glass_pane":
                case"black_stained_glass_pane":
                case"white_stained_glass_pane":
                case"magenta_stained_glass_pane":
                case"lime_stained_glass_pane":
                case"brown_stained_glass_pane":
                case"light_blue_stained_glass_pane":
                case"gray_stained_glass_pane":
                case"light_gray_stained_glass_pane":
                case"green_stained_glass_pane":
                case"orange_stained_glass_pane":
                case"pink_stained_glass_pane":
                case"yellow_stained_glass_pane":evt.getTable().addPool(getInjectPool(file)); break;
                default:break;
            }
        }
    }

    public static LootPool getInjectPool(String entryName) {
        return LootPool.builder()
                .addEntry(getInjectEntry(entryName))
                .name("machinebuiltworld_inject")
                .build();
    }

    private static LootEntry.Builder getInjectEntry(String name) {
        ResourceLocation table = new ResourceLocation(MachineBuiltWorld.MOD_ID, "inject/" + name);
        return TableLootEntry.builder(table);
    }


}
