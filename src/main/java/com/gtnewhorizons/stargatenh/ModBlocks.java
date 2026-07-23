package com.gtnewhorizons.stargatenh;

import net.minecraft.block.Block;

import com.gtnewhorizons.stargatenh.common.block.BlockDialingDevice;
import com.gtnewhorizons.stargatenh.common.block.BlockFormedGate;
import com.gtnewhorizons.stargatenh.common.block.BlockStargate;
import com.gtnewhorizons.stargatenh.common.block.BlockStargateController;

import cpw.mods.fml.common.registry.GameRegistry;

public class ModBlocks {

    public static final Block stargateBlock = new BlockStargate("default");
    public static final Block stargateControllerBlock = new BlockStargateController()
        .setBlockName("stargate_controller");

    public static final Block legacyStargateBlockAncient = new BlockStargate("ancient");
    public static final Block legacyStargateBlockEoh = new BlockStargate("eoh");
    public static final Block legacyStargateBlockInfinity = new BlockStargate("infinity");
    public static final Block legacyStargateBlockSpacetime = new BlockStargate("spacetime");

    public static final Block formedGateFakeBlock = new BlockFormedGate().setBlockName("formed_stargate");
    public static final Block dialingDeviceBlock = new BlockDialingDevice().setBlockName("dialing_device");

    public static void init() {
        GameRegistry.registerBlock(stargateBlock, BlockStargate.ItemBlockStargate.class, "default_stargate_block");

        GameRegistry.registerBlock(legacyStargateBlockAncient, BlockStargate.ItemBlockStargate.class, "legacy_ancient_stargate_block");
        GameRegistry.registerBlock(legacyStargateBlockEoh, BlockStargate.ItemBlockStargate.class, "legacy_eoh_stargate_block");
        GameRegistry.registerBlock(legacyStargateBlockInfinity, BlockStargate.ItemBlockStargate.class, "legacy_infinity_stargate_block");
        GameRegistry.registerBlock(legacyStargateBlockSpacetime, BlockStargate.ItemBlockStargate.class, "legacy_spacetime_stargate_block");

        GameRegistry
            .registerBlock(stargateControllerBlock, BlockStargate.ItemBlockStargate.class, "stargate_controller");
        GameRegistry.registerBlock(formedGateFakeBlock, "formed_stargate");
        GameRegistry.registerBlock(dialingDeviceBlock, "dialing_device");
    }
}
