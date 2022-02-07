package net.darktree.stylishoccult.blocks.runes.flow;

import net.darktree.stylishoccult.blocks.runes.TransferRuneBlock;
import net.darktree.stylishoccult.script.engine.Script;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.world.World;

public class RedstoneGateRuneBlock extends TransferRuneBlock {

    public RedstoneGateRuneBlock(String name) {
        super(name);
    }

    @Override
    public Direction[] getDirections(World world, BlockPos pos, Script script) {
        if( world.isReceivingRedstonePower(pos) ) {
            return super.getDirections(world, pos, script);
        }else{
            return new Direction[] {};
        }
    }

}
