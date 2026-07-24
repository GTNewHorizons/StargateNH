package com.gtnewhorizons.stargatenh.common.block;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.ItemStack;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;

import com.gtnewhorizon.gtnhlib.blockpos.BlockPos;
import com.gtnewhorizons.stargatenh.ModBlocks;

public class BlockStargateController extends Block {

    private final ModBlocks.StargateBlocks blockGroup;

    public BlockStargateController(ModBlocks.StargateBlocks blockGroup) {
        super(Material.iron);
        this.blockGroup = blockGroup;
        this.setBlockName(blockGroup.id + "_stargate_controller");
    }

    @Override
    protected String getTextureName() {
        return "stargatenh:" + blockGroup.id + "/controller";
    }

    @Override
    public void onPostBlockPlaced(World worldIn, int x, int y, int z, int meta) {
        runStructureCheck(worldIn, x, y, z);
        super.onPostBlockPlaced(worldIn, x, y, z, meta);
    }

    @Override
    public void onBlockPlacedBy(World world, int x, int y, int z, EntityLivingBase placer, ItemStack stack) {
        int direction = MathHelper.floor_double((placer.rotationYaw / 90F) + 0.5D) & 3;
        world.setBlockMetadataWithNotify(x, y, z, direction, 2);
    }

    public void runStructureCheck(World world, int x, int y, int z) {
        int facing = world.getBlockMetadata(x, y, z);

        if (checkBlockAndMeta(world, x, y, z, -2, 0, blockGroup.stargateBlock, 1, facing)
            && checkBlockAndMeta(world, x, y, z, -1, 0, blockGroup.stargateBlock, 0, facing)
            && checkBlockAndMeta(world, x, y, z, 1, 0, blockGroup.stargateBlock, 0, facing)
            && checkBlockAndMeta(world, x, y, z, 2, 0, blockGroup.stargateBlock, 1, facing)

            && checkBlockAndMeta(world, x, y, z, -2, 1, blockGroup.stargateBlock, 0, facing)
            && checkBlockAndMeta(world, x, y, z, 2, 1, blockGroup.stargateBlock, 0, facing)
            && checkBlockAndMeta(world, x, y, z, -2, 2, blockGroup.stargateBlock, 1, facing)
            && checkBlockAndMeta(world, x, y, z, 2, 2, blockGroup.stargateBlock, 1, facing)
            && checkBlockAndMeta(world, x, y, z, -2, 3, blockGroup.stargateBlock, 0, facing)
            && checkBlockAndMeta(world, x, y, z, 2, 3, blockGroup.stargateBlock, 0, facing)

            && checkBlockAndMeta(world, x, y, z, -2, 4, blockGroup.stargateBlock, 1, facing)
            && checkBlockAndMeta(world, x, y, z, -1, 4, blockGroup.stargateBlock, 0, facing)
            && checkBlockAndMeta(world, x, y, z, 0, 4, blockGroup.stargateBlock, 1, facing)
            && checkBlockAndMeta(world, x, y, z, 1, 4, blockGroup.stargateBlock, 0, facing)
            && checkBlockAndMeta(world, x, y, z, 2, 4, blockGroup.stargateBlock, 1, facing)) {
            formGate(world, x, y, z, facing);
        }
    }

