package fr.light_vana_mod.nicofighter45.main.client;

import fr.light_vana_mod.nicofighter45.main.CommonInitializer;
import fr.light_vana_mod.nicofighter45.main.gui.CustomPlayerManagementScreen;
import fr.light_vana_mod.nicofighter45.main.gui.CustomPlayerManagementScreenHandler;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.screen.ingame.HandledScreens;
import net.minecraft.client.network.ClientPlayNetworkHandler;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.screen.ScreenHandlerType;
import net.minecraft.util.Identifier;
import net.minecraft.resource.featuretoggle.FeatureSet;
import org.jetbrains.annotations.NotNull;
import net.fabricmc.fabric.api.networking.v1.PacketSender;


public class ClientInitializer implements ClientModInitializer {

    public static int permissionLevel;
    public static boolean canMove;
    public static ScreenHandlerType<CustomPlayerManagementScreenHandler> CUSTOM_PLAYER_MANAGER_SCREEN_HANDLER;

    @Override
    public void onInitializeClient() {

        ClientPlayNetworking.registerGlobalReceiver(CommonInitializer.UPDATE_CUSTOM_PLAYER_PACKET, ClientInitializer::receiveCustomPlayerFromServer);

        CUSTOM_PLAYER_MANAGER_SCREEN_HANDLER = Registry.register(Registries.SCREEN_HANDLER,
                new Identifier(CommonInitializer.MODID, "custom_player_manager_screen_handler"),
                new ScreenHandlerType<>(CustomPlayerManagementScreenHandler::new, FeatureSet.empty()));
        HandledScreens.register(CUSTOM_PLAYER_MANAGER_SCREEN_HANDLER, CustomPlayerManagementScreen::new);

    }

    public static void receiveCustomPlayerFromServer(@NotNull MinecraftClient client, ClientPlayNetworkHandler handler,
                                                     @NotNull PacketByteBuf buf, PacketSender responseSender){
        buf.retain();
        client.execute(() -> {
            permissionLevel = buf.readInt();
            canMove = buf.readBoolean();
            buf.release();
        });
    }


}