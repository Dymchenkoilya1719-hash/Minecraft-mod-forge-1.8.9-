package ru.dymchenko.rainbowmod.world;

import net.minecraft.init.Blocks;
import net.minecraft.util.BlockPos;
import net.minecraft.world.World;
import ru.dymchenko.rainbowmod.RainbowMod;

public final class RainbowVillageGenerator {
    private RainbowVillageGenerator() { }
    public static void generateIfNeeded(World world) {
        if (world.getBlockState(new BlockPos(0, 64, 0)).getBlock() == Blocks.glowstone) return;
        for (int x = -16; x <= 16; x++) for (int z = -16; z <= 16; z++) {
            for (int y = 63; y <= 73; y++) world.setBlockToAir(new BlockPos(x, y, z));
            world.setBlockState(new BlockPos(x, 63, z), Blocks.grass.getDefaultState());
        }
        for (int x = -16; x <= 16; x++) for (int z = -2; z <= 2; z++) world.setBlockState(new BlockPos(x, 63, z), Blocks.stonebrick.getDefaultState());
        for (int x = -2; x <= 2; x++) for (int z = -16; z <= 16; z++) world.setBlockState(new BlockPos(x, 63, z), Blocks.stonebrick.getDefaultState());
        house(world, -11, -11, 0);
        house(world, 5, -11, 1);
        house(world, -11, 5, 2);
        house(world, 5, 5, 3);
        world.setBlockState(new BlockPos(0, 64, 0), Blocks.glowstone.getDefaultState());
    }
    private static void house(World world, int x, int z, int color) {
        for (int dx = 0; dx < 6; dx++) for (int dz = 0; dz < 6; dz++) {
            world.setBlockState(new BlockPos(x + dx, 64, z + dz), Blocks.planks.getStateFromMeta(0));
            for (int dy = 1; dy <= 3; dy++) if (dx == 0 || dx == 5 || dz == 0 || dz == 5) {
                world.setBlockState(new BlockPos(x + dx, 64 + dy, z + dz), RainbowMod.RAINBOW_BRICK.getStateFromMeta(color));
            }
        }
        for (int dx = -1; dx <= 6; dx++) for (int dz = -1; dz <= 6; dz++) {
            world.setBlockState(new BlockPos(x + dx, 68, z + dz), Blocks.planks.getDefaultState());
        }
        world.setBlockToAir(new BlockPos(x + 2, 65, z));
        world.setBlockToAir(new BlockPos(x + 3, 65, z));
        world.setBlockState(new BlockPos(x + 1, 66, z), Blocks.glass.getDefaultState());
        world.setBlockState(new BlockPos(x + 4, 66, z), Blocks.glass.getDefaultState());
    }
}
