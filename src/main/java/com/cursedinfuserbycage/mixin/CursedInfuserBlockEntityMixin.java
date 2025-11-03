package com.cursedinfuserbycage.mixin;

import com.Polarice3.Goety.common.blocks.entities.CursedInfuserBlockEntity;
import com.Polarice3.Goety.common.blocks.ModBlocks;
import com.Polarice3.Goety.common.blocks.entities.CursedCageBlockEntity;
import com.cursedinfuserbycage.ICursedInfuserBlockEntityAccessor;
import com.cursedinfuserbycage.config.ModConfig;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.state.BlockState;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.ModifyVariable;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

/**
 * Mixin 修改 CursedInfuserBlockEntity，使其能够检测并使用诅咒之笼
 */
@Mixin(CursedInfuserBlockEntity.class)
public class CursedInfuserBlockEntityMixin implements ICursedInfuserBlockEntityAccessor {
    
    @Shadow
    private int[] cookingProgress;
    
    private CursedCageBlockEntity cachedCageTile = null;
    private int consumeTickCounter = 0;
    private boolean wasWorking = false; // 记录上一tick是否有配方在工作
    
    /**
     * 在 tick() 方法中，修改 flag 变量以支持诅咒之笼
     */
    @ModifyVariable(
        method = "tick()V",
        at = @At("STORE"),
        ordinal = 0,
        remap = false
    )
    private boolean modifyFlag(boolean flag) {
        CursedInfuserBlockEntity self = (CursedInfuserBlockEntity)(Object)this;
        
        // 如果已经检测到刷怪笼，直接返回
        if (flag) {
            return true;
        }
        
        // 检查下方是否是诅咒之笼
        if (self.getLevel() != null) {
            BlockPos belowPos = new BlockPos(
                self.getBlockPos().getX(), 
                self.getBlockPos().getY() - 1, 
                self.getBlockPos().getZ()
            );
            BlockState blockState = self.getLevel().getBlockState(belowPos);
            
            if (blockState.is(ModBlocks.CURSED_CAGE_BLOCK.get())) {
                var tileentity = self.getLevel().getBlockEntity(belowPos);
                if (tileentity instanceof CursedCageBlockEntity cageTile) {
                    this.cachedCageTile = cageTile;
                    // 检查诅咒之笼是否有灵魂能量
                    if (!cageTile.getItem().isEmpty() && cageTile.getSouls() > 0) {
                        return true;
                    }
                }
            }
        }
        return flag;
    }
    
    /**
     * 在 work() 方法末尾，根据配置的间隔消耗灵魂能量
     */
    @Inject(
        method = "work()V",
        at = @At("TAIL"),
        remap = false
    )
    private void consumeSoulEnergyOnProgress(CallbackInfo ci) {
        CursedInfuserBlockEntity self = (CursedInfuserBlockEntity)(Object)this;
        
        // 检查是否有配方在工作（cookingProgress > 0）
        boolean hasActiveRecipe = false;
        if (this.cookingProgress != null) {
            for (int progress : this.cookingProgress) {
                if (progress > 0) {
                    hasActiveRecipe = true;
                    break;
                }
            }
        }
        
        // 只有在有活动配方时才消耗能量
        if (hasActiveRecipe && self.getLevel() != null && !self.getLevel().isClientSide) {
            BlockPos belowPos = new BlockPos(
                self.getBlockPos().getX(), 
                self.getBlockPos().getY() - 1, 
                self.getBlockPos().getZ()
            );
            BlockState blockState = self.getLevel().getBlockState(belowPos);
            
            if (blockState.is(ModBlocks.CURSED_CAGE_BLOCK.get())) {
                var tileentity = self.getLevel().getBlockEntity(belowPos);
                if (tileentity instanceof CursedCageBlockEntity cageTile) {
                    this.cachedCageTile = cageTile;
                    
                    // 检测配方是否刚刚开始（从无配方变为有配方）
                    boolean justStarted = !this.wasWorking && hasActiveRecipe;
                    this.wasWorking = true;
                    
                    // 如果配方刚刚开始，立即消耗1点能量
                    if (justStarted && cageTile.getSouls() > 0) {
                        cageTile.decreaseSouls(1);
                        this.consumeTickCounter = 0; // 重置计数器
                    }
                    
                    // 根据配置的间隔消耗能量
                    int interval = ModConfig.SoulEnergyConsumeInterval.get();
                    if (interval < 1) {
                        interval = 1; // 确保最小值为1
                    }
                    
                    // 如果配方不是刚刚开始，则按间隔消耗
                    if (!justStarted) {
                        this.consumeTickCounter++;
                        if (this.consumeTickCounter >= interval) {
                            this.consumeTickCounter = 0;
                            if (cageTile.getSouls() > 0) {
                                cageTile.decreaseSouls(1);
                            }
                        }
                    }
                }
            }
        } else {
            // 没有活动配方时重置状态
            this.wasWorking = false;
            this.consumeTickCounter = 0;
        }
    }
    
    @Override
    public CursedCageBlockEntity getCursedCageTile() {
        return this.cachedCageTile;
    }
}

