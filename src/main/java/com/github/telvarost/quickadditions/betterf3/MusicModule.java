package com.github.telvarost.quickadditions.betterf3;

import com.github.telvarost.quickadditions.ModHelper;
import net.minecraft.client.Minecraft;
import ralf2oo2.betterf3.modules.BaseModule;
import ralf2oo2.betterf3.utils.DebugLine;

public class MusicModule extends BaseModule {

    public MusicModule() {
        this.defaultNameColor = 0x00AA00;
        this.defaultValueColor = 0x55FFFF;

        this.nameColor = defaultNameColor;
        this.valueColor = defaultValueColor;

        lines.add(new DebugLine("music"));
        //lines.get(0).inReducedDebug = true;
    }

    @Override
    public void update(Minecraft minecraft) {
        lines.get(0).setValue(ModHelper.ModHelperFields.currentBGM);
    }
}
