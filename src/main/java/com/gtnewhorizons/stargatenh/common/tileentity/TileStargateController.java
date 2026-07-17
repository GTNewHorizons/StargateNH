package com.gtnewhorizons.stargatenh.common.tileentity;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.AxisAlignedBB;

import com.gtnewhorizon.gtnhlib.blockpos.BlockPos;
import com.gtnewhorizons.stargatenh.common.util.StargateAddress;
import com.gtnewhorizons.stargatenh.common.util.StargateRegistry;

public class TileStargateController extends TileEntity {

    public int facing = -1;
    public boolean hasAddress = false;
    private StargateAddress address = null;
    // Position of block this gate is dialed TO
    private BlockPos dialing = null;
    // Whether or not this gate is being dialed TO
    private boolean dialed = false;

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

    public boolean isTransmitting() {
        return dialing != null;
    }

    public boolean dialOut(int[] sigils) {
        if (dialed) return false;

        StargateAddress sga = new StargateAddress(sigils);
        if (sga.equals(address)) return false;

        StargateRegistry reg = StargateRegistry.get(worldObj);
        BlockPos dialedGate = reg.lookup(sga);
        if (dialedGate == null) return false;

        TileStargateController gate = ((TileStargateController) worldObj
            .getTileEntity(dialedGate.x, dialedGate.y, dialedGate.z));
        if (!gate.getDialed(address)) return false;

        dialing = dialedGate;
        return true;
    }

    public boolean getDialed(StargateAddress incoming) {
        dialed = true;
        return true;
    }

    public void doTeleport(Entity entity) {
        if (entity instanceof EntityLivingBase living) {
            living.setPositionAndUpdate(dialing.x, dialing.y + 1.5, dialing.z);
        } else {
            entity.setPosition(dialing.x, dialing.y + 1.5, dialing.z);
        }
    }

    public void setAddress(int[] sigils) {
        if (hasAddress) return;

        StargateRegistry reg = StargateRegistry.get(worldObj);
        StargateAddress sga = new StargateAddress(sigils);

        if (reg.lookup(sga) == null) {
            reg.register(sga, new BlockPos(xCoord, yCoord, zCoord));
            hasAddress = true;
            address = sga;
        }
    }
}
