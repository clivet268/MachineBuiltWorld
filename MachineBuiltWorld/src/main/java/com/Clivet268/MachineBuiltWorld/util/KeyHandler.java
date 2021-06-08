package com.Clivet268.MachineBuiltWorld.util;

import com.Clivet268.MachineBuiltWorld.MachineBuiltWorld;
import com.Clivet268.MachineBuiltWorld.items.IReloadable;
import com.Clivet268.MachineBuiltWorld.util.packets.ReloadPacket;
import javafx.util.Builder;
import net.minecraft.client.Minecraft;
import net.minecraft.client.settings.KeyBinding;
import net.minecraft.client.util.InputMappings;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.Item;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.InputEvent;
import net.minecraftforge.client.settings.KeyConflictContext;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.DistExecutor;
import net.minecraftforge.fml.client.registry.ClientRegistry;
import net.minecraftforge.server.ServerMain;
import org.lwjgl.glfw.GLFW;

import java.util.function.Supplier;

import static com.Clivet268.MachineBuiltWorld.MachineBuiltWorld.CHANNEL;

/**
 * do logic on server because smart guy said so, idk what logic to do on it but whatever
 */
public class KeyHandler {
    private boolean chacha = true;
    public static KeyBinding reloadKey = new KeyBinding("reload", KeyConflictContext.IN_GAME, InputMappings.Type.KEYSYM,
            GLFW.GLFW_KEY_R, "machine_built_world");

    /*public static KeyBinding detailsKey = new KeyBinding(MekanismLang.KEY_DETAILS_MODE.getTranslationKey(), KeyConflictContext.GUI, InputMappings.Type.KEYSYM,
            GLFW.GLFW_KEY_LEFT_SHIFT, MekanismLang.MEKANISM.getTranslationKey());
    public static KeyBinding descriptionKey = new KeyBinding(MekanismLang.KEY_DESCRIPTION_MODE.getTranslationKey(), KeyConflictContext.GUI,
            KeyModifier.SHIFT, InputMappings.Type.KEYSYM, GLFW.GLFW_KEY_N, MekanismLang.MEKANISM.getTranslationKey());


     */
    //MachineBuiltWorld.CHANNEL.sendToServer(new C2SRequestEnableTerrainCollisions())
    public KeyBinding[] keyBindings = new KeyBinding[1];

    public KeyHandler() {
        keyBindings[0] = reloadKey;

        ClientRegistry.registerKeyBinding(reloadKey);

        MinecraftForge.EVENT_BUS.addListener(this::onTick);
    }



    private void onTick(InputEvent.KeyInputEvent event) {
        keyTick();
    }


    public void keyDown(KeyBinding kb) {
        PlayerEntity player = Minecraft.getInstance().player;
        if (player == null) {
            return;
        }
        if (kb == reloadKey) {
            Item thing = player.getHeldItemMainhand().getItem();
            if (thing instanceof IReloadable) {
                //((IReloadable) thing).reload(player);

                MachineBuiltWorld.PACKETHANDLER.sendToServer(new ReloadPacket(EquipmentSlotType.MAINHAND));
            }
        }
    }
    public void keyUp(KeyBinding kb) {
        this.chacha = true;
    }
    public void keyTick() {
        for (KeyBinding keyBinding : keyBindings) {
            if (keyBinding.isPressed() ) {
               if(this.chacha) {
                   keyDown(keyBinding);
                   this.chacha = false;
                   System.out.println("chacharealsmooth");
               }
            }
            else {
                keyUp(keyBinding);
            }
        }
    }
}
