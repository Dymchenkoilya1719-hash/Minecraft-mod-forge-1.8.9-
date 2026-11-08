package ru.dymchenko.rainbowmod.world;
import net.minecraft.world.WorldProviderSurface;
public class WorldProviderRainbow extends WorldProviderSurface {
    @Override public String getDimensionName() { return "Rainbow Village"; }
    @Override public String getInternalNameSuffix() { return "_rainbow"; }
}
