package com.github.telvarost.quickadditions.mixin;

import com.github.telvarost.quickadditions.Config;
import net.minecraft.level.Level;
import net.minecraft.level.LevelProperties;
import net.minecraft.sortme.LevelMonsterSpawner;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

import java.util.List;

@Mixin(Level.class)
public abstract class LevelMixin {

    @Shadow private boolean field_192;

    @Shadow public int difficulty;

    @Shadow public List players;

    @Shadow protected LevelProperties properties;

    @Shadow protected abstract void wakeUpSleepingPlayers();

    @Redirect(
            method = "method_242",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/level/Level;canSkipNight()Z"
            )
    )
    public boolean method_242(Level instance) {
        if (Config.config.bedsSpeedUpNightRatherThanSkipIt) {
            if (instance.canSkipNight()) {
                boolean var1 = false;
                if (this.field_192 && this.difficulty >= 1) {
                    var1 = LevelMonsterSpawner.method_1869(instance, this.players);
                }

                if (!var1) {
                    long currentTime = this.properties.getTime();
                    long desiredTime = (currentTime + 24000L) - currentTime % 24000L;
                    long advancedTime = currentTime + 20L;
                    this.properties.setTime(advancedTime);

                    if (desiredTime < (currentTime + 20L)) {
                        this.wakeUpSleepingPlayers();
                    }
                }
            }

            return false;
        } else {
            return instance.canSkipNight();
        }
    }
}
