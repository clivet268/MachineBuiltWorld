package com.Clivet268.MachineBuiltWorld.util.packets;

import com.Clivet268.MachineBuiltWorld.MachineBuiltWorld;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.network.PacketBuffer;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.network.NetworkDirection;
import net.minecraftforge.fml.network.NetworkEvent;
import net.minecraftforge.fml.network.NetworkRegistry;
import net.minecraftforge.fml.network.simple.SimpleChannel;

import java.util.Optional;
import java.util.function.BiConsumer;
import java.util.function.Function;
import java.util.function.Supplier;

import static com.Clivet268.MachineBuiltWorld.MachineBuiltWorld.CHANNEL;

public class PacketHandler {
    private int index = 0;


    protected  <MSG> void registerClientToServer(Class<MSG> type, BiConsumer<MSG, PacketBuffer> encoder, Function<PacketBuffer, MSG> decoder,
                                                     BiConsumer<MSG, Supplier<NetworkEvent.Context>> consumer) {
            CHANNEL.registerMessage(index++, type, encoder, decoder, consumer, Optional.of(NetworkDirection.PLAY_TO_SERVER));
        }

        public void initialize() {
            //Client to server messages
            registerClientToServer(ReloadPacket.class, ReloadPacket::encode, ReloadPacket::decode, ReloadPacket::handle);

    }
    public <MSG> void sendToServer(MSG message) {
        CHANNEL.sendToServer(message);
    }
}
