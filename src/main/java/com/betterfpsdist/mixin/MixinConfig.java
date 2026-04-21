package com.betterfpsdist.mixin;

import com.betterfpsdist.BetterfpsdistMod;
import net.fabricmc.loader.api.FabricLoader;
import org.objectweb.asm.tree.ClassNode;
import org.spongepowered.asm.mixin.extensibility.IMixinConfigPlugin;
import org.spongepowered.asm.mixin.extensibility.IMixinInfo;

import java.util.List;
import java.util.Set;

public class MixinConfig implements IMixinConfigPlugin
{
    @Override
    public void onLoad(final String mixinPackage)
    {

    }

    @Override
    public String getRefMapperConfig()
    {
        return null;
    }

    @Override
    public boolean shouldApplyMixin(final String targetClassName, final String mixinClassName)
    {
        if (FabricLoader.getInstance().isModLoaded("magnesium") ||
              FabricLoader.getInstance().isModLoaded("sodium"))
        {
            if (mixinClassName.equals("com.betterfpsdist.mixin.LevelRendererMixin"))
            {
                return false;
            }

            if (mixinClassName.equals("com.betterfpsdist.mixin.VideoSettingsScreenSodiumMixin")
                || mixinClassName.equals("com.betterfpsdist.mixin.SodiumMixin"))
            {
                return true;
            }
        }

        if (mixinClassName.contains("EntityRenderDistMixin"))
        {
            return BetterfpsdistMod.config.getCommonConfig().affectEntities;
        }

        return true;
    }

    @Override
    public void acceptTargets(final Set<String> myTargets, final Set<String> otherTargets)
    {

    }

    @Override
    public List<String> getMixins()
    {
        return null;
    }

    @Override
    public void preApply(final String targetClassName, final ClassNode targetClass, final String mixinClassName, final IMixinInfo mixinInfo)
    {

    }

    @Override
    public void postApply(final String targetClassName, final ClassNode targetClass, final String mixinClassName, final IMixinInfo mixinInfo)
    {

    }
}
