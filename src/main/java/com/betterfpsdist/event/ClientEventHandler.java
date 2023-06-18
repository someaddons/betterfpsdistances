package com.betterfpsdist.event;

import com.betterfpsdist.BetterfpsdistMod;
import com.mojang.serialization.Codec;
import net.minecraft.client.OptionInstance;
import net.minecraft.network.chat.Component;

public class ClientEventHandler
{
    public static final OptionInstance<Double> chunkrenderdist =
      new OptionInstance<>("options.circularrenderdist",
        OptionInstance.noTooltip(),
        ClientEventHandler::percentValueLabel,
        (new OptionInstance.IntRange(2, 40)).xmap((value) ->
        {
            return (double) value / 4.0D;
        }, (value) -> {
            return (int) (value * 4.0D);
        }), Codec.doubleRange(0.5D, 5.0D), 1.0D, (value) -> {
          BetterfpsdistMod.config.getCommonConfig().stretch = (double) value;
          BetterfpsdistMod.config.save();
      });

    private static Component percentValueLabel(Component p_231898_, double p_231899_)
    {
        return Component.translatable("options.percent_value", p_231898_, (int) (p_231899_ * 100.0D));
    }
}
