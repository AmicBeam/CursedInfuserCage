package com.cursedinfuserbycage;

import com.cursedinfuserbycage.config.ModConfig;
import com.cursedinfuserbycage.ponder.CursedInfuserByCagePonderPlugin;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.config.ModConfig.Type;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.createmod.ponder.foundation.PonderIndex;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

@Mod(CursedInfuserByCage.MOD_ID)
public class CursedInfuserByCage {
    public static final String MOD_ID = "cursedinfuserbycage";
    public static final Logger LOGGER = LogManager.getLogger();

    public CursedInfuserByCage() {
        // 注册配置
        ModLoadingContext.get().registerConfig(Type.COMMON, ModConfig.SPEC);
        
        MinecraftForge.EVENT_BUS.register(this);
        FMLJavaModLoadingContext.get().getModEventBus().addListener(this::doClientStuff);
        LOGGER.info("Cursed Infuser By Cage mod loaded!");
    }
    
    private void doClientStuff(final FMLClientSetupEvent event) {
        event.enqueueWork(() -> {
            PonderIndex.addPlugin(new CursedInfuserByCagePonderPlugin());
        });
    }
}

