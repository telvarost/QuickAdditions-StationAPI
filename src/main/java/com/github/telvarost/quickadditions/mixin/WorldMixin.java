package com.github.telvarost.quickadditions.mixin;

import com.github.telvarost.quickadditions.Config;
import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import net.minecraft.entity.passive.PigEntity;
import net.minecraft.entity.vehicle.BoatEntity;
import net.minecraft.world.NaturalSpawner;
import net.minecraft.world.World;
import net.minecraft.world.WorldProperties;
import net.minecraft.world.biome.Biome;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Constant;
import org.spongepowered.asm.mixin.injection.ModifyConstant;

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
    @Unique private BoatEntity skipObject;

    @WrapOperation(
            method = "tick",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/world/World;canSkipNight()Z"
            )
    )
    public boolean quickAdditions_tickCanSkipNight(World instance, Operation<Boolean> original) {
        if (Config.config.bedsSpeedUpNightRatherThanSkipIt) {
            if (original.call(instance)) {
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
            return original.call(instance);
        }
    }

    @WrapOperation(
            method = "afterSkipNight",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/world/World;clearWeather()V"
            )
    )
    protected void quickAdditions_afterSkipNight(World instance, Operation<Void> original) {
        if (Config.config.WEATHER_CONFIG.sleepOnlyResetsWeatherWhenRaining) {
            if (isRaining() || isThundering()) {
                this.clearWeather();
            } else {
                /** - Do nothing */
            }
        } else {
            this.clearWeather();
        }
    }

    @ModifyConstant(
            method = "updateWeatherCycles",
            constant = @Constant(
                    intValue = 12000,
                    ordinal = 0
            )
    )
    protected int updateWeatherCycles_thunderDurationRandomLimit(int constant) {
        return Config.config.WEATHER_CONFIG.thunderDurationRandomLimit;
    }

    @ModifyConstant(
            method = "updateWeatherCycles",
            constant = @Constant(
                    intValue = 3600,
                    ordinal = 0
            )
    )
    protected int updateWeatherCycles_thunderDurationMinimum(int constant) {
        return Config.config.WEATHER_CONFIG.thunderDurationMinimum;
    }

    @ModifyConstant(
            method = "updateWeatherCycles",
            constant = @Constant(
                    intValue = 168000,
                    ordinal = 0
            )
    )
    protected int updateWeatherCycles_timeUntilThunderRandomLimit(int constant) {
        return Config.config.WEATHER_CONFIG.timeUntilThunderRandomLimit;
    }

    @ModifyConstant(
            method = "updateWeatherCycles",
            constant = @Constant(
                    intValue = 12000,
                    ordinal = 1
            )
    )
    protected int updateWeatherCycles_timeUntilThunderMinimum(int constant) {
        return Config.config.WEATHER_CONFIG.timeUntilThunderMinimum;
    }

    @ModifyConstant(
            method = "updateWeatherCycles",
            constant = @Constant(
                    intValue = 12000,
                    ordinal = 2
            )
    )
    protected int updateWeatherCycles_rainDurationRandomLimit(int constant) {
        return Config.config.WEATHER_CONFIG.rainDurationRandomLimit;
    }

    @ModifyConstant(
            method = "updateWeatherCycles",
            constant = @Constant(
                    intValue = 12000,
                    ordinal = 3
            )
    )
    protected int updateWeatherCycles_rainDurationMinimum(int constant) {
        return Config.config.WEATHER_CONFIG.rainDurationMinimum;
    }

    @ModifyConstant(
            method = "updateWeatherCycles",
            constant = @Constant(
                    intValue = 168000,
                    ordinal = 1
            )
    )
    protected int updateWeatherCycles_timeUntilRainRandomLimit(int constant) {
        return Config.config.WEATHER_CONFIG.timeUntilRainRandomLimit;
    }

    @ModifyConstant(
            method = "updateWeatherCycles",
            constant = @Constant(
                    intValue = 12000,
                    ordinal = 4
            )
    )
    protected int updateWeatherCycles_timeUntilRainMinimum(int constant) {
        return Config.config.WEATHER_CONFIG.timeUntilRainMinimum;
    }

    @WrapOperation(
            method = "manageChunkUpdatesAndEvents",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/world/World;getTopSolidBlockY(II)I"
            )
    )
    private int quickAdditions_tickGetTopSolidBlock(World instance, int x, int z, Operation<Integer> original) {
        highestBlockYLocation = original.call(instance, x, z);
        return highestBlockYLocation;
    }

    @WrapOperation(
            method = "manageChunkUpdatesAndEvents",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/world/biome/Biome;canSnow()Z"
            )
    )
    private boolean quickAdditions_tickCanSnow(Biome instance, Operation<Boolean> original) {
        boolean allowSnow = original.call(instance);

        if (Config.config.WEATHER_CONFIG.enableAlwaysSnowAboveSetYLevel) {
            if (Config.config.WEATHER_CONFIG.alwaysSnowAboveThisYLevel < highestBlockYLocation) {
                allowSnow = true;
            }
        }

        return allowSnow;
    }

    @WrapOperation(
            method = "countEntities",
            at = @At(
                    value = "INVOKE",
                    target = "Ljava/util/List;get(I)Ljava/lang/Object;"
            )
    )
    public Object quickAdditions_countEntities(List instance, int index, Operation<Object> original) {
        Object entityObject = original.call(instance, index);

        if (Config.config.ENTITY_SPAWN_MECHANICS_CONFIG.doNotCountSaddledPigs) {
            if (entityObject instanceof PigEntity) {
                PigEntity pigEntity = (PigEntity) entityObject;
                if (pigEntity.isSaddled()) {
                    if (null == skipObject) {
                        skipObject = new BoatEntity(pigEntity.world);
                    }
                    return skipObject;
                }
            }
        }

        return entityObject;
    }

}
