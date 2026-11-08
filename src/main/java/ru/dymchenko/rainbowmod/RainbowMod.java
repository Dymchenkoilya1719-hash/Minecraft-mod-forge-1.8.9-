package ru.dymchenko.rainbowmod;

import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraftforge.common.DimensionManager;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.oredict.OreDictionary;
import ru.dymchenko.rainbowmod.block.BlockPrismCrystal;
import ru.dymchenko.rainbowmod.block.BlockRainbowBrick;
import ru.dymchenko.rainbowmod.item.ItemRainbowFlint;
import ru.dymchenko.rainbowmod.tile.TileEntityPrismCrystal;
import ru.dymchenko.rainbowmod.world.WorldProviderRainbow;

@Mod(modid = RainbowMod.MODID, name = RainbowMod.NAME, version = RainbowMod.VERSION)
public class RainbowMod {
    public static final String MODID = "rainbowmod";
    public static final String NAME = "Rainbow Village";
    public static final String VERSION = "1.0.1";
    public static final int DIMENSION_ID = 73;
    public static Block RAINBOW_BRICK;
    public static Block PRISM_CRYSTAL;
    public static ItemRainbowFlint RAINBOW_FLINT;

    @SidedProxy(clientSide = "ru.dymchenko.rainbowmod.client.ClientProxy", serverSide = "ru.dymchenko.rainbowmod.CommonProxy")
    public static CommonProxy proxy;

    @EventHandler
    public void preInit(FMLPreInitializationEvent event) {
        RAINBOW_BRICK = new BlockRainbowBrick();
        PRISM_CRYSTAL = new BlockPrismCrystal();
        RAINBOW_FLINT = new ItemRainbowFlint();
        GameRegistry.registerBlock(RAINBOW_BRICK, ru.dymchenko.rainbowmod.item.ItemBlockRainbowBrick.class, "rainbow_brick");
        GameRegistry.registerBlock(PRISM_CRYSTAL, "prism_crystal");
        GameRegistry.registerItem(RAINBOW_FLINT, "rainbow_flint");
        GameRegistry.registerTileEntity(TileEntityPrismCrystal.class, MODID + ".prism_crystal");
        DimensionManager.registerProviderType(DIMENSION_ID, WorldProviderRainbow.class, false);
        DimensionManager.registerDimension(DIMENSION_ID, DIMENSION_ID);
    }

    @EventHandler
    public void init(FMLInitializationEvent event) {
        int[] dyeDamage = {1, 14, 11, 2, 12, 4, 5};
        for (int color = 0; color < dyeDamage.length; color++) {
            GameRegistry.addShapelessRecipe(new ItemStack(RAINBOW_BRICK, 4, color),
                    new ItemStack(Blocks.stonebrick, 4, 0), new ItemStack(Items.dye, 1, dyeDamage[color]));
        }
        GameRegistry.addShapelessRecipe(new ItemStack(PRISM_CRYSTAL), new ItemStack(Blocks.glass),
                new ItemStack(Blocks.glowstone), new ItemStack(Items.dye, 1, OreDictionary.WILDCARD_VALUE));
        GameRegistry.addShapelessRecipe(new ItemStack(RAINBOW_FLINT), new ItemStack(Items.iron_ingot),
                new ItemStack(Items.blaze_rod), new ItemStack(Items.dye, 1, 5));
        proxy.registerRenderers();
    }
}
