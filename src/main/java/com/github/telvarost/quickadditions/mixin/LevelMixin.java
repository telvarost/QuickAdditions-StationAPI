package com.github.telvarost.quickadditions.mixin;

import com.github.telvarost.quickadditions.Config;
import net.minecraft.class_567;
import net.minecraft.world.World;
import net.minecraft.world.WorldProperties;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

import java.util.List;

@Mixin(World.class)
public abstract class LevelMixin {

    @Shadow private boolean allowMonsterSpawning;

    @Shadow public int difficulty;

    @Shadow public List players;

    @Shadow protected WorldProperties properties;

    @Shadow protected abstract void afterSkipNight();

    @Redirect(
            method = "tick",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/world/World;canSkipNight()Z"
            )
    )
    public boolean method_242(World instance) {
        if (Config.config.bedsSpeedUpNightRatherThanSkipIt) {
            if (instance.canSkipNight()) {
                boolean var1 = false;
                if (this.allowMonsterSpawning && this.difficulty >= 1) {
                    var1 = class_567.method_1869(instance, this.players);
                }

                if (!var1) {
                    long currentTime = this.properties.getTime();
                    long desiredTime = (currentTime + 24000L) - currentTime % 24000L;
                    long advancedTime = currentTime + 20L;
                    this.properties.setTime(advancedTime);

                    if (desiredTime < (currentTime + 20L)) {
                        this.afterSkipNight();
                    }
                }
            }

            return false;
        } else {
            return instance.canSkipNight();
        }
    }
}
