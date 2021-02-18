package com.Clivet268.MachineBuiltWorld.events;

import com.Clivet268.MachineBuiltWorld.MachineBuiltWorld;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.item.ItemEntity;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.util.text.TextFormatting;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import static com.Clivet268.MachineBuiltWorld.util.RegistryHandler.MAGNET;

@Mod.EventBusSubscriber(modid = MachineBuiltWorld.MOD_ID, bus = Mod.EventBusSubscriber.Bus.FORGE, value = Dist.CLIENT)

public class ModClientEvents {
    @SubscribeEvent
    public static void onMagnetRightClick(PlayerInteractEvent.RightClickItem event)
    {
        LivingEntity player = event.getEntityLiving();
        if(player.getHeldItemMainhand().getItem() == MAGNET.get())
         {

            Entity targetItems = (Entity) event.getEntity();
            if(targetItems instanceof ItemEntity)
            {

            targetItems.addVelocity(0+player.getPosX(),0+ player.getPosY(), 0+player.getPosZ());
            if(!event.getPlayer().getEntityWorld().isRemote)
            {
                 String msg = TextFormatting.DARK_AQUA + "Shwoop";
                player.sendMessage(new StringTextComponent(msg));
            }
            }
        }
    }

}
