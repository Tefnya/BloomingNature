package net.satisfy.bloomingnature.core.block;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.tags.BlockTags;
import net.minecraft.util.RandomSource;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.LeavesBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.satisfy.bloomingnature.core.registry.ObjectRegistry;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class SnowyFirLeavesBlock extends LeavesBlock {
    public static final BooleanProperty SNOWY = BooleanProperty.create("snowy");

    public SnowyFirLeavesBlock(Properties properties) {
        super(properties);
        this.registerDefaultState(this.stateDefinition.any().setValue(DISTANCE, 7).setValue(PERSISTENT, false).setValue(SNOWY, false));
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        super.createBlockStateDefinition(builder);
        builder.add(SNOWY);
    }

    private static boolean shouldBeSnowy(LevelReader level, BlockPos pos) {
        return level.getBlockState(pos.above()).is(Blocks.SNOW);
    }

    @Override
    public @NotNull BlockState getStateForPlacement(BlockPlaceContext ctx) {
        return this.defaultBlockState().setValue(SNOWY, shouldBeSnowy(ctx.getLevel(), ctx.getClickedPos()));
    }

    @Override
    public void randomTick(BlockState state, ServerLevel world, BlockPos pos, RandomSource random) {
        if (!state.getValue(PERSISTENT)) {
            int distance = updateDistance(world, pos);
            if (distance >= 7) {
                world.removeBlock(pos, false);
                return;
            }
            world.setBlock(pos, state.setValue(DISTANCE, distance), 3);
        }

        boolean snowy = shouldBeSnowy(world, pos);
        if (state.getValue(SNOWY) != snowy) {
            world.setBlock(pos, state.setValue(SNOWY, snowy), 3);
        }
    }

    @Override
    public @NotNull BlockState updateShape(BlockState state, Direction direction, BlockState neighborState, LevelAccessor world, BlockPos pos, BlockPos neighborPos) {
        if (direction == Direction.UP) {
            boolean snowy = shouldBeSnowy(world, pos);
            if (state.getValue(SNOWY) != snowy) {
                return state.setValue(SNOWY, snowy);
            }
        }
        return super.updateShape(state, direction, neighborState, world, pos, neighborPos);
    }


    private int updateDistance(Level world, BlockPos pos) {
        int minDistance = 7;
        for (BlockPos neighbor : BlockPos.betweenClosed(pos.offset(-1, -1, -1), pos.offset(1, 1, 1))) {
            BlockState neighborState = world.getBlockState(neighbor);
            if (neighborState.is(BlockTags.LOGS)) {
                return 1;
            }
            if (neighborState.is(ObjectRegistry.FIR_LEAVES.get()) && neighborState.hasProperty(DISTANCE)) {
                minDistance = Math.min(minDistance, neighborState.getValue(DISTANCE) + 1);
            }
        }
        return minDistance;
    }

}
