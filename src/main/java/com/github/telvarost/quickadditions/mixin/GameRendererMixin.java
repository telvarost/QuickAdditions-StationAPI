package com.github.telvarost.quickadditions.mixin;

import com.github.telvarost.quickadditions.Config;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.Minecraft;
import net.minecraft.client.render.GameRenderer;
import org.lwjgl.opengl.Display;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Environment(EnvType.CLIENT)
@Mixin(GameRenderer.class)
public class GameRendererMixin {

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
}
