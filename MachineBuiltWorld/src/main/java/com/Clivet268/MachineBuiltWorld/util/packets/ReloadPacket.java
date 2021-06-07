package com.Clivet268.MachineBuiltWorld.util.packets;

import com.Clivet268.MachineBuiltWorld.MachineBuiltWorld;
import com.Clivet268.MachineBuiltWorld.items.IReloadable;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.ItemStack;
import net.minecraft.network.PacketBuffer;
import net.minecraftforge.fml.network.NetworkEvent;

import java.util.function.Supplier;

public class ReloadPacket {

    private final EquipmentSlotType slot;

    public ReloadPacket(EquipmentSlotType slot) {
        this.slot = slot;
    }

    public static void handle(ReloadPacket message, Supplier<NetworkEvent.Context> context) {
        PlayerEntity player = Minecraft.getInstance().player;
        if (player == null) {
            return;
        }
        context.get().enqueueWork(() -> {
            ItemStack stack = player.getItemStackFromSlot(message.slot);
            if (!stack.isEmpty() && stack.getItem() instanceof IReloadable) {
                ((IReloadable) stack.getItem()).reload(player);
            }
        });
        context.get().setPacketHandled(true);
    }

    public static void encode(ReloadPacket pkt, PacketBuffer buf) {
        buf.writeEnumValue(pkt.slot);
    }

    public static ReloadPacket decode(PacketBuffer buf) {
        return new ReloadPacket(buf.readEnumValue(EquipmentSlotType.class));
    }
}
