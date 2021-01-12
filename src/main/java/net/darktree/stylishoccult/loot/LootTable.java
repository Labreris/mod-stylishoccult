package net.darktree.stylishoccult.loot;

import net.darktree.stylishoccult.loot.entry.*;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Identifier;

import java.security.InvalidParameterException;
import java.util.ArrayList;
import java.util.Random;

public class LootTable {

    private LootTable context;

    private final ArrayList<AbstractEntry> entries = new ArrayList<>();
    private int amountMin = 0;
    private boolean dropForCreative = false;

    public void push( LootTable context ) {
        this.context = context;
    }

    public LootTable pop() {
        if( context == null ) {
            throw new InvalidParameterException( "Unable to pop from root element!" );
        }
        return context;
    }

    public LootTable addItem( Item item ) {
        return addItem( item, 100.0F );
    }

    public LootTable addItem( ItemStack stack ) {
        return addItem( stack, 100.0F, stack.getCount(), stack.getCount() );
    }

    public LootTable addItem( Item stack, float chance ) {
        return addItem( new ItemStack( stack ), chance );
    }

    public LootTable addItem( ItemStack stack, float chance ) {
        return addItem( stack, chance, stack.getCount(), stack.getCount() );
    }

    public LootTable addItem( ItemStack stack, float chance, int min, int max ) {
        if( max > stack.getMaxCount() || min < 0 ) {
            throw new InvalidParameterException( "Invalid stack size range!" );
        }

        entries.add( new ItemEntry( stack, chance, (byte) min, (byte) max ) );
        return this;
    }

    public LootTable addExperience( int min, int max ) {
        return addExperience( 100.0f, min, max );
    }

    public LootTable addExperience( float chance, int min, int max ) {
        entries.add( new ExperienceEntry( chance, min, max ) );
        return this;
    }

    public LootTable addValve( Valve valve ) {
        ValveEntry entry = new ValveEntry( valve );
        entries.add( entry );
        entry.getTable().push( this );
        return entry.getTable();
    }

    public LootTable addGenerator( Generator generator ) {
        GeneratorEntry entry = new GeneratorEntry( generator );
        entries.add( entry );
        return this;
    }

    public LootTable addCondition( Condition condition ) {
        ConditionEntry entry = new ConditionEntry( condition );
        entries.add( entry );
        entry.getTable().push( this );
        entry.getElseTable().push( this );
        return entry.getTable();
    }

    public LootTable addElse() {
        AbstractEntry entry = entries.get( entries.size() - 1 );
        if( entry instanceof ConditionEntry ) {
            return ((ConditionEntry) entry).getElseTable();
        }

        throw new InvalidParameterException( "addElse must only exists after a addCondition!" );
    }

    public LootTable addTable() {
        return addTable( new LootTable() );
    }

    public LootTable addTable( LootTable table ) {
        return addTable( table, 100.0F );
    }

    public LootTable addTable( LootTable table, float chance ) {
        TableEntry entry = new TableEntry( table, chance );
        entries.add( entry );
        entry.getTable().push( this );
        return entry.getTable();
    }

    public LootTable minimum(int min ) {
        amountMin = min;
        return this;
    }

    public LootTable applyForCreative() {
        dropForCreative = true;
        return this;
    }

    public BakedLootTable build( Identifier id ) {
        return BakedLootTable.bake( id, this );
    }

    public BakedLootTable build( String name ) {
        return build( new Identifier( name ) );
    }

    private int countItems( ArrayList<ItemStack> stacks ) {
        int c = 0;
        for( ItemStack stack : stacks ) {
            c += stack.getCount();
        }
        return c;
    }

    public ArrayList<ItemStack> getLoot( Random random, LootContext context ) {
        if( context.getPlayer() != null && context.getPlayer().isCreative() && !dropForCreative ) {
            return LootManager.getEmpty();
        }

        int count = 0;
        ArrayList<ItemStack> itemStacks = new ArrayList<>();

        do {
            for (AbstractEntry entry : entries) {
                ArrayList<ItemStack> returnedStacks = entry.getLoot(random, context);
                count += (amountMin > 0) ? countItems(returnedStacks) : 0;
                itemStacks.addAll(returnedStacks);
            }
        } while( count < amountMin );

        return itemStacks;
    }

}