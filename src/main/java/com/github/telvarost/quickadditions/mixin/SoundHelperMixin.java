package com.github.telvarost.quickadditions.mixin;

import com.github.telvarost.quickadditions.Config;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.sound.SoundHelper;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Constant;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.ModifyConstant;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.Random;

@Environment(EnvType.CLIENT)
@Mixin(SoundHelper.class)
public class SoundHelperMixin {

    @Shadow private int musicCountdown;

    @Shadow private Random rand;

    @Inject(
            method = "<init>",
            at = @At("RETURN")
    )
    public void SoundHelper(CallbackInfo ci) {
        this.musicCountdown = this.rand.nextInt(Config.config.musicCoundownRandomIntervalMax);
    }

    @ModifyConstant(
            method = "handleBackgroundMusic",
            constant = @Constant(
                intValue = 12000,
                ordinal = 0
            )
    )
    private int quickAdditions_handleBackgroundMusicRandomIntervalMax(int constant) {
        return Config.config.musicCoundownRandomIntervalMax;
    }


    @ModifyConstant(
            method = "handleBackgroundMusic",
            constant = @Constant(
                intValue = 12000,
                ordinal = 1
            )
    )
    private int quickAdditions_handleBackgroundMusicRandomIntervalMin(int constant) {
        return Config.config.musicCoundownRandomIntervalMin;
    }
}
