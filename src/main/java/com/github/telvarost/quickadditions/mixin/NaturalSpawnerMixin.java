package com.github.telvarost.quickadditions.mixin;

import com.github.telvarost.quickadditions.Config;
import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import net.minecraft.entity.SpawnGroup;
import net.minecraft.world.NaturalSpawner;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Constant;
import org.spongepowered.asm.mixin.injection.ModifyConstant;

@Mixin(NaturalSpawner.class)
public class NaturalSpawnerMixin {

    @WrapOperation(
            method = "tick",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/entity/SpawnGroup;getCapacity()I"
            )
    )
    private static int quickAdditions_tick(SpawnGroup instance, Operation<Integer> original) {
        if (70 == original.call(instance)) {
            return Config.config.ENTITY_SPAWN_MECHANICS_CONFIG.MAX_SPAWN_CAPACITY_MONSTER;
        } else if (15 == original.call(instance)) {
            return Config.config.ENTITY_SPAWN_MECHANICS_CONFIG.MAX_SPAWN_CAPACITY_PASSIVE;
        } else if (5 == original.call(instance)) {
            return Config.config.ENTITY_SPAWN_MECHANICS_CONFIG.MAX_SPAWN_CAPACITY_SQUID;
        } else {
            return original.call(instance);
        }
    }

    @ModifyConstant(
            method = "tick",
            constant = @Constant(intValue = 4)
    )
    private static final int quickAdditions_spawnAttempts(int spawnAttempts) {
        return Config.config.ENTITY_SPAWN_MECHANICS_CONFIG.SPAWN_GROUP_SIZE;
    }
}
