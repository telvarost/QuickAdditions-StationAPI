package com.github.telvarost.quickadditions.mixin;

import com.github.telvarost.quickadditions.Config;
import net.minecraft.entity.SpawnGroup;
import net.minecraft.world.NaturalSpawner;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Constant;
import org.spongepowered.asm.mixin.injection.ModifyConstant;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(NaturalSpawner.class)
public class NaturalSpawnerMixin {

    @Redirect(
            method = "tick",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/entity/SpawnGroup;getCapacity()I"
            )
    )
    private static int quickAdditions_tick(SpawnGroup instance) {
        if (70 == instance.getCapacity()) {
            return Config.config.MAX_SPAWN_CAPACITY_MONSTER;
        } else if (15 == instance.getCapacity()) {
            return Config.config.MAX_SPAWN_CAPACITY_PASSIVE;
        } else if (5 == instance.getCapacity()) {
            return Config.config.MAX_SPAWN_CAPACITY_SQUID;
        }
        else {
            return instance.getCapacity();
        }
    }

    @ModifyConstant(
            method = "tick",
            constant = @Constant(intValue = 4)
    )
    private static final int quickAdditions_spawnAttempts(int spawnAttempts) {
        return Config.config.SPAWN_GROUP_SIZE;
    }
}
