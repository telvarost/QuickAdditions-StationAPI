package com.github.telvarost.quickadditions.mixin;

import com.github.telvarost.quickadditions.Config;
import net.minecraft.world.NaturalSpawner;
import net.minecraft.world.World;
import net.minecraft.world.WorldProperties;
import net.minecraft.world.biome.Biome;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

import java.util.List;

@Mixin(World.class)
public abstract class WorldMixin {

    @Shadow private boolean allowMonsterSpawning;

    @Shadow public int difficulty;

    @Shadow public List players;

    @Shadow protected WorldProperties properties;

    @Shadow protected abstract void afterSkipNight();

    @Shadow public abstract boolean isRaining();

    @Shadow public abstract boolean isThundering();

    @Shadow protected abstract void clearWeather();

    @Unique private int highestBlockYLocation = 0;

    @Redirect(
            method = "tick",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/world/World;canSkipNight()Z"
            )
    )
    public boolean quickAdditions_tickCanSkipNight(World instance) {
        if (Config.config.bedsSpeedUpNightRatherThanSkipIt) {
            if (instance.canSkipNight()) {
                boolean var1 = false;
                if (this.allowMonsterSpawning && this.difficulty >= 1) {
                    var1 = NaturalSpawner.spawnMonstersAndWakePlayers(instance, this.players);
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

    @Redirect(
            method = "afterSkipNight",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/world/World;clearWeather()V"
            )
    )
    protected void quickAdditions_afterSkipNight(World instance) {
        if (Config.config.sleepOnlyResetsWeatherWhenRaining) {
            if (isRaining() || isThundering()) {
                this.clearWeather();
            } else {
                /** - Do nothing */
            }
        } else {
            this.clearWeather();
        }
    }

    @Redirect(
            method = "manageChunkUpdatesAndEvents",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/world/World;getTopSolidBlockY(II)I"
            )
    )
    private int quickAdditions_tickGetTopSolidBlock(World instance, int x, int z) {
        highestBlockYLocation = instance.getTopSolidBlockY(x, z);
        return highestBlockYLocation;
    }

    @Redirect(
            method = "manageChunkUpdatesAndEvents",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/world/biome/Biome;canSnow()Z"
            )
    )
    private boolean quickAdditions_tickCanSnow(Biome instance) {
        boolean allowSnow = instance.canSnow();

        if (Config.config.enableAlwaysSnowAboveSetYLevel) {
            if (Config.config.alwaysSnowAboveThisYLevel < highestBlockYLocation) {
                allowSnow = true;
            }
        }

        return allowSnow;
    }

}
