package com.betterfpsdist.event;

import com.betterfpsdist.BetterfpsdistMod;
import com.mojang.serialization.Codec;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.minecraft.client.Minecraft;
import net.minecraft.client.OptionInstance;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;

import java.util.HashSet;

public class ClientEventHandler
{
    public static final OptionInstance<Double> chunkrenderdist =
      new OptionInstance<>("options.verticalstretch",
          OptionInstance.cachedConstantTooltip(Component.translatable("options.verticalstretch.tooltip")),
        ClientEventHandler::percentValueLabel,
        (new OptionInstance.IntRange(2, 40)).xmap((value) ->
        {
            return (double) value / 4.0D;
        }, (value) -> {
            return (int) (value * 4.0D);
        }, false), Codec.doubleRange(0.5D, 5.0D), 1.0D, (value) -> {
          BetterfpsdistMod.config.getCommonConfig().verticalScaling = (double) value;
          BetterfpsdistMod.config.save();
      });

    public static final OptionInstance<Double> chunkrenderdistxz =
      new OptionInstance<>("options.horizontalstretch",
          OptionInstance.cachedConstantTooltip(Component.translatable("options.horizontalstretch.tooltip")),
        ClientEventHandler::percentValueLabel,
        (new OptionInstance.IntRange(0, 100)).xmap((value) ->
        {
            return (double) value / 100.0D;
        }, (value) -> {
            return (int) (value  * 100.0D);
        }, false), Codec.doubleRange(0D, 100D), 1.0D, (value) -> {
          BetterfpsdistMod.config.getCommonConfig().horizontalScaling = (double) 1.0 + value;
          BetterfpsdistMod.config.save();
      });

    static
    {
        ClientTickEvents.END_CLIENT_TICK.register(ClientEventHandler::onClientTick);
        chunkrenderdist.set(BetterfpsdistMod.config.getCommonConfig().verticalScaling);
        chunkrenderdistxz.set(BetterfpsdistMod.config.getCommonConfig().horizontalScaling - 1.0);
    }

    private static void onClientTick(Minecraft minecraft)
    {
        // Calculate modifiers

        if (Minecraft.getInstance().player == null)
        {
            xStretch = 1;
            yStretch = 1;
            return;
        }

        // Rotate angle in radians
        cosAngle = (float) Math.cos(-Minecraft.getInstance().player.getViewYRot(1.0f) * (Math.PI / 180.0));
        sinAngle = (float) Math.sin(-Minecraft.getInstance().player.getViewYRot(1.0f) * (Math.PI / 180.0));

        xStretch = (float) (BetterfpsdistMod.config.getCommonConfig().horizontalScaling * BetterfpsdistMod.config.getCommonConfig().horizontalScaling);
        yStretch = (float) (BetterfpsdistMod.config.getCommonConfig().verticalScaling * BetterfpsdistMod.config.getCommonConfig().verticalScaling);

        maxSqDist = (Minecraft.getInstance().options.renderDistance().get() * 16) * (Minecraft.getInstance().options.renderDistance().get() * 16) + 1;

        if (BetterfpsdistMod.config.getCommonConfig().debugMode && Minecraft.getInstance().player.level().getGameTime() > nextUpdate && hiddenSections.size() > 0)
        {
            nextUpdate = Minecraft.getInstance().player.level().getGameTime() + 20 * 2;
            BetterfpsdistMod.LOGGER.warn("Hidden Sections:" + hiddenSections.size());
            Minecraft.getInstance().player.sendSystemMessage(Component.literal("Hidden chunk sections:" + hiddenSections.size()));
            hiddenSections.clear();
        }
    }

    public static float xStretch = 1;
    public static float yStretch = 1;
    public static float cosAngle = 1;
    public static float sinAngle = 1;
    public static int maxSqDist = 0;

    public static HashSet<BlockPos> hiddenSections = new HashSet<>();
    public static long              nextUpdate     = 0;

    public static double adjustedDistance(BlockPos from, BlockPos to)
    {
        return adjustedDistance(from.getX(), from.getY(), from.getZ(), to.getX(), to.getY(), to.getZ());
    }

    public static double adjustedDistance(final int x1, final int y1, final int z1, final double x2, final double y2, final double z2)
    {
        double x2New = (x2 - x1) * cosAngle - (z2 - z1) * sinAngle + x1;
        double z2New = (x2 - x1) * sinAngle + (z2 - z1) * cosAngle + z1;

        double d0 = x1 - x2New;
        double d1 = y1 - y2;
        double d2 = z1 - z2New;

        return xStretch * d0 * d0 + (yStretch) * (d1 * d1) + d2 * d2;
    }

    private static Component percentValueLabel(Component p_231898_, double p_231899_)
    {
        return Component.translatable("options.percent_value", p_231898_, (int) (p_231899_ * 100.0D));
    }
}
