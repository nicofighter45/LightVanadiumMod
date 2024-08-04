package fr.light_vana_mod.nicofighter45.main;

import fr.light_vana_mod.nicofighter45.main.server.commands.Command;
import net.fabricmc.api.ModInitializer;
import net.minecraft.util.Identifier;

public class CommonInitializer implements ModInitializer {


    public static final String MODID = "light-vana-mod";

    public static final Identifier UPDATE_CUSTOM_PLAYER_PACKET = new Identifier(CommonInitializer.MODID, "update_custom_player");


    @Override
    public void onInitialize() {

        Command.registerAllCommands();

    }

}