package com.github.telvarost.quickadditions.mixin;

import com.github.telvarost.quickadditions.Config;
import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import net.minecraft.entity.MobEntity;
import net.minecraft.entity.mob.MonsterEntity;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

import java.util.Random;

@Mixin(MonsterEntity.class)
public class MonsterEntityMixin extends MobEntity {

    public MonsterEntityMixin(World world) {
        super(world);
    }

    @WrapOperation(
            method = "canSpawn",
            at = @At(
                    value = "INVOKE",
                    target = "Ljava/util/Random;nextInt(I)I",
                    ordinal = 1
            )
    )
    public int quickAdditions_canSpawn(Random instance, int bound, Operation<Integer> original) {
        int canSpawnAtLightLevelOrBelow = original.call(instance, bound);

        if (Config.config.ENTITY_SPAWN_MECHANICS_CONFIG.monstersSpawnAtOrBelowLightLevel < canSpawnAtLightLevelOrBelow) {
            canSpawnAtLightLevelOrBelow = Config.config.ENTITY_SPAWN_MECHANICS_CONFIG.monstersSpawnAtOrBelowLightLevel;
        }

        return canSpawnAtLightLevelOrBelow;
    }
}
