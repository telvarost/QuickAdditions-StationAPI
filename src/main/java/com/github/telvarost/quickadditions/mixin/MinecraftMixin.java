package com.github.telvarost.quickadditions.mixin;

import com.github.telvarost.quickadditions.Config;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.Minecraft;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.io.File;

@Environment(EnvType.CLIENT)
@Mixin(Minecraft.class)
public class MinecraftMixin {

    @Inject(
            method = "loadSoundFromDir",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/client/sound/SoundHelper;addMusic(Ljava/lang/String;Ljava/io/File;)V"
            ),
            cancellable = true
    )
    public void loadSoundFromDir(String file, File par2, CallbackInfo ci) {
        if (Config.config.disableDefaultMinecraftBGM) {
            System.out.println("Removing default Minecraft background music.");
            ci.cancel();
        }
    }
}
