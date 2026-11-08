package ru.dymchenko.rainbowmod.item;
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
import ru.dymchenko.rainbowmod.world.RainbowTeleporter;
import ru.dymchenko.rainbowmod.world.RainbowVillageGenerator;
public class ItemRainbowFlint extends Item {
    public ItemRainbowFlint() { setUnlocalizedName("rainbow_flint"); setTextureName(RainbowMod.MODID+":rainbow_flint"); setCreativeTab(net.minecraft.creativetab.CreativeTabs.tabTools); setMaxStackSize(1); setMaxDamage(64); }
    @Override public boolean onItemUse(ItemStack stack, EntityPlayer player, World world, BlockPos pos, EnumFacing side,float hitX,float hitY,float hitZ) {
        if (!(world.getBlockState(pos).getBlock() instanceof BlockPrismCrystal)) return false;
        if (!world.isRemote) {
            BlockPrismCrystal crystal=(BlockPrismCrystal)world.getBlockState(pos).getBlock();
            if (!crystal.hasRainbowFrame(world,pos)) { player.addChatMessage(new ChatComponentText("Рамка должна быть 3×4 из радужных кирпичей.")); return true; }
            for(int i=0;i<24;i++) { double a=i*Math.PI/12; world.spawnParticle(net.minecraft.util.EnumParticleTypes.REDSTONE,pos.getX()+0.5+Math.cos(a),pos.getY()+0.5+Math.sin(a),pos.getZ()+0.5,Math.random(),Math.random(),Math.random()); }
            if(player instanceof EntityPlayerMP) {
                WorldServer destination=MinecraftServer.getServer().worldServerForDimension(RainbowMod.DIMENSION_ID);
                if(destination!=null) { RainbowVillageGenerator.generate(destination); MinecraftServer.getServer().getConfigurationManager().transferPlayerToDimension((EntityPlayerMP)player,RainbowMod.DIMENSION_ID,new RainbowTeleporter(destination)); stack.damageItem(1,player); }
            }
        }
        return true;
    }
}
