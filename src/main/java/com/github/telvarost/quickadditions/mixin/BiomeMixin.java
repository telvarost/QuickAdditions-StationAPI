package com.github.telvarost.quickadditions.mixin;

import com.github.telvarost.quickadditions.Config;
import net.minecraft.world.biome.Biome;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Mutable;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(Biome.class)
public abstract class BiomeMixin {
    @Mutable
    @Shadow @Final public static Biome SAVANNA;

    @Inject(method = "init", at = @At("RETURN"))
    private static void quickAdditions_init(CallbackInfo ci) {
        if (Config.config.WEATHER_CONFIG.disableRainInSavannaBiomes) {
            SAVANNA.disableRain();
        }
    }
}
