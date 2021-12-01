package com.betterfpsdist.event;

import com.betterfpsdist.BetterfpsdistMod;
import com.betterfpsdist.config.ConfigurationCache;
import net.minecraft.client.AbstractOption;
import net.minecraft.client.gui.screen.VideoSettingsScreen;
import net.minecraft.client.settings.SliderPercentageOption;
import net.minecraft.util.text.StringTextComponent;
import net.minecraftforge.client.event.GuiOpenEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ClientEventHandler
{
    public static final SliderPercentageOption RenderSizeStretch = new SliderPercentageOption("options.renderDistance", 0.5D, 10.0D, 0.25F, (value) -> {
        return ConfigurationCache.stretch;
    }, (setting, value) -> {
        ConfigurationCache.stretch = value;
        BetterfpsdistMod.config.getCommonConfig().stretch.set(value);
    }, (settings, value) -> {
        return new StringTextComponent("HRdistStretch:" + ConfigurationCache.stretch);
    });
    static
    {
        try
        {
            final List<AbstractOption> options = new ArrayList<>(Arrays.asList(VideoSettingsScreen.OPTIONS));
            options.add(options.indexOf(AbstractOption.GUI_SCALE) + 1, RenderSizeStretch);
            VideoSettingsScreen.OPTIONS = options.toArray(new AbstractOption[0]);
        }
        catch (Throwable e)
        {
            BetterfpsdistMod.LOGGER.error("Error trying to add an option Button to video settings, likely optifine is present which removes vanilla functionality required."
                                            + " The mod still works, but you'll need to manually adjust the config to get different Render distance stretch values as the button could not be added.");
        }
    }
    @SubscribeEvent
    public static void on(GuiOpenEvent event)
    {
        if (event.isCanceled())
        {
            return;
        }
    }
}
