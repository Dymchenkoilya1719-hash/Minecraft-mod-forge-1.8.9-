package ru.dymchenko.rainbowmod.item;

import net.minecraft.block.Block;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;

public class ItemBlockRainbowBrick extends ItemBlock {
    public ItemBlockRainbowBrick(Block block) { super(block); setHasSubtypes(true); }
    @Override public int getMetadata(int damage) { return damage; }
    @Override public String getUnlocalizedName(ItemStack stack) { return super.getUnlocalizedName() + "." + stack.getMetadata(); }
}
