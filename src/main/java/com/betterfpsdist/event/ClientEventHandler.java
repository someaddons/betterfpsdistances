package com.betterfpsdist.event;

import com.betterfpsdist.BetterfpsdistMod;
import com.mojang.serialization.Codec;
import net.minecraft.client.option.SimpleOption;
import net.minecraft.text.Text;

public class ClientEventHandler
{
    public static final SimpleOption<Double> chunkrenderdist =
      new SimpleOption<>("options.circularrenderdist",
        SimpleOption.emptyTooltip(),
        ClientEventHandler::percentValueLabel,
        (new SimpleOption.ValidatingIntSliderCallbacks(2, 40)).withModifier((value) ->
        {
            return (double) value / 4.0D;
        }, (value) -> {
            return (int) (value * 4.0D);
        }), Codec.doubleRange(0.5D, 5.0D), 1.0D, (value) -> {
          BetterfpsdistMod.config.getCommonConfig().stretch = (double) value;
          BetterfpsdistMod.config.save();
      });

    private static Text percentValueLabel(Text p_231898_, double p_231899_)
    {
        return Text.translatable("options.percent_value", p_231898_, (int) (p_231899_ * 100.0D));
    }
}
