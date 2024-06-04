package com.github.telvarost.quickadditions;

import net.modificationstation.stationapi.api.util.Util;

public interface PlayerInterface {
    default void player_setMovementSpeed(float movementSpeed) {
        Util.assertImpl();
    }
}