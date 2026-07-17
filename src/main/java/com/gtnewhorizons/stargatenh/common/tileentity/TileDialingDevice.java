package com.gtnewhorizons.stargatenh.common.tileentity;

import java.util.Random;

import net.minecraft.tileentity.TileEntity;

import com.cleanroommc.modularui.api.IGuiHolder;
import com.cleanroommc.modularui.api.drawable.IKey;
import com.cleanroommc.modularui.drawable.DynamicDrawable;
import com.cleanroommc.modularui.factory.PosGuiData;
import com.cleanroommc.modularui.screen.ModularPanel;
import com.cleanroommc.modularui.screen.UISettings;
import com.cleanroommc.modularui.value.sync.BooleanSyncValue;
import com.cleanroommc.modularui.value.sync.IntSyncValue;
import com.cleanroommc.modularui.value.sync.InteractionSyncHandler;
import com.cleanroommc.modularui.value.sync.PanelSyncManager;
import com.cleanroommc.modularui.widgets.ButtonWidget;
import com.cleanroommc.modularui.widgets.CycleButtonWidget;
import com.cleanroommc.modularui.widgets.layout.Row;
import com.gtnewhorizon.gtnhlib.blockpos.BlockPos;
import com.gtnewhorizons.stargatenh.client.ui.UITextures;
import com.gtnewhorizons.stargatenh.common.util.StargateAddress;
import com.gtnewhorizons.stargatenh.common.util.StargateRegistry;

public class TileDialingDevice extends TileEntity implements IGuiHolder<PosGuiData> {

    private final int[] address = { 0, 0, 0, 0, 0, 0, 0 };
    private boolean hasAddress = false;
    TileStargateController controller;

    public void connectGate() {
        if (worldObj.getTileEntity(xCoord + 4, yCoord, zCoord) instanceof TileStargateController c) controller = c;
        else if (worldObj.getTileEntity(xCoord - 4, yCoord, zCoord) instanceof TileStargateController c) controller = c;
        else if (worldObj.getTileEntity(xCoord, yCoord, zCoord + 4) instanceof TileStargateController c) controller = c;
        else if (worldObj.getTileEntity(xCoord, yCoord, zCoord - 4) instanceof TileStargateController c) controller = c;
    }

    @Override
    public ModularPanel buildUI(PosGuiData data, PanelSyncManager syncManager, UISettings settings) {
        ModularPanel panel = new ModularPanel("panel").size(176, 100);
        if (controller == null) {
            panel.child(
                IKey.str("Link Failed!")
                    .asWidget()
                    .marginLeft(5)
                    .marginRight(5)
                    .marginTop(5)
                    .marginBottom(-15));
        } else if (hasAddress) buildDialingUI(panel, syncManager);
        else buildSetupUI(panel, syncManager);

        return panel;
    }

    private void buildSetupUI(ModularPanel panel, PanelSyncManager syncManager) {
        StargateRegistry reg = StargateRegistry.get(worldObj);
        BooleanSyncValue isUnique = new BooleanSyncValue(() -> reg.lookup(new StargateAddress(address)) == null);

        IntSyncValue[] chevrons = new IntSyncValue[7];
        for (int i = 0; i < chevrons.length; i++) {
            int fi = i;
            chevrons[i] = new IntSyncValue(() -> address[fi], x -> address[fi] = x);
            syncManager.syncValue("chevron" + i, chevrons[i]);
        }

        syncManager.syncValue("isUnique", isUnique);

        panel.child(
            IKey.str("Set Address for this Stargate")
                .asWidget()
                .marginLeft(5)
                .marginRight(5)
                .marginTop(5)
                .marginBottom(-15));

        Row sigils = new Row();
        panel.child(
            sigils.size(156, 16)
                .marginTop(20)
                .marginLeft(14)
                .childPadding(6));

        for (int i = 0; i < chevrons.length; i++) {
            int fi = i;
            sigils.child(
                new CycleButtonWidget().syncHandler("chevron" + fi)
                    .size(16, 16)
                    .background(UITextures.SIGIL_BG)
                    .hoverBackground(UITextures.SIGIL_BG_ACTIVE)
                    .length(16)
                    .overlay(new DynamicDrawable(() -> UITextures.getSigil(chevrons[fi].getIntValue()))));
        }

        panel.child(
            new ButtonWidget<>().marginTop(48)
                .marginLeft(8)
                .size(18, 18)
                .tooltip(t -> t.add("Generate a random unused address"))
                .overlay(UITextures.OVERLAY_RANDOM)
                .syncHandler(new InteractionSyncHandler().setOnMousePressed(mouseData -> {
                    Random rng = new Random();
                    do {
                        for (IntSyncValue chevron : chevrons) {
                            chevron.setIntValue(rng.nextInt(16));
                        }
                        isUnique.updateCacheFromSource(false);
                    } while (!isUnique.getBoolValue());
                })));

        panel.child(
            IKey.dynamic(() -> isUnique.getBoolValue() ? "Address is available" : "Address already in use")
                .asWidget()
                .marginLeft(36)
                .marginTop(52));

        panel.child(
            new ButtonWidget<>().marginTop(48)
                .marginLeft(148)
                .size(18, 18)
                .tooltip(t -> t.add("Lock in stargate address"))
                .setEnabledIf(ignored -> isUnique.getBoolValue())
                .overlay(UITextures.OVERLAY_CHECK)
                .syncHandler(new InteractionSyncHandler().setOnMousePressed(mouseData -> {
                    reg.register(new StargateAddress(address), new BlockPos(xCoord, yCoord, zCoord));
                    hasAddress = true;
                    panel.closeIfOpen();
                })));
    }

    private void buildDialingUI(ModularPanel panel, PanelSyncManager syncManager) {
        panel.child(
            IKey.str("Stargate operational.")
                .asWidget()
                .marginLeft(5)
                .marginRight(5)
                .marginTop(5)
                .marginBottom(-15));
    }
}
