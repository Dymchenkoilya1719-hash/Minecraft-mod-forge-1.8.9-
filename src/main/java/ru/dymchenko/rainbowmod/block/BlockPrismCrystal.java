package ru.dymchenko.rainbowmod.block;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.util.BlockPos;
import net.minecraft.world.World;
import ru.dymchenko.rainbowmod.RainbowMod;
public class BlockPrismCrystal extends Block {
    public BlockPrismCrystal() { super(Material.glass); setUnlocalizedName("prism_crystal"); setCreativeTab(CreativeTabs.tabBlock); setHardness(0.5F); setLightLevel(0.8F); }
    @Override public boolean isOpaqueCube() { return false; }
    @Override public boolean isFullCube() { return false; }
    @Override public int getRenderBlockPass() { return 1; }
    public boolean hasRainbowFrame(World world, BlockPos p) {
        for(int dx=-1;dx<=1;dx++) for(int dy=-1;dy<=2;dy++) {
            if(Math.abs(dx)!=1 && dy!=-1 && dy!=2) continue;
            IBlockState state=world.getBlockState(p.add(dx,dy,0));
            if(state.getBlock()!=RainbowMod.RAINBOW_BRICK) return false;
        }
        return true;
    }
}
