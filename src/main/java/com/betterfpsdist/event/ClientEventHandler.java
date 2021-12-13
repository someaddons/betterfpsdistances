package com.betterfpsdist.event;

import com.betterfpsdist.BetterfpsdistMod;
import net.minecraft.client.option.DoubleOption;
import net.minecraft.text.LiteralText;

public class ClientEventHandler
{
    public static final DoubleOption RenderSizeStretch = new DoubleOption("options.renderDistance", 0.5D, 10.0D, 0.25F, (value) -> {
        return BetterfpsdistMod.config.getCommonConfig().stretch;
    }, (setting, value) -> {
        BetterfpsdistMod.config.getCommonConfig().stretch = value;
        BetterfpsdistMod.config.save();
    }, (settings, value) -> {
        return new LiteralText("HRdistStretch:" + BetterfpsdistMod.config.getCommonConfig().stretch);
    });
}
