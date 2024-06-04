package com.github.telvarost.quickadditions.mixin;

import com.github.telvarost.quickadditions.ModHelper;
import net.minecraft.entity.EntityBase;
import net.minecraft.entity.Living;
import net.minecraft.util.maths.Box;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.List;

@Mixin(Living.class)
public class LivingMixin {


    @Shadow protected float field_1060;

    @Shadow protected float field_1029;

    @Inject(
            method = "updateDespawnCounter",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/entity/Living;travel(FF)V"
            )
    )
    public void updateDespawnCounter(CallbackInfo ci) {
        field_1060 *= ModHelper.ModHelperFields.currentSpeed;
        field_1029 *= ModHelper.ModHelperFields.currentSpeed;
    }
}
