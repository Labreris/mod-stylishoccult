package net.darktree.stylishoccult.blocks.runes;

import net.darktree.stylishoccult.script.RunicScript;
import net.darktree.stylishoccult.script.components.RuneException;
import net.darktree.stylishoccult.script.components.RuneInstance;
import net.minecraft.nbt.CompoundTag;

public class NumberRuneBlock extends TransferRuneBlock {

    public final char value;

    public NumberRuneBlock(String name, char value) {
        super(name);
        this.value = value;
    }

    public RuneInstance getInstance() {
        NumberRuneInstance instance = new NumberRuneInstance(this);
        instance.raw += value;
        return instance;
    }

    public static class NumberRuneInstance extends RuneInstance {

        String raw = "";

        public NumberRuneInstance(RuneBlock rune) {
            super(rune);
        }

        public CompoundTag toTag() {
            CompoundTag tag = new CompoundTag();
            tag.putString("raw", raw);
            return super.toTag( tag );
        }

        public void fromTag( CompoundTag tag ) {
            raw = tag.getString("raw");
        }

        @Override
        public boolean push(RunicScript script, RuneInstance instance ) {

            if( raw.length() > 16 ) {
                throw new RuneException("number_too_long");
            }

            if( instance instanceof NumberRuneInstance ) {
                raw += ((NumberRuneBlock) instance.rune).value;
                return true;
            }

            try {
                script.value = Integer.parseInt(raw, 6);
            }catch (Exception e){
                throw new RuneException("invalid_number");
            }

            return false;
        }

    }

}
