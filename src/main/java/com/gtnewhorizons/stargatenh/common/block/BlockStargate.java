package com.gtnewhorizons.stargatenh.common.block;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IIcon;
import net.minecraft.world.World;
import net.minecraftforge.common.util.ForgeDirection;

import com.gtnewhorizon.gtnhlib.blockpos.BlockPos;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class BlockStargate extends Block {

    private final String id;

    public BlockStargate(String id) {
        super(Material.iron);
        this.id = id;
        this.setBlockName(id + "_stargate_block");
    }

    public int damageDropped(int meta) {
        return meta;
    }

    @SideOnly(Side.CLIENT)
    public void getSubBlocks(Item itemIn, CreativeTabs tab, List<ItemStack> list) {
        for (int i = 0; i < 2; ++i) {
            list.add(new ItemStack(itemIn, 1, i));
        }
    }

    private IIcon ringTextureSide;
    private IIcon ringTextureTop;
    private IIcon chevronTexture;

    @Override
    @SideOnly(Side.CLIENT)
    public void registerBlockIcons(IIconRegister iconRegister) {
        ringTextureSide = iconRegister.registerIcon("stargatenh:" + id + "_stargate_side");
        ringTextureTop = iconRegister.registerIcon("stargatenh:" + id + "_stargate_top");
        chevronTexture = iconRegister.registerIcon("stargatenh:" + id + "_stargate_chevron");
    }

    @Override
    @SideOnly(Side.CLIENT)
    public IIcon getIcon(int side, int meta) {
        if (side == ForgeDirection.DOWN.ordinal() || side == ForgeDirection.UP.ordinal()) {
            return ringTextureTop;
        }

        return (meta == 1) ? chevronTexture : ringTextureSide;
    }

    @Override
    public void onPostBlockPlaced(World worldIn, int x, int y, int z, int meta) {
        Set<BlockPos> checked = new HashSet<>();
        checked.add(new BlockPos(x, y, z));
        findController(worldIn, x, y, z, checked);
        super.onPostBlockPlaced(worldIn, x, y, z, meta);
    }

    public void findController(World world, int x, int y, int z, Set<BlockPos> checked) {
        // Hard limit so nothing horrible happens
        if (checked.size() > 20) return;
        int meta = world.getBlockMetadata(x, y, z);
        if (meta != 2) {
            for (ForgeDirection dir : ForgeDirection.VALID_DIRECTIONS) {
                int nx = x + dir.offsetX;
                int ny = y + dir.offsetY;
                int nz = z + dir.offsetZ;

                Block neighbor = world.getBlock(nx, ny, nz);

                if (neighbor instanceof BlockStargateController controller)
                    controller.runStructureCheck(world, nx, ny, nz);
                else if (neighbor == this && checked.add(new BlockPos(nx, ny, nz))) {
                    findController(world, nx, ny, nz, checked);
                }
            }
        }
    }

    public static class ItemBlockStargate extends ItemBlock {

        public ItemBlockStargate(Block block) {
            super(block);
            this.setHasSubtypes(true);
            this.setMaxDamage(0);
        }

        @Override
        public int getMetadata(int damage) {
            return damage;
        }

        @Override
        public String getUnlocalizedName(final ItemStack stack) {
            return this.getUnlocalizedName() + "." + stack.getItemDamage();
        }
    }
}
