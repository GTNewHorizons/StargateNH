package com.gtnewhorizons.stargatenh.client.ui;

import static com.cleanroommc.modularui.utils.MathUtils.clamp;

import com.cleanroommc.modularui.drawable.UITexture;
import com.gtnewhorizons.stargatenh.StargateNH;

public class UITextures {

    public static final UITexture OVERLAY_CHECK = UITexture.fullImage(StargateNH.MODID, "gui/checkmark");
    public static final UITexture OVERLAY_RANDOM = UITexture.fullImage(StargateNH.MODID, "gui/random");

    public static final UITexture SIGIL_BG = UITexture.fullImage(StargateNH.MODID, "gui/sigil_bg");
    public static final UITexture SIGIL_BG_ACTIVE = UITexture.fullImage(StargateNH.MODID, "gui/sigil_bg_active");

    private static final UITexture[] SIGILS = new UITexture[16];

    static {
        for (int i = 0; i < 16; i++) {
            SIGILS[i] = UITexture.fullImage(StargateNH.MODID, "gui/sigil_" + i);
        }
    }

    public static UITexture getSigil(int i) {
        return SIGILS[clamp(i, 0, 15)];
    }
}
