package com.Clivet268.MachineBuiltWorld.util;

import com.Clivet268.MachineBuiltWorld.MachineBuiltWorld;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.storage.loot.LootEntry;
import net.minecraft.world.storage.loot.LootPool;
import net.minecraft.world.storage.loot.TableLootEntry;
import net.minecraftforge.event.LootTableLoadEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import java.util.List;

@Mod.EventBusSubscriber(modid = MachineBuiltWorld.MOD_ID)
public class LootHandler {

    private static String[] COLORS ={
            "red","blue", "cyan","black", "white", "magenta", "lime","brown","light_blue", "gray","light_gray","green",
            "orange","pink", "yellow"
    };

    private static String[] GLASS_TYPES ={
            "_stained_glass","_stained_glass_pane"
    };


    private static String[] TABLES = new String[COLORS.length*GLASS_TYPES.length+2];

    public static void makeTheGlasses(){
        TABLES = new String[COLORS.length*GLASS_TYPES.length+2];
        int iii = 0;
        int iiii = 0;
        for (String i: GLASS_TYPES) {
            for (String ii: COLORS) {
                TABLES[(iiii + 1) * iii] = ("" + COLORS[iii] + "" + GLASS_TYPES[iiii]);
                System.out.println(TABLES[(iiii + 1) * iii]);
                iii++;
            }
            iii=0;
            iiii++;
        }
        iiii=0;
    }
    @SubscribeEvent
    public static void lootLoad(LootTableLoadEvent evt) {
        String prefix = "minecraft:blocks/";
        String name = evt.getName().toString();

        if (name.startsWith(prefix)) {
            String file = name.substring(name.indexOf(prefix) + prefix.length());
            for(String i: TABLES)
            {
                if(i.equals(file)) {
                    evt.getTable().addPool(getInjectPool(file));
                    break;
                }
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
