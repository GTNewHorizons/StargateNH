package com.gtnewhorizons.stargatenh.common.tileentity;

import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.AxisAlignedBB;

public class TileStargateController extends TileEntity {

    public int facing = -1;
    private final int[] address = { -1, -1, -1, -1, -1, -1, -1 };
    private int[] dialed = null;

    public int setFacing() {
        facing = worldObj.getBlockMetadata(xCoord, yCoord, zCoord);
        return facing;
    }

    @Override
    public AxisAlignedBB getRenderBoundingBox() {
        return AxisAlignedBB.getBoundingBox(xCoord - 3, yCoord, zCoord - 3, xCoord + 2, yCoord + 4, zCoord + 2);
    }

    public float ringRotation;
    public float prevRingRotation;

    @Override
    public void updateEntity() {
        prevRingRotation = ringRotation;

        ringRotation += 1.0F;
        ringRotation %= 360F;
    }

    public int[] getDialed() {
        return dialed;
    }
}
