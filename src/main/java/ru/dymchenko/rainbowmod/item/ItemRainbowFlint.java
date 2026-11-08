package ru.dymchenko.rainbowmod.item;

import net.minecraft.block.Block;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.BlockPos;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.EnumFacing;
import net.minecraft.world.World;
import net.minecraft.world.WorldServer;
import net.minecraft.server.MinecraftServer;
import ru.dymchenko.rainbowmod.RainbowMod;
import ru.dymchenko.rainbowmod.block.BlockPrismCrystal;
import ru.dymchenko.rainbowmod.tile.TileEntityPrismCrystal;
import ru.dymchenko.rainbowmod.world.RainbowTeleporter;
import ru.dymchenko.rainbowmod.world.RainbowVillageGenerator;

public class ItemRainbowFlint extends Item {
    public ItemRainbowFlint() {
        setUnlocalizedName("rainbow_flint");
        setCreativeTab(net.minecraft.creativetab.CreativeTabs.tabTools);
        setMaxStackSize(1);
        setMaxDamage(64);
    }
    @Override public boolean onItemUse(ItemStack stack, EntityPlayer player, World world, BlockPos pos,
                                       EnumFacing side, float hitX, float hitY, float hitZ) {
        if (world.isRemote) return true;
        if (!(player instanceof EntityPlayerMP)) return true;
        EntityPlayerMP serverPlayer = (EntityPlayerMP)player;
        if (player.dimension == RainbowMod.DIMENSION_ID) {
            WorldServer overworld = MinecraftServer.getServer().worldServerForDimension(0);
            if (overworld != null) {
                MinecraftServer.getServer().getConfigurationManager().transferPlayerToDimension(serverPlayer, 0, new RainbowTeleporter(overworld));
                stack.damageItem(1, player);
            }
            return true;
        }
        Block block = world.getBlockState(pos).getBlock();
        if (!(block instanceof BlockPrismCrystal)) return false;
        BlockPrismCrystal crystal = (BlockPrismCrystal)block;
        if (!crystal.hasRainbowFrame(world, pos)) {
            player.addChatMessage(new ChatComponentText("Для активации нужна арка 3×4 из радужных кирпичей."));
            return true;
        }
        TileEntity tile = world.getTileEntity(pos);
        if (!(tile instanceof TileEntityPrismCrystal)) return true;
        TileEntityPrismCrystal prism = (TileEntityPrismCrystal)tile;
        if (prism.isAnimating()) return true;
        if (prism.beginActivation(serverPlayer)) {
            stack.damageItem(1, player);
            player.addChatMessage(new ChatComponentText("Кристалл призмы заряжается..."));
        } else {
            prism.use(serverPlayer);
        }
        return true;
    }
}
