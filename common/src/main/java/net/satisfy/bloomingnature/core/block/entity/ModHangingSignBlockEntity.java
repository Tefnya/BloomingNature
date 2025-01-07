package net.satisfy.bloomingnature.core.block.entity;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.satisfy.bloomingnature.core.registry.EntityTypeRegistry;


public class ModHangingSignBlockEntity extends ModSignBlockEntity {

    public ModHangingSignBlockEntity(BlockPos blockPos, BlockState blockState) {
        super(EntityTypeRegistry.MOD_HANGING_SIGN.get(), blockPos, blockState);
    }

    @Override
    public BlockEntityType<?> getType() {
        return EntityTypeRegistry.MOD_HANGING_SIGN.get();
    }
}
