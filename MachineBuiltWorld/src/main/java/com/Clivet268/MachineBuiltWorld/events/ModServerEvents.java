package com.Clivet268.MachineBuiltWorld.events;

import com.Clivet268.MachineBuiltWorld.MachineBuiltWorld;
import net.minecraft.entity.LivingEntity;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.util.text.TextFormatting;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import static com.Clivet268.MachineBuiltWorld.util.RegistryHandler.NEODYMIUM_MAGNET;

@Mod.EventBusSubscriber(modid = MachineBuiltWorld.MOD_ID, bus = Mod.EventBusSubscriber.Bus.FORGE, value = Dist.DEDICATED_SERVER)
public class ModServerEvents {
    @SubscribeEvent
    public static void onMagnetRightClick(PlayerInteractEvent.RightClickItem event)
    {
        LivingEntity player = event.getPlayer();
        if(player.getHeldItemMainhand().getItem() == NEODYMIUM_MAGNET.get())
        {


                if(!event.getPlayer().getEntityWorld().isRemote)
                {
                    String msg = TextFormatting.DARK_AQUA + "Shwoop";
                    player.sendMessage(new StringTextComponent(msg));

            }
        }
    }
}
