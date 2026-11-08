package ru.dymchenko.rainbowmod.client;

import net.minecraft.client.renderer.color.IBlockColor;
import net.minecraft.client.renderer.color.IItemColor;
import net.minecraft.client.resources.model.ModelResourceLocation;
import net.minecraft.item.Item;
import net.minecraftforge.client.MinecraftForgeClient;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.fml.client.registry.RenderingRegistry;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraft.client.Minecraft;
import net.minecraftforge.client.event.TextureStitchEvent;
import net.minecraftforge.fml.common.eventhandler.EventBus;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraft.client.renderer.color.BlockColors;
import net.minecraft.client.renderer.color.ItemColors;
import net.minecraft.block.state.IBlockState;
import net.minecraft.world.IBlockAccess;
import net.minecraft.util.BlockPos;
import ru.dymchenko.rainbowmod.RainbowMod;
import ru.dymchenko.rainbowmod.block.BlockRainbowBrick;

public class RainbowClientProxy {
    public void registerRenderers() {
        for (int i = 0; i < 7; i++) {
            ModelLoader.setCustomModelResourceLocation(Item.getItemFromBlock(RainbowMod.RAINBOW_BRICK), i,
                    new ModelResourceLocation(RainbowMod.MODID + ":rainbow_brick_" + BlockRainbowBrick.Color.values()[i].getName(), "inventory"));
        }
        ModelLoader.setCustomModelResourceLocation(Item.getItemFromBlock(RainbowMod.PRISM_CRYSTAL), 0,
                new ModelResourceLocation(RainbowMod.MODID + ":prism_crystal", "inventory"));
        ModelLoader.setCustomModelResourceLocation(RainbowMod.RAINBOW_FLINT, 0,
                new ModelResourceLocation(RainbowMod.MODID + ":rainbow_flint", "inventory"));
        Minecraft.getMinecraft().getBlockColors().registerBlockColorHandler(new IBlockColor() {
            @Override public int colorMultiplier(IBlockState state, IBlockAccess world, BlockPos pos, int tintIndex) {
                return ((BlockRainbowBrick)state.getBlock()).colorMultiplier(world, pos, tintIndex);
            }
        }, RainbowMod.RAINBOW_BRICK);
        Minecraft.getMinecraft().getItemColors().registerItemColorHandler(new IItemColor() {
            @Override public int getColorFromItemstack(net.minecraft.item.ItemStack stack, int tintIndex) {
                return new int[]{0xE53935,0xFB8C00,0xFDD835,0x43A047,0x26C6DA,0x1E5BDB,0x8E24AA}[stack.getMetadata() % 7];
            }
        }, Item.getItemFromBlock(RainbowMod.RAINBOW_BRICK));
    }
}
