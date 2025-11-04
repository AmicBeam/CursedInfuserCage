package com.cursedinfuserbycage;

import com.cursedinfuserbycage.config.ModConfig;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.ModList;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.config.ModConfig.Type;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
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
        
        // 只有当 Ponder 模组存在时才注册 Ponder 场景
        if (ModList.get().isLoaded("ponder")) {
            FMLJavaModLoadingContext.get().getModEventBus().addListener(this::doClientStuff);
            LOGGER.info("Ponder detected, enabling Ponder integration");
        }
        
        LOGGER.info("Cursed Infuser By Cage mod loaded!");
    }
    
    private void doClientStuff(final FMLClientSetupEvent event) {
        // 使用反射动态加载 Ponder 插件，避免编译时依赖
        event.enqueueWork(() -> {
            try {
                Class<?> ponderIndexClass = Class.forName("net.createmod.ponder.foundation.PonderIndex");
                Class<?> pluginClass = Class.forName("com.cursedinfuserbycage.ponder.CursedInfuserByCagePonderPlugin");
                
                Object plugin = pluginClass.getDeclaredConstructor().newInstance();
                var addPluginMethod = ponderIndexClass.getMethod("addPlugin", Class.forName("net.createmod.ponder.api.registration.PonderPlugin"));
                addPluginMethod.invoke(null, plugin);
                
                LOGGER.info("Successfully registered Ponder plugin");
            } catch (Exception e) {
                LOGGER.warn("Failed to register Ponder plugin (this is normal if Ponder is not installed): " + e.getMessage());
            }
        });
    }
}

