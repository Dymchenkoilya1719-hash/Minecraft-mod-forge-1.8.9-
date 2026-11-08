package ru.dymchenko.rainbowmod.client;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.color.IBlockColor;
import net.minecraft.client.renderer.color.IItemColor;
import net.minecraft.client.resources.model.ModelResourceLocation;
import net.minecraft.item.Item;
import net.minecraftforge.client.model.ModelLoader;
import ru.dymchenko.rainbowmod.CommonProxy;
import ru.dymchenko.rainbowmod.RainbowMod;
import ru.dymchenko.rainbowmod.block.BlockRainbowBrick;

public class ClientProxy extends CommonProxy {
    @Override public void registerRenderers() {
        Item bricks = Item.getItemFromBlock(RainbowMod.RAINBOW_BRICK);
        for (int i = 0; i < BlockRainbowBrick.Color.values().length; i++) {
            String color = BlockRainbowBrick.Color.values()[i].getName();
            ModelLoader.setCustomModelResourceLocation(bricks, i,
                    new ModelResourceLocation(RainbowMod.MODID + ":rainbow_brick_" + color, "inventory"));
        }
        ModelLoader.setCustomModelResourceLocation(Item.getItemFromBlock(RainbowMod.PRISM_CRYSTAL), 0,
                new ModelResourceLocation(RainbowMod.MODID + ":prism_crystal", "inventory"));
        ModelLoader.setCustomModelResourceLocation(RainbowMod.RAINBOW_FLINT, 0,
                new ModelResourceLocation(RainbowMod.MODID + ":rainbow_flint", "inventory"));
        Minecraft.getMinecraft().getBlockColors().registerBlockColorHandler(new IBlockColor() {
            @Override public int colorMultiplier(net.minecraft.block.state.IBlockState state,
                                                 net.minecraft.world.IBlockAccess world,
                                                 net.minecraft.util.BlockPos pos, int tintIndex) {
                return ((BlockRainbowBrick)state.getBlock()).colorMultiplier(world, pos, tintIndex);
            }
        }, RainbowMod.RAINBOW_BRICK);
        Minecraft.getMinecraft().getItemColors().registerItemColorHandler(new IItemColor() {
            @Override public int getColorFromItemstack(net.minecraft.item.ItemStack stack, int tintIndex) {
                return BlockRainbowBrick.RGB[Math.max(0, Math.min(6, stack.getMetadata()))];
            }
        }, bricks);
    }
}
