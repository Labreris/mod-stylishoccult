package net.darktree.stylishoccult.blocks.runes.trigger;

import net.darktree.interference.api.LookAtEvent;
import net.darktree.stylishoccult.blocks.runes.EntryRuneBlock;
import net.minecraft.block.BlockState;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class LookAtRuneBlock extends EntryRuneBlock implements LookAtEvent {

	public LookAtRuneBlock(String name) {
		super(name);
	}

	@Override
	public void onLookAtStart(BlockState state, World world, BlockPos pos, PlayerEntity player, BlockHitResult hit) {
		emit(world, pos);
	}

}
