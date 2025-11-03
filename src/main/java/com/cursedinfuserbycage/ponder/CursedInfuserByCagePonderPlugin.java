package com.cursedinfuserbycage.ponder;

import com.Polarice3.Goety.common.blocks.ModBlocks;
import com.cursedinfuserbycage.CursedInfuserByCage;
import net.createmod.ponder.api.level.PonderLevel;
import net.createmod.ponder.api.registration.IndexExclusionHelper;
import net.createmod.ponder.api.registration.PonderPlugin;
import net.createmod.ponder.api.registration.PonderSceneRegistrationHelper;
import net.createmod.ponder.api.registration.PonderTagRegistrationHelper;
import net.createmod.ponder.api.registration.SharedTextRegistrationHelper;
import net.minecraft.resources.ResourceLocation;

public class CursedInfuserByCagePonderPlugin implements PonderPlugin {
    
    @Override
    public String getModId() {
        return CursedInfuserByCage.MOD_ID;
    }
    
    @Override
    public void registerScenes(PonderSceneRegistrationHelper<ResourceLocation> helper) {
        // 为诅咒注入器、阴影注入器和诅咒之笼都注册同一个场景
        helper.addStoryBoard(
            ModBlocks.CURSED_INFUSER.getId(),
            "cursed_infuser_by_cage",
            PonderScenes::cursedInfuserByCage
        );
        helper.addStoryBoard(
            ModBlocks.GRIM_INFUSER.getId(),
            "cursed_infuser_by_cage",
            PonderScenes::cursedInfuserByCage
        );
        helper.addStoryBoard(
            ModBlocks.CURSED_CAGE_BLOCK.getId(),
            "cursed_infuser_by_cage",
            PonderScenes::cursedInfuserByCage
        );
    }
    
    @Override
    public void registerTags(PonderTagRegistrationHelper<ResourceLocation> helper) {
        // 不需要标签
    }
    
    @Override
    public void registerSharedText(SharedTextRegistrationHelper helper) {
        // 不需要共享文本
    }
    
    @Override
    public void onPonderLevelRestore(PonderLevel ponderLevel) {
        // 不需要特殊处理
    }
    
    @Override
    public void indexExclusions(IndexExclusionHelper helper) {
        // 不需要排除
    }
}

