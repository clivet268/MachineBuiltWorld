package com.Clivet268.MachineBuiltWorld.util.packets;

import com.Clivet268.MachineBuiltWorld.MachineBuiltWorld;
import com.Clivet268.MachineBuiltWorld.items.IReloadable;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.ItemStack;
import net.minecraft.network.INetHandler;
import net.minecraft.network.PacketBuffer;
import net.minecraft.network.play.ServerPlayNetHandler;
import net.minecraft.world.server.ServerWorld;
import net.minecraftforge.fml.network.NetworkEvent;

import java.util.function.Supplier;

public class ReloadPacket {

    private final EquipmentSlotType slot;

    public ReloadPacket(EquipmentSlotType slot) {
        this.slot = slot;
    }

    public static void handle(ReloadPacket message, Supplier<NetworkEvent.Context> context) {
        NetworkEvent.Context contextt = context.get();
        INetHandler handler = contextt.getNetworkManager().getNetHandler();
        ServerWorld world = (ServerWorld) ((ServerPlayNetHandler) handler).player.world;
        ServerPlayerEntity player = ((ServerPlayNetHandler) handler).player;
        if (player == null) {
            return;
        }
        context.get().enqueueWork(() -> {
            ItemStack stack = player.getItemStackFromSlot(message.slot);
            if (!stack.isEmpty() && stack.getItem() instanceof IReloadable) {
                ((IReloadable) stack.getItem()).reload(player);
                System.out.println("something");
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
