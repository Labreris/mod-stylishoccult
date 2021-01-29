package net.darktree.stylishoccult.blocks.runes;

import net.darktree.stylishoccult.network.Network;
import net.darktree.stylishoccult.script.RunicScript;
import net.darktree.stylishoccult.script.components.RuneException;
import net.minecraft.block.BlockState;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundCategory;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class PlaceRuneBlock extends ActorRuneBlock {

    private final BlockState state;
    private final int range;

    public PlaceRuneBlock(String name, BlockState state, int range) {
        super(name);
        this.state = state;
        this.range = range;
    }

    @Override
    public void apply(RunicScript script, World world, BlockPos pos) {
        try {
            int x = Math.round( (float) script.getStack().pull() );
            int y = Math.round( (float) script.getStack().pull() );
            int z = Math.round( (float) script.getStack().pull() );

            BlockPos target = pos.add(x, y, z);

            if( !target.isWithinDistance( pos, range ) ) {
                throw new RuneException("invalid_argument");
            }

            BlockState targetState = world.getBlockState(target);

            if( (targetState.isAir() || targetState.getMaterial().isReplaceable()) && targetState.getBlock() != state.getBlock() ) {
                world.setBlockState( target, state );
                world.playSound(null, target, state.getBlock().getSoundGroup(state).getPlaceSound(), SoundCategory.BLOCKS, 0.9f, 1.0f);
                Network.ASH_PACKET.send( target, (ServerWorld) world );

                if( !targetState.isAir() ) {
                    world.playSound(null, target, targetState.getBlock().getSoundGroup(targetState).getBreakSound(), SoundCategory.BLOCKS, 0.9f, 1.0f);
                }
            }
        }catch (Exception exception) {
            throw new RuneException("invalid_argument_count");
        }

        super.apply(script);
    }


}