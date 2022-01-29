package net.darktree.stylishoccult.blocks.runes.trigger;

import net.darktree.stylishoccult.blocks.entities.RuneBlockEntity;
import net.darktree.stylishoccult.blocks.runes.EntryRuneBlock;
import net.darktree.stylishoccult.script.components.RuneExceptionType;
import net.minecraft.block.BlockState;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class ClockRuneBlock extends EntryRuneBlock {

    private final int timeout;

    public ClockRuneBlock(String name, int timeout) {
        super(name);
        this.timeout = timeout;
    }

    @Override
    public void onBlockAdded(BlockState state, World world, BlockPos pos, BlockState oldState, boolean notify) {
        onDelayEnd(world, pos);
    }

    @Override
    protected void onDelayEnd( World world, BlockPos pos ) {
        world.getBlockTickScheduler().schedule(pos, world.getBlockState(pos).getBlock(), getDelayLength());
        RuneBlockEntity entity = getEntity(world, pos);

        try {
            if( !entity.hasMeta() || updateTime(entity) ) {
                emit(world, pos);
            }
        }catch (Exception exception){
            throw RuneExceptionType.INVALID_METADATA.get();
        }
    }

    @Override
    protected void emit(World world, BlockPos pos) {
        RuneBlockEntity entity = getEntity(world, pos);

        NbtCompound tag = new NbtCompound();
        tag.putInt("time", timeout);
        entity.setMeta(tag);
        super.emit(world, pos);
    }

    private boolean updateTime(RuneBlockEntity entity) {
        NbtCompound tag = entity.getMeta();
        int time = tag.getInt("time");
        if( time <= 0 ) {
            entity.setMeta(null);
            return true;
        }else{
            tag.putInt("time", time - 1);
            entity.setMeta(tag);
            return false;
        }
    }

}