package com.github.telvarost.quickadditions.mixin;

import com.github.telvarost.quickadditions.Config;
import net.minecraft.entity.LivingEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(LivingEntity.class)
public class LivingEntityMixin {

    @Inject(
            method = "getLimitPerChunk",
            at = @At("HEAD"),
            cancellable = true
    )
    public void getLimitPerChunk(CallbackInfoReturnable<Integer> cir) {
        cir.setReturnValue(Config.config.SPAWN_GROUP_SIZE);
    }
}
