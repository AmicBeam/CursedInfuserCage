package com.cursedinfuserbycage.ponder;

import com.Polarice3.Goety.common.blocks.ModBlocks;
import net.createmod.ponder.api.PonderPalette;
import net.createmod.ponder.api.scene.SceneBuilder;
import net.createmod.ponder.api.scene.SceneBuildingUtil;
import net.createmod.ponder.api.scene.Selection;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.network.chat.Component;

public class PonderScenes {
    
    public static void cursedInfuserByCage(SceneBuilder builder, SceneBuildingUtil util) {
        builder.title("cursed_infuser_by_cage", Component.translatable("ponder.cursedinfuserbycage.cursed_infuser_by_cage.header").getString());
        builder.configureBasePlate(0, 0, 5);
        builder.showBasePlate();
        
        // 显示 NBT 文件中的方块（y=1 层及以上）
        builder.world().showSection(util.select().layersFrom(1), Direction.DOWN);
        
        builder.idle(20);
        
        builder.addKeyframe();
        
        // 在 [2,1,2] 创建一个 80tick 红色选框（带文本）
        BlockPos cagePos = util.grid().at(2, 1, 2);
        Selection cageSelection = util.select().position(cagePos);
        builder.overlay().showOutlineWithText(cageSelection, 80)
            .colored(PonderPalette.RED)
            .text(Component.translatable("ponder.cursedinfuserbycage.cursed_infuser_by_cage.text_1").getString())
            .placeNearTarget()
            .pointAt(util.vector().centerOf(cagePos));
        
        builder.idle(80);
        
        // 在 [2,1.5,2] 创建一个 80tick 文本
        builder.overlay().showText(80)
            .text(Component.translatable("ponder.cursedinfuserbycage.cursed_infuser_by_cage.text_2").getString())
            .pointAt(util.vector().of(2, 1.5, 2));
        
        builder.idle(80);
        
        builder.addKeyframe();
        
        // setBlock，把诅咒注入器替换为阴影注入器（带破坏粒子效果）
        BlockPos infuserPos = util.grid().at(2, 2, 2);
        builder.world().setBlock(infuserPos, ModBlocks.GRIM_INFUSER.get().defaultBlockState(), true);
        
        // 在 [2,2,2] 创建一个 80tick 红色选框（带文本）
        Selection infuserSelection = util.select().position(infuserPos);
        builder.overlay().showOutlineWithText(infuserSelection, 80)
            .colored(PonderPalette.RED)
            .text(Component.translatable("ponder.cursedinfuserbycage.cursed_infuser_by_cage.text_3").getString())
            .placeNearTarget()
            .pointAt(util.vector().centerOf(infuserPos));
        
        builder.idle(80);
        builder.markAsFinished();
    }
}

