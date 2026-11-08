package ru.dymchenko.rainbowmod.tile;

import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.Packet;
import net.minecraft.network.play.server.S35PacketUpdateTileEntity;
import net.minecraft.server.MinecraftServer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.WorldServer;
import ru.dymchenko.rainbowmod.RainbowMod;
import ru.dymchenko.rainbowmod.world.RainbowTeleporter;
import ru.dymchenko.rainbowmod.world.RainbowVillageGenerator;

public class TileEntityPrismCrystal extends TileEntity {
    private static final int ACTIVATION_TICKS = 50;
    private int activationTicks;
    private String activatingPlayer = "";
    private boolean active;

    public boolean beginActivation(EntityPlayerMP player) {
        if (active || activationTicks > 0) return false;
        activatingPlayer = player.getName();
        activationTicks = ACTIVATION_TICKS;
        markDirty();
        worldObj.markBlockForUpdate(pos);
        return true;
    }
    public boolean isAnimating() { return activationTicks > 0; }

    @Override public void update() {
        if (worldObj == null || worldObj.isRemote || activationTicks <= 0) return;
        activationTicks--;
        if (activationTicks == 0) {
            active = true;
            markDirty();
            worldObj.markBlockForUpdate(pos);
            EntityPlayerMP player = MinecraftServer.getServer().getConfigurationManager().getPlayerByUsername(activatingPlayer);
            if (player != null) transfer(player);
        }
    }
    public void use(EntityPlayerMP player) { if (active) transfer(player); }

    private void transfer(EntityPlayerMP player) {
        MinecraftServer server = MinecraftServer.getServer();
        int destinationId = player.dimension == RainbowMod.DIMENSION_ID ? 0 : RainbowMod.DIMENSION_ID;
        WorldServer destination = server.worldServerForDimension(destinationId);
        if (destination == null) return;
        if (destinationId == RainbowMod.DIMENSION_ID) RainbowVillageGenerator.generateIfNeeded(destination);
        server.getConfigurationManager().transferPlayerToDimension(player, destinationId, new RainbowTeleporter(destination));
    }

    @Override public void readFromNBT(NBTTagCompound tag) {
        super.readFromNBT(tag);
        activationTicks = tag.getInteger("ActivationTicks");
        activatingPlayer = tag.getString("ActivatingPlayer");
        active = tag.getBoolean("Active");
    }
    @Override public void writeToNBT(NBTTagCompound tag) {
        super.writeToNBT(tag);
        tag.setInteger("ActivationTicks", activationTicks);
        tag.setString("ActivatingPlayer", activatingPlayer == null ? "" : activatingPlayer);
        tag.setBoolean("Active", active);
    }
    @Override public Packet getDescriptionPacket() {
        NBTTagCompound tag = new NBTTagCompound();
        writeToNBT(tag);
        return new S35PacketUpdateTileEntity(pos, 1, tag);
    }
    @Override public void onDataPacket(NetworkManager manager, S35PacketUpdateTileEntity packet) {
        readFromNBT(packet.getNbtCompound());
    }
}
