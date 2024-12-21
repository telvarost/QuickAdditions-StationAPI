package com.github.telvarost.quickadditions.mixin;

import com.github.telvarost.quickadditions.Config;
import com.github.telvarost.quickadditions.ModHelper;
import net.minecraft.client.Minecraft;
import net.minecraft.client.util.Session;
import net.minecraft.entity.player.ClientPlayerEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ClientPlayerEntity.class)
public class ClientPlayerEntityMixin extends PlayerEntity {

    public ClientPlayerEntityMixin(Minecraft minecraft, World world, Session session, int dimensionId) {
        super(world);
    }

    public void spawn() {
    }

    @Inject(
            method = "tickMovement",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/client/Minecraft;changeDimension()V"
            ),
            cancellable = true
    )
    public void tickMovement(CallbackInfo ci) {
        if (Config.config.MUSIC_CONFIG.stopDimensionSpecificSongOnPortalUse) {
            if (ModHelper.ModHelperFields.songLevelId == this.dimensionId) {
                ModHelper.ModHelperFields.cancelCurrentBGM = true;
            }
        }
    }
}
