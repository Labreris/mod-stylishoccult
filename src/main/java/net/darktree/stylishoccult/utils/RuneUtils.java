package net.darktree.stylishoccult.utils;

import net.darktree.stylishoccult.items.ModItems;
import net.darktree.stylishoccult.script.components.RuneException;
import net.minecraft.block.Block;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class RuneUtils {

    public static final int COLOR_0 = 90 << 16;
    public static final int COLOR_1 = 115 << 16;
    public static final int COLOR_2 = 140 << 16;
    public static final int COLOR_3 = 165 << 16;

    public static void createErrorReport(RuneException exception, World world, BlockPos pos) {
        NbtCompound tag = new NbtCompound();
        tag.putString("error", exception.getMessage());
        tag.putInt("x", pos.getX());
        tag.putInt("y", pos.getY());
        tag.putInt("z", pos.getZ());

        ItemStack stack = new ItemStack(ModItems.RUNE_ERROR_REPORT, 1);
        stack.setNbt(tag);
        Block.dropStack(world, pos, stack);
    }

    public static int getTint( int index ) {
        return switch (index) {
            case 0 -> COLOR_0;
            case 1 -> COLOR_1;
            case 2 -> COLOR_2;
            case 3 -> COLOR_3;
            default -> 0;
        };
    }

}
