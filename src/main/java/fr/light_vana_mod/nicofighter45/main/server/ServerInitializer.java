package fr.light_vana_mod.nicofighter45.main.server;

import fr.light_vana_mod.nicofighter45.main.CommonInitializer;
import net.fabricmc.api.DedicatedServerModInitializer;
import net.fabricmc.fabric.api.networking.v1.PacketByteBufs;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.network.ServerPlayerEntity;
import org.jetbrains.annotations.NotNull;

import java.util.*;

public class ServerInitializer implements DedicatedServerModInitializer {

    public static final String BROADCAST_MSG_PREFIX = "§8[§4BROADCAST§8] §f";

    public static final List<UUID> frozenPlayer = new ArrayList<>();

    @Override
    public void onInitializeServer() {
    }

    public static void sendInfoToClient(@NotNull UUID uuid, @NotNull MinecraftServer server){
        ServerPlayerEntity serverPlayerEntity = server.getPlayerManager().getPlayer(uuid);
        PacketByteBuf buf = PacketByteBufs.create();
        assert serverPlayerEntity != null;
        buf.writeInt(server.getPermissionLevel(serverPlayerEntity.getGameProfile()));
        buf.writeBoolean(!frozenPlayer.contains(uuid));
        ServerPlayNetworking.send(serverPlayerEntity, CommonInitializer.UPDATE_CUSTOM_PLAYER_PACKET, buf);
    }

    public static void sendInfoToClient(@NotNull ServerPlayerEntity player){
        PacketByteBuf buf = PacketByteBufs.create();
        MinecraftServer server = player.getServer();
        if(server == null){
            buf.writeInt(0);
        }else{
            buf.writeInt(server.getPermissionLevel(player.getGameProfile()));
        }
        buf.writeBoolean(!frozenPlayer.contains(player.getUuid()));
        ServerPlayNetworking.send(player, CommonInitializer.UPDATE_CUSTOM_PLAYER_PACKET, buf);
    }

}