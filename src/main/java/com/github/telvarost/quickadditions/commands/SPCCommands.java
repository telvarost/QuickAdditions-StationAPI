package com.github.telvarost.quickadditions.commands;

import com.matthewperiut.spc.api.CommandRegistry;

public class SPCCommands {
    public static void register() {
        CommandRegistry.add(new SetSpeedCommand());
    }
}