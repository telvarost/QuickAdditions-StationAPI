package com.github.telvarost.quickadditions.mixin;

import com.github.telvarost.quickadditions.Config;
import net.minecraft.world.World;
import net.minecraft.world.biome.Biome;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(World.class)
public abstract class WorldMixin {

    @Unique private int highestBlockYLocation = 0;

    @Redirect(
            method = "manageChunkUpdatesAndEvents",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/world/World;getTopSolidBlockY(II)I"
            )
    )
    private int quickAdditions_tick(World instance, int x, int z) {
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
    private boolean quickAdditions_tick(Biome instance) {
        boolean allowSnow = instance.canSnow();

        if (Config.config.enableAlwaysSnowAboveSetYLevel) {
            if (Config.config.alwaysSnowAboveThisYLevel < highestBlockYLocation) {
                allowSnow = true;
            }
        }

        return allowSnow;
    }

}
