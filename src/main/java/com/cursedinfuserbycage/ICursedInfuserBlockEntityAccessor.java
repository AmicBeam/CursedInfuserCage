package com.cursedinfuserbycage;

import com.Polarice3.Goety.common.blocks.entities.CursedCageBlockEntity;

/**
 * 访问器接口，用于在 Mixin 中访问诅咒之笼的引用
 */
public interface ICursedInfuserBlockEntityAccessor {
    CursedCageBlockEntity getCursedCageTile();
}

