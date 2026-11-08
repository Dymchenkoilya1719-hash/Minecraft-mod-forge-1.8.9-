package ru.dymchenko.rainbowmod.block;

import java.util.List;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.PropertyEnum;
import net.minecraft.block.state.IBlockState;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.IStringSerializable;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

public class BlockRainbowBrick extends Block {
    public static final PropertyEnum<Color> COLOR = PropertyEnum.create("color", Color.class);
    public static final int[] RGB = {0xE53935, 0xFB8C00, 0xFDD835, 0x43A047, 0x26C6DA, 0x1E5BDB, 0x8E24AA};
    public enum Color implements IStringSerializable {
        RED("red"), ORANGE("orange"), YELLOW("yellow"), GREEN("green"), CYAN("cyan"), BLUE("blue"), PURPLE("purple");
        private final String serializedName;
        Color(String name) { serializedName = name; }
        @Override public String getName() { return serializedName; }
    }

    public BlockRainbowBrick() {
        super(Material.rock);
        setUnlocalizedName("rainbow_brick");
        setCreativeTab(CreativeTabs.tabBlock);
        setHardness(1.5F);
        setResistance(10.0F);
        setDefaultState(blockState.getBaseState().withProperty(COLOR, Color.RED));
    }
    @Override public IBlockState getStateFromMeta(int meta) {
        return getDefaultState().withProperty(COLOR, Color.values()[Math.max(0, Math.min(6, meta))]);
    }
    @Override public int getMetaFromState(IBlockState state) { return state.getValue(COLOR).ordinal(); }
    @Override protected net.minecraft.block.state.BlockState createBlockState() {
        return new net.minecraft.block.state.BlockState(this, COLOR);
    }
    @Override public void getSubBlocks(Item item, CreativeTabs tab, List<ItemStack> items) {
        for (int i = 0; i < Color.values().length; i++) items.add(new ItemStack(item, 1, i));
    }
    @Override public int colorMultiplier(IBlockAccess world, BlockPos pos, int tintIndex) {
        int color = world.getBlockState(pos).getValue(COLOR).ordinal();
        int rgb = RGB[color];
        for (EnumFacing side : EnumFacing.values()) {
            IBlockState neighbor = world.getBlockState(pos.offset(side));
            if (neighbor.getBlock() == this && neighbor.getValue(COLOR).ordinal() == color) {
                rgb = scale(rgb, 1.12F);
                break;
            }
        }
        if (world instanceof World && isSpectrumLine(world, pos)) {
            float pulse = 0.96F + 0.04F * (float)Math.sin(((World)world).getTotalWorldTime() * 0.18D);
            rgb = scale(rgb, pulse);
        }
        return rgb;
    }
    private boolean isSpectrumLine(IBlockAccess world, BlockPos pos) {
        for (EnumFacing.Axis axis : new EnumFacing.Axis[]{EnumFacing.Axis.X, EnumFacing.Axis.Z}) {
            for (int start = -6; start <= 0; start++) {
                boolean match = true;
                for (int i = 0; i < 7; i++) {
                    BlockPos check = axis == EnumFacing.Axis.X ? pos.add(start + i, 0, 0) : pos.add(0, 0, start + i);
                    IBlockState state = world.getBlockState(check);
                    if (state.getBlock() != this || state.getValue(COLOR).ordinal() != i) { match = false; break; }
                }
                if (match) return true;
            }
        }
        return false;
    }
    private static int scale(int rgb, float factor) {
        int red = Math.min(255, (int)(((rgb >> 16) & 255) * factor));
        int green = Math.min(255, (int)(((rgb >> 8) & 255) * factor));
        int blue = Math.min(255, (int)((rgb & 255) * factor));
        return (red << 16) | (green << 8) | blue;
    }
}
