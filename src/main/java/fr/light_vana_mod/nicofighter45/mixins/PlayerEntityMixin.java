package fr.light_vana_mod.nicofighter45.mixins;

import fr.light_vana_mod.nicofighter45.main.client.ClientInitializer;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.text.Text;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(PlayerEntity.class)
public abstract class PlayerEntityMixin {

    @Unique
    private final PlayerEntity player = (PlayerEntity) (Object) this;

    @Inject(at = @At("HEAD"), method="getDisplayName", cancellable = true)
    public void getDisplayName(CallbackInfoReturnable<Text> cir) {
        String name = player.getName().getString();
        int permission;
        if(player.getWorld().isClient){
            permission = ClientInitializer.permissionLevel;
        }else{
            assert player.getServer() != null;
            permission = player.getServer().getPermissionLevel(player.getGameProfile());
        }
        if(permission == 0){
            cir.setReturnValue(Text.of("§8[§9Joueur§8] §f" + name));
        }else if(permission == 1){
            cir.setReturnValue(Text.of("§8[§6Vanadeur§8] §f" + name));
        }else if(permission == 2){
            cir.setReturnValue(Text.of("§8[§2Modo§8] §f" + name));
        }else{
            cir.setReturnValue(Text.of("§8[§cAdmin§8] §f" + name));
        }
        cir.cancel();
    }

}