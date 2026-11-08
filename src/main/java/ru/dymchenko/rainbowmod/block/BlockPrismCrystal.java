package ru.dymchenko.rainbowmod.block;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.BlockPos;
import net.minecraft.world.World;
import ru.dymchenko.rainbowmod.RainbowMod;
import ru.dymchenko.rainbowmod.tile.TileEntityPrismCrystal;

public class BlockPrismCrystal extends Block {
    public BlockPrismCrystal() {
        super(Material.glass);
        setUnlocalizedName("prism_crystal");
        setCreativeTab(net.minecraft.creativetab.CreativeTabs.tabBlock);
        setHardness(0.5F);
        setLightLevel(0.8F);
        setLightOpacity(0);
    }
    @Override public boolean isOpaqueCube() { return false; }
    @Override public boolean isFullCube() { return false; }
    @Override public boolean hasTileEntity(IBlockState state) { return true; }
    @Override public TileEntity createTileEntity(World world, IBlockState state) { return new TileEntityPrismCrystal(); }
    @Override public int getRenderBlockPass() { return 1; }

    public boolean hasRainbowFrame(World world, BlockPos crystal) {
        return isFrame(world, crystal, true) || isFrame(world, crystal, false);
    }
    private boolean isFrame(World world, BlockPos crystal, boolean alongX) {
        for (int width = -1; width <= 1; width++) {
            for (int height = -1; height <= 2; height++) {
                if (Math.abs(width) != 1 && height != -1 && height != 2) continue;
                BlockPos frame = alongX ? crystal.add(width, height, 0) : crystal.add(0, height, width);
                if (world.getBlockState(frame).getBlock() != RainbowMod.RAINBOW_BRICK) return false;
            }
        }
        return world.isAirBlock(crystal.up());
    }
    @net.minecraftforge.fml.relauncher.SideOnly(net.minecraftforge.fml.relauncher.Side.CLIENT)
    @Override public void randomDisplayTick(World world, BlockPos pos, IBlockState state, java.util.Random random) {
        TileEntity tile = world.getTileEntity(pos);
        if (!(tile instanceof TileEntityPrismCrystal) || !((TileEntityPrismCrystal)tile).isAnimating()) return;
        double angle = random.nextDouble() * Math.PI * 2.0D;
        world.spawnParticle(net.minecraft.util.EnumParticleTypes.REDSTONE,
                pos.getX() + 0.5D + Math.cos(angle) * 0.65D,
                pos.getY() + 0.5D + (random.nextDouble() - 0.5D) * 1.4D,
                pos.getZ() + 0.5D + Math.sin(angle) * 0.65D,
                random.nextDouble(), random.nextDouble(), random.nextDouble());
    }
}
