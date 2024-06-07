package com.github.telvarost.quickadditions.mixin;

import com.github.telvarost.quickadditions.Config;
import net.minecraft.entity.player.PlayerBase;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(PlayerBase.class)
public abstract class PlayerBaseMixin {

    @Shadow public abstract boolean isSleeping();

    @Shadow public abstract void wakeUp(boolean bl, boolean bl2, boolean bl3);

    @Shadow public abstract boolean hasSleptEnough();

    @Inject(
            method = "swingHand",
            at = @At("HEAD"),
            cancellable = true
    )
    public void swingHand(CallbackInfo ci) {
        if (isSleeping() && hasSleptEnough() && Config.config.handSwingClickToExitBedsEnabled) {
            wakeUp(false, false, false);
        }
    }
}
