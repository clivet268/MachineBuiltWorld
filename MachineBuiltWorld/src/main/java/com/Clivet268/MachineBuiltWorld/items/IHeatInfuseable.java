package com.Clivet268.MachineBuiltWorld.items;

import com.Clivet268.MachineBuiltWorld.util.RegistryHandler;
import com.google.common.collect.Maps;
import net.minecraft.item.Item;
import net.minecraft.item.Items;
import net.minecraft.tags.Tag;
import net.minecraft.util.IItemProvider;

import java.util.Map;
/**
 * If you want to make a new coeking recipe you cant for now :/
 * but eventually youll have to make your own arrays with the infuseable
 * and infusie then inter it into the class params when i make them
 */
public interface IHeatInfuseable {
    Item[] heatInfuseable = {
            RegistryHandler.COKE.get(),
            Items.COAL,
            RegistryHandler.BORON_DUST.get()
    };

    Item[] heatInfusie = {
            Items.IRON_ORE,
            Items.COAL,
            RegistryHandler.BROKEN_GLASS_BUNCH_ITEM.get()
    };

    default Item whataAmIAGonnaDo(Item ii) {
        for(int i = 0; i < heatInfusie.length; i++)
        {
            if (heatInfusie[i] == ii)
            {
                return heatInfuseable[i];
            }
        }
        return null;
    }
    default boolean needsSomeInfusin(Item ii){
        for(Item i: heatInfusie){
            if(i == ii) {
                return true;
            }
        }
        return false;
    }

    default boolean canDoSomeInfusin(Item ii) {
        System.out.println(ii);
        System.out.println(RegistryHandler.Tags.ITEM_HEAT_INFUSEABLE.contains(ii));
        return RegistryHandler.Tags.ITEM_HEAT_INFUSEABLE.contains(ii);
    }
}
