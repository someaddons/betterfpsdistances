package com.betterfpsdist.event;

import com.betterfpsdist.BetterfpsdistMod;
import com.betterfpsdist.config.ConfigurationCache;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.event.config.ModConfigEvent;

public class ModEventHandler
{
    @SubscribeEvent
    public static void onConfigChanged(ModConfigEvent event)
    {
        ConfigurationCache.stretch = BetterfpsdistMod.config.getCommonConfig().stretch.get();
    }
}
