package com.cursedinfuserbycage.config;

import com.electronwill.nightconfig.core.file.CommentedFileConfig;
import com.electronwill.nightconfig.core.io.WritingMode;
import net.minecraftforge.common.ForgeConfigSpec;

import java.io.File;

/**
 * 模组配置类
 */
public class ModConfig {
    
    public static final ForgeConfigSpec.Builder BUILDER = new ForgeConfigSpec.Builder();
    public static final ForgeConfigSpec SPEC;
    
    public static final ForgeConfigSpec.ConfigValue<Integer> SoulEnergyConsumeInterval;
    
    static {
        BUILDER.push("Cursed Infuser By Cage");
        SoulEnergyConsumeInterval = BUILDER.comment("How many ticks between each soul energy consumption when the infuser is working. Default: 20 (1 second), Minimum: 1")
                .defineInRange("soulEnergyConsumeInterval", 20, 1, Integer.MAX_VALUE);
        BUILDER.pop();
        SPEC = BUILDER.build();
    }
    
    public static void loadConfig(ForgeConfigSpec config, String path) {
        final CommentedFileConfig file = CommentedFileConfig.builder(new File(path))
                .sync()
                .autosave()
                .writingMode(WritingMode.REPLACE)
                .build();
        file.load();
        config.setConfig(file);
    }
}

