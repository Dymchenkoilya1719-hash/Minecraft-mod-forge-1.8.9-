package ru.dymchenko.rainbowmod.world;
import net.minecraft.entity.Entity;
import net.minecraft.world.Teleporter;
import net.minecraft.world.WorldServer;
public class RainbowTeleporter extends Teleporter {
    public RainbowTeleporter(WorldServer world) { super(world); }
    @Override public void placeInPortal(Entity entity,double x,double y,double z,float yaw) { entity.setLocationAndAngles(0.5D,66.0D,0.5D,yaw,entity.rotationPitch); entity.motionX=entity.motionY=entity.motionZ=0.0D; }
    @Override public boolean makePortal(Entity entity) { return true; }
}
