package ru.dymchenko.rainbowmod.item;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;
import net.minecraft.world.World;
import net.minecraft.world.WorldServer;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.StatCollector;
import net.minecraftforge.common.DimensionManager;
import ru.dymchenko.rainbowmod.RainbowMod;
import ru.dymchenko.rainbowmod.block.BlockPrismCrystal;
import ru.dymchenko.rainbowmod.world.RainbowTeleporter;
import ru.dymchenko.rainbowmod.world.RainbowVillageGenerator;

public class ItemRainbowFlint extends Item {
    public ItemRainbowFlint() {
        setUnlocalizedName("rainbow_flint");
        setTextureName(RainbowMod.MODID + ":rainbow_flint");
        setCreativeTab(net.minecraft.creativetab.CreativeTabs.tabTools);
        setMaxStackSize(1);
        setMaxDamage(64);
    }
    @Override public boolean onItemUse(ItemStack stack, EntityPlayer player, World world, BlockPos pos, EnumFacing side,
                                        float hitX, float hitY, float hitZ) {
        if (!(world.getBlockState(pos).getBlock() instanceof BlockPrismCrystal)) return false;
        BlockPrismCrystal crystal = (BlockPrismCrystal)world.getBlockState(pos).getBlock();
        if (!crystal.hasRainbowFrame(world, pos)) {
            if (!world.isRemote) player.addChatMessage(new ChatComponentText("Рамка должна быть 3×4 из радужных кирпичей."));
            return true;
        }
        if (world.isRemote) return true;
        if (!(player instanceof EntityPlayerMP)) return true;
        EntityPlayerMP mp = (EntityPlayerMP)player;
        MinecraftServer server = MinecraftServer.getServer();
        WorldServer destination = server.worldServerForDimension(RainbowMod.RAINBOW_DIMENSION_ID);
        if (destination == null) return true;
        RainbowVillageGenerator.generate(destination);
        server.getConfigurationManager().transferPlayerToDimension(mp, RainbowMod.RAINBOW_DIMENSION_ID, new RainbowTeleporter(destination));
        stack.damageItem(1, player);
        return true;
    }
}
