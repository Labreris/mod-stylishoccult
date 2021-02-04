package net.darktree.stylishoccult.blocks.occult;

import net.darktree.stylishoccult.blocks.ModBlocks;
import net.darktree.stylishoccult.utils.*;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Material;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.BlockSoundGroup;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.BooleanProperty;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.world.BlockView;
import net.minecraft.world.World;

import java.util.Random;

public class FossilizedFleshBlock extends SimpleBlock implements ImpureBlock {

    public static final BooleanProperty STABLE = BooleanProperty.of("stable");

    public FossilizedFleshBlock() {
        super(RegUtil.settings( Material.ORGANIC_PRODUCT, BlockSoundGroup.HONEY, 0.8F, 0.8F, true ).slipperiness(0.8f).ticksRandomly());
        setDefaultState( getDefaultState().with(STABLE, false) );
    }

    public boolean isPosValid(BlockView world, BlockPos origin) {
        for( Direction direction : Direction.values() ){
            BlockState state = world.getBlockState( origin.offset( direction ) );
            Block block = state.getBlock();
            if( block != ModBlocks.DEFAULT_FLESH && block != ModBlocks.BONE_FLESH ) return false;
        }
        return true;
    }

    @Override
    protected void appendProperties(StateManager.Builder<Block, BlockState> builder) {
        builder.add(STABLE);
    }

    @Override
    public boolean hasRandomTicks(BlockState state) {
        return !state.get(STABLE);
    }

    @Override
    public void randomTick(BlockState state, ServerWorld world, BlockPos pos, Random random) {
        if( RandUtils.getBool(33.0f) ) {
            if( BlockUtils.countInArea(world, pos, ModBlocks.BONE_FLESH, 4) < 5 ) {
                BlockPos target = pos.offset( RandUtils.getEnum(Direction.class) );
                BlockState targetState = world.getBlockState( target );

                if( targetState.getBlock() == ModBlocks.DEFAULT_FLESH && isPosValid(world, target) ) {
                    world.setBlockState(target, state);
                }
            }else{
                world.setBlockState(pos, state.cycle(STABLE));
            }
        }
    }

    @Override
    public void cleanse(World world, BlockPos pos, BlockState state) {
        OccultHelper.cleanseFlesh(world, pos, state);
    }

    @Override
    public int impurityLevel(BlockState state) {
        return 16;
    }

}