    private void formGate(World world, int x, int y, int z, int facing) {
        setRel(world, x, y, z, facing, -2, 0, blockGroup.formedGateBlock, 1);
        setRel(world, x, y, z, facing, -1, 0, blockGroup.formedGateBlock, 0);
        setRel(world, x, y, z, facing, 1, 0, blockGroup.formedGateBlock, 0);
        setRel(world, x, y, z, facing, 2, 0, blockGroup.formedGateBlock, 1);

        setRel(world, x, y, z, facing, -2, 1, blockGroup.formedGateBlock, 0);
        setRel(world, x, y, z, facing, 2, 1, blockGroup.formedGateBlock, 0);
        setRel(world, x, y, z, facing, -2, 2, blockGroup.formedGateBlock, 1);
        setRel(world, x, y, z, facing, 2, 2, blockGroup.formedGateBlock, 1);
        setRel(world, x, y, z, facing, -2, 3, blockGroup.formedGateBlock, 0);
        setRel(world, x, y, z, facing, 2, 3, blockGroup.formedGateBlock, 0);

        setRel(world, x, y, z, facing, -2, 4, blockGroup.formedGateBlock, 1);
        setRel(world, x, y, z, facing, -1, 4, blockGroup.formedGateBlock, 0);
        setRel(world, x, y, z, facing, 0, 4, blockGroup.formedGateBlock, 1);
        setRel(world, x, y, z, facing, 1, 4, blockGroup.formedGateBlock, 0);
        setRel(world, x, y, z, facing, 2, 4, blockGroup.formedGateBlock, 1);

        world.setBlock(x, y, z, blockGroup.formedGateBlock, facing + 2, 3);
    }

    private void deform(World world, int x, int y, int z, int facing) {
        setRel(world, x, y, z, facing, -2, 0, blockGroup.formedGateBlock, 1);
        setRel(world, x, y, z, facing, -1, 0, blockGroup.formedGateBlock, 0);
        setRel(world, x, y, z, facing, 1, 0, blockGroup.formedGateBlock, 0);
        setRel(world, x, y, z, facing, 2, 0, blockGroup.formedGateBlock, 1);

        setRel(world, x, y, z, facing, -2, 1, blockGroup.formedGateBlock, 0);
        setRel(world, x, y, z, facing, 2, 1, blockGroup.formedGateBlock, 0);
        setRel(world, x, y, z, facing, -2, 2, blockGroup.formedGateBlock, 1);
        setRel(world, x, y, z, facing, 2, 2, blockGroup.formedGateBlock, 1);
        setRel(world, x, y, z, facing, -2, 3, blockGroup.formedGateBlock, 0);
        setRel(world, x, y, z, facing, 2, 3, blockGroup.formedGateBlock, 0);

        setRel(world, x, y, z, facing, -2, 4, blockGroup.formedGateBlock, 1);
        setRel(world, x, y, z, facing, -1, 4, blockGroup.formedGateBlock, 0);
        setRel(world, x, y, z, facing, 0, 4, blockGroup.formedGateBlock, 1);
        setRel(world, x, y, z, facing, 1, 4, blockGroup.formedGateBlock, 0);
        setRel(world, x, y, z, facing, 2, 4, blockGroup.formedGateBlock, 1);
    }

    private void setRel(World world, int x, int y, int z, int facing, int offsetX, int offsetY, Block newBlock,
        int newMeta) {
        BlockPos p = rotate(offsetX, offsetY, facing);
        world.setBlock(x + p.x, y + p.y, z + p.z, newBlock, newMeta, 3);
    }

    private boolean checkBlockAndMeta(World world, int x, int y, int z, int offsetX, int offsetY, Block rBlock,
        int rMeta, int facing) {
        BlockPos p = rotate(offsetX, offsetY, facing);
        Block block = world.getBlock(x + p.x, y + p.y, z + p.z);
        int meta = world.getBlockMetadata(x + p.x, y + p.y, z + p.z);

        return rBlock == block && meta == rMeta;
    }

    private static BlockPos rotate(int dx, int dy, int facing) {
        return switch (facing) {
            case 0 -> // NORTH (-Z)
                new BlockPos(dx, dy, 0);
            case 1 -> // EAST (+X)
                new BlockPos(0, dy, -dx);
            case 2 -> // SOUTH (+Z)
                new BlockPos(-dx, dy, -0);
            case 3 -> // WEST (-X)
                new BlockPos(-0, dy, dx);
            default -> new BlockPos(dx, dy, 0);
        };
    }
}
