package ru.dymchenko.rainbowmod.world;

import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import net.minecraft.util.BlockPos;
import net.minecraft.world.World;
import ru.dymchenko.rainbowmod.RainbowMod;

/** Builds a small, repeatable settlement in the dedicated rainbow dimension. */
public final class RainbowVillageGenerator {
    private RainbowVillageGenerator() { }

    public static void generate(World world) {
        BlockPos origin = new BlockPos(-16, 63, -16);
        // Clear only the mod's small reserved settlement footprint in its own dimension.
        for (int x = -16; x <= 16; x++) for (int z = -16; z <= 16; z++) {
            for (int y = 63; y <= 73; y++) world.setBlockToAir(new BlockPos(x, y, z));
            world.setBlockState(new BlockPos(x, 63, z), Blocks.grass.getDefaultState());
        }
        for (int x = -16; x <= 16; x++) for (int z = -2; z <= 2; z++)
            world.setBlockState(new BlockPos(x, 63, z), Blocks.stonebrick.getDefaultState());
        for (int x = -2; x <= 2; x++) for (int z = -16; z <= 16; z++)
            world.setBlockState(new BlockPos(x, 63, z), Blocks.stonebrick.getDefaultState());

        buildHouse(world, -11, -11, 0);
        buildHouse(world, 5, -11, 1);
        buildHouse(world, -11, 5, 2);
        buildHouse(world, 5, 5, 3);
        world.setBlockState(new BlockPos(0, 64, 0), Blocks.glowstone.getDefaultState());
        world.setBlockState(new BlockPos(0, 65, 0), Blocks.glass.getDefaultState());
    }

    private static void buildHouse(World world, int x, int z, int color) {
        Block rainbow = RainbowMod.RAINBOW_BRICK;
        for (int dx = 0; dx < 6; dx++) for (int dz = 0; dz < 6; dz++) {
            world.setBlockState(new BlockPos(x + dx, 64, z + dz), Blocks.planks.getStateFromMeta(0));
            for (int dy = 1; dy <= 3; dy++) {
                boolean wall = dx == 0 || dx == 5 || dz == 0 || dz == 5;
                if (wall) world.setBlockState(new BlockPos(x + dx, 64 + dy, z + dz), rainbow.getStateFromMeta(color));
            }
        }
        for (int dx = -1; dx <= 6; dx++) for (int dz = -1; dz <= 6; dz++)
            world.setBlockState(new BlockPos(x + dx, 68, z + dz), Blocks.planks.getStateFromMeta(0));
        world.setBlockToAir(new BlockPos(x + 2, 65, z));
        world.setBlockToAir(new BlockPos(x + 3, 65, z));
        world.setBlockState(new BlockPos(x + 1, 66, z), Blocks.glass.getDefaultState());
        world.setBlockState(new BlockPos(x + 4, 66, z), Blocks.glass.getDefaultState());
    }
}
