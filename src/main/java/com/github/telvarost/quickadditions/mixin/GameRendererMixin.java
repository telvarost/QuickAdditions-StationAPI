package com.github.telvarost.quickadditions.mixin;

import com.github.telvarost.quickadditions.Config;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.Minecraft;
import net.minecraft.client.render.GameRenderer;
import net.minecraft.client.render.Tessellator;
import net.minecraft.entity.LivingEntity;
import net.minecraft.world.World;
import net.minecraft.world.biome.Biome;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.LocalCapture;

@Environment(EnvType.CLIENT)
@Mixin(GameRenderer.class)
public class GameRendererMixin {

    @Shadow private Minecraft client;

    @Redirect(
            method = "onFrameUpdate",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/client/Minecraft;pauseGame()V"
            )
    )
    public void quickAdditions_method_1844(Minecraft instance) {
        if (!Config.config.forceDisplayActive) {
            instance.pauseGame();
        }
    }

    @Unique private int currentX = 0;
    @Unique private int currentY = 0;

    @Inject(
            method = "renderSnow",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/world/biome/Biome;canSnow()Z",
                    shift = At.Shift.BEFORE
            ),
            locals = LocalCapture.CAPTURE_FAILHARD
    )
    private void quickAdditions_getLocationSnow(float par1, CallbackInfo ci, float var2, LivingEntity var3, World var4, int var5, int var6, int var7, Tessellator var8, double var9, double var11, double var13, int var15, int var16, Biome[] var17, int var18, int var19, int var20, Biome var21)
    {
        currentX = var19;
        currentY = var20;
    }

    @Redirect(
            method = "renderSnow",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/world/biome/Biome;canSnow()Z"
            )
    )
    private boolean quickAdditions_renderSnow(Biome instance) {
        boolean allowSnow = instance.canSnow();

        if (Config.config.enableAlwaysSnowAboveSetYLevel) {
            if (Config.config.alwaysSnowAboveThisYLevel < this.client.world.getTopSolidBlockY(currentX, currentY)) {
                allowSnow = true;
            }
        }

        return allowSnow;
    }

    @Inject(
            method = "renderSnow",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/world/biome/Biome;canRain()Z",
                    shift = At.Shift.BEFORE
            ),
            locals = LocalCapture.CAPTURE_FAILHARD
    )
    private void quickAdditions_getLocationRain(float par1, CallbackInfo ci, float var2, LivingEntity var3, World var4, int var5, int var6, int var7, Tessellator var8, double var9, double var11, double var13, int var15, int var16, Biome[] var17, int var18, int var19, int var20, Biome var21)
    {
        currentX = var19;
        currentY = var20;
    }

    @Redirect(
            method = "renderSnow",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/world/biome/Biome;canRain()Z"
            )
    )
    private boolean quickAdditions_renderRain(Biome instance) {
        boolean allowRain = instance.canRain();

        if (Config.config.enableAlwaysSnowAboveSetYLevel) {
            if (Config.config.alwaysSnowAboveThisYLevel < this.client.world.getTopSolidBlockY(currentX, currentY)) {
                allowRain = false;
            }
        }

        return allowRain;
    }

    @Unique private int highestBlockYLocation = 0;

    @Redirect(
            method = "renderRain",
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
            method = "renderRain",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/world/biome/Biome;canRain()Z"
            )
    )
    private boolean quickAdditions_tick(Biome instance) {
        boolean allowRain = instance.canRain();

        if (Config.config.enableAlwaysSnowAboveSetYLevel) {
            if (Config.config.alwaysSnowAboveThisYLevel < highestBlockYLocation) {
                allowRain = false;
            }
        }

        return allowRain;
    }
}
