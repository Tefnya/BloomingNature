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
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.level.material.Fluids;
import org.jetbrains.annotations.NotNull;

public class SnowyFirLeavesBlock extends LeavesBlock {
    public static final BooleanProperty SNOWY = BooleanProperty.create("snowy");
    public static final BooleanProperty WATERLOGGED = BooleanProperty.create("waterlogged");

    public SnowyFirLeavesBlock(Properties properties) {
        super(properties);
        this.registerDefaultState(this.stateDefinition.any().setValue(DISTANCE, 7).setValue(PERSISTENT, false).setValue(WATERLOGGED, false).setValue(SNOWY, false));
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
    public BlockState getStateForPlacement(BlockPlaceContext ctx) {
        Level level = ctx.getLevel();
        BlockPos pos = ctx.getClickedPos();
        FluidState fluidstate = level.getFluidState(pos);
        return this.defaultBlockState().setValue(DISTANCE, 7).setValue(PERSISTENT, true).setValue(SNOWY, shouldBeSnowy(level, pos)).setValue(WATERLOGGED, fluidstate.getType() == Fluids.WATER);
    }

    @Override
    public void randomTick(BlockState state, ServerLevel world, BlockPos pos, RandomSource random) {
        int newDistance = updateDistance(world, pos);
        boolean newSnowy = shouldBeSnowy(world, pos);
        if (newDistance >= 7 && !state.getValue(PERSISTENT) && !state.getValue(SNOWY)) {
            world.removeBlock(pos, false);
            return;
        }
        if (state.getValue(DISTANCE) != newDistance || state.getValue(SNOWY) != newSnowy) {
            state = state.setValue(DISTANCE, newDistance).setValue(SNOWY, newSnowy);
            world.setBlock(pos, state, 3);
        }
        if (!state.getValue(PERSISTENT)) {
            world.scheduleTick(pos, this, 0);
        }
    }

    @Override
    public @NotNull BlockState updateShape(BlockState state, Direction direction, BlockState neighborState, LevelAccessor world, BlockPos pos, BlockPos neighborPos) {
        if (state.getValue(WATERLOGGED)) {
            world.scheduleTick(pos, Fluids.WATER, Fluids.WATER.getTickDelay(world));
        }
        if (direction == Direction.UP && state.getValue(SNOWY) != shouldBeSnowy(world, pos)) {
            state = state.setValue(SNOWY, shouldBeSnowy(world, pos));
        }
        if (!state.getValue(PERSISTENT)) {
            world.scheduleTick(pos, this, 0);
        }
        return super.updateShape(state, direction, neighborState, world, pos, neighborPos);
    }

    private int updateDistance(Level world, BlockPos pos) {
        int minDistance = 7;
        for (BlockPos neighborPos : BlockPos.betweenClosed(pos.offset(-1, -1, -1), pos.offset(1, 1, 1))) {
            BlockState neighborState = world.getBlockState(neighborPos);
            if (neighborState.is(BlockTags.LOGS)) return 1;
            if (neighborState.getBlock() instanceof LeavesBlock && neighborState.hasProperty(DISTANCE)) {
                int candidate = neighborState.getValue(DISTANCE) + 1;
                if (candidate < minDistance) {
                    minDistance = candidate;
                    if (minDistance == 1) break;
                }
            }
        }
        return minDistance;
    }

    @Override
    public @NotNull FluidState getFluidState(BlockState state) {
        return state.getValue(WATERLOGGED) ? Fluids.WATER.getSource(false) : super.getFluidState(state);
    }
}
